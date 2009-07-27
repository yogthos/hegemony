package tiles;

import game.ResourceLoader;

import java.awt.image.BufferedImage;

public class SnowTile extends Tile {

	private static String[] potentialSprites = {"snow/snow0.png","snow/snow1.png",
												"snow/snow2.png","snow/snow3.png",
												"snow/snow4.png","snow/snow5.png",
												"snow/snow6.png","snow/snow7.png",
												"snow/snow8.png","snow/snow9.png"};

	private String sprite = null;

	
	public SnowTile(int x, int y) {		
		super(x, y);
		int tileNumber = (int) (Math.random() * ((potentialSprites.length -1) - 0 + 1) ) + 0;
		
		if (4 == tileNumber || 5 == tileNumber || 6  == tileNumber || 8 == tileNumber|| 9 == tileNumber) {
			if (Math.random() > 0.15)
				tileNumber = 0;
		}
		
		sprite = potentialSprites[tileNumber];
		
	}

	
	@Override
	public Type getType() {
		return Type.GRASS;
	}

	
	public BufferedImage draw() {
		return ResourceLoader.INSTANCE.getSprite(sprite);
		
	}
	
}
