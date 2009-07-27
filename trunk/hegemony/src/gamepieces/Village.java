package gamepieces;

public class Village extends GamePiece {

	private int value = 1;
	
	public Village() {
		super();
		sprites = new String[]{"snow/beacon0.png","snow/beacon1.png","snow/beacon2.png","snow/beacon1.png"};
		frame = 0;
		frameSpeed = 35;
	}
	
	public int getValue() {				
		return value;
	}
}
