package gamepieces;

import game.ResourceManager;

public class Mine extends GamePiece {

	private int type;
	private Types mine_type;
	private int value = 1;
	
	public enum Types {
		DIAMOND,
		COPPER,
		GOLD,
		SILVER
	}
	
	
	public Mine(Types mine_type) {
		super();
		if (mine_type.equals(Types.DIAMOND)) {
			sprites = ResourceManager.DIAMOND_MINE.getSprites();
			actionSound = ResourceManager.DIAMOND_MINE.getSound();
		}
		else if (mine_type.equals(Types.COPPER)) {
			sprites = ResourceManager.COPPER_MINE.getSprites();
			actionSound = ResourceManager.COPPER_MINE.getSound();
		}
		else if (mine_type.equals(Types.GOLD)) {
			sprites = ResourceManager.GOLD_MINE.getSprites();
			actionSound = ResourceManager.GOLD_MINE.getSound();
		}
		else if (mine_type.equals(Types.SILVER)) {
			sprites = ResourceManager.SILVER_MINE.getSprites();
			actionSound = ResourceManager.SILVER_MINE.getSound();
		}
		
		frame = 0;
		frameSpeed = 35;
		this.mine_type = mine_type;
	}

	
	public int getValue() {
		return value;
	}
}
