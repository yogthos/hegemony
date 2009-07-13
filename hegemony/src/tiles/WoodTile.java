package tiles;

import java.util.ArrayList;
import java.util.List;

import gamepieces.GamePiece;
import gamepieces.Tree;

public class WoodTile extends Tile {

	
	
	public WoodTile() {
		items.add(new Tree());
	}
	
	@Override
	public Type getType() {
		return Type.FOREST;
	}
			
}
