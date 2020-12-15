package view;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Card;
import model.CardException;

public class CardView {

	
	static public VBox showCard(Card card, boolean compare) {
		if (card.getValue() < 0) {
			throw new CardException("Invalid Card NUmber");
		}
		VBox cardView = new VBox();
	
		ImageView cardImage = new ImageView("resources/cards/" + (card.getValue() % 11) + ".png");
		cardImage.setFitHeight(250);
		cardImage.setFitWidth(150);
		cardView.setAlignment(Pos.CENTER);
		cardView.setSpacing(20);
		positionCard(cardView, compare);
		cardView.getChildren().add(cardImage);
		return cardView;
	}
	
	static private void positionCard(VBox card, boolean compare) {
		if (compare == false) {
		card.setLayoutX(320);
		card.setLayoutY(170);}
		else {
			card.setLayoutX(130);
			card.setLayoutY(170);
		}
	}
		
	}
