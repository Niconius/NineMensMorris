package aiStuff;

import java.io.Serializable;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiRandomPlayer implements AiGeneral, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5951322946757211676L;
	private Player player;
	private Player ai;
	private Board board;
	private Piece[] aiPieces;
	private Piece[] playerPieces;
	private int[][] boardAsArray;
	
	public AiRandomPlayer(Player player, Player ai, Board board) {
		this.player=player;
		this.ai=ai;
		this.board=board;
		this.aiPieces=this.ai.getPieces();
		this.playerPieces=this.player.getPieces();
		this.boardAsArray=this.board.getBoardAsArray();
	}

	// return objekt array: what type of move, old position, new position, did remove / mill happened, old (!) position of piece which got removed, did opponent / player lose
	public Object[] aiTurn() {
		if(ai.getActiveState()==true) {
			// Placing Phase
			if(ai.inPlacePhase()==true) {
				int i=0;
				for(i=0;i<9;i++) {
					if(aiPieces[i].getPosition()==0) {
						break;
					}
				}
				boolean checking=false;
				int random=0;
				while(checking==false) {
					random = (int) ((Math.random()*24)+1);
					if(board.isPlacePossible(random)==true) {
						checking=true;
					}
				}
				
				ai.placePiece(aiPieces[i], random);
				
				// mill check (and win check) + remove
				return afterTurnCheck(aiPieces[i], 0, 0);
			}
			
			// Moves normally
			else if(ai.hasToJump()==false) {
				boolean whichPiece = false;
				int random=0;
				while(whichPiece==false) {
					random = (int) (Math.random()*9);
					if(aiPieces[random].getPosition()>0 && board.canPieceMove(aiPieces[random])==true) {
						whichPiece=true;
					}
				}
				boolean checking=false;
				int random2=0;
				while(checking==false) {
					random2 = (int) (Math.random()*4);
					if(board.isMovePossible(random2, aiPieces[random])) {
						checking=true;
					}
				}
				int newPosition = boardAsArray[aiPieces[random].getPosition()][random2];
				int oldPosition = aiPieces[random].getPosition();
				ai.movePiece(newPosition, aiPieces[random]);
				
				// mill check (and win check) + remove
				return afterTurnCheck(aiPieces[random], oldPosition, 1);
			}
			
			// Has to jump
			else {
				boolean whichPiece = false;
				int random=0;
				while(whichPiece==false) {
					random = (int) (Math.random()*9);
					if(aiPieces[random].getPosition()>0) {
						whichPiece=true;
					}
				}
				boolean checking=false;
				int random2=0;
				while(checking==false) {
					random2 = (int) ((Math.random()*24)+1);
					if(board.isPlacePossible(random2)==true) {
						checking=true;
					}
				}
				int oldPosition = aiPieces[random].getPosition();
				ai.placePiece(aiPieces[random], random2);
				
				// mill check (and win check) + remove
				return afterTurnCheck(aiPieces[random], oldPosition, 2);
			}
		}
		Object[] arr = {-1}; 
		return arr;
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
		if(ai.checkForMill(selectedPiece)==true) {
			Object[] whatHappend = removeAfterMill();
			if((boolean) whatHappend[1]==true) {
				Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),true,whatHappend[0],true};
				return arr;
			}
			else {
				Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),true,whatHappend[0],false};
				return arr;
			}
		}
		else {
			Object[] arr = {typeOfTurn,oldPosition,selectedPiece.getPosition(),false,null,false};
			return arr;
		}
	}
	
	private Object[] removeAfterMill() {
		int random=0;
		Piece selectedPiece=null;
		boolean active=true;
		while(active==true) {
			random = (int) (Math.random()*9);
			selectedPiece = playerPieces[random];
			if((selectedPiece.getIsInMill()==false || player.areAllActiveStoneInMill()==true) && selectedPiece.getPosition()>0) {
				active=false;
			}
		}
		int oldPosition = selectedPiece.getPosition();
		player.removePieceFromThisPlayer(selectedPiece);
		for(int j=0;j<9;j++) {
			player.checkForMill(playerPieces[j]);
		}
		boolean completelyBlocked = true;
		for(int j=0;j<9;j++) {
			if(board.canPieceMove(playerPieces[j])==true || player.hasToJump()==true) {
				completelyBlocked=false;
				break;
			}
		}
		if(completelyBlocked==true || player.didPlayerLose()==true) {
			Object[] arr = {oldPosition,true};
			return arr;
		}
		else {
			Object[] arr = {oldPosition,false};
			return arr;
		}
	}
}
