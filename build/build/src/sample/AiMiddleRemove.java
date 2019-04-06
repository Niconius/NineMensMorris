package sample;

public class AiMiddleRemove {
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
					if(openMillInPlacing()==false) {
						if(openMillWhileMoving()==false) {
							if(pieceThatBlocksMill()==false) {
								randomPiece();
								System.out.println("Remove:RandomPiece");
							}
							else {
								System.out.println("Remove:PieceBlockedMill");
							}
						}
						else {
							System.out.println("Remove:OpenMillWhileMoving");
						}
					}
					else {
						System.out.println("Remove:OpenMillInPlacing");
					}
				}
				else {
					System.out.println("Remove:OpponentCouldMakeMillWithIt");
				}
			}
			else {
				System.out.println("Remove:PieceWouldCompletelyBlockNextTurn");
			}
		}
		else {
			System.out.println("Remove:OpponentIsBlockedNow");
		}
		return whichPiece;
	}
	
	private boolean opponentIsBlocked() {
		System.out.println("1");
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
		System.out.println("2");
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
		System.out.println("3");
		if(isAMillOpenForClosingAdapted(player)==true) {
			System.out.println("why");
			for(int j=4;j>=1;j--) {
				for(int i=0;i<allPiecesForMill.length;i++) {
					if(allPiecesForMill[i][0]==null) {
						break;
					}
					System.out.println("k");
					if(((Piece) allPiecesForMill[i][0]).getPosition()>0 && (int)allPiecesForMill[i][1]==j && (((Piece) allPiecesForMill[i][0]).getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
						System.out.println("kö");
						whichPiece=(Piece) allPiecesForMill[i][0];
						System.out.println("no");
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

	private boolean openMillInPlacing() {
		if(ai.inPlacePhase()==true) {
			for(int i=0;i<9;i++) {
				if(player.getPieces()[i].getPosition()>0 && (player.getPieces()[i].getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
					int counter1= openMillCounter(ai);
					int posSave=player.getPieces()[i].getPosition();
					player.getPieces()[i].setPosition(-1);
					int counter2=openMillCounter(ai);
					player.getPieces()[i].setPosition(posSave);
					System.out.println(counter1 + " " + counter2);
					if(counter2>counter1) {
						whichPiece=player.getPieces()[i];
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean openMillWhileMoving() {
		System.out.println("heykksk");
		if(ai.inPlacePhase()==false) {
			for(int i=0;i<9;i++) {
				if(player.getPieces()[i].getPosition()>0 && (player.getPieces()[i].getIsInMill()==false || player.areAllActiveStoneInMill()==true)) {
					int counter1= openMillCounter(ai);
					int posSave=player.getPieces()[i].getPosition();
					player.getPieces()[i].setPosition(-1);
					int counter2=openMillCounter(ai);
					if(counter2>counter1) {
						if(board.canAPieceMoveToPosition(posSave, ai)[0]!=null) {
							Piece thePiece =(Piece) board.canAPieceMoveToPosition(posSave, ai)[0];
							int posSave2 = thePiece.getPosition();
							thePiece.setPosition(posSave);
							player.checkForMill(thePiece);
							if(thePiece.getIsInMill()==true) {
								thePiece.setPosition(posSave2);
								player.getPieces()[i].setPosition(posSave);
								player.checkForMill(thePiece);
								whichPiece=player.getPieces()[i];
								return true;
							}
							thePiece.setPosition(posSave2);
							player.checkForMill(thePiece);
						}
					}
					player.getPieces()[i].setPosition(posSave);
				}
			}
		}
		return false;	
	}
	
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
								System.out.println(l +" "+pos1 +" "+ pos2);
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
	
	private boolean pieceThatBlocksMill() {
		System.out.println("4");
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
	
	private void randomPiece() {
		System.out.println("5");
		boolean active=true;
		while(active=true) {
			int random = (int) (Math.random()*9);
			if(player.getPieces()[random].getPosition()>0 && player.getPieces()[random].getIsInMill()==false || player.areAllActiveStoneInMill()==true) {
				whichPiece=player.getPieces()[random];
				return;
			}
		}
	}
	
	
}
