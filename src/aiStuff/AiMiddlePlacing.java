package aiStuff;

import java.io.Serializable;

import sample.Board;
import sample.Piece;
import sample.Player;

public class AiMiddlePlacing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3168029333600412494L;
	private Player player;
	private Player ai;
	private Board board;
	private Piece[] aiPieces;
	private Piece[] playerPieces;
	private int[][] boardAsArray;
	private int wherePlace;
	private int selectedOpeningLength;
	
	public AiMiddlePlacing(Player player, Player ai, Board board) {
		wherePlace=5000;
		this.player=player;
		this.ai=ai;
		this.board=board;
		this.aiPieces=this.ai.getPieces();
		this.playerPieces=this.player.getPieces();
		this.boardAsArray=this.board.getBoardAsArray();
	}
	
	public int whereToPlace() {
		if(isMillOpenForPlacing(ai,0)==false) {
			if(preventOpponentMill()==false) {
				if(preventOpponentCross(1)==false) {
					if(recognizeCrossOpportunity(1)==false) {
						if(preventOpponentOpening()==false) {
							if(opening(ai)==false) {
								if(opponentMillBlockable()==false) {
									if(recognizeCrossOpportunity(0)==false) {
										if(doubleCrossPrep()==false) {
											if(twoPiecesNearbyAndThirdIsFree()==false) {
												fieldWithMostMobility();
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
			}
			else {
			}
		}
		else {
		}
		return wherePlace;
	}
	
	// mode 0 for placing; mode 1 for moving
	private boolean isMillOpenForPlacing(Player checkingFor, int mode) {
		int[][][] millPos = checkingFor.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = checkingFor.getPieces()[j].getPosition();
			if(pos1>0) {
				for(int k=0;k<9;k++) {
					int pos2 = checkingFor.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2) {
						for(int l=1;l<25;l++) {
							boolean check3=true;
							if(mode==0) {
								if(board.isPlacePossible(l)==false) {
									check3=false;
								}
							}
							else {
								if(board.canAPieceMoveToPosition(l, checkingFor)==null) {
									check3=false;
								}
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
										wherePlace=l;
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
	
	private boolean preventOpponentMill() {
		if(player.inPlacePhase()==true) {
			if(isMillOpenForPlacing(player,0)==true) {
				if(board.isPlacePossible(wherePlace)==true) {
					return true;
				}
				return false;
			}
			return false;
		}
		else {
			if(isMillOpenForPlacing(player,1)==true) {
				if(board.isPlacePossible(wherePlace)==true) {
					return true;
				}
				return false;
			}
			return false;
		}
	}
	
	private boolean preventOpponentOpening() {
		opening(ai);
		int ownOpeningLength = selectedOpeningLength;
		if(opening(player)==true && selectedOpeningLength==1 && ownOpeningLength!=selectedOpeningLength) {
			return true;
		}
		return false;
	}
	
	private boolean preventOpponentCross(int mode) {
		if(player.howManyPieceCanPlayerPlace()<(mode+1)) {
			return false;
		}
		int i=0;
		for(i=0;i<9;i++) {
			if(playerPieces[i].getPosition()==0) {
				break;
			}
		}
		for(int j=1;j<25;j++) {
			int savePos= playerPieces[i].getPosition();
			if(board.isPlacePossible(j)==true) {
				playerPieces[i].setPosition(j);
				if(crossHelper(playerPieces[i],player)==true) {
					// reset if I need maybe anywhere else
					wherePlace=j;
					playerPieces[i].setPosition(savePos);
					return true;
				}
				playerPieces[i].setPosition(savePos);
			}
		}
		return false;
	}
	
	// Checking if openings still possible (every needed place free and placing phase long enough!)
	private boolean opening(Player selectedPlayer) {
		// 0-7 diagonal for outer; 8-15 for inner; 16-23 for middle; 24-35 for cross for outer; 36-47 for inner / middle
		// -> order could be wrong; outer is definitely first rest idk for sure
		int[][] allOpenings = {{17,21},{17,21},{17,21},{17,21},{19,23},{19,23},{19,23},{19,23},{11,15},{11,15},{11,15},{11,15},{9,13},{9,13},{9,13},{9,13},{1,5},{1,5},{1,5},{1,5},{3,7},{3,7},{3,7},{3,7},{10,17},{10,17},{10,19},{12,19},{12,19},{12,21},{14,21},{14,21},{14,23},{16,23},{16,23},{16,17},{2,9},{2,9},{2,11},{4,11},{4,11},{4,13},{6,13},{6,13},{6,15},{8,15},{8,15},{8,9}};
		int[][] completeOpenings = {{17,21,19,18},{17,21,19,20},{17,21,23,22},{17,21,22,24},{19,23,21,20},{19,23,21,22},{19,23,17,18},{19,23,17,24},{11,15,13,12},{11,15,13,14},{11,15,9,10},{11,15,9,16},{9,13,11,10},{9,13,11,12},{9,13,15,14},{9,13,15,16},{1,5,3,2},{1,5,3,4},{1,5,7,6},{1,5,7,8},{3,7,5,4},{3,7,5,6},{3,7,1,2},{3,7,1,8},{10,17,18,19},{10,17,18,2},{10,19,18,2},{12,19,20,21},{12,19,20,4},{12,21,20,4},{14,21,22,23},{14,21,22,6},{14,23,22,6},{16,23,24,17},{16,23,24,8},{16,17,24,8},{2,9,10,11},{2,9,10,18},{2,11,10,18},{4,11,12,13},{4,11,12,20},{4,13,12,20},{6,13,14,15},{6,13,14,22},{6,15,14,22},{8,15,16,9},{8,15,16,24},{8,9,16,24}};
		int[] possibleOpenings = new int[allOpenings.length];
		int indexCounter=0;
		boolean check=true;
		int disableCounter=0;
		for(int i=0;i<allOpenings.length;i++) {
			if(disableCounter==0) {
				check=true;
				int a=0;
				if(i<24) {
					a=4;
				}
				else{
					if(i<46) {
						a=3;
					}
					else if(i==46) {
						a=2;
					}
					else {
						a=1;
					}
				}
				for(int x=0;x<a;x++) {
					for(int j=0;j<4;j++) {
						for(int k=0;k<9;k++) {
							if(selectedPlayer.getOpponent().getPieces()[k].getPosition()==completeOpenings[i+x][j] || selectedPlayer.getPieces()[k].getIsInMill()) {
								check=false;
								if(i<24) {
									disableCounter=3;
								}
								else {
									disableCounter=2;
								}
								break;
							}
						}
						if(check==false) {
							break;
						}
					}
				}
				if(check==true) {
					possibleOpenings[indexCounter]=i;
					indexCounter++;
				}
			}
			else {
				disableCounter--;
			}
		}
		if(indexCounter==0) {
			return false;
		}
		int[] openingLength = new int[possibleOpenings.length];
		for(int i=0;i<possibleOpenings.length;i++) {
			if(possibleOpenings[i]!=0) {
				for(int j=0;j<2;j++) {
					for(int k=0;k<9;k++) {
						if(allOpenings[possibleOpenings[i]][j]==selectedPlayer.getPieces()[k].getPosition()) {
							openingLength[i]++;
						}
					}
				}
			}
		}
		// no 2 because this would mean mill is already finished
		for(int j=1;j>=0;j--) {
			for(int i=0;i<openingLength.length;i++) {
				if(possibleOpenings[i]!=0) {
					if(openingLength[i]==j) {
						// Not enough placing turns to finish the opening / that means no openings that can't finish!
						if(selectedPlayer.howManyPieceCanPlayerPlace()<(4-(j+2))) {
							return false;
						}
						for(int k=0;k<2;k++) {
							boolean isFree=true;
							// which position of the 2 is free
							for(int l=0;l<9;l++) {
								if(allOpenings[possibleOpenings[i]][k]==selectedPlayer.getPieces()[l].getPosition()) {
									isFree=false;
									break;
								}
							}
							if(isFree==true) {
								selectedOpeningLength=openingLength[i];
								wherePlace=allOpenings[possibleOpenings[i]][k];
								return true;
							}
						}
					}
				}
			}
		}
		// Should never occur!
		System.out.println("Error in AiMiddlePlacing!");
		return false;
	}
	
	private boolean recognizeCrossOpportunity(int mode) {
		if(ai.howManyPieceCanPlayerPlace()<(mode+1)) {
			return false;
		}
		int i=0;
		for(i=0;i<9;i++) {
			if(aiPieces[i].getPosition()==0) {
				break;
			}
		}
		for(int j=1;j<25;j++) {
			int savePos= aiPieces[i].getPosition();
			if(board.isPlacePossible(j)==true) {
				aiPieces[i].setPosition(j);
				if(crossHelper(aiPieces[i],ai)==true) {
					wherePlace=j;
					aiPieces[i].setPosition(savePos);
					return true;
				}
				aiPieces[i].setPosition(savePos);
			}
		}
		return false;
	}
	
	private boolean crossHelper(Piece selectedPiece, Player selectedPlayer) {
		boolean found=false;
		int saveAgainstDouble=0;
		int[][][] millPos = selectedPlayer.getMillPossibilities();
		for(int j=0;j<9;j++) {
			int pos1 = selectedPlayer.getPieces()[j].getPosition();
			if(pos1>0 && pos1!=selectedPiece.getPosition()) {
				for(int k=0;k<9;k++) {
					int pos2 = selectedPlayer.getPieces()[k].getPosition();
					if(pos2>0 && pos1!=pos2 && pos2!=selectedPiece.getPosition()) {
						for(int y=0;y<2;y++) {
							boolean check1 =false;
							boolean check2 =false;
							for(int x=0;x<2;x++) {
								if(millPos[selectedPiece.getPosition()][y][x]==pos1) {
									int xx=0;
									if(x==0) {
										xx=1;
									}
									else {
										xx=0;
									}
									if(board.isPlacePossible(millPos[selectedPiece.getPosition()][y][xx])==true) {
										check1=true;
									}
								}
								if(millPos[selectedPiece.getPosition()][y][x]==pos2) {
									int xx=0;
									if(x==0) {
										xx=1;
									}
									else {
										xx=0;
									}
									if(board.isPlacePossible(millPos[selectedPiece.getPosition()][y][xx])==true) {
										check2=true;
									}
								}
							}
							if(check1==true && found==true && saveAgainstDouble!=pos1) {
								return true;
							}
							if(check2==true && found==true && saveAgainstDouble!=pos2) {
								return true;
							}
							if(check1==true) {
								saveAgainstDouble=pos1;
								found=true;
							}
							if(check2==true) {
								saveAgainstDouble=pos2;
								found=true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean doubleCrossPrep() {
		if(ai.howManyPieceCanPlayerPlace()<3) {
			return false;
		}
		int i=0;
		for(i=0;i<9;i++) {
			if(aiPieces[i].getPosition()==0) {
				break;
			}
		}
		int k=0;
		for(k=0;k<9;k++) {
			if(aiPieces[k].getPosition()==0 && k!=i) {
				break;
			}
		}
		for(int j=1;j<25;j++) {
			int savePos1= aiPieces[i].getPosition();
			if(board.isPlacePossible(j)==true) {
				for(int l=1;l<25;l++) {
					int savePos2=aiPieces[k].getPosition();
					if(board.isPlacePossible(l)==true) {
						aiPieces[i].setPosition(j);
						aiPieces[k].setPosition(l);
						if(crossHelper(aiPieces[i],ai)==true) {
							// reset if I need maybe anywhere else
							wherePlace=l;
							aiPieces[i].setPosition(savePos1);
							aiPieces[k].setPosition(savePos2);
							return true;
						}
						if(crossHelper(aiPieces[k],ai)==true) {
							// reset if I need maybe anywhere else
							wherePlace=j;
							aiPieces[i].setPosition(savePos1);
							aiPieces[k].setPosition(savePos2);
							return true;
						}
						aiPieces[i].setPosition(savePos1);
						aiPieces[k].setPosition(savePos2);
					}
				}
			}
		}
		return false;
	}
	
	// not a real block; uses canPieceMoveWithoutBeingBlockedNextTurnByOpponent
	private boolean opponentMillBlockable() {
		int[][][] millPos = player.getMillPossibilities();
		for(int i=1;i<millPos.length;i++) {
			for(int j=0;j<9;j++) {
				Piece piece1=null;
				Piece piece2=null;
				Piece piece3=null;
				if(playerPieces[j].getIsInMill()==true && playerPieces[j].getPosition()>0) {   // playerPieces[j].getPosition()>0 needed for ai Hard mode prob too many calculations idc
					piece1=playerPieces[j];
					for(int k=0;k<2;k++) {
						boolean check1=false;
						boolean check2=false;
						for(int l=0;l<2;l++) {
							for(int y=0;y<9;y++) {
								if(millPos[piece1.getPosition()][k][l]==playerPieces[y].getPosition()) {
									if(playerPieces[y].getPosition()!=0) {
										if(check1==false) {
											piece2=playerPieces[y];
											check1=true;
											break;
										}
										else {
											piece3=playerPieces[y];
											check2=true;
											break;
										}
									}
								}
							}
						}
						int field=0;
						int counter=0;
						if(check1==true && check2==true) {
							if(board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece1,player)==true && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece2,player)==false && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece3,player)==false) {
								for(int x=0;x<4;x++) {
									if(board.isPlacePossible(boardAsArray[piece1.getPosition()][x])==true) {
										field=boardAsArray[piece1.getPosition()][x];
										counter++;
									}
								}
							}
							else if(board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece1,player)==false && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece2,player)==true && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece3,player)==false) {
								for(int x=0;x<4;x++) {
									if(board.isPlacePossible(boardAsArray[piece2.getPosition()][x])==true) {
										field=boardAsArray[piece2.getPosition()][x];
										counter++;
									}
								}
							}
							else if(board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece1,player)==true && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece2,player)==false && board.canPieceMoveWithoutBeingBlockedNextTurnByOpponent(piece3,player)==true) {
								for(int x=0;x<4;x++) {
									if(board.isPlacePossible(boardAsArray[piece3.getPosition()][x])==true) {
										field=boardAsArray[piece3.getPosition()][x];
										counter++;
									}
								}
							}
							if(counter==1 || counter==2) {
								wherePlace=field;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean twoPiecesNearbyAndThirdIsFree() {
		int i=0;
		for(i=0;i<9;i++) {
			if(aiPieces[i].getPosition()==0) {
				break;
			}
		}
		for(int j=1;j<25;j++) {
			int savePos= aiPieces[i].getPosition();
			if(board.isPlacePossible(j)==true) {
				aiPieces[i].setPosition(j);
				if(isMillOpenForPlacing(ai, 0)==true) {
					wherePlace=j;
					aiPieces[i].setPosition(savePos);
					return true;
				}
				aiPieces[i].setPosition(savePos);
			}
		}
		return false;
	}
	
	private void fieldWithMostMobility() {
		int[] allFieldsPossible = new int[24];
		int indexCounter=0;
		for(int i=1;i<25;i++) {
			if(board.isPlacePossible(i)==true) {
				allFieldsPossible[indexCounter]=i;
				indexCounter++;
			}
		}
		int[] howManyMoves = new int[allFieldsPossible.length];
		for(int i=0;i<howManyMoves.length;i++) {
			if(allFieldsPossible[i]!=0) {
				for(int j=0;j<4;j++) {
					if(board.isPlacePossible(boardAsArray[allFieldsPossible[i]][j])==true) {
						if(boardAsArray[allFieldsPossible[i]][j]!=0) {
							howManyMoves[i]++;
						}
					}
				}
			}
		}
		for(int i=4;i>=0;i--) {
			for(int j=0;j<howManyMoves.length;j++) {
				if(allFieldsPossible[j]==0) {
					break;
				}
				if(howManyMoves[j]==i) {
					wherePlace=allFieldsPossible[j];
					return;
				}
			}
		}
	}
	
}
