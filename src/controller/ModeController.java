package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sample.GameApplication;

public class ModeController implements Initializable{

	private GameApplication mainApp;
	@FXML 
	private Button playerLocalBtn, AIBtn, savegameBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handlePlayerLocalBtn() {
		mainApp.setCreatePlayerOneHeadTextForPlayer();
		mainApp.setWhoBeginsBtnNamesForPlayer();
		mainApp.setCreatePlayerOneMode(0);
		mainApp.setChosePlayerOneScene();
		mainApp.forWhatTypeOfMode(false);
	}
	
	@FXML
	public void handleAIBtn() {
		mainApp.setCreatePlayerOneHeadTextForAI();
		mainApp.setWhoBeginsBtnNamesForAI();
		mainApp.setCreatePlayerOneMode(1);
		mainApp.setAIDifficultyScene();
		mainApp.forWhatTypeOfMode(true);
	}
	
	@FXML
	public void handleSavegameBtn() {
		mainApp.loadSaveGame();
	}
	
}
