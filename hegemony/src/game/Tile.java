package game;

import java.awt.Image;

public class Tile {

	protected static String[] sprites = {"wood.png","grass.png","stone.png"};
	
	static {
		for (String sprite : sprites) {
			ResourceLoader.getInstance().getSprite(sprite);
		}
	}
	
	private String sprite = null;
	
	public enum Type {
		FOREST,
		GRASS,
		STONE,
		VILLAGE,
		GOLD,
		SILVER,
		PLATINUM		
	}
	
	public Tile(Type type) {
		if (type ==Type.FOREST) 
			sprite = sprites[0];
		else if(type == Type.GRASS)
			sprite = sprites[1];
		else if (type == Type.STONE)
			sprite = sprites[2];
	}
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprite);
	}
	
}
