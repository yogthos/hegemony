package tiles;

public class GrassTile extends Tile {

	private static final int value = 0;

	@Override
	public Type getType() {
		return Type.GRASS;
	}

	@Override
	public int getValue() {
		return value;
	}
}
