package view;


import javafx.scene.text.Text;

/**
 * Sub scene showing game credits.
 */
public class CreditsSubScene extends SnapSubScene {
	private Text title;
	private Text text;

	/**
	 * Creates credit scene from credits text file found in resources.
	 * If file is not found, the exception is handled and gameplay continues
	 * as normal.
	 */
	public CreditsSubScene() {
		setTitle();
		setText();
		addNodes();	
	}
	
	private void addNodes() {
		this.getPane().getChildren().add(title);
		this.getPane().getChildren().add(text);
		positionNode(title, 150, 40);
		positionNode(text, 35, 120);
	}
	
	private void setTitle() {
		title = makeText("CREDITS\n*********", 22);
	}
	
	private void setText() {
		text = makeText(getFromTextFile("./src/resources/text/credits.txt"), 12);
	}
}


