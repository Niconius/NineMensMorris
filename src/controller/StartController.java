package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sample.GameApplication;

public class StartController implements Initializable{

	private GameApplication mainApp;
	@FXML
	private Button btnPlay,btnOptions,btnExit;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleButtonPlay() {
		mainApp.setChosePlayModeScene();
	}
	
	@FXML
	public void handleButtonOptions() {
		mainApp.setOptionsFromStartScene();
	}

	@FXML
	public void handleButtonExit() {
		mainApp.close();
	}

}
