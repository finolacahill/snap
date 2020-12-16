package view;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class EndSubScene extends SnapSubScene {
	private Text text;
	private SnapButton exitButton = new SnapButton("GOODBYE");
	private SnapButton mainButton = new SnapButton("MAIN MENU");
	private ImageView image;
	
	public EndSubScene(String s) {
		setText(s);
		initialiseBody();
	}

	private void initialiseBody() {
		image = new ImageView("resources/end.png");
		image.setFitHeight(120);
		image.setFitWidth(120);
		positionNodes();
		this.getPane().getChildren().add(text);
		this.getPane().getChildren().add(image);
		this.getPane().getChildren().add(exitButton);
		this.getPane().getChildren().add(mainButton);

	}
	
	private void positionNodes() {
		positionNode(image, 150, 310);
		positionNode(text, 70, 100);
		positionNode(exitButton, 220, 220);
		positionNode(mainButton, 15, 220);
	}
	private void setText(String s) {
		text = makeText(s, 33);
	}
	
	public SnapButton getExitButton() {
		return exitButton;
	}
	
	public SnapButton getMainButton() {
		return mainButton;
	}

}