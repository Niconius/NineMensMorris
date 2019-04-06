package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CreatePlayerOneController implements Initializable{

	private GameApplication mainApp;
	private int mode;
	@FXML 
	private Button whiteBtn, blackBtn;
	@FXML
	private Text headText;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp=mainApp;
	}
	
	@FXML
	public void handleWhiteBtn() {
		if(mode==1) {
			mainApp.setWhoBeginsMode(1);
		}
		mainApp.forCreatePlayerOneWhite(mode);
	}
	
	@FXML
	public void handleBlackBtn() {
		if(mode==1) {
			mainApp.setWhoBeginsMode(1);
		}
		mainApp.forCreatePlayerOneBlack(mode);
	}
	
	public void setMode(int mode) {
		this.mode=mode;
	}
	
	public void setHeadTextForAI() {
		headText.setText("Color for Player");
	}
	
	public void setHeadTextForPlayer() {
		headText.setText("Color for Player 1");
	}

	
}
