package view;

import javafx.scene.text.Text;

/**
 * The help sub scene contains general instructions for the player to navigate the menu stage.
 * It reads from a text file, if that file is not found it handles the error silently and
 * does not interrupt game play.
 */

public class HelpSubScene extends SnapSubScene {
	private Text title;
	private Text text;
	
	public HelpSubScene() {
		setTitle();
		setText();
		initialiseBody();
	}

	private void initialiseBody() {
		this.getPane().getChildren().add(title);
		this.getPane().getChildren().add(text);
		positionNode(title, 180, 40);
		positionNode(text, 20, 60);

	}
	
	private void setTitle() {
		title = makeText("HELP\n*****", 22);
	}
	
	private void setText() {
		text = makeText(super.getFromTextFile("./src/resources/text/help.txt"), 14);
	}



}
