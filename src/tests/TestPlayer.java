package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import model.Deck;
import model.GameException;
import model.Player;

class TestPlayer {
	Player player1;

	@Test
	void testConstructerId() {
		try {
			player1 = new Player(0, "Test");
			fail("Invalid id");
		} catch (GameException ex) {};
		
		try {
			player1 = new Player(5, "Test");
			fail("Invalid id");
		} catch (GameException ex) {};
		player1 = new Player(1, "Test");
	}
	
	@Test
	void testEmptyNane() {
		try {
			player1 = new Player(1, "                                     ");
			fail("Invalid name");
		} catch (GameException ex) {};
		try {
			player1 = new Player(1, "  \t       \n\n           \n\t\n\t               ");
			fail("Invalid name");
		} catch (GameException ex) {};
		try {
			player1 = new Player(1,"t             i               m");
			fail("name too long");
		} catch (GameException ex) {};
		player1 = new Player(1, "Finola 90");
		assertTrue(player1.getName().equals(("Finola 90")));
	}

	@Test
	void testGetName(){
		String s = new String();
		s = "Test";
		player1 = new Player(1, s);
		assertTrue(player1.getName().equals(s));	
	}
	
	@Test
	void testGetSetDeck() {
		Deck d = new Deck(2);
		player1 = new Player(1, "test");
		player1.setDeck(d);
		assertTrue(player1.getDeck().equals(d));
	}

	@Test
	void testHasLost() {
		player1 = new Player(1, "test");
		assertFalse(player1.getHasLost());
		player1.hasNowLost();
		assertTrue(player1.getHasLost());
	}
	
	@Test
	void testGetKeys() {
		player1 = new Player(1,"test");
		assertTrue(player1.getKeyCode().equals(KeyCode.S));
		player1 = new Player(2,"test");
		assertTrue(player1.getKeyCode().equals(KeyCode.P));
		player1 = new Player(3,"test");
		assertTrue(player1.getKeyCode().equals(KeyCode.A));
		player1 = new Player(4,"test");
		assertTrue(player1.getKeyCode().equals(KeyCode.N));
	}
	
	@Test
	void testGetId() {
		Player player = new Player(1,"test");
		assertEquals(player.getId(), 1);
		player = new Player(2,"test");
		assertEquals(player.getId(), 2);
		player = new Player(3,"test");
		assertEquals(player.getId(), 3);
		player = new Player(4,"test");
		assertEquals(player.getId(), 4);
	}
}
