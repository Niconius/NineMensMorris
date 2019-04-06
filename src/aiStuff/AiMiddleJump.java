package aiStuff;

import java.io.Serializable;
import sample.Board;
import sample.Piece;
import sample.Player;

/**
 * A class that can calculate a jump with the "middle" rules
 * @author Nico
 * @version 1.0
 */
public class AiMiddleJump implements Serializable{
	
	/** Serial number */
	private static final long serialVersionUID = -6978480099767351828L;
	/** The real human as player */
	private Player player;
	/** The calculating ai as player */
	private Player ai;
	/** The board that is used */
	private Board board;
	/** Position where to jump to */
	private int whereJump;
	/** Piece that should make the jump */
	private Piece whichPiece;
	/** Remaining three pieces and if they block a mill currently */
	private Object[][] lastThreePiecesAndDoTheyBlock;
	
	/**
	 * Constructor to great a AiMiddleJump object that can calculate a jump with the "middle" rules
	 * @param player The real human as player
	 * @param ai The calculating ai as player
	 * @param board The board that is used
	 */
	public AiMiddleJump(Player player, Player ai, Board board) {
		whereJump=5000;
		whichPiece=null;
		this.player=player;
		this.ai=ai;
		this.board=board;
	}
	
	/**
	 * Goes through the implemented rules which jump the ai should do
	 * Calculates the last three pieces and if they block an opponent mill first
	 * @return Object array with index 0 the piece and index 1 the position to where to jump to
	 */
	public Object[] whereToJump() {
		calcLastThreeAndTheirBlock();
		if(closeMill()==false) {
			if(blockOpponentMill()==false) {
				if(setUpCross1()==false) {
					if(setUpCross2()==false) {
						if(preRandomJump()==false) {
							randomJump();
						}
						else {
						}
					}
					else {
					}
				}
				else {
				}
			}
			else {
			}
		}
		else {
		}
		
		
		Object[] arr = {whichPiece,whereJump};
		return arr;
	}
	
