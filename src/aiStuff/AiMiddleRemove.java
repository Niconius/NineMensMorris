package aiStuff;

import java.io.Serializable;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiMiddleRemove implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7422343737237085356L;
	private Player player;
	private Player ai;
	private Board board;
	private Piece whichPiece;
	private Object[][] allPiecesForMill;
	
	public AiMiddleRemove(Player player, Player ai, Board board) {
		whichPiece=null;
		allPiecesForMill = new Object[50][2];
		this.player=player;
		this.ai=ai;
		this.board=board;
	}
	
	public Piece whatRemoveShouldAiDo() {
		if(opponentIsBlocked()==false) {
			if(pieceWouldCompletelyBlockNextTurn()==false) {
				if(pieceOpponentCanMakeMillWith()==false) {
					if(findOpenMill()==false) {
							if(pieceThatBlocksMill()==false) {
								if(preRandom()==false) {
									randomPiece();
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
			}
			else {
			}
		
		return whichPiece;
		}
	
	
	private boolean opponentIsBlocked() {
		for(int i=0;i<9;i++) {
			if(player.getPieces()[i].getPosition()>0) {
				if(player.getPieces()[i].getIsInMill()==false || player.areAllActiveStoneInMill()==true) {
					int savePos= player.getPieces()[i].getPosition();
					player.getPieces()[i].setPosition(-1);
					boolean completelyBlocked = true;
	        		for(int k=0;k<9;k++) {
	        			if(board.canPieceMove(player.getPieces()[k])==true || player.hasToJump()==true) {
	        				completelyBlocked=false;
	        				break;
	        			}
	        		}
	        		player.getPieces()[i].setPosition(savePos);
					if(completelyBlocked==true) {
						whichPiece=player.getPieces()[i];
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean pieceWouldCompletelyBlockNextTurn() {
		for(int i=0;i<9;i++) {
			if(player.getPieces()[i].getPosition()>0) {
				if(player.getPieces()[i].getIsInMill()==false || player.areAllActiveStoneInMill()==true) {
					int savePos= player.getPieces()[i].getPosition();
					for(int l=1;l<25;l++) {
						for(int j=0;j<4;j++) {
							if(board.isMovePossible(j, player.getPieces()[i])==true) {
								int newPosition1 = board.getBoardAsArray()[player.getPieces()[i].getPosition()][j];
								player.getPieces()[i].setPosition(newPosition1);
								boolean completelyBlocked = true;
				        		for(int k=0;k<9;k++) {
				        			if(board.canPieceMove(ai.getPieces()[k])==true || ai.hasToJump()==true) {
				        				completelyBlocked=false;
				        				break;
				        			}
				        		}
				        		player.getPieces()[i].setPosition(savePos);
				        		if(completelyBlocked==true) {
				        			whichPiece=player.getPieces()[i];
				        			return true;
				        		}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean pieceOpponentCanMakeMillWith() {
		if(isAMillOpenForClosingAdapted(player)==true) {
			for(int j=4;j>=1;j--) {
				for(int i=0;i<allPiecesForMill.length;i++) {
					if(allPiecesForMill[i][0]==null) {
						break;
					}
					if(((Piece) allPiecesForMill[i][0]).getPosition()>0 && (int)allPiecesForMill[i][1]==j && (((Piece) allPiecesForMill[i][0]).getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
						whichPiece=(Piece) allPiecesForMill[i][0];
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isAMillOpenForClosingAdapted(Player checkingFor) {
		allPiecesForMill = new Object[50][2];
		for(int b=0;b<allPiecesForMill.length;b++) {
			allPiecesForMill[b][1]=0;
		}
		boolean found=false;
		int[][][] millPos = checkingFor.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = checkingFor.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = checkingFor.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							if(board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0]!=null) {
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
										found=true;
										boolean foundInArray = false;
										whichPiece=(Piece) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0];
										for(int i=0;i<allPiecesForMill.length;i++) {
											if(allPiecesForMill[i][0]==whichPiece) {
												allPiecesForMill[i][1]=(int)allPiecesForMill[i][1]+1;
												foundInArray=true;
												break;
											}
										}
										if(foundInArray==false) {
											for(int f=0;f<allPiecesForMill.length;f++) {
												if(allPiecesForMill[f][0]==null) {
													allPiecesForMill[f][0]=whichPiece;
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(found==true) {
			return true;
		}
		return false;
	}

	
	private boolean findOpenMill() {
		// zähle mühlen durch methode oben dann geh array durch welches piece die meisten mühlen macht und remove das wenn möglich
		// problem beim setzen da des piece ja noch ppisition 0 hat
		for(int i=0;i<9;i++) {
			if(player.getPieces()[i].getPosition()>0 && player.hasToJump()==false) {
				for(int j=0;j<4;j++) {
					if(board.canThisPieceMoveToThisPos(player.getPieces()[i], board.getBoardAsArray()[player.getPieces()[i].getPosition()][j])==true) {
						int oldPos = player.getPieces()[i].getPosition();
						player.getPieces()[i].setPosition(board.getBoardAsArray()[player.getPieces()[i].getPosition()][j]);
						if(player.checkForMill(player.getPieces()[i])==true) {
							player.checkForMill(player.getPieces()[i]);
							if(player.areAllActiveStoneInMill()==true || player.getPieces()[i].getIsInMill()==false) {
								whichPiece=player.getPieces()[i];
								player.getPieces()[i].setPosition(oldPos);
								return true;
							}
						}
						player.checkForMill(player.getPieces()[i]);
						player.getPieces()[i].setPosition(oldPos);
					}
				}
			}
//			else if(player.getPieces()[i].getPosition()==0) {
//				for(int j=1;j<25;j++) {
//					if(board.canThisPieceMoveToThisPos(player.getPieces()[i], j)==true) {
//						int oldPos = player.getPieces()[i].getPosition();
//						player.getPieces()[i].setPosition(j);
//						if(player.checkForMill(player.getPieces()[i])==true) {
//							if(player.areAllActiveStoneInMill()==true || player.getPieces()[i].getIsInMill()==false) {
//								whichPiece=player.getPieces()[i];
//								player.checkForMill(player.getPieces()[i]);
//								player.getPieces()[i].setPosition(oldPos);
//								return true;
//							}
//						}
//						player.checkForMill(player.getPieces()[i]);
//						player.getPieces()[i].setPosition(oldPos);
//					}
//				}
//			}
		}
		return false;
	}
	
	/**
	 * Counts how many open mills a given player has
	 * @param checkingFor The player which gets checked
	 * @return The number of mills the given player has
	 */
	private int openMillCounter(Player checkingFor) {
		int counter=0;
		int[][][] millPos = checkingFor.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = checkingFor.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = checkingFor.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							if(board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0]!=null) {
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
		return counter;
	}
	
	/**
	 * Removes a piece that is near an ai piece that is in a mill
	 * @return Successful or not
	 */
	private boolean pieceThatBlocksMill() {
		for(int i=0;i<9;i++) {
			if(ai.getPieces()[i].getPosition()>0 && ai.getPieces()[i].getIsInMill()==true && board.canPieceMove(ai.getPieces()[i])==false) {
				for(int j=0;j<4;j++) {
					for(int k=0;k<9;k++) {
						if(board.getBoardAsArray()[ai.getPieces()[i].getPosition()][j]>0 && board.getBoardAsArray()[ai.getPieces()[i].getPosition()][j]==player.getPieces()[k].getPosition()) {
							if(player.getPieces()[k].getIsInMill()==false || player.areAllActiveStoneInMill()==true) {
								whichPiece=player.getPieces()[k];
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes a piece that is not blocked
	 * @return Successful or not
	 */
	private boolean preRandom() {
		for(int i=0;i<9;i++) {
			if(board.isPieceBlocked(player.getPieces()[i])==false && (player.areAllActiveStoneInMill()==true || player.getPieces()[i].getIsInMill()==false)) {
				whichPiece=player.getPieces()[i];
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes a possible piece
	 */
	private void randomPiece() {
		for(int i=0;i<9;i++) {
			if(player.getPieces()[i].getPosition()>0 && (player.getPieces()[i].getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
				whichPiece=player.getPieces()[i];
				return;
			}
		}
//		while(active=true) {
//			int random = (int) (Math.random()*9);
//			if(player.getPieces()[random].getPosition()>0 && (player.getPieces()[random].getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
//				whichPiece=player.getPieces()[random];
//				return;
//			}
//		}
	}
	
	
}
