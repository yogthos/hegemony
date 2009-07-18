package gamepieces;

import game.ResourceLoader;

import java.awt.Image;

public class Knight extends GamePiece {

	private static final String[] sprites = {"knight.png"};
	
	private int value = 1;
	
	public int getValue() {
		return value;
	}
	
	@Override
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
}
