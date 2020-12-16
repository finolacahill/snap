package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import model.Card;
import model.CardException;

class TestsCard {
	public Card card;
	@Test
	void testTooSmallValue() {
		try {
			card = new Card(0);
			fail("Can't be below 1.");
		} catch(CardException ex) {};
	}
	
	@Test
	void testTooBigValue() {
		try {
			card = new Card(53);
			fail("Can't be below 52.");
		} catch(CardException ex) {};
	}
	
	@Test
	void testCorrectValue() {
		card = new Card(52);
		assertEquals(52, card.getValue());
		card = new Card(1);
		assertEquals(1, card.getValue());
	}
	
	@Test
	void testEquals() {
		Card card = new Card(1);
		Card card2 = new Card(27);
		Card card3 = new Card(26);
		Card card4 = new Card(52);
		assertTrue(card.equals(card2));
		assertTrue(card3.equals(card4));
		assertFalse(card.equals(card3));
		assertFalse(card2.equals(card3));
	}
	
}
