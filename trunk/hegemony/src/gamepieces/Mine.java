package gamepieces;

public class Mine extends GamePiece {

	private int type;
	private Types mine_type;
	private int value = 1;
	
	public enum Types {
		DIAMOND,
		COPPER,
		GOLD,
		SILVER
	}
	
	
	public Mine(Types mine_type) {
		sprites = new String[]{"mine.png"};
		
		frame = 0;
		frameSpeed = 35;
		this.mine_type = mine_type;
	}

	
	public int getValue() {
		return value;
	}
}
