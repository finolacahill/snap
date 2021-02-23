package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

/**
 * A customized button to be used throughout subscenes.
 */
public class SnapButton extends Button {
	
	final String FONT_PATH = "../Snap/src/resources/kenvector_future.ttf";
	final String BUTTON_PRESSED_STYLE = "-fx-background-image: url('./resources/green_button03.png')";
	final String BUTTON_FREE_STYLE = "-fx-background-image: url('./resources/green_button02.png')";
	
	private int width = 190;
	private int height = 49;

	/** If width and height are not specified, uses default values.
	 *
	 * @param text
	 * text to be displayed on button.
	 */
	public SnapButton(String text) {
		createButton(text);
	}

	/**
	 * If width and height are given, default values are ignored. Both values
	 * must be greater than zero.
	 * @param text
	 * text to be displayed
	 * @param w
	 * button width
	 * @param h
	 * button height
	 */
	public SnapButton(String text, int w, int h) {
		if (w < 0 || h < 0)
			throw new IllegalArgumentException("Width/height must be positive");
		width = w;
		height = h;
		createButton(text);

	}
	
	private void createButton(String text) {
		setText(text);
		setButtonFont();
		setPrefWidth(width);
		setPrefHeight(height);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListenersStyle();
	}

	private void setButtonFont() {
		try {
		setFont(Font.loadFont(new FileInputStream(FONT_PATH), height/3));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", height/3));
		}
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(height-4);
		setLayoutY(getLayoutY() + 4);
	}
	
	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(height);
		setLayoutY(getLayoutY() - 4);
	}
	/*
	When button is clicked, show the pressed style and then released
	style in quick succession. When mouse passes over button, show drop shadow.
	 */
	private void initializeButtonListenersStyle() {
		
		setOnMousePressed(event ->{
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				setButtonPressedStyle();
			}
		});
		
		setOnMouseReleased(event ->{
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				setButtonReleasedStyle();
			}
		});
		
		setOnMouseEntered(event -> setEffect(new DropShadow()));
		
		setOnMouseExited(event -> setEffect(null));
	}
}
