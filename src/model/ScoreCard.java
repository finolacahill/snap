package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScoreCard {
	private File scores;
	private PrintWriter out;
	private Scanner in;
	
	public ScoreCard() {
		setScores();
	}
	
	private void setScores() {
		try {
			scores = new File("./src/resources/text/scorecard.txt");
			try {
				if(!scores.exists()) {
					scores.createNewFile();
				}
			} catch (IOException ex) {
				scores = null;
			}
		} catch (Exception ex) {
			scores = null;
		}
	}
	
	public void writeScore(String s) {
		if (scores != null) {
			try {
				out = new PrintWriter(new FileWriter(scores, true));
				out.println(s);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			finally {out.close();}
		}
	}
	
	public String readScore() {
		if (scores == null)
			return (new String("Score file not found."));
		String output = new String();
		try {
			in = new Scanner(scores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(in.hasNext()) {
			output = "\n\n * " + in.nextLine() + output;
		}
		in.close();
		return output;
	}
	
}
