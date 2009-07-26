package gamepieces;

public class Tree extends GamePiece {
	
	private int value = 1;
	
	public Tree () {
		sprites = new String[]{"snow/crystal.png"};
		
		frame = 0;
		frameSpeed = 35;
	}
	
	public int getValue() {
		return value;
	}
}
