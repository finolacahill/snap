package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class MenuView {
	private CreditsSubScene credits;
	private ScoreSubScene scores;
	private HelpSubScene help;
	private StartSubScene start;
	private SnapButton creditButton;
	private SnapButton scoreButton;
	private SnapButton helpButton;
	private SnapButton exitButton;
	private SnapButton startButton;
	private AnchorPane mainPane;
	private int  MENU_BUTTONS_START_X = 550;
	private int MENU_BUTTONS_START_Y = 50;
	List<SnapButton> menuButtons;
	HashMap<SnapButton, SnapSubScene> buttonScenePair;

	
	public MenuView(AnchorPane mainPane) {
		this.mainPane = mainPane;
		createBackground();
		createSubScenes();
		createButtons();
		createPairings();
	}
	
	private void createBackground() {
		Image image = new Image("resources/snap_bg.png", 800, 600, false, true);
		BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
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
	
	public HashMap<SnapButton, SnapSubScene> getButtonScenePair() {
		return buttonScenePair;
	}
	
	public List<SnapButton> getMenuButtons(){
		return menuButtons;
	}
	
	public SnapButton getExitButton() {
		return exitButton;
	}
	
	public StartSubScene getStartScene() {
		return start;
	}
	
}
