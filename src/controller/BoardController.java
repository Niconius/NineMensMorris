package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import sample.GameApplication;
import sample.Piece;

public class BoardController implements Initializable {

	private GameApplication mainApplication;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private Text showNameOne,showNameTwo,helper1,helper2;
	@FXML
	private ImageView pl1pi1,pl1pi2,pl1pi3,pl1pi4,pl1pi5,pl1pi6,pl1pi7,pl1pi8,pl1pi9;
	@FXML
	private ImageView pl2pi1,pl2pi2,pl2pi3,pl2pi4,pl2pi5,pl2pi6,pl2pi7,pl2pi8,pl2pi9;
	@FXML
	private ImageView field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24;
	@FXML
	private Button btnOptions;
	@FXML
	private TextArea communicationField;
	@FXML
	private Button btnEnd;
	
	private Piece pieceSave;
	private int fieldNrSave;
	private int PlayerIdANDPlaceMoveOrJump; // like 10 or 22
	private boolean haveToRemove;
	private boolean gameEnded;			// Can't click anything else than EndButton and OptionsButton
	private boolean completelyBlocked;
	private Image[] allWhiteImg, allBlackImg, allImgOne, allImgTwo;
	
	
	public void initMain(GameApplication mainApplication) {
		this.mainApplication=mainApplication;
		haveToRemove=false;
		gameEnded=false;
		Image imgW1 = new Image(getClass().getResourceAsStream("/pictures/whitePiece1.png"));		
		Image imgW2 = new Image(getClass().getResourceAsStream("/pictures/whitePiece2.png"));		
		Image imgW3 = new Image(getClass().getResourceAsStream("/pictures/whitePiece3.png"));		
		Image imgW4 = new Image(getClass().getResourceAsStream("/pictures/whitePiece4.png"));		
		Image imgW5 = new Image(getClass().getResourceAsStream("/pictures/whitePiece5.png"));		
		Image imgW6 = new Image(getClass().getResourceAsStream("/pictures/whitePiece6.png"));		
		Image imgW7 = new Image(getClass().getResourceAsStream("/pictures/whitePiece7.png"));		
		Image imgW8 = new Image(getClass().getResourceAsStream("/pictures/whitePiece8.png"));		
		Image imgW9 = new Image(getClass().getResourceAsStream("/pictures/whitePiece9.png"));
		allWhiteImg = new Image[] {imgW1,imgW2,imgW3,imgW4,imgW5,imgW6,imgW7,imgW8,imgW9};
		Image imgB1 = new Image(getClass().getResourceAsStream("/pictures/blackPiece1.png"));
		Image imgB2 = new Image(getClass().getResourceAsStream("/pictures/blackPiece2.png"));
		Image imgB3 = new Image(getClass().getResourceAsStream("/pictures/blackPiece3.png"));
		Image imgB4 = new Image(getClass().getResourceAsStream("/pictures/blackPiece4.png"));
		Image imgB5 = new Image(getClass().getResourceAsStream("/pictures/blackPiece5.png"));
		Image imgB6 = new Image(getClass().getResourceAsStream("/pictures/blackPiece6.png"));
		Image imgB7 = new Image(getClass().getResourceAsStream("/pictures/blackPiece7.png"));
		Image imgB8 = new Image(getClass().getResourceAsStream("/pictures/blackPiece8.png"));
		Image imgB9 = new Image(getClass().getResourceAsStream("/pictures/blackPiece9.png"));
		allBlackImg = new Image[] {imgB1,imgB2,imgB3,imgB4,imgB5,imgB6,imgB7,imgB8,imgB9};
	}
	
	public void setOneName(String name) {
		showNameOne.setText(name);
	}
	
	public void setTwoName(String name) {
		showNameTwo.setText(name);
	}
	
	public void setPiecesAtStart(int playerId, String color){
		
		ImageView[] allP1 = {pl1pi1,pl1pi2,pl1pi3,pl1pi4,pl1pi5,pl1pi6,pl1pi7,pl1pi8,pl1pi9};
		ImageView[] allP2 = {pl2pi1,pl2pi2,pl2pi3,pl2pi4,pl2pi5,pl2pi6,pl2pi7,pl2pi8,pl2pi9};
		
		if(playerId==1) {
			if(color.equals("white")) {
				for(int i=0;i<9;i++) {
					Image img = allWhiteImg[i];
					allP1[i].setImage(img);
				}
				allImgOne=allWhiteImg;
			}
			else {
				for(int i=0;i<9;i++) {
					Image img = allBlackImg[i];
					allP1[i].setImage(img);
				}
				allImgOne=allBlackImg;
			}
		}
		else {
			if(color.equals("white")) {
				for(int i=0;i<9;i++) {
					Image img = allWhiteImg[i];
					allP2[i].setImage(img);
				}
				allImgTwo=allWhiteImg;
			}
			else {
				for(int i=0;i<9;i++) {
					Image img = allBlackImg[i];
					allP2[i].setImage(img);
				}
				allImgTwo=allBlackImg;
			}
		}
	}
	
