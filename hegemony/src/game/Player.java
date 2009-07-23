package game;

import java.awt.Color;

import cards.Card;

public class Player {

	private Card[] cards = new Card[3];
	private int score;
	private Color color;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
