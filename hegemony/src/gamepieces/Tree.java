package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Tree extends GamePiece {
	
	private int value = 1;
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void act() {
		ResourceManager.FOREST.updateFrame();
		ResourceManager.FOREST.playSound();
		
	}

	@Override
	public BufferedImage draw() {
		return ResourceManager.FOREST.getSprite();
	}
}
