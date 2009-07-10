package tiles;

import game.ResourceLoader;
import gamepieces.GamePiece;

import java.awt.Image;
import java.util.List;

public abstract class Tile {

	protected static String[] sprites = {"grass.png"};
	
	static {
		for (String sprite : sprites) {
			ResourceLoader.getInstance().getSprite(sprite);
		}
	}
	
	public enum Type {
		FOREST,
		GRASS,
		STONE,
		VILLAGE,
		GOLD,
		SILVER,
		PLATINUM		
	}

	public abstract Type getType();
	
	public abstract List<GamePiece> getItems();
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
	
}
