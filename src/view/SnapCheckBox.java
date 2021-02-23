package view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.GameException;

/**
 * A number/check box pair to be used to gather user input.
 */
public class SnapCheckBox extends VBox{
	
	private ImageView boxImage;
	private ImageView numberImage;
	
	final int number;
	final String tickedBox = "resources/blue_boxCheckmark.png";
	final String emptyBox = "resources/grey_box.png";
	final String numberImages = "resources/numbers/";
	private boolean isChosen;

	/**
	 * The checkbox will display about it the indicated number.
	 * The number can be between 2 and 6.
	 * @param n
	 * the number to be displayed
	 */
	public SnapCheckBox(int n) {
		if (n < 2 || n > 6)
			throw new GameException("number is out of range");
		number = n;
		isChosen = false;
		initialiseNumberImage();
		initialiseBoxImage();
	}
	
	private void initialiseNumberImage() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		numberImage = new ImageView(numberImages + number + ".png");
		numberImage.setFitHeight(20);
		numberImage.setFitWidth(25);
		this.getChildren().add(numberImage);
	}
	
	private void initialiseBoxImage() {
		boxImage = new ImageView(emptyBox);
		boxImage.setFitHeight(20);
		boxImage.setFitWidth(25);
		this.getChildren().add(boxImage);
	}

	/**
	 * returns the number value associated with the checkbox
	 * @return
	 * checkbox numerical value
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * returns whether the chekBox has been selected or not
	 * @return
	 * is checkbox chosen
	 */
	public boolean getIsChosen() {
		return isChosen;
	}

	/**
	 * Sets the checkbox as chosen or not, and updates
	 * the image accordingly.
	 * @param isChosen
	 * has the checkbox has been selected
	 */
	public void setChosen(boolean isChosen) {
		this.isChosen = isChosen;
		String imageToSet = this.isChosen ? tickedBox : emptyBox;
		boxImage.setImage(new Image(imageToSet));
	}
}
