package sample;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {
	
	Stage window;
	Scene startScene,chosePlayModeScene, chosePlayerOneScene,chosePlayerTwoScene,whoBeginsScene,graphicalBoardScene, aIDifficultyScene, graphicalBoardVsAiScene;
	// Setup classes for game
	Board board;
	Player one;
	Player two;
	// Setup for AI
	private int difficulty;
	// Controller
	StartController startController;
	BoardController boardController;
	ModeController modeController;
	CreatePlayerOneController createP1Controller;
	CreatePlayerTwoController createP2Controller;
	WhoBeginsController whoBeginsController;
	AIDifficultyController aIDifficultyController;
	BoardVsAiController boardVsAiController;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		// Stage
		this.window = primaryStage;
		window.setTitle("Nine Men's Morris");
		
		// FXML
		FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/graphicalBoard2.fxml"));
		Parent graphicalBoardLayout = boardLoader.load();
		boardController = boardLoader.getController();
		boardController.initMain(this);
		
		FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/StartScreen.fxml"));
		Parent startLayout = startLoader.load();
		startController = startLoader.getController();
		startController.initMain(this);
		
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
		chosePlayModeScene = new Scene(choseModeLayout,500,500);
		chosePlayerOneScene = new Scene(chosePlayerOneLayout,500,500);
		chosePlayerTwoScene = new Scene(chosePlayerTwoLayout,500,500);
		whoBeginsScene = new Scene(whoBeginsLayout,500,500);
		graphicalBoardScene = new Scene(graphicalBoardLayout,500,500);
		
		// --> AI
		aIDifficultyScene = new Scene(aIDifficultyLayout,500,500);
		graphicalBoardVsAiScene = new Scene(aIBoardLayout,500,500);
		
		
		
		// Start
		window.setScene(startScene);
		window.show();
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
			one= new Player("white",1);
			window.setScene(chosePlayerTwoScene);
		}
		else if(mode==1) {
			one = new Player("white",1);
			two = new Player("black",2);
			board = new Board(one, two);
			boardVsAiController.setOneName("Player");
			boardVsAiController.setTwoName("AI");
			boardVsAiController.setPiecesAtStart(1, 1);
			boardVsAiController.setPiecesAtStart(2, 2);
			createAiWithDifficulty(difficulty);
		    window.setScene(whoBeginsScene);
		}
	}
	
	public void forCreatePlayerOneBlack(int mode) {
		if(mode==0) {
			createP2Controller.disableWhiteBtn(false);
			createP2Controller.disableBlackBtn(true);
			one= new Player("black",1);
			window.setScene(chosePlayerTwoScene);
		}
		else if(mode==1) {
			one = new Player("black",1);
			two = new Player("white",2);
			board = new Board(one, two);
			boardVsAiController.setOneName("Player");
			boardVsAiController.setTwoName("AI");
			boardVsAiController.setPiecesAtStart(1, 2);
			boardVsAiController.setPiecesAtStart(2, 1);
			createAiWithDifficulty(difficulty);
		    window.setScene(whoBeginsScene);
		}
	}
	
	public void forCreatePlayerTwoWhite() {
		two = new Player("white",2);
		board= new Board(one,two);
		boardController.setOneName("Player " + one.getId());
	    boardController.setTwoName("Player " + two.getId());
	    boardController.setPiecesAtStart(1, 2);
	    boardController.setPiecesAtStart(2, 1);
		window.setScene(whoBeginsScene);
	}
	
	public void forCreatePlayerTwoBlack() {
		two = new Player("black",2);
		board= new Board(one,two);
		boardController.setOneName("Player " + one.getId());
	    boardController.setTwoName("Player " + two.getId());
	    boardController.setPiecesAtStart(1, 1);
	    boardController.setPiecesAtStart(2, 2);
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
	
	
	public void close() {
		window.close();
	}

}