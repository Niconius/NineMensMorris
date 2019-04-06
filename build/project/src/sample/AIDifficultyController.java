package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AIDifficultyController implements Initializable{

	private GameApplication mainApp;
	@FXML 
	private Button easyBtn, middleBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleEasyBtn() {
		mainApp.setCreatePlayerOneHeadTextForAI();
		mainApp.setDifficultyForAi(0);
		mainApp.setChosePlayerOneScene();
	}
	
	@FXML
	public void handleMiddleBtn() {
		mainApp.setCreatePlayerOneHeadTextForAI();
		mainApp.setDifficultyForAi(1);
		mainApp.setChosePlayerOneScene();
	}
	
}
