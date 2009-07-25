package gamepieces;

import game.Player;

public class Castle extends GamePiece {

	private int value = 0;
	private Player player;
	
	public Castle(Player player) {
		
		sprites = new String[]{"snow/base0.png","snow/base1.png","snow/base2.png","snow/base3.png"};
		frame = 0;
		frameSpeed = 35;
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPlayer() {
		return player;
	}
}
