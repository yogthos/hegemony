package cards;

import game.BoardController;
import game.GameController;

import java.util.Stack;

public class Deck {

	private static final long serialVersionUID = 1L;
	
	private Stack<Card> cards = new Stack<Card>();
	
	public Deck(GameController controller) {
		generateDeck(controller);
	}
			
	private void generateDeck(GameController controller) {
		for (int i = 0; i < 100; i++) {
				cards.push(new Card(controller, BoardController.MODE.PLACE_KNIGHT));
				cards.push(new Card(controller, BoardController.MODE.PLACE_WALL));
				cards.push(new Card(controller, BoardController.MODE.EXPAND_AREA));
		}
	}
	
	public Card draw() {
		return cards.pop();
	}
}
