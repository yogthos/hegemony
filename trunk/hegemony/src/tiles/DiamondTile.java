package tiles;

import gamepieces.Mine;

public class DiamondTile extends Tile implements MineTile {

	private static final int value = 0;
	
	public DiamondTile(int x, int y) {
		super(x,y);
		setMine(new Mine(Mine.Types.DIAMOND));
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