	@FXML
	public void handleBtnOptions() {
//		gameEnded=false;
//		completelyBlocked=false;
//		btnEnd.setOpacity(0);
//		btnEnd.setVisible(false);
//		btnEnd.setDisable(true);
//		ImageView[] allP1 = {pl1pi1,pl1pi2,pl1pi3,pl1pi4,pl1pi5,pl1pi6,pl1pi7,pl1pi8,pl1pi9};
//		ImageView[] allP2 = {pl2pi1,pl2pi2,pl2pi3,pl2pi4,pl2pi5,pl2pi6,pl2pi7,pl2pi8,pl2pi9};
//		ImageView[] allFields = {field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24};
//		for(int i=0;i<9;i++) {
//			allP1[i].setImage(null);
//			allP2[i].setImage(null);
//		}
//		for(int i=0;i<allFields.length;i++) {
//			allFields[i].setImage(null);
//		}
//		
//		mainApplication.goBackToStartScene();
		// -- Fill -- 
		
		mainApplication.setOptionsFromIngameScene();
	}
	
	
	@FXML
	public void handleDragDetected(MouseEvent event) {
		if(haveToRemove==false && gameEnded==false) {
			Image[] allOne = allImgOne;
			Image[] allTwo = allImgTwo;
			ImageView mySource = (ImageView) event.getSource();
			Image img = mySource.getImage();
			int[] PlnPi = getPlayerAndPiece(img);
			if(PlnPi[0]==1) {
				Piece[] pieces = mainApplication.one.getPieces();
				int PosFromPlnPi = PlnPi[1];
				int zwischenPos= PosFromPlnPi-1;
				Piece whichPiece = pieces[zwischenPos];
				int pos = whichPiece.getPosition();
				if(pos==-1) {
					return;
				}
				if(mainApplication.one.inPlacePhase()==true && pos!=0) {
					return;
				}
			}
			else if(PlnPi[0]==2){
				Piece[] pieces = mainApplication.two.getPieces();
				int PosFromPlnPi = PlnPi[1];
				int zwischenPos= PosFromPlnPi-1;
				Piece whichPiece = pieces[zwischenPos];
				int pos = whichPiece.getPosition();
				if(pos==-1) {
					return;
				}
				if(mainApplication.two.inPlacePhase()==true && pos!=0) {
					return;
				}
			}
			else {
				return;
			}
			int i,j;
			for(i=0;i<9;i++) {
				if(allOne[i].equals(img)) {
					if(mainApplication.board.whoIsActive().getId()==2){
						return;
					}
				}
			}
			for(j=0;j<9;j++) {
				if(allTwo[j].equals(img)) {
					if(mainApplication.board.whoIsActive().getId()==1){
						return;
					}
				}
			}
			Dragboard db = mySource.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(img);
			db.setContent(content);
			event.consume();
		}
	}
	
