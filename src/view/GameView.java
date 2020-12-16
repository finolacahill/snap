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
	private int numPlayers;
	private int ganeWidth = 600;
	private int gameHeight = 600;
	
	
	public GameView(int numPlayers) {
		this.numPlayers = numPlayers;
		initialiseStage();
	}
	
	private void initialiseStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, ganeWidth, gameHeight);
		gameStage = new Stage();
	    gameStage.setResizable(false);
		gameStage.setScene(gameScene);
		createBackground();
		instructions = new InstructionsSubScene();
		pile = new PileView();
		snap = new SnapActionView();
		gamePane.getChildren().add(instructions);

	}
	
	
	public void initialisePlayerViews(ArrayList<String> nameList) {
		playerViewList = new ArrayList<PlayerView>();
		for (int i = 1; i <= numPlayers; i++) 
			playerViewList.add(new PlayerView(i, nameList.get(i-1)));
	}
	

	public void createEndScene(String text) {
		endScene = new EndSubScene("CONGRATS\n" + text + "\nYOU HAVE WON!");
		gamePane.getChildren().add(endScene);
	}

	
	private void createBackground() {
		Image image = new Image("resources/snap_bg.png", 600, 600, false, true);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}

	public void showLastCard(int cardValue) {
		clearNode(leftPile);
		leftPile = CardView.showCard(cardValue, true);
		gamePane.getChildren().add(leftPile);
	}


	public void updatePileCount(int numberCards) {
		clearNode(leftPile);
		leftPile = pile.getPileView(numberCards);
			gamePane.getChildren().add(leftPile);
	}

	public void updateDisplay(int player, int numberCards) {
		if (gamePane.getChildren().contains(playerViewList.get(player-1).getDisplay()))
				gamePane.getChildren().remove(playerViewList.get(player-1).getDisplay());
			gamePane.getChildren().add(playerViewList.get(player-1).showDisplay(numberCards));
		}

	public void clearSnap(int pileCount) {
		clearNode(snap.getSnap());
		clearNode(rightPile);
		clearNode(turn);
		updatePileCount(pileCount);
	}

	public void showTopCard(int cardValue) {
		rightPile = CardView.showCard(cardValue, false);
		gamePane.getChildren().add(rightPile);
		}


	public void showTurn(int playerId) {
		turn = playerViewList.get(playerId).getTurn();
		gamePane.getChildren().add(turn);
	}

	private void clearNode(Node n) {
		if (gamePane.getChildren().contains(n))
			gamePane.getChildren().remove(n);
	}
	
	public void showSnapResult(int id, boolean success) {
		gamePane.getChildren().add(snap.showSnapResult(id, success));
	}
	
	public void moveInstructions() {
		if (gamePane.getChildren().contains(instructions)) {
			gamePane.getChildren().remove(instructions);
			gamePane.getChildren().add(instructions);
		}
		instructions.move();
}
	public void close() {
		gameStage.close();
	}
	
	public void show() {
		gameStage.show();
	}
	
	public Scene getGameScene() {
		return gameScene;
	}
	
	public EndSubScene getEndScene() {
		return endScene;
	}
	
	public InstructionsSubScene getInstructions() {
		return instructions;
	}
}
