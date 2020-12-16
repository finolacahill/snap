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
	ArrayList<String> names = new ArrayList<String>();
	
	void addNames(int n) {
		for (int i = names.size(); i < n; i++) {
			names.add("test" + (i+1));
		}
	}
	
	void setGame(int p, int decks) {
		names = new ArrayList<String>();
		addNames(p);
		game = new Game(names, decks);
	}
	
	@Test
	void testConstructer() {
		addNames(1);
		ArrayList<String> empty = null;
		try {
			game = new Game(empty, 2);
			fail("Null players list");
		}catch (GameException ex) {};
		try {
			game = new Game(names, 2);
			fail("players must be at least 2");
		}catch (GameException ex) {};
		try {
			addNames(5);
			game = new Game(names, 2);
			fail("too many players");
		}catch (GameException ex) {};
		names.remove(1);
		game = new Game(names, 2);
		System.out.println(names);
		assertEquals(game.getPlayers().size(), 4);
		try {
			game = new Game(names, 1);
			fail("Not enough decks");
		}catch (GameException ex) {};
		try {
			game = new Game(names, 7);
			fail("Too many decks");
		}catch (GameException ex) {};
	}

	@Test 
	void testGetPlayers(){
		setGame(3, 2);
		ArrayList<Player> players= game.getPlayers();
		for (int i = 0; i < 3; i++){
			assertTrue(players.get(i).getName().equals("test" + (i+1)));
		}
	}

	@Test
	void testAssignCards() {
		setGame(2,2);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 52);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 35);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 35);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 34);
		
		setGame(4,2);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 26);
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
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 51);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 52);
		test = game.getTopCard();
		assertEquals(1, game.getPile().getNumberOfCards());
		game.turn();
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 51);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 51);
		assertEquals(test, game.getLastCard());
		assertTrue(test != game.getTopCard());
		assertEquals(2, game.getPile().getNumberOfCards());
		
		while (game.getPlayer(0).getDeck().getNumberOfCards() > 0)
				game.getPlayer(0).getDeck().popTopCard();
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
		assertTrue(!game.snap(game.getPlayer(0)));
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 62);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 34);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 40);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 40);
		
		setGame(4,2);
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 31);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 31);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 31);

				
	}
	
	@Test
	void testSkipLostPlayers() {
		Card a = new Card(1);
		Card b = new Card(2);
		setGame(4,2);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPile().getNumberOfCards(), 0);
		while (game.getPlayer(0).getDeck().getNumberOfCards() > 0)
			game.getPlayer(0).getDeck().popTopCard();
		for (int i = 0 ; i < 16; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 34);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 34);
		
		setGame(3,5); 
		while (game.getPlayer(1).getDeck().getNumberOfCards() > 0)
			game.getPlayer(1).getDeck().popTopCard();
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 102);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 86);
		
		setGame(4,3); 
		while (game.getPlayer(1).getDeck().getNumberOfCards() > 0)
			game.getPlayer(1).getDeck().popTopCard();
		while (game.getPlayer(0).getDeck().getNumberOfCards() > 0)
			game.getPlayer(0).getDeck().popTopCard();
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(!game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 39);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 54);

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
		assertTrue(game.snap(game.getPlayer(0)));
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 62);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 52);
		
		setGame(3,2);
		for (int i = 0 ; i < 10; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 35);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 35);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 44);

		
		setGame(4,2);
		for (int i = 0 ; i < 15; i++)
			game.getPile().addCard(a);
		game.setLastCard(a);
		game.setTopCard(b);
		assertTrue(game.snap(game.getPlayer(2)));
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 26);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 41);

		
	}
	
	@Test
	void testSnapEmptyPile() {
		setGame(2,2);
		Card a = new Card(1);
		Card b = new Card(1);
		Card c = new Card(2);
		game.snap(game.getPlayer(0));
		game.setTopCard(a);
		assertFalse(game.snap(game.getPlayer(0)));
		game.setTopCard(a);
		game.setLastCard(b);
		assertTrue(game.snap(game.getPlayer(1)));
		game.setTopCard(a);
		game.setLastCard(c);
		assertFalse(game.snap(game.getPlayer(0)));
	}

	
	@Test
	void testGameOver() {
		setGame(4,2);
		game.turn();
		game.turn();
		game.turn();
		assertFalse(game.getGameOver());
		while(game.getPlayer(2).getDeck().getNumberOfCards() > 0)
			game.turn();
		assertEquals(game.getPlayer(0).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(1).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(2).getDeck().getNumberOfCards(), 0);
		assertEquals(game.getPlayer(3).getDeck().getNumberOfCards(), 1);
		assertTrue(game.getGameOver());
		assertTrue(game.getPlayer(3) == (game.getWinner()));
		
		setGame(3,2);
		game.getPlayer(2).getDeck().popTopCard();
		game.getPlayer(2).getDeck().popTopCard();
		game.getPlayer(2).getDeck().popTopCard();
		assertFalse(game.getGameOver());
		while(game.getPlayer(2).getDeck().getNumberOfCards() > 0)
			game.turn();
		assertFalse(game.getGameOver());
		while(game.getGameOver() == false)
			game.turn();
		assertEquals(game.getWinner(), game.getPlayer(1));
	}

}
