package tiles;

import gamepieces.Village;

public class VillageTile extends Tile {

	private static int value = 3;
	
	public VillageTile(int x, int y) {
		super(x,y);
		setVillage(new Village());
	}
	
	@Override
	public Type getType() {
		return Type.VILLAGE;
	}

	@Override
	public int getValue() {
		return value;
	}


}
