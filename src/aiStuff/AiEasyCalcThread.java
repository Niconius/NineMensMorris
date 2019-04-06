package aiStuff;

import java.io.IOException;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiEasyCalcThread extends Thread{

	private int indexOnThread;
	private AiEasyPlayer aiEasy;
	
	private Player player;
	private Player ai;
	private Board board;
	private int[][] boardAsArray;
	
	private Player playerAsMiddleAi;
	private Player aiAsRandom;
	private Board simulatedBoard;
	private AiGeneral aiMiddle;
	
	private int counter;
	private Object[] highestEvaluation; 
	private Piece theCurrentPiece;
	private int newPosFromCurrent;
	private boolean isThereRemove;
	private Piece removingPiece;
	private int oldPosOfRemoved;
	private boolean endedByRemove;
	private boolean end1ByAi,end2ByAi,end3ByAi,end1ByPlayer,end2ByPlayer,end3ByPlayer;
	
	private int a,b;
	
	private boolean flag;
	
	public AiEasyCalcThread(int indexOnThread, Player player, Player ai, Board board, AiEasyPlayer aiEasy){
		this.indexOnThread=indexOnThread;
		this.player=player;
		this.ai=ai;
		this.board=board;
		this.boardAsArray=this.board.getBoardAsArray();
		this.aiEasy=aiEasy;
		flag=false;
		if(indexOnThread==1) {
			a=0;
			b=3;
		}
		else if(indexOnThread==2) {
			a=3;
			b=6;
		}
		else if(indexOnThread==3) {
			a=6;
			b=9;
		}
	}
	
	@Override
	public void run() {
		while(flag==false) {
			makeTheMagicHappen(0);
			if(highestEvaluation[0]==null) {
				highestEvaluation[0]=-50000000;
			}
			if(indexOnThread==1) {
				aiEasy.setEva1(highestEvaluation);
			}
			else if(indexOnThread==2) {
				aiEasy.setEva2(highestEvaluation);
			}
			else if(indexOnThread==3) {
				aiEasy.setEva3(highestEvaluation);
			}
			else if(indexOnThread==4) {
				aiEasy.setEva4(highestEvaluation);
			}
			else if(indexOnThread==5) {
				aiEasy.setEva5(highestEvaluation);
			}
			flag=true;
			interrupt();
		}
	}
	
	
	
	
	private void makeTheMagicHappen(int index) {
		if(index==0) {
			try {
				playerAsMiddleAi = player.serializeThisPlayer();
				aiAsRandom = playerAsMiddleAi.getOpponent();
				simulatedBoard = playerAsMiddleAi.getBoard();
				aiMiddle= new AiMiddlePlayer(aiAsRandom, playerAsMiddleAi, simulatedBoard);
				highestEvaluation = new Object[7];
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		if(index==1) {
//			a=0;
//			b=9;
//		}
		if(aiAsRandom.inPlacePhase()==true) {
			// Ai First Turn
			for(int i=a;i<b;i++) {
				if(aiAsRandom.getPieces()[i].getPosition()==0) {
					for(int j=1;j<25;j++) {
						if(simulatedBoard.isPlacePossible(j)==true) {
							int oldPosition1 = aiAsRandom.getPieces()[i].getPosition();
							aiAsRandom.placePiece(aiAsRandom.getPieces()[i], j);
							boolean end1 = checkTheTurn(aiAsRandom);
							if(index==0) {
								end1ByAi=end1;
							}
//							else if(index==1) {
//								end2ByAi=end1;
//							}
//							else if(index==2) {
//								end3ByAi=end1;
//							}
							if(index==0) {
								theCurrentPiece = aiAsRandom.getPieces()[i];
								newPosFromCurrent = j;
								isThereRemove=false;
								endedByRemove=false;
								removingPiece=null;
								oldPosOfRemoved=0;
							}
							if(aiAsRandom.getPieces()[i].getIsInMill()==true) {
								for(int k=0;k<9;k++) {
									if((playerAsMiddleAi.getPieces()[k].getIsInMill()==false || playerAsMiddleAi.areAllActiveStoneInMill()==true) && playerAsMiddleAi.getPieces()[k].getPosition()>0) {
										if(index==0) {
											isThereRemove=true;
											removingPiece=playerAsMiddleAi.getPieces()[k];
										}
										int oldPositionFromRemoved1 = playerAsMiddleAi.getPieces()[k].getPosition();
										playerAsMiddleAi.removePieceFromThisPlayer(playerAsMiddleAi.getPieces()[k]);
										if(index==0) {
											oldPosOfRemoved=oldPositionFromRemoved1;
										}
										boolean end2 = checkTheTurn(aiAsRandom);
										if(index==0) {
											end1ByAi=end2 || end1ByAi;
										}
//										else if(index==1) {
//											end2ByAi=end2 || end2ByAi;
//										}
//										else if(index==2) {
//											end3ByAi=end2 || end3ByAi;
//										}
										if(index==0) {
											endedByRemove=end2;
										}
										// Player First Turn
										simulatedBoard.switchActivePlayer();
										Object[] playerFirstTurn = aiMiddle.aiTurn();
										simulatedBoard.switchActivePlayer();
										Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
										int oldPosition2 = (int) playerFirstTurn[1];
										boolean end3 = (boolean) playerFirstTurn[5];
										if(index==0) {
											end1ByPlayer=end3;
										}
//										else if(index==1) {
//											end2ByPlayer=end3;
//										}
//										else if(index==2) {
//											end3ByPlayer=end3;
//										}
										Piece whichPieceRemoved1=null;
										int oldPositionFromRemoved2=0; 
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1 = (Piece) playerFirstTurn[6];
											oldPositionFromRemoved2 = (int) playerFirstTurn[4];
										}
										if(counter==0) {
											// Evaluate
											evaluate(end1ByAi, end1ByPlayer);
//											evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
										}
										else {
											counter++;
											makeTheMagicHappen(counter);
											counter--;
										}
										
										whichPieceMoved1.setPosition(oldPosition2);
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
										}
										playerAsMiddleAi.getPieces()[k].setPosition(oldPositionFromRemoved1);
									}
									if(index==0) {
										isThereRemove=false;
									}
								}
							}
							else {
								// Player First Turn
								simulatedBoard.switchActivePlayer();
								Object[] playerFirstTurn = aiMiddle.aiTurn();
								simulatedBoard.switchActivePlayer();
								Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
								int oldPosition2 = (int) playerFirstTurn[1];
								boolean end4 = (boolean) playerFirstTurn[5];
								if(index==0) {
									end1ByPlayer=end4;
								}
//								else if(index==1) {
//									end2ByPlayer=end4;
//								}
//								else if(index==2) {
//									end3ByPlayer=end4;
//								}
								Piece whichPieceRemoved1=null;
								int oldPositionFromRemoved2=0;
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1 = (Piece) playerFirstTurn[6];
									oldPositionFromRemoved2 = (int) playerFirstTurn[4];
								}
								// Evaluate
								if(counter==0) {
									// Evaluate
									evaluate(end1ByAi, end1ByPlayer);
//									evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
								}
								else {
									counter++;
									makeTheMagicHappen(counter);
									counter--;
								}
								
								whichPieceMoved1.setPosition(oldPosition2);
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
									checkTheTurn(aiAsRandom);
								}
							}
							aiAsRandom.getPieces()[i].setPosition(oldPosition1);
							checkTheTurn(aiAsRandom);
						}
					}
				}
			}
		}
		else if(aiAsRandom.hasToJump()==false) {
			// Ai First Turn
			for(int i=a;i<b;i++) {
				if(aiAsRandom.getPieces()[i].getPosition()>0) {
					for(int j=0;j<4;j++) {
						if(simulatedBoard.isMovePossible(j, aiAsRandom.getPieces()[i])==true) {
							int newPosZwisch = simulatedBoard.getBoardAsArray()[aiAsRandom.getPieces()[i].getPosition()][j];
							int oldPosition1 = aiAsRandom.getPieces()[i].getPosition();
							aiAsRandom.placePiece(aiAsRandom.getPieces()[i], newPosZwisch);
							boolean end1 = checkTheTurn(aiAsRandom);
							if(index==0) {
								end1ByAi=end1;
							}
//							else if(index==1) {
//								end2ByAi=end1;
//							}
//							else if(index==2) {
//								end3ByAi=end1;
//							}
							if(index==0) {
								theCurrentPiece = aiAsRandom.getPieces()[i];
								newPosFromCurrent = newPosZwisch;
								isThereRemove=false;
								endedByRemove=false;
								removingPiece=null;
								oldPosOfRemoved=0;
							}
							if(aiAsRandom.getPieces()[i].getIsInMill()==true) {
								for(int k=0;k<9;k++) {
									if((playerAsMiddleAi.getPieces()[k].getIsInMill()==false || playerAsMiddleAi.areAllActiveStoneInMill()==true) && playerAsMiddleAi.getPieces()[k].getPosition()>0) {
										if(index==0) {
											isThereRemove=true;
											removingPiece=playerAsMiddleAi.getPieces()[k];
										}
										int oldPositionFromRemoved1 = playerAsMiddleAi.getPieces()[k].getPosition();
										playerAsMiddleAi.removePieceFromThisPlayer(playerAsMiddleAi.getPieces()[k]);
										if(index==0) {
											oldPosOfRemoved=oldPositionFromRemoved1;
										}
										boolean end2 = checkTheTurn(aiAsRandom);
										if(index==0) {
											end1ByAi=end2 || end1ByAi;
										}
//										else if(index==1) {
//											end2ByAi=end2 || end2ByAi;
//										}
//										else if(index==2) {
//											end3ByAi=end2 || end3ByAi;
//										}
										if(index==0) {
											endedByRemove=end2;
										}
										// Player First Turn
										simulatedBoard.switchActivePlayer();
										Object[] playerFirstTurn = aiMiddle.aiTurn();
										simulatedBoard.switchActivePlayer();
										Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
										int oldPosition2 = (int) playerFirstTurn[1];
										boolean end3 = (boolean) playerFirstTurn[5];
										if(index==0) {
											end1ByPlayer=end3;
										}
//										else if(index==1) {
//											end2ByPlayer=end3;
//										}
//										else if(index==2) {
//											end3ByPlayer=end3;
//										}
										Piece whichPieceRemoved1=null;
										int oldPositionFromRemoved2=0; 
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1 = (Piece) playerFirstTurn[6];
											oldPositionFromRemoved2 = (int) playerFirstTurn[4];
										}
										if(counter==0) {
											// Evaluate
											evaluate(end1ByAi, end1ByPlayer);
//											evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
										}
										else {
											counter++;
											makeTheMagicHappen(counter);
											counter--;
										}
										
										whichPieceMoved1.setPosition(oldPosition2);
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
											checkTheTurn(aiAsRandom);
										}
										playerAsMiddleAi.getPieces()[k].setPosition(oldPositionFromRemoved1);
										checkTheTurn(aiAsRandom);
									}
									if(index==0) {
										isThereRemove=false;
									}
								}
							}
							else {
								// Player First Turn
								simulatedBoard.switchActivePlayer();
								Object[] playerFirstTurn = aiMiddle.aiTurn();
								simulatedBoard.switchActivePlayer();
								Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
								int oldPosition2 = (int) playerFirstTurn[1];
								boolean end4 = (boolean) playerFirstTurn[5];
								if(index==0) {
									end1ByPlayer=end4;
								}
//								else if(index==1) {
//									end2ByPlayer=end4;
//								}
//								else if(index==2) {
//									end3ByPlayer=end4;
//								}
								Piece whichPieceRemoved1=null;
								int oldPositionFromRemoved2=0;
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1 = (Piece) playerFirstTurn[6];
									oldPositionFromRemoved2 = (int) playerFirstTurn[4];
								}
								// Evaluate
								if(counter==0) {
									// Evaluate
									evaluate(end1ByAi, end1ByPlayer);
//									evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
								}
								else {
									counter++;
									makeTheMagicHappen(counter);
									counter--;
								}
								
								whichPieceMoved1.setPosition(oldPosition2);
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
								}
							}
							aiAsRandom.getPieces()[i].setPosition(oldPosition1);
						}
					}
				}
			}
		}
		else {
			// Ai First Turn
			for(int i=a;i<b;i++) {
				if(aiAsRandom.getPieces()[i].getPosition()>0) {
					for(int j=1;j<25;j++) {
						if(simulatedBoard.isPlacePossible(j)==true) {
							int oldPosition1 = aiAsRandom.getPieces()[i].getPosition();
							aiAsRandom.placePiece(aiAsRandom.getPieces()[i], j);
							boolean end1 = checkTheTurn(aiAsRandom);
							if(index==0) {
								end1ByAi=end1;
							}
//							else if(index==1) {
//								end2ByAi=end1;
//							}
//							else if(index==2) {
//								end3ByAi=end1;
//							}
							if(index==0) {
								theCurrentPiece = aiAsRandom.getPieces()[i];
								newPosFromCurrent = j;
								isThereRemove=false;
								endedByRemove=false;
								removingPiece=null;
								oldPosOfRemoved=0;
							}
							if(aiAsRandom.getPieces()[i].getIsInMill()==true) {
								for(int k=0;k<9;k++) {
									if((playerAsMiddleAi.getPieces()[k].getIsInMill()==false || playerAsMiddleAi.areAllActiveStoneInMill()==true) && playerAsMiddleAi.getPieces()[k].getPosition()>0) {
										if(index==0) {
											isThereRemove=true;
											removingPiece=playerAsMiddleAi.getPieces()[k];
										}
										int oldPositionFromRemoved1 = playerAsMiddleAi.getPieces()[k].getPosition();
										playerAsMiddleAi.removePieceFromThisPlayer(playerAsMiddleAi.getPieces()[k]);
										if(index==0) {
											oldPosOfRemoved=oldPositionFromRemoved1;
										}
										boolean end2 = checkTheTurn(aiAsRandom);
										if(index==0) {
											end1ByAi=end2 || end1ByAi;
										}
//										else if(index==1) {
//											end2ByAi=end2 || end2ByAi;
//										}
//										else if(index==2) {
//											end3ByAi=end2 || end3ByAi;
//										}
										if(index==0) {
											endedByRemove=end2;
										}
										// Player First Turn
										simulatedBoard.switchActivePlayer();
										Object[] playerFirstTurn = aiMiddle.aiTurn();
										simulatedBoard.switchActivePlayer();
										Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
										int oldPosition2 = (int) playerFirstTurn[1];
										boolean end3 = (boolean) playerFirstTurn[5];
										if(index==0) {
											end1ByPlayer=end3;
										}
//										else if(index==1) {
//											end2ByPlayer=end3;
//										}
//										else if(index==2) {
//											end3ByPlayer=end3;
//										}
										Piece whichPieceRemoved1=null;
										int oldPositionFromRemoved2=0; 
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1 = (Piece) playerFirstTurn[6];
											oldPositionFromRemoved2 = (int) playerFirstTurn[4];
										}
										if(counter==0) {
											// Evaluate
											evaluate(end1ByAi, end1ByPlayer);
//											evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
										}
										else {
											counter++;
											makeTheMagicHappen(counter);
											counter--;
										}
										
										whichPieceMoved1.setPosition(oldPosition2);
										if((boolean)playerFirstTurn[3]==true) {
											whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
											checkTheTurn(aiAsRandom);
										}
										playerAsMiddleAi.getPieces()[k].setPosition(oldPositionFromRemoved1);
										checkTheTurn(aiAsRandom);
									}
									if(index==0) {
										isThereRemove=false;
									}
								}
							}
							else {
								// Player First Turn
								simulatedBoard.switchActivePlayer();
								Object[] playerFirstTurn = aiMiddle.aiTurn();
								simulatedBoard.switchActivePlayer();
								Piece whichPieceMoved1 = simulatedBoard.getPieceByPosition((int) playerFirstTurn[2]);
								int oldPosition2 = (int) playerFirstTurn[1];
								boolean end4 = (boolean) playerFirstTurn[5];
								if(index==0) {
									end1ByPlayer=end4;
								}
//								else if(index==1) {
//									end2ByPlayer=end4;
//								}
//								else if(index==2) {
//									end3ByPlayer=end4;
//								}
								Piece whichPieceRemoved1=null;
								int oldPositionFromRemoved2=0;
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1 = (Piece) playerFirstTurn[6];
									oldPositionFromRemoved2 = (int) playerFirstTurn[4];
								}
								// Evaluate
								if(counter==0) {
									// Evaluate
									evaluate(end1ByAi, end1ByPlayer);
//									evaluate(end1ByAi, end1ByPlayer, end2ByAi, end2ByPlayer, end3ByAi, end3ByPlayer);
								}
								else {
									counter++;
									makeTheMagicHappen(counter);
									counter--;
								}
								
								whichPieceMoved1.setPosition(oldPosition2);
								if((boolean)playerFirstTurn[3]==true) {
									whichPieceRemoved1.setPosition(oldPositionFromRemoved2);
								}
							}
							aiAsRandom.getPieces()[i].setPosition(oldPosition1);
							checkTheTurn(aiAsRandom);
						}
					}
				}
			}
		}
	}
	
	private boolean checkTheTurn(Player selectedPlayer) {
		for(int j=0;j<9;j++) {
			selectedPlayer.checkForMill(selectedPlayer.getPieces()[j]);
			selectedPlayer.getOpponent().checkForMill(selectedPlayer.getOpponent().getPieces()[j]);
		}
		boolean completelyBlocked = true;
		for(int j=0;j<9;j++) {
			if(simulatedBoard.canPieceMove(selectedPlayer.getOpponent().getPieces()[j])==true || selectedPlayer.getOpponent().hasToJump()==true) {
				completelyBlocked=false;
				break;
			}
		}
		if(completelyBlocked==true || selectedPlayer.getOpponent().didPlayerLose()) {
			return true;
		}
		return false;
	}
	
	private void evaluate(boolean aiEnded1, boolean playerEnded1) {
		int points=0;
		if(aiEnded1==true) {
			points+=1000;
		}
		if(aiEnded1!=true && playerEnded1==true) {
			points-=1000;
		}
		
		
		int activePiecesPoints = (9-aiAsRandom.nonActivePieces()) - (9 - playerAsMiddleAi.nonActivePieces());
		activePiecesPoints = activePiecesPoints * (10);
		points += activePiecesPoints;
		
		Object[][] aiAllMills = aiAsRandom.getAllMills();
		for(int i=0;i<aiAllMills.length;i++) {
			if(aiAllMills[i][0]==null) {
				break;
			}
			if(simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) aiAllMills[i][0], playerAsMiddleAi)==true && simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) aiAllMills[i][1], playerAsMiddleAi)==true && simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) aiAllMills[i][2], playerAsMiddleAi)==true) {
				points+=25;
			}
			else {
				points+=10;
			}
		}
		
		Object[][] playerAllMills = playerAsMiddleAi.getAllMills();
		for(int i=0;i<playerAllMills.length;i++) {
			if(playerAllMills[i][0]==null) {
				break;
			}
			if(simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) playerAllMills[i][0], aiAsRandom)==true && simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) playerAllMills[i][1], aiAsRandom)==true && simulatedBoard.canPieceMoveWithoutBeingBlockedNextTurnByOpponent((Piece) playerAllMills[i][2], aiAsRandom)==true) {
				points-=26;
			}
			else {
				points-=12;
			}
		}
		
		for(int i=0;i<9;i++) {
			if(aiAsRandom.getPieces()[i].getPosition()>=0) {
				for(int j=1;j<25;j++) {
					if(simulatedBoard.canThisPieceMoveToThisPos(aiAsRandom.getPieces()[i], j)==true) {
						int oldPos = aiAsRandom.getPieces()[i].getPosition();
						aiAsRandom.getPieces()[i].setPosition(j);
						checkTheTurn(aiAsRandom);
						if(aiAsRandom.getPieces()[i].getIsInMill()==true) {
							points+=3;
						}
						aiAsRandom.getPieces()[i].setPosition(oldPos);
						checkTheTurn(aiAsRandom);
					}
				}
			}
		}
		
		boolean usedTurn =false;
		int safedPosForTurnBlock =0;
		for(int i=0;i<9;i++) {
			if(playerAsMiddleAi.getPieces()[i].getPosition()>=0) {
				for(int j=1;j<25;j++) {
					if(simulatedBoard.canThisPieceMoveToThisPos(playerAsMiddleAi.getPieces()[i], j)==true && (simulatedBoard.canAPieceMoveToPosition(j, aiAsRandom)[0]==null || (usedTurn=true && safedPosForTurnBlock != j))) {
						int oldPos = playerAsMiddleAi.getPieces()[i].getPosition();
						playerAsMiddleAi.getPieces()[i].setPosition(j);
						checkTheTurn(playerAsMiddleAi);
						if(playerAsMiddleAi.getPieces()[i].getIsInMill()==true) {
							points-=4;
						}
						playerAsMiddleAi.getPieces()[i].setPosition(oldPos);
						checkTheTurn(playerAsMiddleAi);
					}
					if(simulatedBoard.canAPieceMoveToPosition(j, aiAsRandom)[0]!=null) {
						usedTurn=true;
						safedPosForTurnBlock=j;
					}
				}
			}
		}
		
		
		
		if(highestEvaluation[0]==null || (int)highestEvaluation[0]<=points) {
			if(highestEvaluation[0]!=null) {
				int refresh = (int) (Math.random()*2);
				if(refresh==1) {
					return;
				}
			}
			highestEvaluation[0]=points;
			highestEvaluation[1]=theCurrentPiece;
			highestEvaluation[2]=newPosFromCurrent;
			highestEvaluation[3]=isThereRemove;
			highestEvaluation[4]=removingPiece;
			highestEvaluation[5]=oldPosOfRemoved;
			highestEvaluation[6]=endedByRemove;
		}
	}
}
