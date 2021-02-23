package view;

import java.util.ArrayList;

import javafx.scene.layout.HBox;

/**
 * This class contains the visual elements for selecting player numbers and
 * deck numbers, and also the button to launch the game play view.
 */
public class StartSubScene extends SnapSubScene {
	private SnapButton playButton;
	private ArrayList<SnapCheckBox> playerBoxes;
	private ArrayList<SnapCheckBox> deckBoxes;
	
	public StartSubScene() {
		createPlayerNumberChooser();
		createDeckChooser();
		createPlayButton();
	}
	
	private void createPlayerNumberChooser() {
		InfoLabel playerLabel = new InfoLabel("HOW MANY PLAYERS?", 300, 40);
		playerBoxes = new ArrayList<SnapCheckBox>();
		HBox choosePlayers = createQuantityPicker(4, playerBoxes);
		positionNode(choosePlayers, 115, 120);
		positionNode(playerLabel, 60, 60);
		this.getPane().getChildren().add(playerLabel);
		this.getPane().getChildren().add(choosePlayers);
	}

	private void createDeckChooser() {
		deckBoxes = new ArrayList<SnapCheckBox>();
		InfoLabel deckLabel = new InfoLabel("HOW MANY DECKS OF CARDS?", 300, 40);
		HBox chooseDeck = createQuantityPicker(6, deckBoxes);
		positionNode(chooseDeck, 30, 270);
		positionNode(deckLabel, 60, 220);
		this.getPane().getChildren().add(deckLabel);
		this.getPane().getChildren().add(chooseDeck);

	}
	private void createPlayButton() {
		playButton = new SnapButton("PLAY");
		positionNode(playButton, 125, 350);
		this.getPane().getChildren().add(playButton);
	}

	/*
	Creates a a horizontal box with a range of check boxes, to pick a numerical value.
	 */
	private HBox createQuantityPicker(int max, ArrayList<SnapCheckBox> boxes) {
		HBox frame = new HBox();
		frame.setSpacing(60);
		for (int i = 2; i <= max; i++) {
			SnapCheckBox box = new SnapCheckBox(i);
		    boxes.add(box);
			frame.getChildren().add(box);
		}
		return frame;
	}

	/**
	 * Returns the SnapCheckBox objects necessary for selecting the number
	 * of players.
	 * @return
	 * ArrayList of SnapCheckBox Objects
	 */
	public ArrayList<SnapCheckBox> getPlayerBoxes(){
		return playerBoxes;
	}

	/**
	 * Returns the SnapCheckBox objects necessary for selecting the number
	 * of decks.
	 * @return
	 * ArrayList of SnapCheckBox Objects
	 */
	public ArrayList<SnapCheckBox> getDeckBoxes(){
		return deckBoxes;
	}

	/**
	 * Returns the play button for launching the game view
	 * @return
	 * play snap button
	 */
	public SnapButton getPlayButton() {
		return playButton;
	}

	/**
	 * Returns the numerical value of the chosen check box for the
	 * player picker. If no value is selected, returns -1.
	 * @return
	 * number of players selected
	 */
	public int getChosenDeck() {
		for (SnapCheckBox b: deckBoxes) {
			if (b.getIsChosen())
				return b.getNumber();
		}
		return -1;
	}

	/**
	 * Returns the numerical value of the chosen check box for the
	 * deck picker. If no value is selected, returns -1.
	 * @return
	 * number of decks selected
	 */
	public int getChosenPlayers() {
		for (SnapCheckBox b: playerBoxes) {
			if (b.getIsChosen())
				return b.getNumber();
		}
		return -1;
	}



}