package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class PileView {
	private StackPane pile;
	private Text pileCount;


	public PileView() {
		pile = new StackPane();
		ImageView cardImage = new ImageView("resources/cards/empty.png");
		cardImage.setFitHeight(250);
		cardImage.setFitWidth(150);
		pile.setAlignment(Pos.CENTER);
		pile.setLayoutX(130);
		pile.setLayoutY(170);
		pile.getChildren().add(cardImage);
	}

	private void updatePileCount(int numberOfCards) {
		pileCount = new Text("PILE CONTAINS:\n" + numberOfCards + " CARDS!");
		StackPane.setAlignment(pileCount, Pos.BOTTOM_CENTER);
		StackPane.setMargin(pileCount, new Insets(8,8,8,8));
		pileCount.setX(130);
		pileCount.setY(30);
	}
	
	public StackPane getPileView(int numberOfCards) {
		if (pileCount != null)
			pile.getChildren().remove(pileCount);
		updatePileCount(numberOfCards);
		pile.getChildren().add(pileCount);
		
		return pile;
	}
}
