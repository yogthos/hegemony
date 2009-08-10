package gamepieces;

import game.ResourceManager;

import java.awt.image.BufferedImage;

public abstract class GamePiece {
	protected int actorSpeed;
	protected ResourceManager.MediaController mc = null;
	
	public GamePiece(ResourceManager.MediaController mc) {
		this.mc = mc;
	}
	
	public abstract int getValue();
	
	public abstract void act(); 
			
	public abstract BufferedImage draw(); 
}
