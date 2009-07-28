package game;

import java.awt.image.BufferedImage;

	 
public enum ResourceManager {
	CAPITAL(new String[]{"snow/temple0","snow/temple1","snow/temple2","snow/temple3","snow/temple2","snow/temple1"}),	
	CASTLE(new String[]{""}),
	FOREST(new String[]{"wood.png"}),
	GRASS(new String[]{"grass.png"}),			
	KNIGHT(new String[]{""}),
	COPPER_MINE(new String[]{""}),
	DIAMOND_MINE(new String[]{""}),
	GOLD_MINE(new String[]{""}),
	SILVER_MINE(new String[]{""}),
	TOWER(new String[]{""}),
	VILLAGE(new String[]{""}),
	WALL(new String[]{""});
	
	
	private String[] sprites = null;
	private ResourceManager(String[] sprites) {
		this.sprites = sprites;
	}
		
	public BufferedImage getSprite(int frame) {
		return ResourceLoader.INSTANCE.getSprite(sprites[frame]);
	}
	
	public int numSprites() {
		return sprites.length;
	}
	
}
