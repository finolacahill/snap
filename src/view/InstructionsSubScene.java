package view;

import javafx.scene.text.Text;

public class InstructionsSubScene extends SnapSubScene {
	private Text title;
	private Text text;
	private boolean hidden;

	public InstructionsSubScene() {
		setTitle();
		setText();
		initialiseBody();
		hidden = true;

	}
	@Override
	protected void initialiseBody() {
		this.getPane().getChildren().add(title);
		this.getPane().getChildren().add(text);
		positionNode(title, 110, 40);
		positionNode(text, 20, 120);

	}
	
	private void setTitle() {
		title = makeText("INSTRUCTIONS\n***************", 22);
	}
	
	private void setText() {
		text = makeText(getFromTextFile("./src/resources/text/instructions.txt"), 14);
	}
	
	public void move() {
		if (!hidden) {
			super.moveSceneOut();
			hidden = true;
		}
		else {
			super.moveSceneIn();
			hidden=false;
		}
		
	}
	
	public boolean getHidden() {
		return hidden;
	}
}
