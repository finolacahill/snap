package view;

import javafx.scene.text.Text;

/**
 * The instructions subscene contains game-play instructions.
 * These instructions can be viewed at any time during game play.
 */
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
	private void initialiseBody() {
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

	/**
	 * As there is only one key to both show and hide the instructions scene,
	 * the class needs to keep track of whether it is hidden or not. This
	 * function will move the class in if it is hidden, and move it out if
	 * it is not hidden.
	 */
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

	/**
	 * Gameplay should not be able to continue while the game is obscured by
	 * the instructions scene. Hence the gameloop needs to be able to check
	 * if the instructions scene is hidden or not.
	 * @return
	 * boolean indicating whether scene is hidden or not.
	 */
	public boolean getHidden() {
		return hidden;
	}
}
