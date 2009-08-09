package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Village extends GamePiece {

	private int value = 1;

	@Override
	public int getValue() {				
		return value;
	}

	@Override
	public void act() {
		ResourceManager.VILLAGE.updateFrame();
		ResourceManager.VILLAGE.playSound();
		
	}

	@Override
	public BufferedImage draw() {		
		return ResourceManager.VILLAGE.getSprite();
	}
}
