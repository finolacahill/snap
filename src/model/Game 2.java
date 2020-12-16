package model;

import java.util.ArrayList;


public class Game {
	private ArrayList<Player> players;
	private Deck pile;
	private int numberOfPlayers;
	private int turn;
	private Player winner;
	private boolean gameOver;
	private ScoreCard scoreCard;
	
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
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
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
	
	public ScoreCard getScoreCard() {
		return scoreCard;
	}
		
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public void assignCards() {
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
	
	public int getTurn() {
		return turn;
	}
	
	public Deck getPile() {
		return pile;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
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
	
	public void setTopCard(Card c) {
		pile.setTopCard(c);
		
	}
	
	public void setLastCard(Card c) {
		pile.setLastCard(c);
	}

	public Card getTopCard() {
		return pile.getTopCard();
	}
	
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

	public boolean snap(Player p) {
		if (pile.getLastCard() != null) {
			if (pile.isSnap()){
				givePile(p);
				System.out.println(p.getName() + " wins Snap and has cards: "+ p.getDeck().getNumberOfCards());
				System.out.println(p.getName() + " has lost is " + p.getHasLost());

				return true;
			}
		}
		distributePile(p);
		System.out.println(p.getName() + " loses Snap");
		printCardNums();
		return false;
	}
	
	public void printCardNums() {
		for (int i = 0; i < numberOfPlayers; ++i) {
			System.out.println(players.get(i).getName() + " has " + players.get(i).getDeck().getNumberOfCards());
			System.out.println(players.get(i).getName() + " has lost is " + players.get(i).getHasLost());

		}
	}
	
}
