package model;


public class Card{
	
	final int value;

	public Card(int v) {
		if (v < 1 || v > 52)
			throw new CardException("Invalid Card Value");
		value = v;
	}
		
	public int getValue() {
		return value;
	}
	
	public boolean equals(Object c) {
		return (((Card) c).getValue() % 11 == value % 11);
	}
	
}

