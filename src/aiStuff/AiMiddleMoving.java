package aiStuff;

import java.io.Serializable;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiMiddleMoving implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4197106991953033895L;
	private Player player;
	private Player ai;
	private Board board;
	private int whichMove;
	private Piece whichPiece;
	private int whichPositionIsWhichMove;
	private int[] arrWhichPosIsWhichMove;
	private Object[][] arrWhichPieceAndMove;
	
	public AiMiddleMoving(Player player, Player ai, Board board) {
		whichMove=5000;
		whichPiece=null;
		whichPositionIsWhichMove=5000;
		arrWhichPosIsWhichMove=new int[10];
		arrWhichPieceAndMove = new Object[10][2];
		this.player=player;
		this.ai=ai;
		this.board=board;
	}
	
	public Object[] whatMoveShouldAiDo() {
		if(winningMove()==false) {
			if(closeMillIfOpponnentCouldCloseHis()==false) {
				if(closeMillIfOpponentCouldBlockIt()==false) {
					if(closeMillIfOpponentHasNoClosedMill()==false) {
						if(blockMillIfOnlyOne()==false) {
							if(blockMillPrep()==false) {
								if(openMillIfOpponentCantKillIt()==false) {
									if(millPrep()==false) {
										randomMove();
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
			}
			else {
			}
		}
		else {
		}
		Object[] arr = {whichPiece,whichMove};
		return arr;
	}
	
	private boolean winningMove() {
		if(winThroughOpponentLessThan3()==false) {
			if(completelyBlockedThroughMove()==false) {
				if(completelyBlockedThroughRemove()==false) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
	
	private boolean winThroughOpponentLessThan3() {
		if(isAMillOpenForClosing(ai)==true) {
			if(player.hasToJump()==true) {
				return true;
			}
		}
		return false;
	}
	
	private boolean completelyBlockedThroughMove() {
		if(player.hasToJump()==true) {
			return false;
		}
		for(int i=0;i<9;i++) {
			if(ai.getPieces()[i].getPosition()>0) {
				for(int j=0;j<4;j++) {
					if(board.isMovePossible(j, ai.getPieces()[i])==true) {
						int savePos= ai.getPieces()[i].getPosition();
						int newPosition = board.getBoardAsArray()[ai.getPieces()[i].getPosition()][j];
						ai.getPieces()[i].setPosition(newPosition);
						boolean completelyBlocked = true;
		        		for(int k=0;k<9;k++) {
		        			if(board.canPieceMove(player.getPieces()[k])==true || player.hasToJump()==true) {
		        				completelyBlocked=false;
		        				break;
		        			}
		        		}
		        		ai.getPieces()[i].setPosition(savePos);
						if(completelyBlocked==true) {
							whichPiece=ai.getPieces()[i];
							whichMove=j;
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean completelyBlockedThroughRemove() {
		if(isAMillOpenForClosing(ai)==false) {
			return false;
		}
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
						isAMillOpenForClosing(ai);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean closeMillIfOpponnentCouldCloseHis() {
		if(isAMillOpenForClosing(player)==true) {
			if(isAMillOpenForClosing(ai)==false) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean closeMillIfOpponentCouldBlockIt() {
		arrWhichPosIsWhichMove=new int[10];
		arrWhichPieceAndMove = new Object[10][2];
		if(blockMillPrepHelper(ai)>0) {
			for(int b=0;b<arrWhichPieceAndMove.length;b++) {
				if(arrWhichPieceAndMove[b][0]==null) {
					break;
				}
			}
			for(int i=0;i<arrWhichPosIsWhichMove.length;i++) {
				if(arrWhichPosIsWhichMove[i]==0) {
					break;
				}
				if(board.canAPieceMoveToPosition(arrWhichPosIsWhichMove[i], player)[0]!=null) {
					whichPiece=(Piece) arrWhichPieceAndMove[i][0];
					whichMove=(int) arrWhichPieceAndMove[i][1];
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean closeMillIfOpponentHasNoClosedMill() {
		if(isAMillOpenForClosing(ai)==true) {
			for(int i=0;i<9;i++) {
				if(player.getPieces()[i].getIsInMill()==true) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean blockMillIfOnlyOne() {
		int counterOpenMills=0;
		int[][][] millPos = player.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = player.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = player.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							if(board.canAPieceMoveToPositionWithout2(l, player, player.getPieces()[j], player.getPieces()[k])[0]!=null) {
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
										whichPiece=(Piece) board.canAPieceMoveToPositionWithout2(l, player, player.getPieces()[j], player.getPieces()[k])[0];
										whichMove= (int) board.canAPieceMoveToPositionWithout2(l, player, player.getPieces()[j], player.getPieces()[k])[1];
										whichPositionIsWhichMove=l;
										counterOpenMills++;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		if(counterOpenMills<=2 && counterOpenMills!=0) {
			if(board.canAPieceMoveToPosition(whichPositionIsWhichMove, ai)[0]!=null) {
				whichPiece=(Piece) board.canAPieceMoveToPosition(whichPositionIsWhichMove, ai)[0];
				whichMove=(int) board.canAPieceMoveToPosition(whichPositionIsWhichMove, ai)[1];
				return true;
			}
		}
		return false;
	}
	
	private boolean blockMillPrep() {
		int counter1=0;
		int counter2=0;
		for(int i=0;i<9;i++) {
			if(player.getPieces()[i].getPosition()>0) {
				for(int j=0;j<4;j++) {
					if(board.isMovePossible(j, player.getPieces()[i])==true) {
						int newPosition1 = board.getBoardAsArray()[player.getPieces()[i].getPosition()][j];
						counter1=blockMillPrepHelper(player);
						if(board.canAPieceMoveToPosition(newPosition1, ai)[0]!=null) {
							int savePos1= player.getPieces()[i].getPosition();
							player.getPieces()[i].setPosition(newPosition1);
							counter2=blockMillPrepHelper(player);
							if(counter2>counter1) {
								for(int k=0;k<9;k++) {
									if(player.getPieces()[k].getPosition()>0) {
										for(int m=0;m<4;m++) {
											int savePos2= player.getPieces()[k].getPosition();
											if(board.isMovePossible(m, player.getPieces()[k])==true) {
												int newPosition2 = board.getBoardAsArray()[player.getPieces()[k].getPosition()][m];
												boolean found=false;
												player.getPieces()[k].setPosition(newPosition2);
												if(player.checkForMill(player.getPieces()[k])==true) {
													found=true;
												}
												player.getPieces()[k].setPosition(savePos2);
												player.checkForMill(ai.getPieces()[k]);
												if(board.canAPieceMoveToPosition(newPosition2, ai)[0]==null && found==true) {
													player.getPieces()[i].setPosition(savePos1);
													if((((Piece) board.canAPieceMoveToPosition(newPosition1, ai)[0]).getIsInMill()==true && isAMillOpenForClosing(player)==false) || ((Piece) board.canAPieceMoveToPosition(newPosition1, ai)[0]).getIsInMill()==false) {
														whichPiece=(Piece) board.canAPieceMoveToPosition(newPosition1, ai)[0];
														whichMove=(int) board.canAPieceMoveToPosition(newPosition1, ai)[1];
														return true;
													}
												}
											}
										}
									}
								}
							}
							player.getPieces()[i].setPosition(savePos1);
						}
					}
				}
			}
		}
		return false;
	}
	
	private int blockMillPrepHelper(Player checkingFor) {
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
										whichPiece=(Piece) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0];
										whichMove= (int) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[1];
										whichPositionIsWhichMove=l;
										arrWhichPosIsWhichMove[counter]=l;
										arrWhichPieceAndMove[counter][0]=whichPiece;
										arrWhichPieceAndMove[counter][1]=whichMove;
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
	
	private boolean openMillIfOpponentCantKillIt() {
		if(isAMillOpenForClosing(player)==true) {
			return false;
		}
		for(int i=0;i<9;i++) {
			if(ai.getPieces()[i].getIsInMill()==true && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(ai.getPieces()[i], player)) {
				whichPiece=ai.getPieces()[i];
				for(int j=0;j<4;j++) {
					if(board.isMovePossible(j,whichPiece)==true) {
						whichMove=j;
					}
				}
				int savePos= ai.getPieces()[i].getPosition();
				int newPosition1 = board.getBoardAsArray()[ai.getPieces()[i].getPosition()][whichMove];
				ai.getPieces()[i].setPosition(newPosition1);
				if(isAMillOpenForClosing(player)==true) {
					ai.getPieces()[i].setPosition(savePos);
					return false;
				}
				ai.getPieces()[i].setPosition(savePos);
				return true;
			}
		}
		return false;
	}
	
	private boolean millPrep() {
		if(isAMillOpenForClosing(player)==true) {
			return false;
		}
		int counter1=0;
		int counter2=0;
		for(int i=0;i<9;i++) {
			if(ai.getPieces()[i].getPosition()>0) {
				for(int j=0;j<4;j++) {
					if(board.isMovePossible(j, ai.getPieces()[i])==true) {
						int newPosition1 = board.getBoardAsArray()[ai.getPieces()[i].getPosition()][j];
						counter1=blockMillPrepHelper(ai);
						int savePos1= ai.getPieces()[i].getPosition();
						ai.getPieces()[i].setPosition(newPosition1);
						if(isAMillOpenForClosing(player)==true) {
							ai.getPieces()[i].setPosition(savePos1);
							return false;
						}
						counter2=blockMillPrepHelper(ai);
						if(counter2>counter1) {
							for(int k=0;k<9;k++) {
								if(ai.getPieces()[k].getPosition()>0) {
									for(int m=0;m<4;m++) {
										int savePos2= ai.getPieces()[k].getPosition();
										if(board.isMovePossible(m, ai.getPieces()[k])==true && ai.getPieces()[i]!=ai.getPieces()[k]) {
											int newPosition2 = board.getBoardAsArray()[ai.getPieces()[k].getPosition()][m];
											boolean found=false;
											ai.getPieces()[i].setPosition(newPosition1);
											ai.getPieces()[k].setPosition(newPosition2);
											ai.checkForMill(ai.getPieces()[k]);
											if(ai.getPieces()[k].getIsInMill()==true && ai.getPieces()[i].getIsInMill()==true) {
												found=true;
											}
											ai.getPieces()[i].setPosition(savePos1);
											ai.getPieces()[k].setPosition(savePos2);
											ai.checkForMill(ai.getPieces()[k]);
											if(found==true && board.canAPieceMoveToPosition(newPosition2, player)[0]==null) {
												ai.getPieces()[i].setPosition(savePos1);
												whichPiece=ai.getPieces()[i];
												whichMove=j;
												return true;
											}
										}
									}
								}
							}
						}
						ai.getPieces()[i].setPosition(savePos1);
					}
				}
			}
		}
		return false;
	}
	
	private void randomMove() {
		boolean active=true;
		boolean found=false;
		int counter=0;
		int random=0;
		while(active==true) {
			random=(int) (Math.random()*9);
			boolean canThisPieceMakeAMill=false;
			if(ai.getPieces()[random].getIsInMill()==false) {
				for(int i=0;i<4;i++) {
					if(board.isMovePossible(i,ai.getPieces()[random])==true) {
						int savePos=ai.getPieces()[random].getPosition();
						int newPosition = board.getBoardAsArray()[ai.getPieces()[random].getPosition()][i];
						ai.getPieces()[random].setPosition(newPosition);
						int[][][] millPos = ai.getMillPossibilities();
						for(int j=0;j<9;j++) {
							int pos1 = ai.getPieces()[j].getPosition();
							if(pos1>0) {
								for(int k=0;k<9;k++) {
									int pos2 = ai.getPieces()[k].getPosition();
									if(pos2>0 && pos1!=pos2) {
										int l=ai.getPieces()[random].getPosition();
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
												canThisPieceMakeAMill=true;
												ai.getPieces()[random].setPosition(savePos);
												break;
											}
										}
										if(canThisPieceMakeAMill==true) {
											ai.getPieces()[random].setPosition(savePos);
											break;
										}
									}
								}
							}
						}
						if(canThisPieceMakeAMill==true) {
							ai.getPieces()[random].setPosition(savePos);
							break;
						}
						ai.getPieces()[random].setPosition(savePos);
					}
				}
			}
			if(ai.getPieces()[random].getPosition()>0 && board.canPieceMove(ai.getPieces()[random])==true && canThisPieceMakeAMill==false && (ai.getPieces()[random].getIsInMill()==false || ai.areAllActiveStoneInMill()==true)) {
				found=true;
				active=false;
				break;
			}
			else if(counter==1000000) {
				active=false;
				break;
			}
			else {
				counter++;
			}
		}
		if(found==false) {
			for(int i=0;i<9;i++) {
				if(ai.getPieces()[i].getPosition()>0 && board.canPieceMove(ai.getPieces()[i])==true) {
					for(int j=0;j<4;j++) {
						if(board.isMovePossible(j, ai.getPieces()[i])==true) {
							random=i;
						}
					}
				}
			}
		}
		boolean checking=false;
		int random2=0;
		while(checking==false) {
			random2 = (int) (Math.random()*4);
			if(board.isMovePossible(random2, ai.getPieces()[random])) {
				checking=true;
			}
		}
		whichPiece=ai.getPieces()[random];
		whichMove=random2;
	}
	
	private boolean isAMillOpenForClosing(Player checkingFor) {
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
										whichPiece=(Piece) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[0];
										whichMove= (int) board.canAPieceMoveToPositionWithout2(l, checkingFor, checkingFor.getPieces()[j], checkingFor.getPieces()[k])[1];
										whichPositionIsWhichMove=l;
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		// if nothing found
		return false;
	}

}
