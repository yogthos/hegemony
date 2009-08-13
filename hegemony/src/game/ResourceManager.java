package game;

import java.awt.image.BufferedImage;
	 
public enum ResourceManager {
	
	CAPITAL("capital.png", 1, 35, null),
	CASTLE("castle.png", 1, 100, null),
	FOREST("forest.png", 4, 0, null),
	GRASS("grass.png", 1, 35, null),			
	KNIGHT("knight.png", 1, 35, null),
	COPPER_MINE("mine.png", 1, 35, null),
	DIAMOND_MINE("mine.png", 1, 35, null),
	GOLD_MINE("mine.png", 1, 35, null),
	SILVER_MINE("mine.png", 1, 35, null),
	TOWER("tower.png", 1, 35, null),
	VILLAGE("village.png", 1, 35, null),
	V_WALL("wallv.png", 1, 35, "place_wall.wav"),
	H_WALL("wallh.png", 1, 35, "place_wall.wav");
	
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
			if (frameSpeed < 1)
				return;
			
			time++;
			if (time % frameSpeed == 0) {
				time = 0;
				frame = (frame + 1) % animationLength;
			}
		}
		
		public int getNumSprites() {
			return animationLength;
		}
		
		
		public BufferedImage getSprite(int frame) {
			return ResourceLoader.INSTANCE.getAnimationFrame(animation, frame);
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
