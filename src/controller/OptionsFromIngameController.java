package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import sample.CantSaveException;
import sample.GameApplication;

public class OptionsFromIngameController implements Initializable{

	private GameApplication mainApp;
	@FXML
	private Button btnContinue, btnSave, btnStartScreen;
	@FXML
	private Slider sliderVolume;
	@FXML
	private TextArea safeText;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleBtnContinue() {
		boolean mode = mainApp.isThisAi();
		if(mode==false) {
			mainApp.setBoardNormalScene();
		}
		else if(mode==true){
			mainApp.setBoardAiScene();
		}
		safeText.setVisible(false);
		safeText.setOpacity(0);
		safeText.setDisable(true);	
	}
	
	@FXML
	public void handleBtnSave() {
		try {
			mainApp.safeGame();
			safeText.setText("Game saved!");
		} catch (CantSaveException e) {
			safeText.setText(e.getMessage());
		}
		finally {
			safeText.setVisible(true);
			safeText.setOpacity(1);
			safeText.setDisable(false);	
		}
	}
	
	@FXML
	public void handleBtnStartScreen() {
		safeText.setVisible(false);
		safeText.setOpacity(0);
		safeText.setDisable(true);
		boolean whichOne = mainApp.isThisAi();
		if(whichOne==true) {
			((BoardVsAiController) mainApp.getBoardController(whichOne)).handleBtnEnd();
		}
		else {
			((BoardController) mainApp.getBoardController(whichOne)).handleBtnEnd();
		}
	}
	
	@FXML
	public void handleSliderDrag() {
		Clip clip = mainApp.currentAudioClip();
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double volume = sliderVolume.getValue()/100;
		
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (float) ((range * volume) + gainControl.getMinimum());
		gainControl.setValue(gain);
	}
	
	public void setSliderValueToCurrentVolume() {
		Clip clip = mainApp.currentAudioClip();
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		double calcValue = ((gainControl.getValue()-gainControl.getMinimum())/range)*100;
		sliderVolume.setValue(calcValue);
	}
}
