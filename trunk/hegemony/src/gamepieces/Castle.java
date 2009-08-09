package gamepieces;

import java.awt.image.BufferedImage;

import game.Player;
import game.ResourceManager;

public class Castle extends GamePiece {

	private int value = 0;
	private Player player;
	
	public Castle(Player player) {		
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void act() {		
		ResourceManager.CASTLE.updateFrame();
		ResourceManager.CASTLE.playSound();
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public BufferedImage draw() {
		return ResourceManager.CASTLE.getSprite();
	}
}
