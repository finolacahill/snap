package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SnapActionView {
	private VBox snap;
	private Image image;
	private Text text;
	private String success = "resources/snap.png";
	private String failed = "resources/oops.png";
	public SnapActionView() {
		snap = new VBox();
	}
	
	public VBox showSnapResult(int id, boolean successful) {
		if (!successful)
			return showSnap(failed, id);
		return showSnap(success, id);
	}
	
	
	private VBox showSnap(String address, int id) {
		snap.getChildren().clear();
		image = new Image(address, 300, 150, false, true);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		snap.setBackground(new Background(background));
		setText(id);
		positionNodes();
		return snap;
	}
	
	private void setText(int id) {
		text = new Text("\nPLAYER " + id);
		try {
			text.setFont(Font.loadFont(new FileInputStream(new File(InfoLabel.FONT_PATH)), 16));
		} catch (FileNotFoundException e) {
			text.setFont(Font.font("Verdana"));
		}
	}
	
	private void positionNodes() {
		snap.getChildren().add(text);
		snap.setAlignment(Pos.TOP_CENTER);
		snap.setLayoutX(150);
		snap.setLayoutY(20);
		snap.setPrefHeight(150);
		snap.setPrefWidth(300);
	}
	
	public VBox getSnap() {
		return snap;
	}
}
