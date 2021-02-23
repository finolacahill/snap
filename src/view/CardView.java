package view;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.CardException;

/**
 * Shows card image relative to given card value.
 */
public class CardView {

	/**
	 *
	 * @param cardValue
	 * Card Value
	 * @param compare
	 * If compare is true, the card is positioned to the left side of the pile.
	 * @return
	 * VBox of card Image
	 */
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

	// If compare is set to True, card must be positioned in left pile.
	static private void positionCard(VBox card, boolean compare) {
		if (!compare)
			card.setLayoutX(320);
		else
			card.setLayoutX(130);
		card.setLayoutY(170);
	}
		
}
