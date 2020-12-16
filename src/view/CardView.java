package view;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.CardException;

public class CardView {

	
	static public VBox showCard(int cardValue, boolean compare) {
		if (cardValue < 0 || cardValue > 52) {
			throw new CardException("Invalid Card Number");
		}
		return createCard(cardValue%13, compare);
	}
	
	static private VBox createCard(int cardValue, Boolean compare) {
		VBox cardView = new VBox();
		ImageView cardImage = new ImageView("resources/cards/" + (cardValue % 13) + ".png");
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
