package gamepieces;

public class Village extends GamePiece {

	private int value = 1;
	
	public int getValue() {
		
		frame = 0;
		frameSpeed = 35;
		return value;
	}
}
