package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PlayerView {
	private int displayNumber;
	private int id;
	private InfoLabel label;
	private HBox display;
	private String name;
	private Text turn;
	private ArrayList<int[]> corners;

	
	public PlayerView(int id, String name) {
		this.id = id;
		this.name = name;
		setCorners();
		setTurn();
		initialiseDisplay();
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
	public HBox showDisplay(int numCards) {
		if (displayNumber != numCards)
			updateDisplay(numCards);
		return display;
	}
	
	public int getId() {
		return id;
	}
	
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
	
	public Text getTurn() {
		return turn;
	}
}