	/**
	 * Checks if Ai can make a mill and closes it if so
	 * @return Successful or not
	 */
	private boolean closeMill() {
		if(isMillOpenForPlacing(ai)==true) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if opponent can make a mill and blocks it if so. 
	 * If opponent has two mills it doesn't block it
	 * @return Successful or not
	 */
	private boolean blockOpponentMill() {
		if(isMillOpenForPlacing(player)==true) {
			for(int i=0;i<9;i++) {
				if(ai.getPieces()[i].getPosition()>0) {
					int savePos=ai.getPieces()[i].getPosition();
					ai.getPieces()[i].setPosition(whereJump);
					if(isMillOpenForPlacing(player)==false) {
						ai.getPieces()[i].setPosition(savePos);
						whichPiece=ai.getPieces()[i];
						return true;
					}
					ai.getPieces()[i].setPosition(savePos);
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the given player can make a mill and sets whichPiece and whereJump to the fitting conditions
	 * @param checkingFor The given player which get checked
	 * @return Successful or not
	 */
	private boolean isMillOpenForPlacing(Player checkingFor) {
		int[][][] millPos = checkingFor.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = checkingFor.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = checkingFor.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							boolean check3=true;
							if(board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0]==null) {
								check3=false;
							}
							else {
								whichPiece=(Piece) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0];
							}
							if(check3==true) {
								for(int y=0;y<2;y++) {
									boolean check1 =false;
									boolean check2 =false;
									for(int x=0;x<2;x++) {
										if(millPos[l][y][x]==pos1) {
											check1=true;
										}
										if(millPos[l][y][x]==pos2) {
											check2=true;
										}
									}
									if(check1==true && check2==true) {
										whereJump=l;
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks how many mills the given player could do
	 * @param checkingFor The given player which get checked
	 * @return The number of mills that can be closed
	 */
	private int howManyMillsAreOpenForPlacing(Player checkingFor) {
		int counter =0;
		int[][][] millPos = checkingFor.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = checkingFor.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = checkingFor.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							boolean check3=true;
							if(board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0]==null) {
								check3=false;
							}
							else {
							}
							if(check3==true) {
								for(int y=0;y<2;y++) {
									boolean check1 =false;
									boolean check2 =false;
									for(int x=0;x<2;x++) {
										if(millPos[l][y][x]==pos1) {
											check1=true;
										}
										if(millPos[l][y][x]==pos2) {
											check2=true;
										}
									}
									if(check1==true && check2==true) {
										counter++;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println(counter);
		return counter/2;
	}
	
	/**
	 * Calculates the last three active Pieces and if they currently block a mill of the opponent
	 * It gets saved in a private array of the class
	 */
	private void calcLastThreeAndTheirBlock() {
		lastThreePiecesAndDoTheyBlock = new Object[3][2];
		int index=0;
		for(int j=0;j<9;j++) {
			if(ai.getPieces()[j].getPosition()>0) {
				lastThreePiecesAndDoTheyBlock[index][0]=ai.getPieces()[j];
				index++;
			}
		}
		int i=1;
		for(i=1;i<25;i++) {
			if(board.isThisPositionFree(i)==true) {
				break;
			}
		}
		for(int k=0;k<3;k++) {
			int oldPos = ((Piece) lastThreePiecesAndDoTheyBlock[k][0]).getPosition();
			((Piece) lastThreePiecesAndDoTheyBlock[k][0]).setPosition(i);
			if(board.canAPieceMoveToPosition(oldPos, player)[0]!=null) {
				Piece sPiece = (Piece) board.canAPieceMoveToPosition(oldPos, player)[0];
				int oldPosS = sPiece.getPosition();
				sPiece.setPosition(oldPos);
				if(player.checkForMill(sPiece)==true) {
					lastThreePiecesAndDoTheyBlock[k][1]=true;
				}
				else {
					lastThreePiecesAndDoTheyBlock[k][1]=false;
				}
				sPiece.setPosition(oldPosS);
				player.checkForMill(sPiece);
			}
			else {
				lastThreePiecesAndDoTheyBlock[k][1]=false;
			}
			((Piece) lastThreePiecesAndDoTheyBlock[k][0]).setPosition(oldPos);
		}
	}
	
	/**
	 * Calculates if Ai could move a piece so that there are two open mills now -> Cross
	 * @return Successful or not
	 */
	private boolean setUpCross1() {
		for(int i=0;i<3;i++) {
			if((boolean)lastThreePiecesAndDoTheyBlock[i][1]==false) {
				for(int j=1;j<25;j++) {
					if(board.isThisPositionFree(j)==true) {
						int oldPos = ((Piece) lastThreePiecesAndDoTheyBlock[i][0]).getPosition();
						((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(j);
						if(howManyMillsAreOpenForPlacing(ai)>=2) {
							((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(oldPos);
							whichPiece=(Piece) lastThreePiecesAndDoTheyBlock[i][0];
							whereJump=j;
							return true;
						}
						((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(oldPos);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Calculates if Ai could move a piece so that it could move a piece next turn that creates a cross -> Prepare a cross
	 * @return Successful or not
	 */
	private boolean setUpCross2() {
		for(int i=0;i<3;i++) {
			if((boolean)lastThreePiecesAndDoTheyBlock[i][1]==false) {
				for(int j=1;j<25;j++) {
					if(board.isThisPositionFree(j)==true) {
						int oldPos = ((Piece) lastThreePiecesAndDoTheyBlock[i][0]).getPosition();
						((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(j);
						for(int k=0;k<3;k++) {
							if((boolean)lastThreePiecesAndDoTheyBlock[k][1]==false && (Piece) lastThreePiecesAndDoTheyBlock[k][0] != (Piece) lastThreePiecesAndDoTheyBlock[i][0]) {
								for(int l=1;l<25;l++) {
									if(board.isThisPositionFree(l)) {
										int oldPos2 = ((Piece) lastThreePiecesAndDoTheyBlock[k][0]).getPosition();
										((Piece) lastThreePiecesAndDoTheyBlock[k][0]).setPosition(l);
										if(howManyMillsAreOpenForPlacing(ai)>=2) {
											((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(oldPos);
											((Piece) lastThreePiecesAndDoTheyBlock[k][0]).setPosition(oldPos2);
											whichPiece=(Piece) lastThreePiecesAndDoTheyBlock[i][0];
											whereJump=j;
											return true;
										}
										((Piece) lastThreePiecesAndDoTheyBlock[k][0]).setPosition(oldPos2);
									}
								}
							}
						}
						((Piece) lastThreePiecesAndDoTheyBlock[i][0]).setPosition(oldPos);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if the remaining three pieces may block a possible mill.
	 * It checks exactly if after jump the opponent can make a mill on the new open position
	 * @return Successful or not
	 */
	private boolean preRandomJump() {
		for(int s=0;s<3;s++) {
			if((boolean)lastThreePiecesAndDoTheyBlock[s][1]==false) {
				//do random jump
				for(int j=1;j<25;j++) {
					if(board.canThisPieceMoveToThisPos((Piece) lastThreePiecesAndDoTheyBlock[s][0], j)==true) {
						whereJump=j;
						whichPiece=(Piece) lastThreePiecesAndDoTheyBlock[s][0];
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Does a possible jump.
	 * Not random anymore!
	 */
	private void randomJump() {
//		boolean active=true;
//		int counter=0;
//		while(active==true) {
//			int random=(int) (Math.random()*9);
//			if(ai.getPieces()[random].getPosition()>0) {
//				boolean active2=true;
//				while(active2==true) {
//					int random2 = (int) ((Math.random()*24)+1);
//					if(board.isPlacePossible(random2)==true) {
//						int savePos = ai.getPieces()[random].getPosition();
//						ai.getPieces()[random].setPosition(random2);
//						boolean found=false;
//						if(player.hasToJump()==true) {
//							if(isMillOpenForPlacing(player, 0)==true) {
//								found=true;
//							}
//						}
//						else {
//							if(isMillOpenForPlacing(player, 1)==true) {
//								found=true;
//							}
//						}
//						if(found==true) {
//							active2=false;
//							counter++;
//						}
//						else {
//							ai.getPieces()[random].setPosition(savePos);
//							whereJump=random2;
//							whichPiece=ai.getPieces()[random];
//							return;
//						}
//						ai.getPieces()[random].setPosition(savePos);
//					}
//				}
//			}
//			if(counter==1000) {
//				active=false;
//				break;
//			}
//		}
//		active=true;
//		boolean active2=true;
//		int random2=0;
//		int i=0;
//		for(i=0;i<9;i++) {
//			if(ai.getPieces()[i].getPosition()>0) {
//				active2=true;
//				while(active2==true) {
//					random2 = (int) ((Math.random()*24)+1);
//					if(board.isPlacePossible(random2)==true) {
//						active2=false;
//						active=false;
//					}
//				}
//			}
//		}
//		whereJump=random2;
//		whichPiece=ai.getPieces()[i];
		
		for(int i=0;i<9;i++) {
			for(int j=1;j<25;j++) {
				if(board.canThisPieceMoveToThisPos(ai.getPieces()[i], j)) {
					whereJump=j;
					whichPiece=ai.getPieces()[i];
					return;
				}
			}
		}
	}
	
	
	
	
}
