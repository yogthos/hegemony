package gamepieces;

import game.ResourceManager;

public class Capital extends GamePiece {

	private int value = 1;
	
	public Capital () {
		sprites = ResourceManager.CAPITAL.getSprites();
		actionSound = ResourceManager.CAPITAL.getSound();
	}

	public int getValue() {
		return value;
	}
}
