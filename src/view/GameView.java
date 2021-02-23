package view;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Game view manages the views for the game scene. It manages
 * the player views, the card views, the instructions scene,
 * and the end scene.
 */
public class GameView {
	private ArrayList<PlayerView> playerViewList;
	private Pane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Node leftPile;
	private Node rightPile;
	private Text turn;
	
	private InstructionsSubScene instructions;
	private EndSubScene endScene;
	
	private PileView pile;
	private SnapActionView snap;
	final int numPlayers;
	final int gameWidth = 600;
	final int gameHeight = 600;

	/**
	 * Initialises the stage elements.
	 * @param numPlayers
	 * number of players in game.
	 */
	public GameView(int numPlayers) {
		this.numPlayers = numPlayers;
		initialiseStage();
	}
	
	private void initialiseStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, gameWidth, gameHeight);
		gameStage = new Stage();
	    gameStage.setResizable(false);
		gameStage.setScene(gameScene);
		createBackground();
		instructions = new InstructionsSubScene();
		pile = new PileView();
		snap = new SnapActionView();
		gamePane.getChildren().add(instructions);

	}

	/**
	 * Initialise player views with given list of names.
	 * @param nameList
	 * ArrayList of strings containing player names.
	 */
	public void initialisePlayerViews(ArrayList<String> nameList) {
		playerViewList = new ArrayList<>();
		for (int i = 1; i <= numPlayers; i++) 
			playerViewList.add(new PlayerView(i, nameList.get(i-1)));
	}

	/**
	 * Creates end scene indicating winner's name and adds it to gamePane.
	 * @param text
	 * Name of winning player.
	 */
	public void createEndScene(String text) {
		endScene = new EndSubScene("CONGRATS\n" + text + "\nYOU HAVE WON!");
		gamePane.getChildren().add(endScene);
	}

	
	private void createBackground() {
		Image image = new Image("resources/snap_bg.png", 600, 600, false, true);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}

	/**
	 * Shows the card image with the given value in the compare (left) position
	 * @param cardValue
	 * Value of card to be shown
	 */
	public void showLastCard(int cardValue) {
		clearNode(leftPile);
		leftPile = CardView.showCard(cardValue, true);
		gamePane.getChildren().add(leftPile);
	}

	/**
	 * Updates the text showing the number of cards in the communal pile
	 * @param numberCards
	 * current number of cards in pile
	 */
	public void updatePileCount(int numberCards) {
		clearNode(leftPile);
		leftPile = pile.getPileView(numberCards);
			gamePane.getChildren().add(leftPile);
	}

	/**
	 * Updates a players display, with regards to number of cards, and whether they have lost.
	 * @param player
	 * player number
	 * @param numberCards
	 * number of cards player has
	 */
	public void updateDisplay(int player, int numberCards) {
		if (gamePane.getChildren().contains(playerViewList.get(player-1).getDisplay()))
				gamePane.getChildren().remove(playerViewList.get(player-1).getDisplay());
			gamePane.getChildren().add(playerViewList.get(player-1).showDisplay(numberCards));
		}

	/**
	 * Clears nodes after a snap incident, removing shown card, removing snap notice,
	 * clearing turn notice and updating pile count view.
	 * @param pileCount
	 * number of cards in pile
	 */
	public void clearSnap(int pileCount) {
		clearNode(snap.getSnap());
		clearNode(rightPile);
		clearNode(turn);
		updatePileCount(pileCount);
	}

	/**
	 * Shows the card image with the given value in the non-compare (right) position
	 * @param cardValue
	 * Value of card to be shown
	 */
	public void showTopCard(int cardValue) {
		rightPile = CardView.showCard(cardValue, false);
		gamePane.getChildren().add(rightPile);
		}

	/**
	 * Shows text indicating it's the turn of the player with the given id number.
	 * @param playerId
	 * id of player
	 */
	public void showTurn(int playerId) {
		turn = playerViewList.get(playerId).getTurn();
		gamePane.getChildren().add(turn);
	}

	private void clearNode(Node n) {
		if (gamePane.getChildren().contains(n))
			gamePane.getChildren().remove(n);
	}

	/**
	 * Show visual indicating whether attempted snap was successful or not.
	 * @param id
	 * ID of player attempting snap
	 * @param success
	 * Boolean indicating snap was successful or not
	 */
	public void showSnapResult(int id, boolean success) {
		gamePane.getChildren().add(snap.showSnapResult(id, success));
	}

	/**
	 * Move instructions scene. Instructions scene can be called and hidden
	 * multiple times during game. If it has already been called and hidden
	 * and newer nodes have been added to gamepane, they will obscure the instructions.
	 * Hence, when it is called we remove and then re-add the scene, to ensure it
	 * is the most forward facing node on the gamepane.
	 */
	public void moveInstructions() {
		if (gamePane.getChildren().contains(instructions)) {
			gamePane.getChildren().remove(instructions);
			gamePane.getChildren().add(instructions);
		}
		instructions.move();
}

	/**
	 * Close the game stage.
	 */
	public void close() {
		gameStage.close();
	}

	/**
	 * Show the game stage.
	 */
	public void show() {
		gameStage.show();
	}

	/**
	 * Get the game scene
	 * @return
	 * Scene
	 */
	public Scene getGameScene() {
		return gameScene;
	}

	/**
	 * Get the ending scene.
	 * @return
	 * EndSubScene
	 */
	public EndSubScene getEndScene() {
		return endScene;
	}

	/**
	 * Get the Instructions scene
	 * @return
	 * InstructionsSubScene
	 */
	public InstructionsSubScene getInstructions() {
		return instructions;
	}

	/**
	 * Get ArrayList of all player views (one per player).
	 * @return
	 * ArrayList of PlayerView objects.
	 */
	public ArrayList<PlayerView> getPlayerViewList(){
		return playerViewList;
	}
}
