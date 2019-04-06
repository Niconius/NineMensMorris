package sample;

public class Board {

	private Player one;
	private Player two;
	private Piece[] allPieces;
	private final int[][] boardAsArray;
	
	public Board(Player one, Player two) {
		this.one=one;
		this.two=two;
		this.one.setOpponent(two);
		this.one.setBoard(this);
		this.two.setOpponent(one);
		this.two.setBoard(this);
		allPieces = new Piece[18];
		Player selectedPlayer = this.one; 
		for(int i=0;i<2;i++) {
			for(int j=0;j<9;j++) {
				if(i==0) {
					allPieces[j] = selectedPlayer.getPieces()[j];
				}
				else {
					allPieces[j+9] = selectedPlayer.getPieces()[j];
				}
			}	
			selectedPlayer = this.two;
		}
		boardAsArray = new int[][] {{0},{0,2,8,0},{10,3,0,1},{0,0,4,2},{3,12,5,0},{4,0,0,6},{0,5,14,7},{8,6,0,0},{1,0,7,16},{0,10,16,0},{18,11,2,9},{0,0,12,10},{11,20,13,4},{12,0,0,14},{6,13,22,15},{16,14,0,0},{9,8,15,24},{0,18,24,0},{0,19,10,17},{0,0,20,18},{19,0,21,12},{20,0,0,22},{14,21,0,23},{24,22,0,0},{17,16,23,0}};
	}
	
	public boolean isMovePossible(int direction, Piece selectedPiece) {
		int position = selectPiece(selectedPiece).getPosition();
		if(position<=0) {
			return false;
		}
		int newPosition = boardAsArray[position][direction];
		if(newPosition==0) {
			return false;
		}
		if(isPlacePossible(newPosition)==true) {
			return true;
		}
		return false;
	}
	
	public Object[] canAPieceMoveToPosition(int position ,Player checkingFor) {
		int[][] arr = new int[boardAsArray.length][2];
		int arrCounter=0;
		for(int i=1;i<boardAsArray.length;i++) {
			for(int j=0;j<4;j++) {
				if(boardAsArray[i][j]==position) {
					arr[arrCounter][0]=i;
					arr[arrCounter][1]=j;
					arrCounter++;
				}
			}
		}
		for(int i=0;i<arr.length;i++) {
			if(arr[i][0]==0) {
				break;
			}
			for(int j=0;j<9;j++) {
				if(arr[i][0]==checkingFor.getPieces()[j].getPosition()) {
					if(isMovePossible(arr[i][1], checkingFor.getPieces()[j])==true) {
						Object[] array = {checkingFor.getPieces()[j],arr[i][1]};
						return array;
					}
				}
			}
		}
		Object[] array = {null,0};
		return array;
	}
	
	public Object[] canAPieceMoveToPositionWithout2(int position, Player checkingFor, Piece whithout1, Piece whithout2) {
		if(checkingFor.inPlacePhase()==true) {
			for(int i=0;i<9;i++) {
				if(checkingFor.getPieces()[i].getPosition()==0 && isPlacePossible(position)==true) {
					Object[] array = {checkingFor.getPieces()[i],0};
					return array;
				}
			}
		}
		
		int[][] arr = new int[boardAsArray.length][2];
		int arrCounter=0;
		for(int i=1;i<boardAsArray.length;i++) {
			for(int j=0;j<4;j++) {
				if(boardAsArray[i][j]==position) {
					arr[arrCounter][0]=i;
					arr[arrCounter][1]=j;
					arrCounter++;
				}
			}
		}
		for(int i=0;i<arr.length;i++) {
			if(arr[i][0]==0) {
				break;
			}
			for(int j=0;j<9;j++) {
				if(arr[i][0]==checkingFor.getPieces()[j].getPosition() && checkingFor.getPieces()[j]!=whithout1 && checkingFor.getPieces()[j]!=whithout2) {
					if(isMovePossible(arr[i][1], checkingFor.getPieces()[j])==true) {
						Object[] array = {checkingFor.getPieces()[j],arr[i][1]};
						return array;
					}
				}
			}
		}
		Object[] array = {null,0};
		return array;
	}
	
	public boolean isPlacePossible(int newPosition) {
		// no check if newPosition maybe over 24 or under 1!
		for(int i=0;i<18;i++) {
			if(allPieces[i].getPosition()==newPosition) {
				return false;
			}
		}
		return true;
	}
	
	private Piece selectPiece(Piece selectedPiece) {
		int i;
		for(i=0;i<18;i++) {
			if(allPieces[i]==selectedPiece) {
				return allPieces[i];
			}
		}
		return null;
	}
	
	public int[][] getBoardAsArray(){
		return boardAsArray;
	}
	
	public Piece[] getAllPieces() {
		return allPieces;
	}
	
	public boolean canPlayerJump(Player player) {
		if(player.nonActivePieces()==6) {
			return true;
		}
		return false;
	}
	
	public Player whoIsActive() {
		if(one.getActiveState()==true) {
			return one;
		}
		return two;
	}
	
	public void switchActivePlayer() {
		one.setActiveState(!one.getActiveState());
		two.setActiveState(!two.getActiveState());
	}
	
	public boolean canPieceMove(Piece selectedPiece) {
		int position = selectedPiece.getPosition();
		if(position==0) {
			return true;
		}
		if(position==-1) {
			return false;
		}
		if((selectedPiece.getColor()==one.getColor() && one.hasToJump()==true) || (selectedPiece.getColor()==one.getColor() && two.hasToJump()==true)) {
			return true;
		}
		int counter=0;
		int counter2=0;
		for(int i=0;i<4;i++) {
			if(boardAsArray[position][i]!=0) {
				counter++;
				for(int j=0;j<allPieces.length;j++) {		
					if(allPieces[j].getPosition()==boardAsArray[position][i]) {
						counter2++;
						break;
					}
				}
			}
		}
		if(counter==counter2) {
			return false;
		}
		return true;
	}
	
	public boolean canPieceMoveWithoutBeingBlockedNextTurnByOpponent(Piece selectedPiece, Player opponent) {
		if(canPieceMove(selectedPiece)==false) {
			return false;
		}
		int position = selectedPiece.getPosition(); 
		for(int i=0;i<4;i++) {
			if(boardAsArray[position][i]!=0) {
				for(int j=0;j<9;j++) {		
					if(opponent.getPieces()[j].getPosition()==boardAsArray[position][i]) {
						return false;
					}
				}
			}
		}
		return true;	
	}
	
}
