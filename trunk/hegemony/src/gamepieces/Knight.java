package gamepieces;

import game.Player;
import game.ResourceManager;

public class Knight extends GamePiece {


	
	private int value = 0;
	private Player player = null;
	
	public Knight(Player player) {
		super();
		sprites = ResourceManager.KNIGHT.getSprites();
		actionSound = ResourceManager.KNIGHT.getSound();
		
		frame = 0;
		frameSpeed = 60;
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPLayer() {
		return player;
	}
	
}
