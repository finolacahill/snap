package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import model.ScoreCard;

public class ScoreSubScene extends SnapSubScene {
	private Text title;
	private Text text;
	private ScoreCard scoreCard;
	private ScrollPane scorePane;
	
	public ScoreSubScene() {
		initialiseBody();
	}

	@Override
	protected void initialiseBody() {
		scoreCard = new ScoreCard();
		setTitle();
		setText();
		initiaiseScorePane();
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
		text = makeText(scoreCard.readScore(), 10);
	}

}