package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import sample.GameApplication;

public class OptionsFromStartController implements Initializable{

	private GameApplication mainApp;
	@FXML
	private Slider sliderVolume;
	@FXML
	private Button btnBack;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	public void initMain(GameApplication mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleBtnBack() {
		mainApp.goBackToStartScene();
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