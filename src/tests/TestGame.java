package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.CardException;
import model.Game;
import model.GameException;
import model.Player;

class TestGame {
	Game game;
	ArrayList<Player> players = new ArrayList<Player>();
	
	void addPlayers(int n) {
		for (int i = players.size(); i < n; i++) {
			players.add(new Player(i+1, "test" + (i+1)));
		}
	}
	
	void setGame(int p, int decks) {
		players = new ArrayList<Player>();
		addPlayers(p);
		game = new Game(players, decks);
	}
	
	@Test
	void testConstructer() {
		addPlayers(1);
		ArrayList<Player> empty = null;
		try {
			game = new Game(empty, 2);
			fail("Null players list");
		}catch (GameException ex) {};
		try {
			game = new Game(players, 2);
			fail("players must be at least 2");
		}catch (GameException ex) {};
		try {
			addPlayers(5);
			game = new Game(players, 2);
			fail("too many players");
		}catch (GameException ex) {};
		players.remove(1);
		game = new Game(players, 2);
		assertEquals(game.getNumberOfPlayers(), 3);
	}
	
	@Test
	void testAssignCards() {
		setGame(2,2);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 52);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 35);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 35);
		assertEquals(players.get(2).getDeck().getNumberOfCards(), 34);
		
		setGame(4,2);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(2).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(3).getDeck().getNumberOfCards(), 26);
	}
	
	@Test
	void testTurnAssignment() {
		setGame(2,2);
		assertEquals(0, game.getTurn());
		game.turn();
		assertEquals(1, game.getTurn());
		game.turn();
		assertEquals(0, game.getTurn());

		setGame(3,2);
		assertEquals(0, game.getTurn());
		game.turn();
		assertEquals(1, game.getTurn());
		game.turn();
		assertEquals(2, game.getTurn());
		game.turn();
		assertEquals(0, game.getTurn());
		
		setGame(4,2);
		assertEquals(0, game.getTurn());
		game.turn();
		assertEquals(1, game.getTurn());
		game.turn();
		assertEquals(2, game.getTurn());
		game.turn();
		assertEquals(3, game.getTurn());
		game.turn();
		assertEquals(0, game.getTurn());	
	}
	
	@Test
	void testTurnCard() {
		Card test;
		setGame(2,2);
		game.turn();
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 51);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 52);
		test = game.getTopCard();
		assertEquals(1, game.getPile().getNumberOfCards());
		game.turn();
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 51);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 51);
		assertEquals(test, game.getLastCard());
		assertTrue(test != game.getTopCard());
		assertEquals(2, game.getPile().getNumberOfCards());
		
		while (players.get(0).getDeck().getNumberOfCards() > 0)
			players.get(0).getDeck().popTopCard();
		try {
			game.turn();
			fail("empty hand!");
		} catch (CardException ex) {};
	}
	
	
	@Test
	void testBadSnap() {
		setGame(2,2);
		Card a = new Card(1);
		Card b = new Card(2);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		assertEquals(game.getPile().getNumberOfCards(), 10);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(players.get(0)));
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 62);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(players.get(2)));
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 40);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 40);
		
		setGame(4,2);
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(players.get(2)));
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 31);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 31);
		assertEquals(players.get(3).getDeck().getNumberOfCards(), 31);
		
	}
	
	@Test
	void testGoodSnap() {
		setGame(2,2);
		Card a = new Card(1);
		Card b = new Card(1);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		assertEquals(game.getPile().getNumberOfCards(), 10);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(game.snap(players.get(0)));
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 62);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(game.snap(players.get(2)));
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 35);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 35);
		assertEquals(players.get(2).getDeck().getNumberOfCards(), 44);

		
		setGame(4,2);
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(game.snap(players.get(2)));
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(3).getDeck().getNumberOfCards(), 26);
		assertEquals(players.get(2).getDeck().getNumberOfCards(), 41);

		
	}
	
	@Test
	void testSnapEmptyPile() {
		setGame(2,2);
		Card a = new Card(1);
		Card b = new Card(1);
		Card c = new Card(2);
		game.snap(players.get(0));
		game.setTopCard(a);
		assertFalse(game.snap(players.get(0)));
		game.setTopCard(a);
		game.setLastCard(b);
		assertTrue(game.snap(players.get(0)));
		game.setTopCard(a);
		game.setLastCard(c);
		assertFalse(game.snap(players.get(0)));
	}
	
	@Test
	void testGameOver() {
		setGame(4,2);
		game.turn();
		game.turn();
		game.turn();
		assertFalse(game.getGameOver());
		while(players.get(2).getDeck().getNumberOfCards() > 0)
			game.turn();
		assertEquals(players.get(0).getDeck().getNumberOfCards(), 0);
		assertEquals(players.get(1).getDeck().getNumberOfCards(), 0);
		assertEquals(players.get(2).getDeck().getNumberOfCards(), 0);
		assertEquals(players.get(3).getDeck().getNumberOfCards(), 1);
		assertTrue(game.getGameOver());
		assertTrue(players.get(3) == (game.getWinner()));
		
		setGame(3,2);
		players.get(2).getDeck().popTopCard();
		players.get(2).getDeck().popTopCard();
		players.get(2).getDeck().popTopCard();
		assertFalse(game.getGameOver());
		while(players.get(2).getDeck().getNumberOfCards() > 0)
			game.turn();
		assertFalse(game.getGameOver());
		while(game.getGameOver() == false)
			game.turn();
		assertEquals(game.getWinner(), players.get(1));
	}

}
