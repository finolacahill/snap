package model;


import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	final int numberOfDecks;
	final int maxCards;
	private ArrayList <Card> deck;
	private Random random = new Random();
	private Card topCard;
	private Card lastCard;

	public Deck(int number) {
		if (number < 2 || number > 6)
			throw new CardException("Invalid Deck Quantity");
		numberOfDecks = number;
		maxCards = number * 52;
		setDeck();
		}
	
	public Deck(int number, boolean emptyDeck) {
		if (number < 2 || number > 6)
			throw new CardException("Invalid Deck Split");
		numberOfDecks = number;
		maxCards = number * 52;
		if (emptyDeck == false)
			setDeck();
		else
			setEmptyDeck();
	}
	
	public int getNumberOfCards() {
		return deck.size();
	}
	
	private void setEmptyDeck() {
		deck = new ArrayList<Card>();
	}
	
	private void setDeck() {
		deck = new ArrayList<Card>();
		for (int i = 0; i < numberOfDecks; ++i) {
			for (int j = 1; j <= 52; j++) {
				deck.add(new Card(j));
				}
		}
	}

	private void swapRandom() {
		int x;
		int y;
		Card tmp;
		x = random.nextInt(deck.size());
		y = random.nextInt(deck.size());
		tmp = deck.get(x);
		deck.set(x, deck.get(y));
		deck.set(y, tmp);
	}

	public void shuffle() {
		if (getNumberOfCards() > 1) {
			for (int i = 0; i < (15 * numberOfDecks); i++) {
				swapRandom();	
			}
		}
	}
	
	public Card getCardAtIndex(int i) {
		if (i < 0 || i > deck.size() - 1)
			throw new CardException("Index out of range");
		return deck.get(i);
	}
	
	public void addCard(Card c) {
		deck.add(c);
	}

	public Card popTopCard() {
		if (deck.size() == 0)
			throw new CardException("Deck is empty!");
		Card c = deck.get(0);
		deck.remove(0);
		return c;
	}
	
	public ArrayList<Deck> splitDeck(int n){
		if (n < 2 || n > 4)
			throw new CardException("Split too large");
		ArrayList<Deck> split = new ArrayList<Deck>();
		for (int i = 0; i < n; i++) {
			split.add(new Deck(numberOfDecks, true));
		}
		while(deck.size() > 0) {
			for (int i = 0; i < n && deck.size() > 0; i++) {
				split.get(i).addCard(popTopCard());
			}
		}
		return split;
	}

	public boolean equals(Object d) {
		if (!(d instanceof Deck))
			return false;
		if (((Deck) d).getNumberOfCards() != deck.size())
			return false;
		for (int i = 0; i < deck.size(); ++i) {
			if (deck.get(i).getValue() != ((Deck) d).getCardAtIndex(i).getValue())
				return false;
		}
		return true;			
	}
	
	public void setTopCard(Card c) {
		topCard = c;		
	}
	
	public void setLastCard(Card c) {
		lastCard = c;
	}

	public Card getTopCard() {
		return topCard;
	}
	
	public Card getLastCard() {
		return lastCard;
	}
	
	public Boolean isSnap() {
		if(topCard != null && lastCard != null)
			return topCard.equals(lastCard);
		return false;
	}

}
