package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ScoreCard keeps track of results of previous games. It checks if a ScoreCard object
 * exists and if it does it loads these previous scores. It has a default path to this
 * object but can be instantiated with another path. If the object is not found, it
 * starts with a blank ScoreCard. If the path is invalid and scores can not be saved
 * it will not handle the exception and not effect game play. Custom paths are not saved,
 * only the scores are serialized.
 */
public class ScoreCard{
	private String scores;
	private String path = "./src/resources/text/scorecard.obj";
	private ObjectOutputStream oos;

	/**
	 * If no path is given ScoreCard uses a hard-written default path.
	 */
	public ScoreCard() {
		scores = getScores();		
	}

	/**
	 * The path variable indicates where the serialized score object should be saved and retrieved.
	 * @param path
	 * Custom path to object
	 */
	public ScoreCard(String path) {

		this.path = path;
		scores = getScores();
	}
	
	/**
	 * Takes the results of a game as a String parameter, and saves them to the ScoreCard.
	 * @param s
	 * String containing game results
	 */
	public void writeScore(String s) {
		if(s != null) {
			scores = "\n" + s + "\n" + scores;
			saveObject(path);
		}
	}

	/**
	 * Returns the previous results of games as a String.
	 * @return String
	 * previous game results
	 */
	public String readScore() {
		scores = getScores();
		return scores;
	}

	/*
	Saves this score string as a serialised object to either the given path
	 */
	private void saveObject(String path) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(scores);
			oos.close();
		}catch (Exception ex) {};
	}

	/*
	Tries to read in serialised object and return it as a string.
	If object not found or is the incorrect type, returns an indicative string.
	 */
	private String getScores() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
			Object o = ois.readObject();
			if (o instanceof String)
				return (String) o;
		} catch(Exception ex) {}
		return new String("\nNo previous game results found!");
	}
	
}
