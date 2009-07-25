package gamepieces;

import game.Player;

public class Knight extends GamePiece {


	
	private int value = 0;
	private Player player = null;
	
	public Knight(Player player) {
		//sprites = new String[]{"knight0.png","knight1.png"};
		sprites = new String[]{"snow/habitat0.png","snow/habitat1.png","snow/habitat2.png","snow/habitat3.png"};
		
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
