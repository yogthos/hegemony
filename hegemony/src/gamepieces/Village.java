package gamepieces;

import game.ResourceManager;

public class Village extends GamePiece {

	private int value = 1;
	
	public Village() {
		super();
		sprites = ResourceManager.VILLAGE.getSprites();
		actionSound = ResourceManager.VILLAGE.getSound();
		frame = 0;
		frameSpeed = 35;
	}
	
	public int getValue() {				
		return value;
	}
}
