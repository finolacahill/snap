package model;


import java.util.ArrayList;
import java.util.Random;
/**
 *
 * Contains functionality for a deck of Card Objects. It takes an int as an instance variable,
 * which indicates how many of decks of cards the deck contains. It can be 
 * instantiated with cards, or as an empty deck, by setting the boolean "emptyDeck" 
 * in the constructor to True. 
 */
public class Deck {
	
	final int numberOfDecks;
	final int maxCards;
	private ArrayList <Card> deck;
	private Random random = new Random();
	private Card topCard;
	private Card lastCard;

	/**
	 * Constructor variable is an integer indicating number of decks of cards to be contained in deck.
	 * @param number
	 * number of decks
	 */
	public Deck(int number) {
		if (number < 2 || number > 6)
			throw new CardException("Invalid Deck Quantity");
		numberOfDecks = number;
		maxCards = number * 52;
		setDeck();
		}

	/**
	 * If constructor variable emptyDeck is set to true, no cards are initially added to deck.
	 * @param number
	 * integer number of decks
	 * @param emptyDeck
	 * boolean
	 */
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
	/**
	 * 
	 * @return integer
	 */
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
	/**
	 * Shuffles the order of cards in deck randomly.
	 */
	public void shuffle() {
		if (getNumberOfCards() > 1) {
			for (int i = 0; i < (15 * numberOfDecks); i++) {
				swapRandom();	
			}
		}
	}
	
	private Card getCardAtIndex(int i) {
		if (i < 0 || i > deck.size() - 1)
			throw new CardException("Index out of range");
		return deck.get(i);
	}
	
	/**
	 * Adds a new Card object to the deck
	 * @param c
	 * Card Object c
	 */
	public void addCard(Card c) {
		deck.add(c);
	}

	/**
	 * Pops the top card from the deck and returns it. In this case the top card
	 * is considered to be the oldest card. 
	 * @return Card object
	 */
	public Card popTopCard() {
		if (deck.size() == 0)
			throw new CardException("Deck is empty!");
		Card c = deck.get(0);
		deck.remove(0);
		return c;
	}
	
	/**
	 * This splits the current deck into n equal parts. If there is an uneven amount
	 * of cards, they are dealt sequentially, as would occur in a real card game, 
	 * meaning the last player receive one lest card. N is an int between 2 and 4(inclusive).
	 * @param n
	 * int number of decks
	 * @return ArrayList
	 * returns an ArrayList of Deck objects
	 */
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

	/**
	 * Verifies if two decks are equal, meaning they have cards with the same values,
	 * in the same order.  
	 */
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
	

	/**
	 * Sets the last turned card
	 * @param c
	 * Card Object
	 */
	public void setTopCard(Card c) {
		topCard = c;		
	}
	
	/**
	 * Sets the second last turned card.
	 * @param c
	 * Card Object
	 */
	public void setLastCard(Card c) {
		lastCard = c;
	}

	
	/**
	 * Returns the last turned card, if it has been set, or returns null.
	 * @return topCard
	 * Card Object
	 */
	public Card getTopCard() {
		return topCard;
	}
	
	/**
	 * Returns the second last turned card, if it has been set, or returns null.
	 * @return lastCard
	 * Card Object
	 */
	public Card getLastCard() {
		return lastCard;
	}
	
	/**
	 * Verifies if the last two turned cards match and snap is valid.
	 * @return Boolean
	 */
	public Boolean isSnap() {
		if(topCard != null && lastCard != null)
			return topCard.equals(lastCard);
		return false;
	}

}
