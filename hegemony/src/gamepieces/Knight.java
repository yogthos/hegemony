package gamepieces;

import game.Player;

public class Knight extends GamePiece {


	
	private int value = 0;
	private Player player = null;
	
	public Knight(Player player) {
		sprites = new String[]{"knight0.png","knight1.png"};
		
		frame = 0;
		frameSpeed = 60;
		this.player = player;
	}
	
	public int getValue() {
		return value;
	}
	
	public Player getPLayer() {
		return player;
	}		
}
