package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.ScoreCard;

class TestScoreCard {
	private ScoreCard scoreCard;
	private String testPath = "./src/tests/test.obj";
	@Test
	void testConstructer() {
		scoreCard = new ScoreCard();
		scoreCard = new ScoreCard("Custom Path");
	}
	
	@Test
	void testSaveObject() {
		File f = new File(testPath);
		f.delete();
		scoreCard = new ScoreCard(testPath);
		scoreCard.writeScore("tests");
		scoreCard.writeScore("tests");
		assertEquals(scoreCard.readScore(),("\ntests\n\ntests\n"));
		f.delete();
	}
	
	void testPersistence() {
		File f = new File(testPath);
		f.delete();
		scoreCard = new ScoreCard(testPath);
		scoreCard.writeScore("I am a test");
		ScoreCard scoreCard2 = new ScoreCard(testPath);
		assertEquals(scoreCard2.readScore(), "\nI am a test\n");
		f.delete();
	}
	
	void testWrongType() {
		File f = new File(testPath);
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(f.exists());
		scoreCard = new ScoreCard(testPath);
		assertEquals(scoreCard.readScore(), null);
		f.delete();
	}
	

}
