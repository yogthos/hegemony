package gamepieces;

import game.Player;
import game.ResourceLoader;

import java.awt.Image;

public class Castle extends GamePiece {

	private static String[] sprites = {"castle.png"};
	private int value = 1;
	private Player player;
	
	public Castle(Player player) {
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
}
