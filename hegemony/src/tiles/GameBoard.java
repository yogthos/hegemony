package tiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

	/**
	 *  Variables defining the minimum and maximum allowed of each tile type
	 *  per board section.  TOWNS and MINES are ignored for the center board
	 *  as it has a fixed number of each (0 and 4). 
	 */
	private static final int MIN_FIELDS = 5;
	private static final int MAX_FIELDS = 9;
	private static final int MIN_MINES = 1;
	private static final int MAX_MINES = 2;
	private static final int MIN_TOWNS = 1;
	private static final int MAX_TOWNS = 3;
	private static final int MIN_WOODS = 4;
	private static final int MAX_WOODS = 7;

	/**
	 * The length of one side of the tiles by default.
	 */
	public static final int DEFAULT_TILES = 4;

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
	 * Generate a random NxN board from a given seed.
	 * 
	 * @param seed The integer to seed the pseudo-random number generator with.
	 * @return The resultant board as an NxN array.
	 */
	public static Tile[][] generateBoard(int x, int y, int seed) {
		if (x%2 != 0 || y%2 != 0) {
			throw new IllegalArgumentException("Number of sub-boards must be odd.");
		}
		Random r = new Random(seed);

		return generateBoard(x, y, r);
	}

	/**
	 * Generate a random NxN board.
	 * 
	 * @return The resultant board as an NxN array.
	 */
	public static Tile[][] generateBoard(int x, int y) {
		if (x%2 != 0 || y%2 != 0) {
			throw new IllegalArgumentException("Number of sub-boards must be odd.");
		}
		Random r = new Random();
		return generateBoard(x, y, r);
	}

	/**
	 * The actual method that does the board creation
	 * 
	 * @param r - Random object used to select List.
	 * @return
	 */
	public static Tile[][] generateBoard(int x, int y, Random r) {
		List<Tile> mines = new ArrayList<Tile>();
		List<Tile> woods = new ArrayList<Tile>();
		List<Tile> towns = new ArrayList<Tile>();
		List<Tile> fields = new ArrayList<Tile>();

		Tile[][] board = new Tile[x][y];
		Tile[][] subboard;
		
		for (int x_multiplier = 0; x_multiplier < x; x_multiplier++) {
			for (int y_multiplier = 0; y_multiplier < y; y_multiplier++) {
				if (x_multiplier != (x/2 + 1) && y_multiplier != (x/2 + 1)) {
					subboard = generateSubBoard(DEFAULT_TILES, r, woods, fields, mines, towns);
				} else {
					subboard = generateCenterBoard(DEFAULT_TILES, r, woods, fields);
				}
				for (int array = 0; array < x; array++) {
					//TODO Fix this array copy

					//System.arraycopy(subboard[array], 0, board[y_multiplier * array], , subboard[array].length);
				}
			}
		}



		return null;
	}

	private static Tile[][] generateCenterBoard(int defaultTiles, Random r,
			List<Tile> woods, List<Tile> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Tile[][] generateSubBoard(int tiles, Random r, List<Tile> woods, List<Tile> fields, List<Tile> mines, List<Tile> towns) {
		Tile[][] board = new Tile[tiles][tiles];
		int num_mines = 0;
		int num_fields = 0;
		int num_towns = 0;
		int num_woods = 0;
		int min_default_remaining = MIN_MINES + MIN_WOODS + MIN_TOWNS + MIN_FIELDS;
		int random = 0;
		List<List<Tile>> limited_list = new ArrayList<List<Tile>>();
		limited_list.add(mines);
		limited_list.add(fields);
		limited_list.add(woods);
		limited_list.add(towns);

		for (int x  = 0; x < tiles; x++) {
			for (int y = 0; y < tiles; y++) {
				/**
				 * If we have as many tiles remaining to place as default tiles
				 * needed to hit the minimums, then we start removing from our
				 * possible choices any tile that has already reached its minimum.
				 */
				if ((tiles^2 - x+y) == min_default_remaining) {
					if (num_mines >= MIN_MINES) {
						limited_list.remove(mines);
					}
					if (num_fields >= MIN_FIELDS) {
						limited_list.remove(fields);
					}
					if (num_towns >= MIN_TOWNS) {
						limited_list.remove(towns);
					}
					if (num_woods >= MIN_WOODS) {
						limited_list.remove(woods);
					}
				}
				random = r.nextInt(limited_list.size());
				board[x][y] = limited_list.get(random).remove(0);
				if (board[x][y] instanceof MineTile) {
					num_mines++;
					if (num_mines == MAX_MINES) limited_list.remove(random);
					if (num_mines <= MIN_MINES) min_default_remaining--;
				} else if (board[x][y] instanceof GrassTile) {
					num_fields++;
					if (num_fields == MAX_FIELDS) limited_list.remove(random);
					if (num_fields <= MIN_FIELDS) min_default_remaining--;
				} else if (board[x][y] instanceof VillageTile) {
					num_towns++;
					if (num_towns == MAX_TOWNS) limited_list.remove(random);
					if (num_towns <= MIN_TOWNS)	min_default_remaining--;
				} else if (board[x][y] instanceof WoodTile) {
					num_woods++;
					if (num_woods == MAX_WOODS)	limited_list.remove(random);
					if (num_woods <= MIN_WOODS) min_default_remaining--;
				}
			}
		}

		return board;
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