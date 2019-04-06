package sample;

import javafx.scene.image.Image;

public class Player {

	private Piece[] pieces;
	private String color;
	private int id;
	private Board board;
	private Player opponent;
	private int onlyOneOpponent;
	private int onlyOneBoard;
	private final int[][][] millPossibilities;
	private boolean active;
	private Image[] allImages;
	private boolean removeMaskOpen;
	
	public Player(String color, int id) {
		this.removeMaskOpen=false;
		onlyOneOpponent=0;
		onlyOneBoard=0;
		this.color=color;
		this.id=id;
		if(this.color.equals("white")) {
			Image imgW1 = new Image(getClass().getResourceAsStream("/pictures/whitePiece1.png"));		
			Image imgW2 = new Image(getClass().getResourceAsStream("/pictures/whitePiece2.png"));		
			Image imgW3 = new Image(getClass().getResourceAsStream("/pictures/whitePiece3.png"));		
			Image imgW4 = new Image(getClass().getResourceAsStream("/pictures/whitePiece4.png"));		
			Image imgW5 = new Image(getClass().getResourceAsStream("/pictures/whitePiece5.png"));		
			Image imgW6 = new Image(getClass().getResourceAsStream("/pictures/whitePiece6.png"));		
			Image imgW7 = new Image(getClass().getResourceAsStream("/pictures/whitePiece7.png"));		
			Image imgW8 = new Image(getClass().getResourceAsStream("/pictures/whitePiece8.png"));		
			Image imgW9 = new Image(getClass().getResourceAsStream("/pictures/whitePiece9.png"));
			allImages = new Image[] {imgW1,imgW2,imgW3,imgW4,imgW5,imgW6,imgW7,imgW8,imgW9};
		}
		else {
			Image imgB1 = new Image(getClass().getResourceAsStream("/pictures/blackPiece1.png"));
			Image imgB2 = new Image(getClass().getResourceAsStream("/pictures/blackPiece2.png"));
			Image imgB3 = new Image(getClass().getResourceAsStream("/pictures/blackPiece3.png"));
			Image imgB4 = new Image(getClass().getResourceAsStream("/pictures/blackPiece4.png"));
			Image imgB5 = new Image(getClass().getResourceAsStream("/pictures/blackPiece5.png"));
			Image imgB6 = new Image(getClass().getResourceAsStream("/pictures/blackPiece6.png"));
			Image imgB7 = new Image(getClass().getResourceAsStream("/pictures/blackPiece7.png"));
			Image imgB8 = new Image(getClass().getResourceAsStream("/pictures/blackPiece8.png"));
			Image imgB9 = new Image(getClass().getResourceAsStream("/pictures/blackPiece9.png"));
			allImages = new Image[] {imgB1,imgB2,imgB3,imgB4,imgB5,imgB6,imgB7,imgB8,imgB9};
		}
		
		pieces= new Piece[9];
		for(int i=0;i<9;i++) {
			pieces[i]= new Piece(this.color,(i+1),allImages[i]);
		}
		millPossibilities = new int[][][] {{{0}},{{2,3},{7,8}},{{1,3},{10,18}},{{1,2},{4,5}},{{3,5},{12,20}},{{3,4},{6,7}},{{5,7},{14,22}},{{5,6},{1,8}},{{1,7},{16,24}},{{10,11},{15,16}},{{2,18},{9,11}},{{9,10},{12,13}},{{4,20},{11,13}},{{11,12},{14,15}},{{6,22},{13,15}},{{9,16},{13,14}},{{8,24},{9,15}},{{18,19},{23,24}},{{2,10},{17,19}},{{17,18},{20,21}},{{4,12},{19,21}},{{19,20},{22,23}},{{6,14},{21,23}},{{17,24},{21,22}},{{8,16},{17,23}}};
	}
	
	public void setOpponent(Player opponent) {
		if(onlyOneOpponent==0) {
			this.opponent=opponent;
			onlyOneOpponent++;
		}
	}
	
