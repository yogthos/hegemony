package tiles;

import gamepieces.GamePiece;

import java.util.ArrayList;
import java.util.List;

public class GrassTile extends Tile {

	private List<GamePiece> pieces = new ArrayList<GamePiece>();
	
	@Override
	public List<GamePiece> getItems() {
		return pieces;
	}

	@Override
	public Type getType() {
		return Type.GRASS;
	}

}
