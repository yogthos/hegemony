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
		super(Types.DIAMOND == mineType ?
				ResourceManager.DIAMOND_MINE.getMediaController() :
				Types.COPPER == mineType ?
					ResourceManager.COPPER_MINE.getMediaController() :
					Types.GOLD == mineType ?
						ResourceManager.GOLD_MINE.getMediaController() :
						Types.SILVER == mineType ?
							ResourceManager.SILVER_MINE.getMediaController():
							null);
		
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
		mc.updateFrame();
		mc.playSound();				
	}

	@Override
	public BufferedImage draw() {
		return mc.getSprite();
	}
}
