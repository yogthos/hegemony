package tiles;
import java.util.Random;

public class GameBoard {

	private enum Rotate {		
		NONE { Tile rotate(Tile[][] orig_board, int x, int y) { return orig_board[x][y]; }},
		CLOCKWISE { Tile rotate(Tile[][] orig_board, int x, int y) { return orig_board[Math.abs(y-(orig_board.length-1))][x]; }},
		COUNTER { Tile rotate(Tile[][] orig_board, int x, int y) { return orig_board[y][Math.abs(x-(orig_board.length-1))]; }},
		ONEEIGHTY { Tile rotate(Tile[][] orig_board, int x, int y) { return orig_board[Math.abs(x-(orig_board.length-1))][Math.abs(y-(orig_board.length-1))]; }};

		abstract Tile rotate(Tile[][] orig_board, int x, int y);
	}

	private Rotate rotation;
	private Tile[][] board;

	public GameBoard() {
		Random r = new Random();
		switch (r.nextInt(4)) {
		case 0:
			rotation = Rotate.NONE;
			break;
		case 1:
			rotation = Rotate.CLOCKWISE;
			break;
		case 2:
			rotation = Rotate.COUNTER;
			break;
		case 3:
			rotation = Rotate.ONEEIGHTY;
			break;
		}
	}

	/**
	 * Returns the tile from the board, rotated as needed, based on the enum type.
	 * We're doing in-place rotation like this to cut down on processing time (O(1) vs. O(n-something))
	 * 
	 * Right now we assume a square board.
	 * 
	 * @param x - The x coord relative to the rotated board piece.
	 * @param y - The y coord relative to the rotated board piece.
	 * @return The tile that would be in position x, y on the rotated board.
	 */
	public Tile getTile(int x, int y) {
		return rotation.rotate(board, x, y);
	}
	
	/**
	 * Return the entire board for drawing, whatever.
	 * Need to implement.
	 * 
	 * @return
	 */
	public Tile[][] getBoard() {
		return null;
	}

}