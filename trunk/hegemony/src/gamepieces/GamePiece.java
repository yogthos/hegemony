package gamepieces;

import java.awt.image.BufferedImage;

public abstract class GamePiece {
	protected int actorSpeed;

	public abstract int getValue();
	
	public abstract void act(); 
			
	public abstract BufferedImage draw(); 
}
