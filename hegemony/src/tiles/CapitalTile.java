package tiles;

import gamepieces.Capital;

public class CapitalTile extends Tile {

	private static final int value = 5;

	public CapitalTile(int x, int y) {
		super(x,y);
		items.add(new Capital());
	}
	
	@Override
	public Type getType() {
		return Type.CAPITAL;
	}
	
	@Override
	public int getValue() {
		return value;
	}


}
