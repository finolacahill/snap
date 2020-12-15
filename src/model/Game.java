package model;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Game {
	private ArrayList<Player> players;
	private Text pileCount;
	private Deck pile;
	private int turn;
	private Card lastCard;
	private Card topCard;
	private StackPane emptyCard;
	private int numberOfPlayers;
	private Player winner;
	private boolean gameOver;
	private ScoreCard scoreCard;
	
	public Game(ArrayList<Player> players, int decks) {
		if (players == null || players.size() < 2 || players.size() > 4)
			throw new GameException("Must have betweeen 2-4 players");
		if (decks < 2 || decks > 6)
			throw new GameException("Can use between 2 and 6 decks");
		this.players = players;
		pile = new Deck(decks);
		turn = 0;
		numberOfPlayers = players.size();
		assignCards();
		gameOver = false;
		makeEmptyCard();
		scoreCard = new ScoreCard();

	}
	
	public ScoreCard getScoreCard() {
		return scoreCard;
	}
	
	private void updatePileCount() {
		pileCount = new Text("PILE CONTAINS:\n" + pile.getNumberOfCards() + " CARDS!");
		StackPane.setAlignment(pileCount, Pos.BOTTOM_CENTER);
		StackPane.setMargin(pileCount, new Insets(8,8,8,8));
		pileCount.setX(130);
		pileCount.setY(30);
	}
	
	private void makeEmptyCard() {
		emptyCard = new StackPane();
		ImageView cardImage = new ImageView("resources/cards/empty.png");
		cardImage.setFitHeight(250);
		cardImage.setFitWidth(150);
		emptyCard.setAlignment(Pos.CENTER);
		emptyCard.setLayoutX(130);
		emptyCard.setLayoutY(170);
		emptyCard.getChildren().add(cardImage);
	}

	public StackPane getEmptyCard() {
		if (pileCount != null)
			emptyCard.getChildren().remove(pileCount);
		updatePileCount();
		emptyCard.getChildren().add(pileCount);
		
		return emptyCard;
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
				System.out.println("Checking " + p.getName());
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
		lastCard = topCard;
		try{
			topCard = players.get(turn).getDeck().popTopCard();
			pile.addCard(topCard);
			checkGameOver();
			setTurn();
		} catch (NullPointerException ex) {
			throw new GameException("Can't turn, have cards been assigned?");
		}
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
	
	private void givePile(Player p) {
		pile.shuffle();
		while(pile.getNumberOfCards() > 0)
			p.getDeck().addCard(pile.popTopCard());
		lastCard = null;
		topCard = null;
	}
	
	private void distributePile(Player skip) {
		while(pile.getNumberOfCards() > 0) {
			for (int i = 0; i < numberOfPlayers && pile.getNumberOfCards() > 0; ++i) {
				if (i != skip.getId()-1 && players.get(i).getDeck().getNumberOfCards() > 0)					
					players.get(i).getDeck().addCard(pile.popTopCard());
			}
		}
		lastCard = null;
		topCard = null;		
	}

	public boolean snap(Player p) {
		if (lastCard != null) {
			if (topCard.equals(lastCard)){
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
	
	
	
//	public static void main(String [] args) {
//		Game game = new Game();
//	
//	}
	
}
