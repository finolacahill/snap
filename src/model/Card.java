package model;

/**
 *
 * Contains functionality for a playing card. Cards can have a values between 1 and 52.
 * Each card value is considered as a modulo of the divide variable. This reduces
 * the size of the pack in order to increase the possibility of matching. The modulo
 * value may become set-able in future versions, to allow for "easy", "medium" and
 * "hard" levels of the game.
 *
 */
public class Card{

	/**
	 * Card takes an integer between 1 and 52 (inclusive) as a constructor variable.This
	 * represents its' card value.
	 */
	final int value;
	private int divide;
	public Card(int value) {
		if (value < 1 || value > 52)
			throw new CardException("Invalid Card Value");
		this.value = value;
		divide = 13;
	}
	
	/**
	 * Returns integer value of card
	 * @return integer
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Verifies if that (value % divide variable (13)) of both cards is equal.
	 * @return boolean
	 */
	public boolean equals(Object c) {
		if (!(c instanceof Card))
			return false;
		return (((Card) c).getValue() % divide == value % divide);
	}
	
}

