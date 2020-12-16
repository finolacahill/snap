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
		if(!(checkDimensions(w,h)))
			throw new IllegalArgumentException("Width and height must be positive");
		createButton(w, h);
		addText(text);
	}
	
	private void addText(String text) {
		setText(text);
		setWrapText(true);
		setAlignment(Pos.CENTER);
		setLabelFont();		
	}
	
	private void createButton(int w, int h) {
		setPrefWidth(w);
		setPrefHeight(h);
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, w, h, false, true), BackgroundRepeat.NO_REPEAT,  BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
	}

	private boolean checkDimensions(int w, int h) {
		return (w > 0 && h > 0);
	}

	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 13));
	} catch (FileNotFoundException e) {
		setFont(Font.font("Verdana"));
	}
}
}
