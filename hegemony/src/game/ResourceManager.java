package game;

import java.awt.image.BufferedImage;
	 
public enum ResourceManager {
	
	CAPITAL("snow/temple0.png", 1, 35, null),
	CASTLE("snow/base.png", 4, 100, null),
	FOREST("tree.png", 1, 35, null),
	GRASS("grass.png", 1, 35, null),			
	KNIGHT("snow/habitat.png", 4, 35, null),
	COPPER_MINE("snow/mine_green.png", 1, 35, null),
	DIAMOND_MINE("snow/mine.png", 1, 35, null),
	GOLD_MINE("snow/mine_yellow.png", 1, 35, null),
	SILVER_MINE("snow/mine_orange.png", 1, 35, null),
	TOWER("snow/tower.png", 1, 35, null),
	VILLAGE("snow/beacon0.png", 1, 35, null),
	V_WALL("snow/wall-v.png", 1, 35, "place_wall.wav"),
	H_WALL("snow/wall-h.png", 1, 35, "place_wall.wav");
	
	private String sound = null;
	private String animation = null;
	int animationLength;	
	int frameSpeed;
		
	private ResourceManager(String animation, int length, int frameSpeed, String sound) {
		
		this.animation = animation;
		this.animationLength = length;
		this.sound = sound;		
		this.frameSpeed = frameSpeed;
		
		//preload the resources
		ResourceLoader.INSTANCE.createAnimation(animation, length);	
		if(null != sound)
			ResourceLoader.INSTANCE.getSound(sound);					
	}

	public MediaController getMediaController() {
		return new MediaController();
	}
	
	public class MediaController {
		int time;
		int frame;
		
		public MediaController() {
			this.time = 0;
			this.frame = 0;			
		}
		
		public void updateFrame() {
			time++;
			if (time % frameSpeed == 0) {
				time = 0;
				frame = (frame + 1) % animationLength;
			}
		}
		
		public BufferedImage getSprite() {
			return ResourceLoader.INSTANCE.getAnimationFrame(animation, frame);
		}
		
		public BufferedImage getSprite(float opacity) {
			return ResourceLoader.getImageWithOpacity(getSprite(), opacity);
		}
		
		public void playSound() {
			if (null == sound)
				return;
			
			ResourceLoader.INSTANCE.getSound(sound).play();
		}
	}
}
