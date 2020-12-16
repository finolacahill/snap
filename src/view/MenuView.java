package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.geometry.Insets;
//import view.SnapSubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Menu view manages the views for the menu scene. It instantiates and contains
 * the menu-related buttons and subscenes.
 */
public class MenuView {
	
	private CreditsSubScene credits;
	private ScoreSubScene scores;
	private HelpSubScene help;
	private StartSubScene start;
	private SnapSubScene currentScene;

	private SnapButton creditButton;
	private SnapButton scoreButton;
	private SnapButton helpButton;
	private SnapButton exitButton;
	private SnapButton startButton;
	
	private final AnchorPane mainPane;
	
	final int  MENU_BUTTONS_START_X = 550;
	final int MENU_BUTTONS_START_Y = 50;
	
	List<SnapButton> menuButtons;
	HashMap<SnapButton, SnapSubScene> buttonScenePair;

	/**
	 * MenuView takes the main Anchor pane as a constructor variable. This allows it to
	 * place elements to the mainPane.
	 * @param mainPane
	 * mainPane
	 */
	public MenuView(AnchorPane mainPane) {
		this.mainPane = mainPane;
		createBackground();
		createSubScenes();
		createButtons();
		createPairings();
	}
	
	private void createBackground() {
		try {
		Image image = new Image("./resources/snap_bg.png", 800, 600, false, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(backgroundImage));
		}
		catch(Exception ex) {
			BackgroundFill background = new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY);
			mainPane.setBackground(new Background(background));
		}
	}
	
	private void createSubScenes() {
		credits = new CreditsSubScene();
		scores = new ScoreSubScene();
		help = new HelpSubScene();
		start = new StartSubScene();
		mainPane.getChildren().add(credits);
		mainPane.getChildren().add(scores);
		mainPane.getChildren().add(help);
		mainPane.getChildren().add(start);
	}
		
	private void addMenuButton(SnapButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		
	}
	
	private void createButtons() {
		menuButtons = new ArrayList<SnapButton>();
		addMenuButton(startButton = new SnapButton("START"));
		addMenuButton(scoreButton = new SnapButton("SCORES"));
		addMenuButton(helpButton = new SnapButton("HELP"));
		addMenuButton(creditButton = new SnapButton("CREDITS"));
		addMenuButton(exitButton = new SnapButton("GOODBYE"));

	}
	
	private void createPairings() {
		buttonScenePair = new HashMap<SnapButton, SnapSubScene>();
		buttonScenePair.put(startButton, start);
		buttonScenePair.put(scoreButton, scores);
		buttonScenePair.put(helpButton,  help);
		buttonScenePair.put(creditButton, credits);		
	}

	/**
	 * This hashmap contains buttons as keys, and their corresponding subscene as values.
	 * @return
	 * hashmap containing SnapButton keys, and SnapSubScene values.
	 */
	public HashMap<SnapButton, SnapSubScene> getButtonScenePair() {
		return buttonScenePair;
	}

	/**
	 * Returns the exit button. The exit button is not included in the pairing hashmap,
	 * as it does not have a corresponding sub scene, it exits.
	 * @return
	 * SnapButton
	 */
	public SnapButton getExitButton() {
		return exitButton;
	}

	/**
	 * Returns the start subscene.
	 * @return
	 * StartSubScene
	 */
	public StartSubScene getStartScene() {
		return start;
	}

	/**
	 * Returns the scores subscene.
	 * @return
	 * ScoreSubScene
	 */
	public ScoreSubScene getScoreScene() {
		return scores;
	}

	/**
	 * This function moves a subscene out of view before moving a new subscene into view.
	 * @param subScene
	 * A subscene extending SnapSubScene
	 */
	public void showSubScene(SnapSubScene subScene) {
		if(currentScene != null && currentScene != subScene)
				currentScene.moveSceneOut();
		subScene.moveSceneIn();
		currentScene = subScene;
	}
}
