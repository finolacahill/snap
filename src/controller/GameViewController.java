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
import view.PlayerView;

/**
 * Controls the view objects and listeners for game play.
 */
public class GameViewController {

	
	private Stage menuStage;
	private GameView view;
	private Game game;

	private ArrayList<String> nameList;
	private boolean turned;
	private final int[] inputs;

	
	HashMap<KeyCode, Boolean> keyListeners;

	/**
	 * Game View Controller is instantiated with inputs generated by the
	 * menu controller, indicating number of players and decks.
	 * @param input
	 * a list of two ints, the first indicating the number of decks,
	 * and the second the number of players.
	 */
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

	/**
	 * Collects Player names and instantiates a new game, a game loop and a new game view.
	 * @param menuStage
	 * menuStage, to be able to return to menu stage after game.
	 */
	public void createNewGame(Stage menuStage) {
		
		this.menuStage = menuStage;
		this.menuStage.hide();
		view = new GameView(inputs[1]);
		try {
		view.show();
		game = new Game(getNames(), inputs[0]);
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
		for (PlayerView p: view.getPlayerViewList())
			keyListeners.put(p.getKeyCode(), false);
	}
	
	private void resetKeys() {
		for (PlayerView p: view.getPlayerViewList())
			if (!game.getPlayer(p.getId()-1).getHasLost())
				keyListeners.put(p.getKeyCode(), false);
	}

	private void createEndSceneListeners() {
		view.getEndScene().getMainButton().setOnAction(event ->{
			MenuController main = new MenuController();
			menuStage.show();
			view.close();
		});
		view.getEndScene().getExitButton().setOnAction(event -> view.close());

	}

	private void displayPlayers() {
		for (Player p: game.getPlayers()) {
			view.updateDisplay(p.getId(), p.getDeck().getNumberOfCards());
		}
	}
					
	private boolean playerHasPressed(PlayerView p) {
		return (!game.getPlayer(p.getId() - 1).getHasLost()
				&& keyListeners.get(p.getKeyCode()));
	}

	private void checkSnap() {
		if (turned && view.getInstructions().getHidden())
			for (PlayerView p: view.getPlayerViewList())
				if (playerHasPressed(p)) {
					if(game.getLastCard() != null)
						view.showLastCard(game.getLastCard().getValue());
					view.showSnapResult(p.getId(), game.snap(game.getPlayer(p.getId()-1)));
					displayPlayers();
					turned = false;
					break;
				}
	}
	
	private void endGame() {
		view.createEndScene(game.getWinner().getName());
		createEndSceneListeners();
		view.getEndScene().moveSceneIn();
	}
	
	private void turnCard() {
		keyListeners.put(KeyCode.SPACE, false);
		view.clearSnap(game.getPile().getNumberOfCards());
		game.turn();
		view.showTurn(game.getTurn());
		view.showTopCard(game.getTopCard().getValue());
		view.updatePileCount(game.getPile().getNumberOfCards());
		turned = true;
	}

	private void createGameLoop() {
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


