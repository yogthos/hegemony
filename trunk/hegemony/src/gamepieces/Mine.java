package gamepieces;

import java.awt.Image;

public class Mine extends GamePiece {

	private int type;
	private Types mine_type;
	
	public enum Types {
		DIAMOND,
		COPPER,
		GOLD,
		SILVER
	}
	
	
	public Mine(Types mine_type) {
		this.mine_type = mine_type;
	}
	
	public Image draw() {
		return null;
	}
}
