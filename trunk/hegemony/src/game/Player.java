package game;

import gamepieces.Castle;
import gamepieces.Knight;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cards.Card;

public class Player {

	private List<Card> cards = new ArrayList<Card>();
	private List<Castle> castles = new ArrayList<Castle>();
	private List<Knight> knights = new ArrayList<Knight>();
	private int score = 0;
	private Color color;
	
	public Player() {
		for (int i = 0; i < 3; i++) {
			castles.add(new Castle(this));
		}
		for (int i = 0; i < 15; i++) {
			knights.add(new Knight(this));
		}
	}
	
	public void drawCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getScore() {
		return score;
	}
	
	public Knight placeKnight() {
		if (knights.size() < 1)
			return null;
		return knights.remove(0);
	}
	
	public Castle placeCastle() {
		if (castles.size() < 1)
			return null;
		return castles.remove(0);
	}
	
	public int getKnightsRemaining() {
		return knights.size();
	}
	
	public int getCastlesRemainig() {
		return castles.size();		
	}
}
