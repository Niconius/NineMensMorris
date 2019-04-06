package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sample.GameApplication;

public class CreatePlayerTwoController implements Initializable{

	private GameApplication mainApp;
	@FXML 
	private Button whiteBtn, blackBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleWhiteBtn() {
		mainApp.setWhoBeginsMode(0);
		mainApp.forCreatePlayerTwoWhite();
	}
	
	@FXML
	public void handleBlackBtn() {
		mainApp.setWhoBeginsMode(0);
		mainApp.forCreatePlayerTwoBlack();
	}

	public void disableWhiteBtn(boolean state) {
		whiteBtn.setDisable(state);
	}
	
	public void disableBlackBtn(boolean state) {
		blackBtn.setDisable(state);
	}
}