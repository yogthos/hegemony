package tiles;

import gamepieces.Mine;

public class CopperTile extends Tile {

	private static final int value = 0;
	
	public CopperTile() {
		items.add(new Mine(Mine.Types.COPPER));
	}
	
	@Override
	public Type getType() {
		return Type.COPPER;
	}

	@Override
	public int getValue() {
		return value;
	}

}