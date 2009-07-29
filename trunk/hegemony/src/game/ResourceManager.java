package game;
	 
public enum ResourceManager {
	CAPITAL(new String[]{"snow/temple0.png","snow/temple1.png","snow/temple2.png","snow/temple3.png","snow/temple2.png","snow/temple1.png"}, null),	
	CASTLE(new String[]{"snow/base0.png","snow/base1.png","snow/base2.png","snow/base3.png", "snow/base2.png","snow/base1.png"}, null),
	FOREST(new String[]{"tree.png"}, null),
	GRASS(new String[]{"grass.png"}, null),			
	KNIGHT(new String[]{"snow/habitat0.png","snow/habitat1.png","snow/habitat2.png","snow/habitat3.png","snow/habitat2.png","snow/habitat1.png"}, null),
	COPPER_MINE(new String[]{"snow/mine_green.png"}, null),
	DIAMOND_MINE(new String[]{"snow/mine.png"}, null),
	GOLD_MINE(new String[]{"snow/mine_yellow.png"}, null),
	SILVER_MINE(new String[]{"snow/mine_orange.png"}, null),
	TOWER(new String[]{"snow/tower0.png"}, null),
	VILLAGE(new String[]{"snow/beacon0.png","snow/beacon1.png","snow/beacon2.png","snow/beacon1.png"}, null),
	WALL(new String[]{"snow/wall-v.png","snow/wall-h.png"}, "place_wall.wav");
	
	
	private String[] sprites = null;
	private String sound = null;
	
	private ResourceManager(String[] sprites, String sound) {
		this.sprites = sprites;
		this.sound = sound;
	}
		
	public String[] getSprites() {
		return sprites;
	}
	
	public String getSound() {
		return sound;
	}
	
	public static void initialize() {
		for (ResourceManager value : ResourceManager.values()) {
			for (String sprite : value.sprites) {
				ResourceLoader.INSTANCE.getSprite(sprite);
			}
			if (null != value.sound)
				ResourceLoader.INSTANCE.getSound(value.sound);
		}
	}
}
