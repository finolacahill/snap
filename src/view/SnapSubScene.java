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

public abstract class SnapSubScene extends SubScene{
	
	private final String BACKGROUND_IMAGE = "resources/metalPanel.png";
	public SnapSubScene() {
		super(new AnchorPane(), 500, 600);
		prefWidth(600);
		prefHeight(400);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 430, 450, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root = (AnchorPane) this.getRoot();
		root.setBackground(new Background(image));
		setLayoutX(1024);
		setLayoutY(20);
	}
	
	
	public void moveSceneIn() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(.5));
		transition.setNode(this);
		transition.setToX(-944);
		transition.play();
	}
	
	
	public void moveSceneOut() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(1));
		transition.setNode(this);
		transition.setToX(0);
		transition.play();
	}
		
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
	
	public Text makeText(String s, int size) {
		Text t = new Text(s);
		setToFont(t, size);
		return t;
	}
	
	private void setToFont(Text t, int size) {
		try {
			 t.setFont(Font.loadFont(new FileInputStream(new File(InfoLabel.FONT_PATH)), size));
		} catch (FileNotFoundException e) {
				t.setFont(Font.font("Verdana"));
			}
	}
	
	public void positionNode(Node object, int x, int y) {
		object.setLayoutX(x);
		object.setLayoutY(y);
	}
	
	protected String getFromTextFile(String path) {
		String s = new String();
		try {
			File f = new File(path);
			Scanner in = new Scanner(f);
			while (in.hasNext()) {
				s += "\n" + in.nextLine();
			}
			in.close();
		} catch (IOException ex) {
			s = new String("Text File not found, oops");
		}
		return s;
	}
	
	abstract protected void initialiseBody();
}
