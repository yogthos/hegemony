package tiles;

import gamepieces.Tree;

public class WoodTile extends Tile {

	private static final int value = 3;
	
	public WoodTile() {
		items.add(new Tree());
	}
	
	@Override
	public Type getType() {
		return Type.FOREST;
	}
	
	@Override
	public int getValue() {
		return value;
	}
}
