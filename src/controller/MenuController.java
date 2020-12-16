package controller;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GameException;
import model.ScoreCard;
import view.MenuView;
import view.SnapButton;
import view.SnapCheckBox;

public class MenuController {
	
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	private MenuView menu;
	
	int[] inputVariables = {-1,-1};
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private ScoreCard scoreCard;

	
	public MenuController() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
	    mainStage.setResizable(false);
	    menu = new MenuView(mainPane);
	    scoreCard = new ScoreCard();
	    createButtonListeners();
		addStartListeners();
		fetchScores();
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void createButtonListeners() {
		for (SnapButton button : menu.getButtonScenePair().keySet()) {
			button.setOnAction(event -> {
				menu.showSubScene(menu.getButtonScenePair().get(button));
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

	private void fetchScores() {
		menu.getScoreScene().updateScores(scoreCard.readScore());
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

	private boolean inputEntered() {
		return(inputVariables[0] != -1 && inputVariables[1] != -1);
	}

	private void addPlayListener() {
		menu.getStartScene().getPlayButton().setOnAction(event ->{
			if (getInput()){
				try{
					GameViewController game = new GameViewController(inputVariables);
					game.createNewGame(getMainStage());
				}catch (GameException ex) {
				};
			} else
				missingInputAlert();
		});
		
	}
	
	private void missingInputAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Missing input");
		alert.setContentText("Oops, please select number of decks and number of players.");
		alert.showAndWait();
	}
		


	
	

}
