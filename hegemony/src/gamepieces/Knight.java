package gamepieces;

import game.ResourceLoader;

import java.awt.Image;

public class Knight extends GamePiece {

	private static final String[] sprites = {"knight.png"};
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
}
