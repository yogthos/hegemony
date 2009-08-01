package cards;

import game.BoardController;
import game.ControlsPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck extends Stack<Card>{

	private static final long serialVersionUID = 1L;
	
	List<Card> cards = new ArrayList<Card>();
	
	public Deck(ControlsPanel controls) {
		generateDeck(controls);
	}
	
	private void generateDeck(ControlsPanel controls) {
		for (BoardController.MODE mode : BoardController.MODE.values()) {
			cards.add(new Card(controls, mode));
		}
		
	}
}
