package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ScoreCard{
	private String scores;
	private String path = "./src/resources/text/scorecard.obj";
	private ObjectOutputStream oos;
	
	public ScoreCard() {
		scores = getScores();		
//		saveObject(path);
	}
	
	public ScoreCard(String path) {
		this.path = path;
	}

	public void writeScore(String s) {
		if (scores == null)
			scores = new String();
		scores = "\n" + s + "\n" + scores;
		saveObject(path);
	}

	public String readScore() {
		return scores;
	}
	
	private void saveObject(String path) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(scores);
			oos.close();
		}catch (Exception ex) {};
	}
	
	private String getScores() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
			Object o = ois.readObject();
			if (o instanceof String)
				return (String) o;
		} catch(Exception ex) {
			return null;
		}
		return null;
	}
	
}
