package tiles;

import game.ResourceLoader;

import java.awt.Image;

public class SnowTile extends Tile {
	
	private static String[] potentialSprites = {"snow/snow0.png","snow/snow1.png",
		"snow/snow2.png","snow/snow3.png",
		"snow/snow4.png","snow/snow5.png",
		"snow/snow6.png","snow/snow7.png",
		"snow/snow8.png"};

	private String sprite = null;

	
	public SnowTile(int x, int y) {		
		super(x, y);
		int tileNumber = (int) (Math.random() * ((potentialSprites.length -1) - 0 + 1) ) + 0;
		
		if (6 == tileNumber || 7 == tileNumber) {
			if (Math.random() > 0.2)
				tileNumber = 0;
		}
		
		sprite = potentialSprites[tileNumber];
		
	}

	
	@Override
	public Type getType() {
		return Type.GRASS;
	}
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprite);
	}

}
