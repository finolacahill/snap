package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.Game;
import model.GameException;
import model.Player;
import view.CardView;
import view.EndSubScene;
import view.InstructionsSubScene;
import view.NameDialog;
import view.PlayerView;
import view.SnapActionView;


public class GameViewController {

	
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	public int GAME_WIDTH = 600;
	public int GAME_HEIGHT = 600;
	private Game game;
	private SnapActionView snap;
	protected ArrayList<Player> playerList;
	private ArrayList<PlayerView> playerViewList;
	private boolean turned;
	private int[] inputs;	
	private EndSubScene endScene;
	private InstructionsSubScene instructions;
	private Node leftPile;
	private Node rightPile;
	private Node turn;
	
	HashMap<KeyCode, Boolean> keyListeners;
	
	public GameViewController(int[] input) {
		inputs = input;
		initialisePlayers();
		initialiseStage();
		createKeyListeners();
		snap = new SnapActionView();
	}

	private void initialiseStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
	    gameStage.setResizable(false);
		gameStage.setScene(gameScene);
		instructions = new InstructionsSubScene();
		gamePane.getChildren().add(instructions);

	}
	
	public void createNewGame(Stage menuStage) {
		
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		try {
		game = new Game(playerList, inputs[0]);
		gameStage.show();
		turned = false;
		createGameLoop();
		}catch (IndexOutOfBoundsException ex){
			ex.printStackTrace();
			throw new GameException("User cancelled game");
		}
	}
	
	
	public void initialisePlayers() {
		ArrayList<String> names;
		playerList = new ArrayList<Player>();
		playerViewList = new ArrayList<PlayerView>();
		names = getNames();
		int i = 1;
		for (String name: names) {
			playerList.add(new Player(i, name));
			playerViewList.add(new PlayerView(playerList.get(i-1)));
			++i;
		}
	}
	
	private ArrayList<String> getNames() {
		NameDialog n = new NameDialog();
		ArrayList<String> names = new ArrayList<String>();
		try {
			names = n.getNames(inputs[1]);
		}catch(GameException ex) {
			gameStage.close();
			menuStage.show();
		}
		return names;

	}
	
	private void createKeyListeners() {
		addGameKeys();
		gameScene.setOnKeyPressed(event ->{
			if (keyListeners.containsKey(event.getCode())) {
					System.out.println(event.getCode().toString());
					keyListeners.put(event.getCode(), true);
			}
		});
		gameScene.setOnKeyReleased(event ->{
			if (keyListeners.containsKey(event.getCode())) {
				keyListeners.put(event.getCode(),  false);

			}
		});
	}
	
	private void addGameKeys() {
		keyListeners = new HashMap<KeyCode, Boolean>();
		keyListeners.put(KeyCode.SPACE, false);
		keyListeners.put(KeyCode.H, true);
		addPlayerKeys();
	}
	
	private void addPlayerKeys() {
		for (Player p: playerList)
			keyListeners.put(p.getKeyCode(), false);
	}
	
	private void resetKeys() {
		for (Player p: playerList)
			if (!p.getHasLost())
				keyListeners.put(p.getKeyCode(), false);
	}

	private void createEndSceneListeners() {
		endScene = new EndSubScene("CONGRATS\n" + game.getWinner().getName() + "\nYOU HAVE WON!");
		endScene.getMainButton().setOnAction(event ->{
			MenuController main = new MenuController();
			main.getMainStage().show();
			gameStage.close();
		});
		endScene.getExitButton().setOnAction(event ->{
			gameStage.close();
		});

	}
	
	private void createBackground() {
		Image image = new Image("resources/snap_bg.png", 600, 600, false, true);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}


	private void showSnapResult(Player p) {
		showLastCard();
		gamePane.getChildren().add(snap.showSnapResult(p.getId(), game.snap(p)));
		displayPlayers();
		turned = false;
	}
	
	private boolean playerHasPressed(Player p) {
		return (p.getHasLost() == false && keyListeners.get(p.getKeyCode()) == true);
	}

	private void checkSnap() {
		if (turned == true && instructions.getHidden())
			for (Player p: playerList)
				if (playerHasPressed(p)) {
					showSnapResult(p);
					break;
				}
	}
	

	public void showLastCard() {
		if(game.getLastCard() != null) {
			clearNode(leftPile);
			leftPile = CardView.showCard(game.getLastCard(), true);
			gamePane.getChildren().add(leftPile);
		}
	}

	public void displayPlayers() {
		for (PlayerView p: playerViewList){
			if (gamePane.getChildren().contains(p.getDisplay()))
				gamePane.getChildren().remove(p.getDisplay());
			Node n = p.showDisplay(playerList.get(p.getId()-1).getDeck().getNumberOfCards());
			gamePane.getChildren().add(n);
		}
	}
	
	private void clearSnap() {
		clearNode(snap.getSnap());
		clearNode(leftPile);
		clearNode(rightPile);
		clearNode(turn);
		leftPile = game.getEmptyCard();
		gamePane.getChildren().add(leftPile);
	}
	
	private void showTopCard() {
		rightPile = CardView.showCard(game.getTopCard(), false);
		gamePane.getChildren().add(rightPile);
	}

	
	private void showTurn() {
		System.out.println(game.getTurn());
		turn = playerViewList.get(game.getTurn()).getTurn();
		gamePane.getChildren().add(turn);
	}
	
	private void clearNode(Node n) {
		if (gamePane.getChildren().contains(n))
			gamePane.getChildren().remove(n);
	}
	
	private void moveInstructions() {
		if (!(instructions.getHidden())) {
			gamePane.getChildren().remove(instructions);
		    gamePane.getChildren().add(instructions);
		}
		instructions.move();
		keyListeners.put(KeyCode.H, false);
	}

	public void endGame() {
		System.out.println(game.getWinner().getName() + " wins!");
		createEndSceneListeners();
		gamePane.getChildren().add(endScene);
		endScene.moveSceneIn();
	}
	
	public void turnCard() {
		keyListeners.put(KeyCode.SPACE, false);
		clearSnap();
		game.turn();
		showTurn();
		showTopCard();
		turned = true;
	}

	public void createGameLoop() {
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if (keyListeners.get(KeyCode.H)) {
					moveInstructions();
				}			
				if ((instructions.getHidden()) && keyListeners.get(KeyCode.SPACE)){
					turnCard();
					displayPlayers();
				}
				checkSnap();
				resetKeys();
				if (game.getGameOver()) {
					endGame();
					this.stop();
				}
			}
			
		};
		timer.start();
	}

}


