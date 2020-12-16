package model;


import javafx.scene.input.KeyCode;

public class Player {
	
	private String name;
	final int id;
	private Deck deck;
	private KeyCode keyCode;
	private boolean hasLost;
	

	public Player(int i, String name) {
		if (!(checkInput(i, name)))
			throw new GameException("Player name or id is invalid.");
		id = i;
		this.name = name;

		hasLost = false;
		setKeyCode();

	}
	
	private Boolean checkInput(int id, String name) {
		return (id >= 1 && id <= 4 && !isEmptyName(name) && name.length()<= 14);
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
