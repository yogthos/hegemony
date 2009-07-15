package tiles;

import gamepieces.Mine;

public class SilverTile extends Tile {

	private static final int value = 0;
	
	public SilverTile(int x, int y) {
		super(x,y);
		items.add(new Mine(Mine.Types.SILVER));
	}
	
	@Override
	public Type getType() {
		return Type.SILVER;
	}

	@Override
	public int getValue() {
		return value;
	}

}
