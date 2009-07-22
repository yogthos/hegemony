package game;

import java.awt.Color;

import cards.Card;

public class Player {

	private Card[] cards = new Card[3];
	private int score;
	
	public Color getColor() {
		return Color.blue;
	}
}
