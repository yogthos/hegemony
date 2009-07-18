package gamepieces;

import game.Player;
import game.ResourceLoader;

import java.awt.Image;

public class Knight extends GamePiece {

	private static final String[] sprites = {"knight.png"};
	
	private int value = 0;
	private Player player = null;
	
	public Knight(Player player) {
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPLayer() {
		return player;
	}
	
	@Override
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
}
