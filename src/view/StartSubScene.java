package view;

import java.util.ArrayList;

import javafx.scene.layout.HBox;

public class StartSubScene extends SnapSubScene {
	private SnapButton playButton;
	private ArrayList<SnapCheckBox> playerBoxes;
	private ArrayList<SnapCheckBox> deckBoxes;
	
	public StartSubScene() {
		initialiseBody();
	}
	
	
	protected void initialiseBody() {

		InfoLabel deckLabel = new InfoLabel("HOW MANY DECKS OF CARDS?", 300, 40);
		InfoLabel playerLabel = new InfoLabel("HOW MANY PLAYERS?", 300, 40);
		playButton = new SnapButton("PLAY");
		playerBoxes = new ArrayList<SnapCheckBox>();
		deckBoxes = new ArrayList<SnapCheckBox>();
		HBox chooseDeck = createQuantityPicker(2, 6, deckBoxes);
		HBox choosePlayers = createQuantityPicker(2, 4, playerBoxes);
		positionNode(choosePlayers, 115, 120);
		positionNode(chooseDeck, 30, 270);
		positionNode(playButton, 125, 350);
		positionNode(deckLabel, 60, 220);
		positionNode(playerLabel, 60, 60);
	//
		this.getPane().getChildren().add(deckLabel);
		this.getPane().getChildren().add(playerLabel);
		this.getPane().getChildren().add(choosePlayers);
		this.getPane().getChildren().add(chooseDeck);
		this.getPane().getChildren().add(playButton);
	}
	
	
	protected HBox createQuantityPicker(int min, int max, ArrayList<SnapCheckBox> boxes) {
		HBox frame = new HBox();
		frame.setSpacing(60);
		for (int i = min; i <= max; i++) {
			SnapCheckBox box = new SnapCheckBox(i);
		    boxes.add(box);
			frame.getChildren().add(box);

		}
		return frame;
	}
	
	public ArrayList<SnapCheckBox> getPlayerBoxes(){
		return playerBoxes;
	}
	
	public ArrayList<SnapCheckBox> getDeckBoxes(){
		return deckBoxes;
	}
	
	public SnapButton getPlayButton() {
		return playButton;
	}
	
	public int getChosenDeck() {
		for (SnapCheckBox b: deckBoxes) {
			if (b.getIsChosen())
				return b.getNumber();
		}
		return -1;
	}
	
	public int getChosenPlayers() {
		for (SnapCheckBox b: playerBoxes) {
			if (b.getIsChosen())
				return b.getNumber();
		}
		return -1;
	}



}