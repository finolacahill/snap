package view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import model.GameException;
import javafx.scene.control.Alert.AlertType;

public class NameDialog {
	ArrayList<String> nameList;

	public NameDialog() {
		nameList = new ArrayList<String>();
	}
	
	//Code adapted from:
	//https://code.makery.ch/blog/javafx-dialogs-official/
	private void getName(int id, boolean error){
	TextInputDialog dialog = new TextInputDialog("walter");
	if (error == true)
		nameErrorAlert();
	dialog.setHeaderText("Player " + id + " enter your name");
	dialog.setContentText("Name...");
	Optional<String> result = dialog.showAndWait();
	if (result.isPresent() && validName(result.get())){
		nameList.add(result.get());
	}
	else if (result.equals(Optional.empty())) {
		throw new GameException("User has cancelled");
	}
	else
		getName(id, true);
	}
	
	private void nameErrorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setContentText("Ooops, name must be unique and be between 1-14 characters long!");
		alert.showAndWait();
	}
	
	private boolean validName(String s) {
		s = s.trim();
		return s!=null && s.length()>0 && nameList.contains(s) == false && s.length() < 15;
	}
	
	public ArrayList<String> getNames(int n){
		for (int i = 0; i < n; i++)
			getName(i+1, false);
		return nameList;
		}
	}
	

