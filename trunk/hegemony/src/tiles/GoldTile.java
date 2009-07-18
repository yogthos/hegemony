package tiles;

import gamepieces.Mine;

public class GoldTile extends Tile {

	private static final int value = 0;
	
	public GoldTile(int x, int y) {
		super(x,y);
		setMine(new Mine(Mine.Types.GOLD));
	}
	
	@Override
	public Type getType() {
		return Type.GOLD;
	}

	@Override
	public int getValue() {
		return value;
	}

}
