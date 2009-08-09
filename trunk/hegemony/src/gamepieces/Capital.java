package gamepieces;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public class Capital extends GamePiece {

	private int value = 1;

	public int getValue() {
		return value;
	}

	@Override
	public void act() {
		ResourceManager.CAPITAL.updateFrame();
		ResourceManager.CAPITAL.playSound();
	}

	@Override
	public BufferedImage draw() {
		return ResourceManager.CAPITAL.getSprite();
	}
}
