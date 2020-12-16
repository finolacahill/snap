package view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.GameException;

public class SnapCheckBox extends VBox{
	
	private ImageView boxImage;
	private ImageView numberImage;
	
	private int number;
	private String tickedBox = "resources/blue_boxCheckmark.png";
	private String emptyBox = "resources/grey_box.png";
	private String numberImages = "resources/numbers/";	
	private boolean isChosen;


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
		numberImage = new ImageView(numberImages + Integer.toString(number) + ".png");
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

	public int getNumber() {
		return number;
	}
	
	public boolean getIsChosen() {
		return isChosen;
	}
	
	public void setChosen(boolean isChosen) {
		this.isChosen = isChosen;
		String imageToSet = this.isChosen ? tickedBox : emptyBox;
		boxImage.setImage(new Image(imageToSet));
	}
}
