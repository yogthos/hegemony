package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Tree extends GamePiece {
	
	private int value = 1;
	
	public Tree () {
		super (ResourceManager.FOREST.getMediaController());
	}
	@Override
	public int getValue() {
		return value;
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
