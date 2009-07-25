package gamepieces;

public class Capital extends GamePiece {

	private int value = 1;
	
	public Capital () {
		sprites = new String[]{"snow/temple0","snow/temple1","snow/temple2","snow/temple3","snow/temple2","snow/temple1",};
	}

	public int getValue() {
		return value;
	}
}
