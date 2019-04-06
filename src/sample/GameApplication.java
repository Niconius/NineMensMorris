package sample;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

import com.sun.javafx.tools.packager.Main;

import aiStuff.AiGeneral;
import controller.AIDifficultyController;
import controller.BoardController;
import controller.BoardVsAiController;
import controller.CreatePlayerOneController;
import controller.CreatePlayerTwoController;
import controller.ModeController;
import controller.OptionsFromIngameController;
import controller.OptionsFromStartController;
import controller.StartController;
import controller.WhoBeginsController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {
	
	Stage window;
	Scene startScene,chosePlayModeScene, chosePlayerOneScene,chosePlayerTwoScene,whoBeginsScene,graphicalBoardScene, aIDifficultyScene, graphicalBoardVsAiScene, optionsFromStartScene, optionsFromIngameScene;
	// Setup classes for game
	public Board board;
	public Player one;
	public Player two;
	// Setup for AI
	private int difficulty;
	private boolean isThisAi;
	// Controller
	StartController startController;
	BoardController boardController;
	ModeController modeController;
	CreatePlayerOneController createP1Controller;
	CreatePlayerTwoController createP2Controller;
	WhoBeginsController whoBeginsController;
	AIDifficultyController aIDifficultyController;
	BoardVsAiController boardVsAiController;
	OptionsFromStartController optionsFromStartController;
	OptionsFromIngameController optionsFromIngameController;
	// Sound
	private Mixer mixer;
	private Clip clip;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		// Stage
		this.window = primaryStage;
		window.setTitle("Nine Men's Morris");
		
		// FXML
		FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/GraphicalBoard2.fxml"));
		Parent graphicalBoardLayout = boardLoader.load();
		boardController = boardLoader.getController();
		boardController.initMain(this);
		
		FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/StartScreen.fxml"));
		Parent startLayout = startLoader.load();
		startController = startLoader.getController();
		startController.initMain(this);
		
		FXMLLoader optionsStartLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/OptionsFromStart.fxml"));
		Parent optionsFromStartLayout = optionsStartLoader.load();
		optionsFromStartController = optionsStartLoader.getController();
		optionsFromStartController.initMain(this);
		
		FXMLLoader optionsIngameLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/OptionsFromIngame.fxml"));
		Parent optionsFromIngameLayout = optionsIngameLoader.load();
		optionsFromIngameController = optionsIngameLoader.getController();
		optionsFromIngameController.initMain(this);
		
		FXMLLoader modeLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/ChosePlayModeScreen.fxml"));
		Parent choseModeLayout = modeLoader.load();
		modeController = modeLoader.getController();
		modeController.initMain(this);
		
		FXMLLoader playerOneLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/CreatePlayerOneScreen.fxml"));
		Parent chosePlayerOneLayout = playerOneLoader.load();
		createP1Controller = playerOneLoader.getController();
		createP1Controller.initMain(this);
		
		FXMLLoader playerTwoLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/CreatePlayerTwoScreen.fxml"));
		Parent chosePlayerTwoLayout = playerTwoLoader.load();
		createP2Controller = playerTwoLoader.getController();
		createP2Controller.initMain(this);
		
		FXMLLoader whoBeginsLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/WhoBeginsScreen.fxml"));
		Parent whoBeginsLayout = whoBeginsLoader.load();
		whoBeginsController = whoBeginsLoader.getController();
		whoBeginsController.initMain(this);
		
		// --> AI
		FXMLLoader aIDifficultyLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/AIDifficultyScreen.fxml"));
		Parent aIDifficultyLayout = aIDifficultyLoader.load();
		aIDifficultyController = aIDifficultyLoader.getController();
		aIDifficultyController.initMain(this);
		
		FXMLLoader aIBoardLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/GraphicalBoardVsAiScreen.fxml"));
		Parent aIBoardLayout = aIBoardLoader.load();
		boardVsAiController = aIBoardLoader.getController();
		boardVsAiController.initMain(this);
		
		
		
		
		// Scenes
		startScene = new Scene(startLayout,500,500);
		optionsFromStartScene = new Scene(optionsFromStartLayout,500,500);
		optionsFromIngameScene = new Scene(optionsFromIngameLayout,500,500);
		chosePlayModeScene = new Scene(choseModeLayout,500,500);
		chosePlayerOneScene = new Scene(chosePlayerOneLayout,500,500);
		chosePlayerTwoScene = new Scene(chosePlayerTwoLayout,500,500);
		whoBeginsScene = new Scene(whoBeginsLayout,500,500);
		graphicalBoardScene = new Scene(graphicalBoardLayout,500,500);
		
		// --> AI
		aIDifficultyScene = new Scene(aIDifficultyLayout,500,500);
		graphicalBoardVsAiScene = new Scene(aIBoardLayout,500,500);
		
		// Sound
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sounds/bensound-summer.wav"));
		AudioFormat format = audioStream.getFormat();
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		
		// Start
		window.setScene(startScene);
		window.show();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	
	
	/**
	 * ------------------------------------------------
	 */
	
	
	public void goBackToStartScene() {
		window.setScene(startScene);
	}
	
	public void setChosePlayerOneScene() {
		window.setScene(chosePlayerOneScene);
	}
	
	public void setChosePlayModeScene() {
		window.setScene(chosePlayModeScene);
	}
	
	public void setAIDifficultyScene() {
		window.setScene(aIDifficultyScene);
	}
	
	public void setOptionsFromStartScene() {
		optionsFromStartController.setSliderValueToCurrentVolume();
		window.setScene(optionsFromStartScene);
	}
	
	public void setOptionsFromIngameScene() {
		optionsFromIngameController.setSliderValueToCurrentVolume();
		window.setScene(optionsFromIngameScene);
	}
	
	public void setBoardNormalScene() {
		window.setScene(graphicalBoardScene);
	}
	
	public void setBoardAiScene() {
		window.setScene(graphicalBoardVsAiScene);
	}
	
	/**
	 * ------------------------------------------------
	 */
	
	
	public void setCreatePlayerOneMode(int mode) {
		createP1Controller.setMode(mode);
	}
	
	public void setWhoBeginsMode(int mode) {
		whoBeginsController.setMode(mode);
	}
	
	public void setCreatePlayerOneHeadTextForAI() {
		createP1Controller.setHeadTextForAI();
	}
	
	public void setWhoBeginsBtnNamesForAI() {
		whoBeginsController.setBtnNamesForAI();
	}
	
	public void setCreatePlayerOneHeadTextForPlayer() {
		createP1Controller.setHeadTextForPlayer();
	}
	
	public void setWhoBeginsBtnNamesForPlayer() {
		whoBeginsController.setBtnNamesForPlayer();
	}
	
	public void createAiWithDifficulty( int difficulty) {
		boardVsAiController.createAiWithDifficulty(difficulty);
	}
	
	public void setDifficultyForAi(int difficulty) {
		this.difficulty=difficulty;
	}
	
	public void setAiBegins() {
		boardVsAiController.aiBegins();
	}
	
	
	/**
	 * ------------------------------------------------
	 */
	
	
	public void forCreatePlayerOneWhite(int mode) {
		if(mode==0) {
			createP2Controller.disableWhiteBtn(true);
			createP2Controller.disableBlackBtn(false);
			one= new Player("white",1,isThisAi);
			window.setScene(chosePlayerTwoScene);
		}
		else if(mode==1) {
			one = new Player("white",1,isThisAi);
			two = new Player("black",2,isThisAi);
			two.setOpponent(one);
			one.setOpponent(two);
			board = new Board(one, two);
			two.setBoard(board);
			one.setBoard(board);
			boardVsAiController.setOneName("Player");
			boardVsAiController.setTwoName("AI");
			boardVsAiController.setPiecesAtStart(1, one.getColor());
			boardVsAiController.setPiecesAtStart(2, two.getColor());
			createAiWithDifficulty(difficulty);
		    window.setScene(whoBeginsScene);
		}
	}
	
	public void forCreatePlayerOneBlack(int mode) {
		if(mode==0) {
			createP2Controller.disableWhiteBtn(false);
			createP2Controller.disableBlackBtn(true);
			one= new Player("black",1,isThisAi);
			window.setScene(chosePlayerTwoScene);
		}
		else if(mode==1) {
			one = new Player("black",1,isThisAi);
			two = new Player("white",2,isThisAi);
			two.setOpponent(one);
			one.setOpponent(two);
			board = new Board(one, two);
			two.setBoard(board);
			one.setBoard(board);
			boardVsAiController.setOneName("Player");
			boardVsAiController.setTwoName("AI");
			boardVsAiController.setPiecesAtStart(1, one.getColor());
			boardVsAiController.setPiecesAtStart(2, two.getColor());
			createAiWithDifficulty(difficulty);
		    window.setScene(whoBeginsScene);
		}
	}
	
	public void forCreatePlayerTwoWhite() {
		two = new Player("white",2,isThisAi);
		two.setOpponent(one);
		one.setOpponent(two);
		board= new Board(one,two);
		two.setBoard(board);
		one.setBoard(board);
		boardController.setOneName("Player " + one.getId());
	    boardController.setTwoName("Player " + two.getId());
	    boardController.setPiecesAtStart(1, one.getColor());
	    boardController.setPiecesAtStart(2, two.getColor());
		window.setScene(whoBeginsScene);
	}
	
	public void forCreatePlayerTwoBlack() {
		two = new Player("black",2,isThisAi);
		two.setOpponent(one);
		one.setOpponent(two);
		board= new Board(one,two);
		two.setBoard(board);
		one.setBoard(board);
		boardController.setOneName("Player " + one.getId());
	    boardController.setTwoName("Player " + two.getId());
	    boardController.setPiecesAtStart(1, one.getColor());
	    boardController.setPiecesAtStart(2, two.getColor());
		window.setScene(whoBeginsScene);
	}
	
	public void forWhoBeginsOne(int mode) {
		if(mode==0) {
			one.setActiveState(true);
			two.setActiveState(false);
			boardController.setCommunicationField("Turn Player " + one.getId());
			window.setScene(graphicalBoardScene);
		}
		else if(mode==1) {
			one.setActiveState(true);
			two.setActiveState(false);
			boardVsAiController.setCommunicationField("Turn Player");
			window.setScene(graphicalBoardVsAiScene);
		}
	}
	
	public void forWhoBeginsTwo(int mode) {
		if(mode==0) {
			one.setActiveState(false);
			two.setActiveState(true);
			boardController.setCommunicationField("Turn Player " + two.getId());
			window.setScene(graphicalBoardScene);
		}
		else if(mode==1) {
			one.setActiveState(false);
			two.setActiveState(true);
			boardVsAiController.setCommunicationField("Turn AI");
			boardVsAiController.aiBegins();
			window.setScene(graphicalBoardVsAiScene);
		}
	}
	
	public void forWhoBeginsRandom(int mode) {
		if(mode==0) {
			int random = (int) (Math.random()*2);
			if(random==0) {
				one.setActiveState(true);
				two.setActiveState(false);
				boardController.setCommunicationField("Turn Player " + one.getId());
			}
			else {
				one.setActiveState(false);
				two.setActiveState(true);
				boardController.setCommunicationField("Turn Player " + two.getId());
			}
			window.setScene(graphicalBoardScene);
		}
		else if(mode==1) {
			int random = (int) (Math.random()*2);
			if(random==0) {
				one.setActiveState(true);
				two.setActiveState(false);
				boardVsAiController.setCommunicationField("Turn Player");
			}
			else {
				one.setActiveState(false);
				two.setActiveState(true);
				boardVsAiController.setCommunicationField("Turn AI");
				boardVsAiController.aiBegins();
			}
			window.setScene(graphicalBoardVsAiScene);
		}
	}
	
	public void forWhatTypeOfMode(boolean isThisAi) {
		this.isThisAi=isThisAi;
	}
	
	public boolean isThisAi() {
		return isThisAi;
	}
	
	public Clip currentAudioClip() {
		return clip;
	}
	
	public void safeGame() throws CantSaveException{
		if(isThisAi==true) {
			if(boardVsAiController.getHaveToRemove()==true) {
				throw new CantSaveException("Not saved!\nFinish Turn");
			}
			if(boardVsAiController.getGameEnded()==true) {
				throw new CantSaveException("Not Saved!\nGame finished");
			}
		}
		else {
			if(boardController.getHaveToRemove()==true) {
				throw new CantSaveException("Not saved!\nFinish Turn");
			}
			if(boardController.getGameEnded()==true) {
				throw new CantSaveException("Not Saved!\nGame finished");
			}
		}
		
		try {
//			getClass().getResource("/saves/save.txt").getFile() // doesn't work!!
			FileOutputStream fis = new FileOutputStream(new File("save.txt"));
			ObjectOutputStream oos = new ObjectOutputStream(fis);
			if(isThisAi==true) {
				oos.writeObject(one);
				oos.writeObject(two);
				oos.writeObject(board);
				oos.writeObject(isThisAi);
				oos.writeObject(boardVsAiController.getAi());
			}
			else {
				oos.writeObject(one);
				oos.writeObject(two);
				oos.writeObject(board);
				oos.writeObject(isThisAi);
				oos.writeObject(null);
			}
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		window.close();
	}
	
	public Object getBoardController(boolean theAiOne) {
		if(theAiOne==true) {
			return boardVsAiController;
		}
		else {
			return boardController;
		}
	}
	
	public void loadSaveGame() {
		Object[] data = readOutSaveFile();
		one=(Player) data[0];
		two=(Player) data[1];
		board=(Board) data[2];
		isThisAi=(boolean) data[3];
		
		if(isThisAi==true) {
			boardVsAiController.setOneName("Player");
			boardVsAiController.setTwoName("AI");
			boardVsAiController.setCommunicationField("Turn Player");
			boardVsAiController.setPiecesAtStart(one.getId(), one.getColor());
			boardVsAiController.setPiecesAtStart(two.getId(), two.getColor());
			boardVsAiController.setAi((AiGeneral) data[4]);
			boardVsAiController.synchronizePositionsWithImageViews();
			setBoardAiScene();
		}
		else {
			boardController.setOneName("Player " + one.getId());
		    boardController.setTwoName("Player " + two.getId());
			boardController.setCommunicationField("Turn Player " + board.whoIsActive().getId());
			boardController.setPiecesAtStart(one.getId(), one.getColor());
			boardController.setPiecesAtStart(two.getId(), two.getColor());
			boardController.synchronizePositionsWithImageViews();
			setBoardNormalScene();
		}
	}
	
	private Object[] readOutSaveFile() {
		try {
			FileInputStream fis = new FileInputStream(new File("save.txt"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data1 = ois.readObject();
			Object data2 = ois.readObject();
			Object data3 = ois.readObject();
			Object data4 = ois.readObject();
			Object data5 = ois.readObject();
			Object[] data = {(Player)data1,(Player)data2,(Board)data3,(boolean)data4,(Object)data5};
			return data;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}