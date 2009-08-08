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
	private int resources = 5;
	private Color color;
	private Card lastSold = null;
	private Card currentCard = null;
	
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
	
	public void commitPlay() {
		if (null == currentCard)
			return;		
		resources -= currentCard.getCost();
		currentCard = null;		
	}
	
	public boolean playCard(Card card) {
		if (card.getCost() > resources)
			return false;
		currentCard = card;
		cards.remove(card);
		return true;
	}
	
	public void undoPlayCard() {
		if (null != currentCard)
			cards.add(currentCard);
		
		currentCard = null;
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
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
	
	public void updateScore(int value) {
		score += value;
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

	public void addResources(int resources) {
		this.resources += resources;
	}
	
	public void subtractResources(int resources) {
		this.resources -= resources;
	}

	public int getResources() {
		return resources;
	}

	public void setLastSold(Card lastSold) {
		this.lastSold = lastSold;
	}

	public Card getLastSold() {
		return lastSold;
	}
}