	public void setBoard(Board board) {
		if(onlyOneBoard==0) {
			this.board=board;
			onlyOneBoard++;
		}
	}
	
	public void placePiece(Piece selectedPiece, int startingPosition) {
		selectedPiece.setPosition(startingPosition);
	}
	
	public void movePiece(int newPosition, Piece selectedPiece) {
		selectedPiece.setPosition(newPosition);
	}
	
	public void jumpPiece(int newPosition, Piece selectedPiece) {
		selectedPiece.setPosition(newPosition);
		
	}
	
	public void removePieceFromThisPlayer(Piece selectedPiece) {
		selectedPiece.setPosition(-1);
	}
	
	public Piece[] getPieces() {
		return pieces;
	}
	
	// Sets all involved pieces to isInMill if checkForMill is true!!!!!!
	public boolean checkForMill(Piece selectedPiece) {
		if(selectedPiece.getPosition()==0 || selectedPiece.getPosition()==-1) {
			return false;
		}
		boolean isAMill1 = false;
		boolean isAMill2 = false;
		int i,j;
		Piece whichAlso1 = null;
		Piece whichAlso2 = null;
		for(i=0;i<2;i++) {
			for(j=0;j<2;j++) {
				int positionOfOtherPiece = millPossibilities[selectedPiece.getPosition()][i][j];
				for(int k=0;k<9;k++) {
					if(pieces[k].getPosition()==positionOfOtherPiece) {
						if(j==0) {
							isAMill1=true;
							whichAlso1=pieces[k];
						}
						else {
							isAMill2=true;
							whichAlso2=pieces[k];
						}
						break;
					}
				}
			}
			if(isAMill1 == true && isAMill2 == true) {
				whichAlso1.setIsInMill(true);
				whichAlso2.setIsInMill(true);
				selectedPiece.setIsInMill(true);
				return true;
			}
			else {
				isAMill1=false;
				isAMill2=false;
				whichAlso1=null;
				whichAlso2=null;
			}
		}
		selectedPiece.setIsInMill(false);
		return false;
	}
	
	public int nonActivePieces() {
		int counter=0;
		for(int i=0;i<9;i++) {
			if(pieces[i].getPosition()==-1) {
				counter++;
			}
		}
		return counter;
	}
	
	public int getId() {
		return id;
	}
	
	public void setActiveState(boolean state) {
		active=state;
	}
	
	public boolean getActiveState() {
		return active;
	}
	
	public String getColor() {
		return color;
	}
	
	public Image[] getAllImages() {
		return allImages;
	}
	
	public int[][][] getMillPossibilities(){
		return millPossibilities;
	}
	
	public boolean inPlacePhase() {
		for(int i=0;i<9;i++) {
			if(pieces[i].getPosition()==0) {
				return true;
			}
		}
		return false;
	}
	
	public void setRemoveMaskOpen(boolean newState) {
		removeMaskOpen=newState;
	}
	
	public boolean getRemoveMaskOpen() {
		return removeMaskOpen;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public boolean areAllActiveStoneInMill() {
		int counter=0;
		for(int i=0;i<9;i++) {
			if(pieces[i].getIsInMill()==true) {
				counter++;
			}
		}
		int a = nonActivePieces();
		int counter2=0;
		for(int i=0;i<9;i++) {
			if(pieces[i].getPosition()==0) {
				counter2++;
			}
		}
		a = a + counter2;
		
		if(a+counter==9) {
			return true;
		}
		return false;
	}
	
	public int howManyPieceCanPlayerPlace() {
		int counter=0;
		for(int i=0;i<9;i++) {
			if(pieces[i].getPosition()==0) {
				counter++;
			}
		}
		return counter;
	}
	
	// While placing phase not possible to lose even if only 2 pieces on board!
	public boolean didPlayerLose() {
		if(nonActivePieces()==7) {
			return true;
		}
		return false;
	}
	
	public boolean hasToJump() {
		int counter=0;
		for(int i=0;i<9;i++) {
			if(pieces[i].getPosition()==-1) {
				counter++;
			}
		}
		if(counter==6) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
