package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Capital extends GamePiece {

	public Capital() {
		super(ResourceManager.CAPITAL.getMediaController());
	}

	private int value = 1;

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
