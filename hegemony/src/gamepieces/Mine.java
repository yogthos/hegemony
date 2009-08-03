package gamepieces;

import game.ResourceManager;

public class Mine extends GamePiece {

	public static int MONOPOLY_VALUE = 5;
	private Types mineType;
	private int value = 1;
	
	public enum Types {
		DIAMOND,
		COPPER,
		GOLD,
		SILVER
	}
	
	
	public Mine(Types mineType) {
		super();
		if (mineType.equals(Types.DIAMOND)) {
			sprites = ResourceManager.DIAMOND_MINE.getSprites();
			actionSound = ResourceManager.DIAMOND_MINE.getSound();
		}
		else if (mineType.equals(Types.COPPER)) {
			sprites = ResourceManager.COPPER_MINE.getSprites();
			actionSound = ResourceManager.COPPER_MINE.getSound();
		}
		else if (mineType.equals(Types.GOLD)) {
			sprites = ResourceManager.GOLD_MINE.getSprites();
			actionSound = ResourceManager.GOLD_MINE.getSound();
		}
		else if (mineType.equals(Types.SILVER)) {
			sprites = ResourceManager.SILVER_MINE.getSprites();
			actionSound = ResourceManager.SILVER_MINE.getSound();
		}
		
		frame = 0;
		frameSpeed = 35;
		this.mineType = mineType;
	}

	public Types getType() {
		return mineType;
	}
	
	public int getValue() {
		return value;
	}
}
