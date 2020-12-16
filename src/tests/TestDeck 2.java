package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import model.Card;
import model.CardException;
import model.Deck;;

class TestDeck {
	Deck deck;
	
	@Test
	void testDeckTooSmall() {
		try {
			deck = new Deck(0);
			fail("Deck too small");
		} catch(CardException ex) {}
	}
	
	@Test
	void testDeckTooBig() {
		try {
			deck = new Deck(7);
			fail("Deck can't be > 6");
		}catch(CardException ex) {};
	}
	
	@Test
	void testValidDeck() {
		deck = new Deck(2);
		assertEquals(52*2, deck.getNumberOfCards());
		deck = new Deck(3);
		assertEquals(52*3, deck.getNumberOfCards());
		deck = new Deck(6);
		assertEquals(52*6, deck.getNumberOfCards());
	}
	
	@Test
	void testSetEmptyDeck() {
		deck = new Deck(2, true);
		assertEquals(0, deck.getNumberOfCards());
		deck = new Deck(4, true);
		assertEquals(0, deck.getNumberOfCards());
		deck = new Deck(6, true);
		assertEquals(0, deck.getNumberOfCards());
		deck = new Deck(2, false);
		assertEquals(104, deck.getNumberOfCards());
		try {
			deck = new Deck(1, false);
			fail("deck too small");
		} catch(CardException ex) {};
		try {
			deck = new Deck(7, false);
			fail("deck too big");
		} catch(CardException ex) {};
		
	}

	@Test
	void testPopDeck() {
		deck = new Deck(2);
		for(int i = 0; i < 52; i++) {
			assertEquals(i+1, deck.popTopCard().getValue());
		}
		assertEquals(1, deck.popTopCard().getValue());
		assertEquals(51, deck.getNumberOfCards());
	}
	
	@Test
	void testPopEmptyDeck(){
		deck = new Deck(2);
		for(int i = 0; i < 104; i++) {
			deck.popTopCard();
		}
		assertEquals(0, deck.getNumberOfCards());
		try {
			deck.popTopCard();
			fail("deck is empty, can't pop");
		}catch(CardException ex) {};
	}
	
	@Test
	void testGetCard() {
		Deck deck = new Deck(2);
		assertEquals(deck.getCardAtIndex(2).getValue(), 3);
		try {
			deck.getCardAtIndex(-1);
			fail("Can't get negative index");
		} catch(CardException ex) {};
		try {
			deck.getCardAtIndex(105);
			fail("Card index out of range");
		} catch(CardException ex) {};
	}
	
	@Test
	void testAddCard() {
		Deck deck = new Deck(2, true);
		Card card = new Card(1);
		deck.addCard(card);
		assertEquals(deck.getNumberOfCards(), 1);
		deck.addCard(card);
		assertEquals(deck.getNumberOfCards(), 2);
		assertEquals(deck.popTopCard().getValue(), 1);

	}

	@Test
	void testShuffle() {
		Deck deck1 = new Deck(2);
		Deck deck2 = new Deck(2);
		 assertTrue(deck1.equals(deck2));
		 deck1.shuffle();
		 assertTrue(!deck1.equals(deck2));
		 while (deck1.getNumberOfCards() > 9)
			 deck1.popTopCard();
		 deck1.shuffle();
		 while (deck1.getNumberOfCards() > 2)
			 deck1.popTopCard();
		 
		 // Test 2 cards
		 deck1.shuffle();
		 // Test1 cards
		 deck1.popTopCard();
		 deck1.shuffle();
		 // Test shuffle empty deck
		 deck1.popTopCard();
		 deck1.shuffle();
	}
	
	@Test
	void testValuesAfterShuffle() {
		ArrayList<Integer> check = new ArrayList<Integer>();
		Deck deck = new Deck(2);
		Card card;		
		deck.shuffle();
		for (int i = 1; i < 53; ++i) {
			check.add(Integer.valueOf(i));
			check.add(Integer.valueOf(i));
		}
		while (deck.getNumberOfCards() > 0) {
			card = deck.popTopCard();
			assertTrue(check.contains(Integer.valueOf(card.getValue())));
			check.remove(Integer.valueOf(card.getValue()));
		}
		assertTrue(check.size() == 0);
	}
	
	@Test
	void testSplitDeckTwo() {
		Deck deck = new Deck(2);
		Deck deck2 = new Deck(4);
		Deck deck3 = new Deck(6);
		ArrayList<Deck> split;
		split = deck.splitDeck(2);
		assertEquals(split.get(0).getNumberOfCards(), 52);
		assertEquals(split.get(1).getNumberOfCards(), 52);

		split = deck2.splitDeck(2);
		assertEquals(split.get(0).getNumberOfCards(), 2 * 52);
		assertEquals(split.get(1).getNumberOfCards(), 2 * 52);
		
		split = deck3.splitDeck(2);
		assertEquals(split.get(0).getNumberOfCards(), 3 * 52);
		assertEquals(split.get(1).getNumberOfCards(), 3 * 52);
		
		
		
	}
	
