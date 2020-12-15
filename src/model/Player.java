package model;


import javafx.scene.input.KeyCode;



public class Player {
	
	private String name;
	final int id;
	private Deck deck;
	private KeyCode keyCode;
	private boolean hasLost;
	

	public Player(int i, String name) {
		if (i < 1 || i > 4)
			throw new GameException("Invalid playd id");
		if (isEmptyName(name) || name.length() > 20)
			throw new GameException("Name must contain 1-20 characters.");
		id = i;
		this.name = name;

		hasLost = false;
		setKeyCode();

	}
	public boolean getHasLost() {
		return hasLost;
	}
	
	public void hasNowLost() {
		hasLost = true;
	}

	private boolean isEmptyName(String s) {
		return (s.trim().length() == 0);
	}
	public String getName() {
		return name;
	}
	
	public void setDeck(Deck d) {
		deck = d;
	}
	
	public int getId() {
		return id;
	}
	


	public Deck getDeck() {
		return deck;
	}
	
	private void setKeyCode() {
		if (id == 1)
			keyCode = KeyCode.S;
		if (id == 2)
			keyCode = KeyCode.P;
		if (id == 3)
			keyCode = KeyCode.A;
		if (id == 4)
			keyCode = KeyCode.N;
	}
	
	public KeyCode getKeyCode() {
		return keyCode;
}

	

}