	@FXML
	public void handleDragOver(DragEvent event) {
		ImageView[] allFields = {field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24};
		ImageView mySource = (ImageView) event.getSource();
		int x;
		int fieldNr=-1;
		for(x=0;x<allFields.length;x++) {
			if(allFields[x].equals(mySource)) {
				fieldNr=x+1;
				fieldNrSave=fieldNr;
				break;
			}
		}
		
		ImageView imgV = (ImageView) event.getGestureSource();
		Image img = imgV.getImage();
		int[] PlnPi = getPlayerAndPiece(img);
		boolean possible;
		if(mainApplication.board.isPlacePossible(fieldNr)==false) {
			possible=false;
		}
		else {
			if(PlnPi[0]==1) {
				Piece[] pieces = mainApplication.one.getPieces();
				int i;
				for(i=0;i<9;i++) {
					if(pieces[i].getId()==(PlnPi[1])) {
						break;
					}
				}
				pieceSave = pieces[i];
				if(mainApplication.board.canPlayerJump(mainApplication.one)==true) {
					PlayerIdANDPlaceMoveOrJump=12;
					possible=true;
				}
				else {
					if(pieces[i].getPosition()==0) {
						if(mainApplication.board.isPlacePossible(fieldNr)==true) {
							PlayerIdANDPlaceMoveOrJump=10;
							possible=true;
						}
						else {
							possible = false;
						}
					}
					else{
						int[][] boardArray = mainApplication.board.getBoardAsArray();
						boolean zwischen=false;
						for(int j=0;j<4;j++){
							if(boardArray[pieces[i].getPosition()][j]==fieldNr) {
								zwischen=true;
								PlayerIdANDPlaceMoveOrJump=11;
								break;
							}
						}
						possible=zwischen;
					}
				}
			}
			else {
				Piece[] pieces = mainApplication.two.getPieces();
				int i;
				for(i=0;i<9;i++) {
					if(pieces[i].getId()==(PlnPi[1])) {
						break;
					}
				}
				pieceSave = pieces[i];
				if(mainApplication.board.canPlayerJump(mainApplication.two)==true) {
					PlayerIdANDPlaceMoveOrJump=22;
					possible=true;
				}
				else {
					if(pieces[i].getPosition()==0) {
						if(mainApplication.board.isPlacePossible(fieldNr)==true) {
							possible=true;
							PlayerIdANDPlaceMoveOrJump=20;
						}
						else {
							possible = false;
						}
					}
					else{
						int[][] boardArray = mainApplication.board.getBoardAsArray();
						boolean zwischen=false;;
						for(int j=0;j<4;j++){
							if(boardArray[pieces[i].getPosition()][j]==fieldNr) {
								zwischen=true;
								PlayerIdANDPlaceMoveOrJump=21;
								break;
							}
						}
						possible=zwischen;
					}
				}
			}
		}
	    if (event.getGestureSource() != event.getSource() && event.getDragboard().hasImage()&&possible==true) {
	        event.acceptTransferModes(TransferMode.ANY);
	    }
	}
	
//	@FXML
//	public void handleDragEntered(DragEvent event) {
//		if (event.getGestureSource() != event.getSource() &&
//            event.getDragboard().hasImage()) {
//            ImageView imgV = (ImageView) event.getSource();
//            
//            if(mainApplication.board.whoIsActive().getColor().equals("white")) {
//            	Image imgFW = new Image(getClass().getResourceAsStream("/pictures/transpW.png"));
//            	imgV.setImage(imgFW);
//            }
//            else {
//            	Image imgFB = new Image(getClass().getResourceAsStream("/pictures/transpB.png"));
//            	imgV.setImage(imgFB);
//            }
//        }
//        event.consume();
//	}
	
	
	
//	@FXML
//	public void handleDragExited() {
//		
//	}
	
	
	@FXML
    public void handleDragDropped(DragEvent event) {
		Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()==true && PlayerIdANDPlaceMoveOrJump!=0) {
        	
        	if(PlayerIdANDPlaceMoveOrJump==10) {
        		mainApplication.one.placePiece(pieceSave, fieldNrSave);
        		for(int i=0;i<9;i++) {
        			mainApplication.one.checkForMill(mainApplication.one.getPieces()[i]);
        		}
        		// Active player blocked his opponent with this turn
        		completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.two.getPieces()[i])==true || mainApplication.two.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
        		if(mainApplication.one.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.one.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.one.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
        	}
        	
        	else if(PlayerIdANDPlaceMoveOrJump==11) {
	            mainApplication.one.movePiece(fieldNrSave, pieceSave);
	            for(int i=0;i<9;i++) {
        			mainApplication.one.checkForMill(mainApplication.one.getPieces()[i]);
        		}
	            completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.two.getPieces()[i])==true || mainApplication.two.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
	            if(mainApplication.one.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.one.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.one.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
        	}
            
            else if(PlayerIdANDPlaceMoveOrJump==12) {
            	mainApplication.one.jumpPiece(fieldNrSave, pieceSave);
            	for(int i=0;i<9;i++) {
        			mainApplication.one.checkForMill(mainApplication.one.getPieces()[i]);
        		}
            	completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.two.getPieces()[i])==true || mainApplication.two.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
            	if(mainApplication.one.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.one.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.one.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
            }
            else if(PlayerIdANDPlaceMoveOrJump==20) {
        		mainApplication.two.placePiece(pieceSave, fieldNrSave);
        		for(int i=0;i<9;i++) {
        			mainApplication.two.checkForMill(mainApplication.two.getPieces()[i]);
        		}
        		completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.one.getPieces()[i])==true || mainApplication.one.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
        		if(mainApplication.two.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.two.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.two.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
        	}
        	
        	else if(PlayerIdANDPlaceMoveOrJump==21) {
	            mainApplication.two.movePiece(fieldNrSave, pieceSave);
	            for(int i=0;i<9;i++) {
        			mainApplication.two.checkForMill(mainApplication.two.getPieces()[i]);
        		}
	            completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.one.getPieces()[i])==true || mainApplication.one.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
	            if(mainApplication.two.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.two.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.two.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
        	}
            
            else if(PlayerIdANDPlaceMoveOrJump==22) {
            	mainApplication.two.jumpPiece(fieldNrSave, pieceSave);
            	for(int i=0;i<9;i++) {
        			mainApplication.two.checkForMill(mainApplication.two.getPieces()[i]);
        		}
            	completelyBlocked = true;
        		for(int i=0;i<9;i++) {
        			if(mainApplication.board.canPieceMove(mainApplication.one.getPieces()[i])==true || mainApplication.one.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true) {
        			haveToRemove=false;
					setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
					btnEnd.setOpacity(1);
					btnEnd.setVisible(true);
					btnEnd.setDisable(false);
					gameEnded=true;
        		}
            	if(mainApplication.two.checkForMill(pieceSave)==true) {
        			setCommunicationField("Player " + mainApplication.two.getId() + " you got a mill \nChose an opponent piece to remove");
        			mainApplication.two.setRemoveMaskOpen(true);
        			haveToRemove=true;
        		}
            }
        	// reset
        	PlayerIdANDPlaceMoveOrJump=0;
        	pieceSave=null;
        	fieldNrSave=-1;		// could be dangerous
        	if(haveToRemove==false && completelyBlocked==false) {
        		mainApplication.board.switchActivePlayer();
        		setCommunicationField("Turn Player " + mainApplication.board.whoIsActive().getId());
        	}
            
           Image img = ((ImageView) event.getGestureSource()).getImage();
           ((ImageView) event.getSource()).setImage(img);
           ((ImageView) event.getGestureSource()).setImage(null);
           success = true;
        }
        event.setDropCompleted(success);
        event.consume();
     }
	
