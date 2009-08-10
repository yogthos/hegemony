package gamepieces;

import java.awt.image.BufferedImage;

import game.Player;
import game.ResourceManager;

public class Knight extends GamePiece {

	private int value = 0;
	private Player player = null;
	
	public Knight(Player player) {
		super(ResourceManager.KNIGHT.getMediaController());
		this.player = player;
	}

	@Override
	public int getValue() {
		return value;
	}
	
	public Player getPLayer() {
		return player;
	}

	@Override
	public void act() {
		mc.updateFrame();
		mc.playSound();
		
	}

	@Override
	public BufferedImage draw() {
		return mc.getSprite();
	}
	
}
