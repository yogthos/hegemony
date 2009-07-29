package gamepieces;

import game.Player;
import game.ResourceManager;

public class Castle extends GamePiece {

	private int value = 0;
	private Player player;
	
	public Castle(Player player) {
		super();
		sprites = ResourceManager.CASTLE.getSprites();
		actionSound = ResourceManager.CASTLE.getSound();
		
		frame = 0;
		frameSpeed = 35;
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPlayer() {
		return player;
	}
}
