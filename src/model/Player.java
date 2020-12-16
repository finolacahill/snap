package model;

/**
 * Contains functionality for player.
 * This class stores the player name, id, its' personal deck of cards
 * and whether the player is still in the game or not.
 */
public class Player {
	
	private final String name;
	final int id;
	private Deck deck;
	private boolean hasLost;

	/**
	 * Player Object takes an id number between 1 and 4 (inclusive) and a name that is between
	 * 1 and 14 characters long (inclusive).
	 * @param id
	 * int player id
	 * @param name
	 * String player name
	 */
	public Player(int id, String name) {
		if (!(checkInput(id, name)))
			throw new GameException("Player name or id is invalid.");
		this.id = id;
		this.name = name;

		hasLost = false;

	}
	
	private Boolean checkInput(int id, String name) {
		return (id >= 1 && id <= 4 && !isEmptyName(name) && name.length()<= 14);
	}

	/**
	 * Returns true if the player has lost and is out of the game.
	 * @return Boolean
	 */
	public boolean getHasLost() {
		return hasLost;
	}
	
	/**
	 * Set's hasLost variable to true, indicating the player is out of the game. 
	 */
	public void hasNowLost() {
		hasLost = true;
	}

	private boolean isEmptyName(String s) {
		return (s.trim().length() == 0);
	}
	
	/**
	 * Returns the player name.
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Sets the player's personal deck to the given Deck object.
	 * @param d
	 * Deck Object
	 */
	public void setDeck(Deck d) {
		deck = d;
	}
	
	/**
	 * Returns the player's Id.
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the player's personal deck
	 * @return deck
	 */
	public Deck getDeck() {
		return deck;
	}
	


	

}
