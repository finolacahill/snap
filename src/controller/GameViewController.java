package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Game;
import model.GameException;
import model.Player;
import view.GameView;
import view.NameDialog;


public class GameViewController {

	
	private Stage menuStage;
	private GameView view;
	private Game game;

	private ArrayList<String> nameList;
	private boolean turned;
	private int[] inputs;	

	
	HashMap<KeyCode, Boolean> keyListeners;
	
	public GameViewController(int[] input) {
		inputs = input;
	}
	
	private ArrayList<String> getNames() {
		NameDialog n = new NameDialog();
		nameList = new ArrayList<String>();
		try {
			nameList = n.getNames(inputs[1]);
		}catch(GameException ex) {
			view.close();
			menuStage.show();
		}
		return nameList;
	}
	

	public void createNewGame(Stage menuStage) {
		
		this.menuStage = menuStage;
		this.menuStage.hide();
		view = new GameView(inputs[1]);
		try {
		view.show();
		game = new Game(getNames(), inputs[1]);
		turned = false;
		view.initialisePlayerViews(nameList);
		createKeyListeners();
		createGameLoop();
		}catch (IndexOutOfBoundsException ex){
			throw new GameException("User cancelled game");
		}
	}
	

	private void createKeyListeners() {
		addGameKeys();
		view.getGameScene().setOnKeyPressed(event ->{
			if (keyListeners.containsKey(event.getCode())) {
					System.out.println(event.getCode().toString());
					keyListeners.put(event.getCode(), true);
			}
		});
		view.getGameScene().setOnKeyReleased(event ->{
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
		for (Player p: game.getPlayers())
			keyListeners.put(p.getKeyCode(), false);
	}
	
	private void resetKeys() {
		for (Player p: game.getPlayers())
			if (!p.getHasLost())
				keyListeners.put(p.getKeyCode(), false);
	}

	private void createEndSceneListeners() {
		view.getEndScene().getMainButton().setOnAction(event ->{
			MenuController main = new MenuController();
			main.getMainStage().show();
			view.close();
		});
		view.getEndScene().getExitButton().setOnAction(event ->{
			view.close();
		});

	}

	private void displayPlayers() {
		for (Player p: game.getPlayers()) {
			view.updateDisplay(p.getId(), p.getDeck().getNumberOfCards());
		}
	}
					
	private boolean playerHasPressed(Player p) {
		return (p.getHasLost() == false && keyListeners.get(p.getKeyCode()) == true);
	}

	private void checkSnap() {
		if (turned == true && view.getInstructions().getHidden())
			for (Player p: game.getPlayers())
				if (playerHasPressed(p)) {
					if(game.getLastCard() != null)
						view.showLastCard(game.getLastCard().getValue());
					view.showSnapResult(p.getId(), game.snap(p));
					displayPlayers();
					turned = false;
					break;
				}
	}
	
	public void endGame() {
		view.createEndScene(game.getWinner().getName());
		createEndSceneListeners();
		view.getEndScene().moveSceneIn();
	}
	
	public void turnCard() {
		keyListeners.put(KeyCode.SPACE, false);
		view.clearSnap(game.getPile().getNumberOfCards());
		game.turn();
		view.showTurn(game.getTurn());
		view.showTopCard(game.getTopCard().getValue());
		view.updatePileCount(game.getPile().getNumberOfCards());
		turned = true;
	}

	public void createGameLoop() {
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if (keyListeners.get(KeyCode.H)) {
					view.moveInstructions();
					keyListeners.put(KeyCode.H, false);
				}			
				if ((view.getInstructions().getHidden()) && keyListeners.get(KeyCode.SPACE)){
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


