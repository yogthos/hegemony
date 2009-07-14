package tiles;

import gamepieces.Mine;

public class DiamondTile extends Tile {

	private static final int value = 0;
	
	public DiamondTile() {
		items.add(new Mine(Mine.Types.DIAMOND));
	}
	
	@Override
	public Type getType() {
		return Type.DIAMOND;
	}

	@Override
	public int getValue() {
		return value;
	}

}
