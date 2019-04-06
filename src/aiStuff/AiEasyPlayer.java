package aiStuff;

import java.io.IOException;
import java.io.Serializable;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiEasyPlayer implements AiGeneral, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -997919944940729329L;
	private Player player;
	private Player ai;
	private Board board;
	private Piece[] aiPieces;
	private Piece[] playerPieces;
	private int[][] boardAsArray;
	
	private Player playerAsMiddleAi;
	private Player aiAsRandom;
	private Board simulatedBoard;
	private AiGeneral aiMiddle;
	
	private Object[] highestEvaluation;    // Points, Piece, OldPos, NewPos, RemovedPiece
	private Object[] hEva1, hEva2, hEva3, hEva4, hEva5;    
	private Piece theCurrentPiece;
	private int newPosFromCurrent;
	private boolean isThereRemove;
	private Piece removingPiece;
	private int oldPosOfRemoved;
	private boolean endedByRemove;
	private boolean end1ByAi,end2ByAi,end3ByAi,end1ByPlayer,end2ByPlayer,end3ByPlayer;
	
	private int counter;
	
	public AiEasyPlayer(Player player, Player ai, Board board) {
		this.player=player;
		this.ai=ai;
		this.board=board;
		this.aiPieces=this.ai.getPieces();
		this.playerPieces=this.player.getPieces();
		this.boardAsArray=this.board.getBoardAsArray();
	}
	
	@Override
	public Object[] aiTurn() {
		counter=0;
		Thread t1 = new AiEasyCalcThread(1,player,ai,board,this);
		Thread t2 = new AiEasyCalcThread(2,player,ai,board,this);
		Thread t3 = new AiEasyCalcThread(3,player,ai,board,this);
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		calcBestEva();
		if((boolean)highestEvaluation[3]==true) {
			int i=0;
			for(i=0;i<9;i++) {
				if(player.getPieces()[i].getPosition()==(int)highestEvaluation[5]) {
					break;
				}
			}
			player.removePieceFromThisPlayer(player.getPieces()[i]);
		}
		int oldPositionToKnow=((Piece) highestEvaluation[1]).getPosition();
		int i=0;
		for(i=0;i<9;i++) {
			if(ai.getPieces()[i].getPosition()==((Piece) highestEvaluation[1]).getPosition()) {
				break;
			}
		}
		ai.placePiece(ai.getPieces()[i], (int)highestEvaluation[2]);
		return afterTurnCheck(ai.getPieces()[i], oldPositionToKnow, 0);
	}
	
	
	private Object[] afterTurnCheck(Piece selectedPiece, int oldPosition, int typeOfTurn) {
		for(int j=0;j<9;j++) {
			ai.checkForMill(ai.getPieces()[j]);
		}
		boolean completelyBlocked = true;
		for(int j=0;j<9;j++) {
			if(board.canPieceMove(player.getPieces()[j])==true || player.hasToJump()==true) {
				completelyBlocked=false;
				break;
			}
		}
		if(completelyBlocked==true || player.didPlayerLose()) {
			Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),false,null,true};
			return arr;
		}
		if((boolean)highestEvaluation[3]==true) {
			if((boolean) highestEvaluation[6]==true) {
				Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),true,highestEvaluation[5],true,highestEvaluation[4]};
				return arr;
			}
			else {
				Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),true,highestEvaluation[5],false,highestEvaluation[4]};
				return arr;
			}
		}
		else {
			Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),false,null,false};
			return arr;
		}
	}
	
	public void setEva1(Object[] eva) {
		hEva1=eva;
	}
	public void setEva2(Object[] eva) {
		hEva2=eva;
	}
	public void setEva3(Object[] eva) {
		hEva3=eva;
	}
	public void setEva4(Object[] eva) {
		hEva4=eva;
	}
	public void setEva5(Object[] eva) {
		hEva5=eva;
	}
	
	private void calcBestEva() {
		if(hEva5==null && hEva4==null) {
			hEva5= new Object[1];
			hEva4= new Object[1];
			hEva4[0]=-50000000;
			hEva5[0]=-50000000;
		}
		System.out.println((int)hEva1[0] + " " + (int)hEva2[0] + " " + (int)hEva3[0]);
		if((int)hEva1[0]>=(int)hEva2[0] && (int)hEva1[0]>=(int)hEva3[0] && (int)hEva1[0]>=(int)hEva4[0] && (int)hEva1[0]>=(int)hEva5[0]) {
			highestEvaluation=hEva1;
		}
		else if((int)hEva2[0]>=(int)hEva1[0] && (int)hEva2[0]>=(int)hEva3[0] && (int)hEva2[0]>=(int)hEva4[0] && (int)hEva1[0]>=(int)hEva5[0]){
			highestEvaluation=hEva2;
		}
		else if((int)hEva3[0]>=(int)hEva1[0] && (int)hEva3[0]>=(int)hEva2[0] && (int)hEva3[0]>=(int)hEva4[0] && (int)hEva1[0]>=(int)hEva5[0]){
			highestEvaluation=hEva3;
		}
		else if((int)hEva4[0]>=(int)hEva1[0] && (int)hEva4[0]>=(int)hEva2[0] && (int)hEva4[0]>=(int)hEva3[0] && (int)hEva1[0]>=(int)hEva5[0]){
			highestEvaluation=hEva4;
		}
		else if((int)hEva5[0]>=(int)hEva1[0] && (int)hEva5[0]>=(int)hEva2[0] && (int)hEva5[0]>=(int)hEva3[0] && (int)hEva5[0]>=(int)hEva4[0]){
			highestEvaluation=hEva5;
		}
	}
}
