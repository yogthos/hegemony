package gamepieces;

import game.ResourceLoader;

import java.awt.Image;

public abstract class GamePiece {
	protected int time;
	protected int frame;
	protected int frameSpeed;
	protected int actorSpeed;
	protected String[] sprites = null; 
			
	public abstract int getValue();
	
	public void act() {
		updateFrame();
	}
	
	protected void updateFrame() {
		time++;
		if (time % frameSpeed == 0) {
			time = 0;
			frame = (frame + 1) % sprites.length;
		}
	}
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[frame]);
	}
}
