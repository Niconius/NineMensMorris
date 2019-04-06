package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sample.GameApplication;

public class WhoBeginsController implements Initializable{

	private GameApplication mainApp;
	private int mode;
	@FXML 
	private Button p1Btn, p2Btn, randomBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleP1Btn() {
		mainApp.forWhoBeginsOne(mode);
	}
	
	@FXML
	public void handleP2Btn() {
		mainApp.forWhoBeginsTwo(mode);
	}
	
	@FXML
	public void handleRandomBtn() {
		mainApp.forWhoBeginsRandom(mode);
	}
	
	public void setMode(int mode) {
		this.mode=mode;
	}
	
	public void setBtnNamesForAI() {
		p1Btn.setText("Player");
		p2Btn.setText("AI");
	}
	
	public void setBtnNamesForPlayer() {
		p1Btn.setText("Player 1");
		p2Btn.setText("Player 2");
	}

	
}
