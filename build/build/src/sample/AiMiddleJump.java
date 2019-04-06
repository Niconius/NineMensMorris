package sample;

public class AiMiddleJump {
	
	private Player player;
	private Player ai;
	private Board board;
	private int whereJump;
	private Piece whichPiece;
	
	public AiMiddleJump(Player player, Player ai, Board board) {
		whereJump=5000;
		whichPiece=null;
		this.player=player;
		this.ai=ai;
		this.board=board;
	}
	
	public Object[] whereToJump() {
		if(closeMill()==false) {
			if(blockOpponentMill()==false) {
				randomJump();
				System.out.println("RandomJump");
			}
			else {
				System.out.println("BlockMill");
			}
		}
		else {
			System.out.println("CloseMill");
		}
		
		
		Object[] arr = {whichPiece,whereJump};
		return arr;
	}
	
	private boolean closeMill() {
		if(isMillOpenForPlacing(ai, 0)==true) {
			return true;
		}
		return false;
	}
	
	private boolean blockOpponentMill() {
		if(player.hasToJump()==true) {
			if(isMillOpenForPlacing(player, 0)==true) {
				for(int i=0;i<9;i++) {
					if(ai.getPieces()[i].getPosition()>0) {
						int savePos=ai.getPieces()[i].getPosition();
						ai.getPieces()[i].setPosition(whereJump);
						if(isMillOpenForPlacing(player, 0)==false) {
							ai.getPieces()[i].setPosition(savePos);
							whichPiece=ai.getPieces()[i];
							return true;
						}
						ai.getPieces()[i].setPosition(savePos);
					}
				}
			}
		}
		else {
			if(isMillOpenForPlacing(player, 1)==true) {
				for(int i=0;i<9;i++) {
					if(ai.getPieces()[i].getPosition()>0) {
						int savePos=ai.getPieces()[i].getPosition();
						ai.getPieces()[i].setPosition(whereJump);
						if(isMillOpenForPlacing(player, 1)==false) {
							ai.getPieces()[i].setPosition(savePos);
							whichPiece=ai.getPieces()[i];
							return true;
						}
						ai.getPieces()[i].setPosition(savePos);
					}
				}
			}
		}
		return false;
	}
	
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
								else {
									boolean found=false;
									for(int i=0;i<9;i++) {
										if(checkingFor.getPieces()[i].getPosition()>0 && checkingFor.getPieces()[i].getPosition()!=pos1 && checkingFor.getPieces()[i].getPosition()!=pos2) {
											whichPiece=checkingFor.getPieces()[i];
											found=true;
										}
									}
									if(found=false) {
										check3=false;
									}
								}
							}
							else {
								if(board.canAPieceMoveToPosition(l, checkingFor)[0]==null) {
									check3=false;
								}
								else {
									if(((Piece) board.canAPieceMoveToPosition(l, checkingFor)[0]).getPosition()>0) {
										whichPiece=(Piece) board.canAPieceMoveToPosition(l, checkingFor)[0];
									}
									else {
										check3=false;
									}
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

	private void randomJump() {
		boolean active=true;
		int counter=0;
		while(active==true) {
			int random=(int) (Math.random()*9);
			if(ai.getPieces()[random].getPosition()>0) {
				boolean active2=true;
				while(active2==true) {
					int random2 = (int) ((Math.random()*24)+1);
					if(board.isPlacePossible(random2)==true) {
						int savePos = ai.getPieces()[random].getPosition();
						ai.getPieces()[random].setPosition(random2);
						boolean found=false;
						if(player.hasToJump()==true) {
							if(isMillOpenForPlacing(player, 0)==true) {
								found=true;
							}
						}
						else {
							if(isMillOpenForPlacing(player, 1)==true) {
								found=true;
							}
						}
						if(found==true) {
							active2=false;
							counter++;
						}
						else {
							ai.getPieces()[random].setPosition(savePos);
							whereJump=random2;
							whichPiece=ai.getPieces()[random];
							return;
						}
						ai.getPieces()[random].setPosition(savePos);
					}
				}
			}
			if(counter==1000000) {
				active=false;
				break;
			}
		}
		active=true;
		while(active==true) {
			int random=(int) (Math.random()*9);
			if(ai.getPieces()[random].getPosition()>0) {
				boolean active2=true;
				while(active2==true) {
					int random2 = (int) ((Math.random()*24)+1);
					if(board.isPlacePossible(random2)==true) {
						whereJump=random2;
						whichPiece=ai.getPieces()[random];
						return;
					}
				}
			}
		}
	}
	
	
	
	
}
