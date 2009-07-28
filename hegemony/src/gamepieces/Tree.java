package gamepieces;

import game.ResourceManager;

public class Tree extends GamePiece {
	
	private int value = 1;
	
	public Tree () {
		super();
		sprites = ResourceManager.FOREST.getSprites();
		actionSound = ResourceManager.FOREST.getSound();
		
		frame = 0;
		frameSpeed = 35;
	}
	
	public int getValue() {
		return value;
	}
}
