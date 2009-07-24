package gamepieces;

import java.awt.Image;

import game.ResourceLoader;

public class OverlayPiece {

	private int x;
	private int y;
	private GamePiece piece;
	private boolean active = false;
	
	
	public OverlayPiece() {
		
	}
	
	public OverlayPiece(int x, int y, GamePiece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	public int getX() {
		return x;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public GamePiece getPiece() {
		return piece;
	}

	public void setPiece(GamePiece piece) {
		this.piece = piece;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Image draw() {
		return ResourceLoader.getImageWithOpacity(piece.draw(), 0.5f);
	}
}
