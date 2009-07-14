package tiles;

import game.ResourceLoader;
import gamepieces.GamePiece;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Tile {

	protected static String[] sprites = {"grass.png"};
	
	static {
		for (String sprite : sprites) {
			ResourceLoader.getInstance().getSprite(sprite);
		}
	}
	
	protected List<GamePiece> items = new ArrayList<GamePiece>();
	
	public enum Type {
		FOREST,
		GRASS,
		VILLAGE,
		GOLD,
		SILVER,
		COPPER,
		DIAMOND,
		CAPITAL	
	}

	public abstract Type getType();
	public abstract int getValue();
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
	
	public List<GamePiece> getItems() {
		return items;
	}	
	
	public void placeItem(GamePiece piece) {
		items.add(piece);
	}
}
