package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import model.GameException;
import javafx.scene.control.Alert.AlertType;

/**
 * The NameDialog class can create a pop-up dialog to collect player names for the game.
 */
public class NameDialog {
	ArrayList<String> nameList;

	/**
	 * NameDialog constructor instantiates an ArrayList for forthcoming names. When
	 * collecting names it does not accept duplicates, or names shorter than 1 character
	 * or longer than 14 characters after leading and trailing spaces have been trimmed.
	 */
	public NameDialog() {
		nameList = new ArrayList<String>();
	}
	
	//Code adapted from: https://code.makery.ch/blog/javafx-dialogs-official/
	private void getName(int id, boolean error){
	TextInputDialog dialog = createDialogWindow(id, error);
	Optional<String> result = dialog.showAndWait();
	if (result.isPresent() && validName(result.get())){
		nameList.add(result.get().trim());
	}
	else if (result.equals(Optional.empty())) {
		throw new GameException("User has cancelled");
	}
	else
		getName(id, true);
	}
	
	private TextInputDialog createDialogWindow(int id, boolean error) {
		TextInputDialog dialog = new TextInputDialog("walter");
		if (error)
			nameErrorAlert();
		dialog.setHeaderText("Player " + id + " enter your name");
		dialog.setContentText("Name...");
		return dialog;
	}
	
	private void nameErrorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setContentText("Oops, name must be unique and be between 1-14 characters long!");
		alert.showAndWait();
	}
	
	private boolean validName(String s) {
		return s!=null && s.trim().length()>0 && !nameList.contains(s) && s.trim().length() < 15;
	}

	/**
	 * GetNames collects n number of names in String format, and returns them as an
	 * Array List. If an invalid or duplicate name is entered, it shows an error
	 * dialog and waits for a user to enter a valid name or cancel out of the game.
	 * @param n
	 * number of names
	 * @return
	 * ArrayList of Strings
	 */
	public ArrayList<String> getNames(int n){
		for (int i = 0; i < n; i++)
			getName(i+1, false);
		return nameList;
		}
	}
	

