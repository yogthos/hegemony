package gamepieces;

import java.awt.Image;

import game.ResourceLoader;

public class Tree extends GamePiece {
	private static final String[] sprites = {"tree.png"};
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
}
