package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Tree extends GamePiece {
	
	private int value = 1;
	private int spriteNum;
	
	public Tree () {
		super (ResourceManager.FOREST.getMediaController());		
		spriteNum = (int) (Math.random() * (mc.getNumSprites() - 1) );
						
	}
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void act() {
		//mc.updateFrame();
		mc.playSound();
		
	}

	@Override
	public BufferedImage draw() {
		return mc.getSprite(spriteNum);
	}
}
