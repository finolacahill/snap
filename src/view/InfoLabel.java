package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class InfoLabel extends Label {
	
	public final static String FONT_PATH = "../Snap/src/resources/kenvector_future.ttf";

	public final static String BACKGROUND_IMAGE = "resources/green_button13.png";
	
	public InfoLabel(String text, int w, int h) {
		if (w < 0 || h < 0)
			throw new IllegalArgumentException("Width and height must be positive");
		setPrefWidth(w);
		setPrefHeight(h);
		setText(text);
		//setPadding(new Insets(40,40,40,40));
		setWrapText(true);
		setAlignment(Pos.CENTER);
		setLabelFont();
		
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, w, h, false, true), BackgroundRepeat.NO_REPEAT,  BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
	}

	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 13));
	} catch (FileNotFoundException e) {
		setFont(Font.font("Verdana"));
	}
}
}
