package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The player view displays the state of each player. This means displaying their name,
 * their player number, the number of cards in their deck, or if they have lost. It
 * also displays whether or not it is their turn to turn over a card.
 */
public class PlayerView {
	private int displayNumber;
	private final int id;
	private InfoLabel label;
	private HBox display;
	private final String name;
	private Text turn;
	private ArrayList<int[]> corners;
	private KeyCode keyCode;

	/**
	 * Each player view is unique to a player, hence it is instantiated with their
	 * player name and their id.
	 * @param id
	 * player id number.
	 * @param name
	 * player name.
	 */
	public PlayerView(int id, String name) {
		this.id = id;
		this.name = name;
		setCorners();
		setTurn();
		initialiseDisplay();
		setKeyCode();
	}
	
	private void initialiseDisplay() {
		displayNumber = -1;
		display = new HBox();
		display.prefHeight(50);
		display.prefWidth(50);
		display.setLayoutX(corners.get(id-1)[0]);
		display.setLayoutY(corners.get(id-1)[1]);
	}

	private void setCorners() {
		corners = new ArrayList<int[]>();
		corners.add(new int[] {20, 445});
		corners.add(new int[] {420, 445});
		corners.add(new int[] {20, 20});
		corners.add(new int[] {420, 20});		
	}
	
	
	private void hasLost() {
		label = new InfoLabel("Player " + id + ",\n" + name + "\nYOU HAVE LOST!", 160, 90);
		display.setSpacing(0);
		display.getChildren().add(label);
	}

	private void updateDisplay(int numCards) {
			
		displayNumber = numCards;
		if (display.getChildren().contains(label))
			display.getChildren().remove(label);
		if (displayNumber != 0) {
			label = new InfoLabel("Player " + id + ",\n" + name + "\nYOU HAVE: " + displayNumber + "\n cards left!", 160, 90);
			display.setSpacing(0);
			display.getChildren().add(label);
		}
		else
			hasLost();
	}

	/**
	 * If the number of cards has changed, the display is updated
	 * and returned, otherwise it is simply returned.
	 * @param numCards
	 * number of cards player has
	 * @return
	 * HBox, player view.
	 */
	public HBox showDisplay(int numCards) {
		if (displayNumber != numCards)
			updateDisplay(numCards);
		return display;
	}
	
	public int getId() {
		return id;
	}

	/**
	 * Returns current state of player display, without potential update.
	 * @return
	 * player's display
	 */
	public HBox getDisplay() {
		return display;
	}
	
	private void setTurn() {
		turn = new Text("PLAYER " + id + "'S TURN");
		turn.setFill(Color.RED);
		try {
			turn.setFont(Font.loadFont(new FileInputStream(new File(InfoLabel.FONT_PATH)), 16));
		} catch (FileNotFoundException e) {
			turn.setFont(Font.font("Verdana"));
		}
		turn.setLayoutX(corners.get(id-1)[0]);
		turn.setLayoutY(corners.get(id-1)[1]);
	}

	/**
	 * Returns text view indicating it is this player's turn.
	 * @return
	 * turn text
	 */
	public Text getTurn() {
		return turn;
	}
	
	private void setKeyCode() {
		if (id == 1)
			keyCode = KeyCode.S;
		if (id == 2)
			keyCode = KeyCode.P;
		if (id == 3)
			keyCode = KeyCode.N;
		if (id == 4)
			keyCode = KeyCode.A;
	}

	/**
	 * Returns the keyCode associated with this player id.
	 * @return
	 * players snap key
	 */
	public KeyCode getKeyCode() {
		return keyCode;
}
}