	@FXML
	public void handleDragDone(DragEvent event) {
		if (event.getTransferMode() == TransferMode.MOVE) {
            ((ImageView) event.getGestureSource()).setImage(null);
            
        }
        event.consume();
	}
	
	@FXML
	public void handleOnMouseClicked(MouseEvent event) {
		if(mainApplication.board.whoIsActive().getRemoveMaskOpen()==true) {
			ImageView imgV = (ImageView) event.getSource();
			Image img = imgV.getImage();
			int[] PlnPi = getPlayerAndPiece(img);
			// If the selected piece is NOT from the own Player
			if(PlnPi[0]==mainApplication.board.whoIsActive().getOpponent().getId()) {
				if(PlnPi[0]==1) {
					Piece[] pieces = mainApplication.one.getPieces();
					boolean found=false;	// safety! if no piece found
					int i;
					for(i=0;i<9;i++) {
						if(pieces[i].getId()==PlnPi[1]) {
							found=true;
							break;
						}
					}
					if(found==true) {
						if(pieces[i].getIsInMill()==true && mainApplication.one.areAllActiveStoneInMill()==false) {
							return;
						}
						mainApplication.one.removePieceFromThisPlayer(pieces[i]);
						for(int j=0;j<9;j++) {
		        			mainApplication.one.checkForMill(mainApplication.one.getPieces()[j]);
		        		}
						mainApplication.two.setRemoveMaskOpen(false);
						imgV.setImage(null);
						
						// Active player blocked his opponent with this remove than lost
		        		completelyBlocked = true;
		        		for(int x=0;x<9;x++) {
		        			if(mainApplication.board.canPieceMove(mainApplication.one.getPieces()[x])==true || mainApplication.one.hasToJump()==true) {
		        				completelyBlocked=false;
		        				break;
		        			}
		        		}
						
						// if lost
						if(mainApplication.one.didPlayerLose()==true || completelyBlocked==true) {
							haveToRemove=false;
							setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
							btnEnd.setOpacity(1);
							btnEnd.setVisible(true);
							btnEnd.setDisable(false);
							gameEnded=true;
						}
						else {
							mainApplication.board.switchActivePlayer();
							setCommunicationField("Turn Player " + mainApplication.board.whoIsActive().getId());
							haveToRemove=false;
						}
					}
				}
				else if(PlnPi[0]==2) {
					Piece[] pieces = mainApplication.two.getPieces();
					boolean found=false;	// safety! if no piece found
					int i;
					for(i=0;i<9;i++) {
						if(pieces[i].getId()==PlnPi[1]) {
							found=true;
							break;
						}
					}
					if(found==true) {
						if(pieces[i].getIsInMill()==true && mainApplication.two.areAllActiveStoneInMill()==false) {
							return;
						}
						mainApplication.two.removePieceFromThisPlayer(pieces[i]);
						for(int j=0;j<9;j++) {
		        			mainApplication.two.checkForMill(mainApplication.two.getPieces()[j]);
		        		}
						mainApplication.one.setRemoveMaskOpen(false);
						imgV.setImage(null);
						
						// Active player blocked his opponent with this remove
		        		completelyBlocked = true;
		        		for(int x=0;x<9;x++) {
		        			if(mainApplication.board.canPieceMove(mainApplication.two.getPieces()[x])==true || mainApplication.two.hasToJump()==true) {
		        				completelyBlocked=false;
		        				break;
		        			}
		        		}
						
						// if lost
						if(mainApplication.two.didPlayerLose()==true || completelyBlocked==true) {
							
							haveToRemove=false;
							setCommunicationField("Player " + mainApplication.board.whoIsActive().getId() + " won!");
							btnEnd.setOpacity(1);
							btnEnd.setVisible(true);
							btnEnd.setDisable(false);
							gameEnded=true;
						}
						else {
							mainApplication.board.switchActivePlayer();
							setCommunicationField("Turn Player " + mainApplication.board.whoIsActive().getId());
							haveToRemove=false;
						}
					}
				}
				else {		// Safety if getPlayerAndPiece wrong
					System.out.println("Error");
				}
			}
		}
			
	}
	
	
	
