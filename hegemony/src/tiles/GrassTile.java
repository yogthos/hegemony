package tiles;

public class GrassTile extends Tile {

	private static final int value = 0;

	public GrassTile(int x, int y) {
		super(x,y);
	}
	
	@Override
	public Type getType() {
		return Type.GRASS;
	}

	@Override
	public int getValue() {
		return value;
	}	
}
