package gamepieces;

import java.awt.image.BufferedImage;

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
		this.mineType = mineType;
	}

	public Types getType() {
		return mineType;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void act() {
		if (mineType.equals(Types.DIAMOND)) {
			ResourceManager.DIAMOND_MINE.updateFrame();
			ResourceManager.DIAMOND_MINE.playSound();
		}
		else if (mineType.equals(Types.COPPER)) {
			ResourceManager.COPPER_MINE.updateFrame();
			ResourceManager.COPPER_MINE.playSound();
		}
		else if (mineType.equals(Types.GOLD)) {
			ResourceManager.GOLD_MINE.updateFrame();
			ResourceManager.GOLD_MINE.playSound();
		}
		else if (mineType.equals(Types.SILVER)) {
			ResourceManager.SILVER_MINE.updateFrame();
			ResourceManager.SILVER_MINE.playSound();
		}		
	}

	@Override
	public BufferedImage draw() {
		if (mineType.equals(Types.DIAMOND)) 
			return ResourceManager.DIAMOND_MINE.getSprite();
		
		else if (mineType.equals(Types.COPPER)) 
			return ResourceManager.COPPER_MINE.getSprite();

		else if (mineType.equals(Types.GOLD)) 
			return ResourceManager.GOLD_MINE.getSprite();

		else if (mineType.equals(Types.SILVER)) 
			return ResourceManager.SILVER_MINE.getSprite();
		
		return null;
	}
}
