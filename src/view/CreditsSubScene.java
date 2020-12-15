package view;


import javafx.scene.text.Text;

public class CreditsSubScene extends SnapSubScene {
	private Text title;
	private Text text;

	public CreditsSubScene() {
		setTitle();
		setText();
		initialiseBody();	
	}
	
	@Override
	protected void initialiseBody() {
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


