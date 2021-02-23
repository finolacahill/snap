package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Abstract subscene class, with basic aesthetics and functionality for all subscenes.
 */
public abstract class SnapSubScene extends SubScene{

	/**
	 * height, width, and background image are predefined for all subscenes,
	 * and set in the constructor.
	 */
	public SnapSubScene() {
		super(new AnchorPane(), 500, 600);
		prefWidth(600);
		prefHeight(400);
 
		String BACKGROUND_IMAGE = "resources/metalPanel.png";
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 430, 450, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root = (AnchorPane) this.getRoot();
		root.setBackground(new Background(image));
		setLayoutX(1024);
		setLayoutY(20);
	}

	/**
	 * Moves the subscene into view.
	 */
	public void moveSceneIn() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(.5));
		transition.setNode(this);
		transition.setToX(-944);
		transition.play();
	}

	/**
	 * Moves the subscene out of view.
	 */
	public void moveSceneOut() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
		transition.setNode(this);
		transition.setToX(0);
		transition.play();
	}

	/**
	 * Returns this pane cast as an AnchorPane
	 * @return
	 * current pane
	 */
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

	/**
	 * Takes a string and a size, sets the font, and returns a text view.
	 * @param s
	 * Text to be displayed
	 * @param size
	 * Font size
	 * @return
	 * text view
	 */
	protected Text makeText(String s, int size) {
		Text t = new Text(s);
		setToFont(t, size);
		return t;
	}

	/**
	 * Tries to set to default font, if font is not found, sets text to Verdana.
	 * @param t
	 * text view
	 * @param size
	 * font size
	 */
	protected void setToFont(Text t, int size) {
		try {
			 t.setFont(Font.loadFont(new FileInputStream(new File(InfoLabel.FONT_PATH)), size));
		} catch (FileNotFoundException e) {
				t.setFont(Font.font("Verdana"));
			}
	}

	/**
	 * Positions node to given coordinates
	 * @param object
	 * node object
	 * @param x
	 * x coordinate
	 * @param y
	 * y coordinate
	 */
	protected void positionNode(Node object, int x, int y) {
		object.setLayoutX(x);
		object.setLayoutY(y);
	}

	/**
	 * Given a path, tries to collect text from said path
	 * and return it as a String. If the text file is not
	 * found, returns 'Text File not Found, oops'
	 * @param path
	 * path to text file
	 * @return
	 * found text, or error message.
	 */
	protected String getFromTextFile(String path) {
		StringBuilder s = new StringBuilder();
		try {
			File f = new File(path);
			Scanner in = new Scanner(f);
			while (in.hasNext()) {
				s.append("\n").append(in.nextLine());
			}
			in.close();
		} catch (IOException ex) {
			s = new StringBuilder("Text File not found, oops");
		}
		return s.toString();
	}
}