	public void setCommunicationField(String text) {
		communicationField.clear();
		communicationField.setText(text);
	}
	
	@FXML
	public void handleBtnEnd() {
		gameEnded=false;
		completelyBlocked=false;
		btnEnd.setOpacity(0);
		btnEnd.setVisible(false);
		btnEnd.setDisable(true);
		ImageView[] allP1 = {pl1pi1,pl1pi2,pl1pi3,pl1pi4,pl1pi5,pl1pi6,pl1pi7,pl1pi8,pl1pi9};
		ImageView[] allP2 = {pl2pi1,pl2pi2,pl2pi3,pl2pi4,pl2pi5,pl2pi6,pl2pi7,pl2pi8,pl2pi9};
		ImageView[] allFields = {field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24};
		for(int i=0;i<9;i++) {
			allP1[i].setImage(null);
			allP2[i].setImage(null);
		}
		for(int i=0;i<allFields.length;i++) {
			allFields[i].setImage(null);
		}
		
		mainApplication.goBackToStartScene();
	}
	
	
	
	private int[] getPlayerAndPiece(Image img) {
		
		Image[] allOne = allImgOne;
		Image[] allTwo = allImgTwo;
		int player=0;
		int piece=0;
		int i,j;
		for(i=0;i<9;i++) {
			if(allOne[i].equals(img)) {
				player=1;
				piece=i+1;
				break;
			}
		}
		for(j=0;j<9;j++) {
			if(allTwo[j].equals(img)) {
				player=2;
				piece=j+1;
				break;
			}
		}
		int[] arr = {player,piece};
		return arr;
	}
	
	public boolean getHaveToRemove() {
		return haveToRemove;
	}
	
	public boolean getGameEnded() {
		return gameEnded;
	}
	
	public void synchronizePositionsWithImageViews() {
		ImageView[] allP1 = {pl1pi1,pl1pi2,pl1pi3,pl1pi4,pl1pi5,pl1pi6,pl1pi7,pl1pi8,pl1pi9};
		ImageView[] allP2 = {pl2pi1,pl2pi2,pl2pi3,pl2pi4,pl2pi5,pl2pi6,pl2pi7,pl2pi8,pl2pi9};
		ImageView[] allFields = {field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17,field18,field19,field20,field21,field22,field23,field24};
		
		for(int i=0;i<mainApplication.one.getPieces().length;i++) {
			int newImageViewNumber = mainApplication.one.getPieces()[i].getPosition();
			if(newImageViewNumber<0) {
				allP1[i].setImage(null);
			}
			else if(newImageViewNumber==0) {
				// do nothing
			}
			else {
				allFields[newImageViewNumber-1].setImage(allP1[i].getImage());
				allP1[i].setImage(null);
			}
		}
		for(int i=0;i<mainApplication.two.getPieces().length;i++) {
			int newImageViewNumber = mainApplication.two.getPieces()[i].getPosition();
			if(newImageViewNumber<0) {
				allP2[i].setImage(null);

			}
			else if(newImageViewNumber==0) {
				// do nothing
			}
			else {
				allFields[newImageViewNumber-1].setImage(allP2[i].getImage());
				allP2[i].setImage(null);
			}
		}
	}
	
	
}