	@Test
	void testSplitDeckThree() {
		Deck deck = new Deck(2);
		Deck deck2 = new Deck(4);
		Deck deck3 = new Deck(6);
		ArrayList<Deck> split;
		split = deck.splitDeck(3);
		assertEquals(split.get(0).getNumberOfCards(), 35);
		assertEquals(split.get(1).getNumberOfCards(), 35);
		assertEquals(split.get(2).getNumberOfCards(), 34);
		
		split = deck2.splitDeck(3);
		assertEquals(split.get(0).getNumberOfCards(), 70);
		assertEquals(split.get(1).getNumberOfCards(), 69);
		assertEquals(split.get(2).getNumberOfCards(), 69);
		
		split = deck3.splitDeck(3);
		assertEquals(split.get(0).getNumberOfCards(),104);
		assertEquals(split.get(1).getNumberOfCards(), 104);
		assertEquals(split.get(2).getNumberOfCards(), 104);
	
	}
	
	@Test
	void testSplitDeckFour() {
		Deck deck = new Deck(2);
		Deck deck2 = new Deck(4);
		Deck deck3 = new Deck(6);
		ArrayList<Deck> split;
		split = deck.splitDeck(4);
		assertEquals(split.get(0).getNumberOfCards(), 26);
		assertEquals(split.get(1).getNumberOfCards(), 26);
		assertEquals(split.get(2).getNumberOfCards(), 26);
		assertEquals(split.get(3).getNumberOfCards(), 26);
		
		split = deck2.splitDeck(4);
		assertEquals(split.get(0).getNumberOfCards(), 52);
		assertEquals(split.get(1).getNumberOfCards(), 52);
		assertEquals(split.get(2).getNumberOfCards(), 52);
		assertEquals(split.get(3).getNumberOfCards(), 52);

		split = deck3.splitDeck(4);
		assertEquals(split.get(0).getNumberOfCards(), 78);
		assertEquals(split.get(1).getNumberOfCards(), 78);
		assertEquals(split.get(2).getNumberOfCards(), 78);
		assertEquals(split.get(3).getNumberOfCards(), 78);

	}
	
	@Test
	void testWrongSplit(){
		Deck deck = new Deck(2);
		try {
			deck.splitDeck(105);
			fail("Split too big");
		} catch(CardException ex) {};
		try {
			deck.splitDeck(0);
			fail("Split too big");
		} catch(CardException ex) {};
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testDeckEquals() {
		Deck deck1 = new Deck(2);
		Deck deck2 = new Deck(2);
		Deck deck3 = new Deck(3);
		Card c = new Card(52);
		assertTrue(deck1.equals(deck2));
		assertFalse(deck2.equals(deck3));
		deck1.shuffle();
		assertFalse(deck1.equals(deck2));
		deck1 = new Deck(2);
		deck1.addCard(c);
		deck2.addCard(c);
		assertTrue(deck1.equals(deck2));
		deck2.popTopCard();
		assertFalse(deck2.equals(deck1));
		assertFalse(deck2.equals(c));
	}
	
	@Test
	void testTopCard() {
		Card a = new Card(1);
		Card b = new Card(2);
		deck = new Deck(2);
		assertEquals(null, deck.getTopCard());
		deck.setTopCard(a);
		assertFalse(deck.getTopCard().equals(b));
		assertTrue(deck.getTopCard().equals(a));
		assertEquals(deck.popTopCard(), a);
	}
	
	@Test
	void testLastCard() {
		Card a = new Card(1);
		Card b = new Card(2);
		deck = new Deck(2);
		assertEquals(null, deck.getLastCard());
		deck.setLastCard(a);
		assertFalse(deck.getLastCard().equals(b));
		assertTrue(deck.getLastCard().equals(a));
	}
	
	@Test void testSnap() {
		Card a = new Card(1);
		Card b = new Card(2);
		deck = new Deck(2);
		assertFalse(deck.isSnap());
		deck.setTopCard(a);
		assertFalse(deck.isSnap());
		deck.setTopCard(null);
		deck.setLastCard(a);
		assertFalse(deck.isSnap());
		deck.setTopCard(b);
		assertFalse(deck.isSnap());
		deck.setTopCard(a);
		assertTrue(deck.isSnap());
		
	}
}


