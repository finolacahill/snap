package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class ScoreSubScene extends SnapSubScene {
	private Text title;
	private Text text;
	private ScrollPane scorePane;
	private String scores;
	
	public ScoreSubScene() {
		initiaiseScorePane();
		setTitle();
		initialiseScoreCard();
		scores = new String("No scores found!");
		setText();
	}


	private void initialiseScoreCard() {
		this.getPane().getChildren().add(title);
		this.getPane().getChildren().add(scorePane);
		positionNode(title, 100, 80);
		positionNode(scorePane, 65, 100);
	}

	
	private void initiaiseScorePane() {
		scorePane = new ScrollPane();
		scorePane.setPrefSize(300, 300);
		scorePane.setContent(text);	
	}
	
	private void setTitle() {
		title = makeText("PREVIOUS GAMES\n*******************", 22);
	}
	
	private void setText() {
		text = makeText(scores, 10);
	}
	
	public void updateScores(String scores) {
		this.scores = scores;
		this.getPane().getChildren().remove(text);
		setText();
		scorePane.setContent(text);	
	}

}