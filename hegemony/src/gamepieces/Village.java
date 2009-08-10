package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Village extends GamePiece {

	private int value = 1;

	public Village() {
		super(ResourceManager.VILLAGE.getMediaController());
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
