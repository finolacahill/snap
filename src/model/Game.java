package model;

import java.util.ArrayList;

/**
 *  Game handles game play, and contains the players and the communal pile of cards.
 *  It is instantiated with a list of Player names, and an int indicating the number
 *  of decks to be used.
 */
public class Game {
	private ArrayList<Player> players;
	private Deck pile;
	private int numberOfPlayers;
	private int turn;
	private Player winner;
	private boolean gameOver;
	private ScoreCard scoreCard;

	/**
	 * Game is constructed with a list of player names, and the number of decks to be used.
	 * @param names
	 * ArrayList of player names in String format
	 * @param decks
	 * number of decks to be used in game
	 */
	public Game(ArrayList<String> names, int decks) {
		if(!(checkInput(names, decks)))
			throw new GameException("Number of players or decks out of range");
		initialisePlayers(names);
		pile = new Deck(decks);
		turn = 0;
		numberOfPlayers = players.size();
		assignCards();
		gameOver = false;
		scoreCard = new ScoreCard();

	}
	
	/**
	 * Returns the player at index i in the Player list. 
	 * @param  i
	 * int index
	 * @return player
	 * Player Object
	 */
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	/**
	 * Returns an ArrayList of all Players
	 * @return players
	 * ArrayList of Player Objects
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	private void initialisePlayers(ArrayList<String> names) {
		players = new ArrayList<Player>();
		int i = 1;
		for (String name: names) {
			players.add(new Player(i, name));
			++i;
		}
	}
	
	private boolean checkInput(ArrayList<String> names, int decks){
		return (names != null && names.size() >= 2 && names.size() <= 4
				&& decks >= 2 && decks <= 6);
	}
	
	
	private void assignCards() {
		pile.shuffle();
		ArrayList<Deck> split;
		split = pile.splitDeck(numberOfPlayers);
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setDeck(split.get(i));
		}
	}
	
	private void setTurn() {
		if (turn == numberOfPlayers - 1)
			turn = 0;
		else
			++turn;
		if (players.get(turn).getDeck().getNumberOfCards() <= 0)
			setTurn();
	}
	
	/**
	 * Returns an int indicating the id of the player whose turn it is.
	 * @return int
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * Returns the deck that contains the communal pile of the game. 
	 * @return Deck Object
	 */
	public Deck getPile() {
		return pile;
	}
	
	/**
	 * Returns true if the game is over, else false. 
	 * @return boolean
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	/**
	 * Returns the winning player. 
	 * @return Player Object
	 */
	public Player getWinner() {
		return winner;
	}
	
	private void saveScore() {
		String win = new String(" won against");
		boolean first = true;
		for (Player p: players) {
			if (p.getHasLost()) {
				if (first)
					win += " " + p.getName();
				else
					win += " and " + p.getName();
				first = false;
			}
			else
				win = p.getName() + win;
		}
		scoreCard.writeScore(win);
	}

	private void checkGameOver() {
		int alive = 0;
		Player lastAlive = null;
		for (Player p: players) {
			if (!p.getHasLost()) {
				if (p.getDeck().getNumberOfCards() > 0) {
					lastAlive = p;
					++alive;
					}
				else
					p.hasNowLost();
				}
			}
		if (alive == 1) {
			winner = lastAlive;
			gameOver = true;
			saveScore();
		}
	}

	/**
	 * Sets the last turned card as the second last turned card. Takes a card from 
	 * the player whose turn it is and adds it to the communal pile, setting it as
	 * the last turned card. Checks if any players have lost (have no more cards) 
	 * after that action and if the game is over (only one player left).
	 */
	public void turn() {
		pile.setLastCard(pile.getTopCard());
		try{
			pile.setTopCard(players.get(turn).getDeck().popTopCard());
			pile.addCard(pile.getTopCard());
			checkGameOver();
			setTurn();
		} catch (NullPointerException ex) {
			throw new GameException("Can't turn, have cards been assigned?");
		}
	}
	
	/**
	 * Sets the last turned card of the communal pile to the given Card object.
	 * @param c
	 * Card Object
	 */
	public void setTopCard(Card c) {
		pile.setTopCard(c);
		
	}
	
	/**
	 * Sets the second last turned card of the communal pile to the given Card object.
	 * @param c
	 * Card Object
	 */
	public void setLastCard(Card c) {
		pile.setLastCard(c);
	}

	/**
	 * Gets the last turned card of the communal pile
	 * @return Card object
	 */
	public Card getTopCard() {
		return pile.getTopCard();
	}
	
	/**
	 * Gets the second last turned card of the communal pile
	 * @return Card object
	 */
	public Card getLastCard() {
		return pile.getLastCard();
	}
	
	private void givePile(Player p) {
		pile.shuffle();
		while(pile.getNumberOfCards() > 0)
			p.getDeck().addCard(pile.popTopCard());
		pile.setLastCard(null);
		pile.setTopCard(null);
	}
	
	private void distributePile(Player skip) {
		while(pile.getNumberOfCards() > 0) {
			for (int i = 0; i < numberOfPlayers && pile.getNumberOfCards() > 0; ++i) {
				if (i != skip.getId()-1 && players.get(i).getDeck().getNumberOfCards() > 0)					
					players.get(i).getDeck().addCard(pile.popTopCard());
			}
		}
		pile.setLastCard(null);
		pile.setTopCard(null);		
	}

	/**
	 * Checks if the snap provoked by Player p is valid. If it is, player p receives the
	 * cards contained in the communal pile. Else, the cards in the communal pile are
	 * distributed to all other players still in the game. It returns a boolean indicating
	 * whether snap was successful or not. 
	 * @param p
	 * Player Object
	 * @return boolean
	 */
	public boolean snap(Player p) {
		if (pile.getLastCard() != null) {
			if (pile.isSnap()){
				givePile(p);
				System.out.println(p.getName() + " wins Snap and has cards: "+ p.getDeck().getNumberOfCards());
				return true;
			}
		}
		distributePile(p);
		System.out.println(p.getName() + " loses Snap");
		return false;
	}
	
}
