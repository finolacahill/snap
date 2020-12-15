package controller;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.MenuView;
import view.SnapButton;
import view.SnapCheckBox;
import view.SnapSubScene;

public class MenuController {
	
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	private SnapSubScene currentScene;
	private MenuView menu;
	
	int[] inputVariables = {-1,-1};
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	
	public MenuController() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
	    mainStage.setResizable(false);
	    menu = new MenuView(mainPane);
	    createButtonListeners();
		addStartListeners();


	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void createButtonListeners() {
		for (SnapButton button : menu.getButtonScenePair().keySet()) {
			button.setOnAction(event -> {
				showSubScene(menu.getButtonScenePair().get(button));
			});
		}
		menu.getExitButton().setOnAction(event ->{
			mainStage.close();
	});
		
	}
	private void addStartListeners() {
		addPlayListener();
		addBoxListener(menu.getStartScene().getDeckBoxes(), 0);
		addBoxListener(menu.getStartScene().getPlayerBoxes(), 0);
	}


	private void addBoxListener(ArrayList<SnapCheckBox> boxes, int i) {
		for (SnapCheckBox box: boxes) {
			box.setOnMouseClicked(event -> {
			for (SnapCheckBox other : boxes) {
				other.setChosen(false);
				}
			box.setChosen(true);
			});
		}
	}
	private Boolean getInput() {
		inputVariables[0] = menu.getStartScene().getChosenDeck();
		inputVariables[1] = menu.getStartScene().getChosenPlayers();
		return (inputEntered());
	}

	private void addPlayListener() {
		menu.getStartScene().getPlayButton().setOnAction(event ->{
			if (getInput()){
				try{
					GameViewController game = new GameViewController(inputVariables);
					game.createNewGame(getMainStage());
				}catch (Exception ex) {};
			} else
				missingInputAlert();
		});
		
	}
	
	private boolean inputEntered() {
		return(inputVariables[0] != -1 && inputVariables[1] != -1);
	}
	
	private void missingInputAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Missing input");
		alert.setContentText("Oops, please select number of decks and number of players.");
		alert.showAndWait();
	}
		
	private void showSubScene(SnapSubScene subScene) {
		if(currentScene != null && currentScene != subScene)
				currentScene.moveSceneOut();
		subScene.moveSceneIn();
		currentScene = subScene;
	}

	
	

}
