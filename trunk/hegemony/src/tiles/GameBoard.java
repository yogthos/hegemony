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
	private static final int MAX_MINE_TYPE = 3;

	public static final int DEFAULT_X = 3;
	public static final int DEFAULT_Y = 3;

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
	 * Generate a random NxN board using default values for board size.
	 * 
	 * @return The resultant board as an [x*tiles][y*tiles] array.
	 */
	public static Tile[][] generateBoard() {
		Random r = new Random();

		return generateBoard(DEFAULT_X, DEFAULT_Y, DEFAULT_TILES, r);
	}
	
	public static Tile[][] generateBoard(int seed) {
		Random r = new Random(seed);

		return generateBoard(DEFAULT_X, DEFAULT_Y, DEFAULT_TILES, r);
	}
	
	public static Tile[][] generateBoard(Random r) {
		return generateBoard(DEFAULT_X, DEFAULT_Y, DEFAULT_TILES, r);
	}
	
	/**
	 * Generate a random NxN board from a given seed.
	 * 
	 * @param x The number of sub-boards across.
	 * @param y The number of sub-boards vertical.
	 * @param tiles The number of tiles along one edge of a sub-board.
	 * @param seed The integer to seed into the random generator.
	 * 
	 * @return The resultant board as an [x*tiles][y*tiles] array.
	 */
	public static Tile[][] generateBoard(int x, int y, int tiles, int seed) {
		if (x%2 == 0 || y%2 == 0) {
			throw new IllegalArgumentException("Number of sub-boards must be odd.");
		}
		Random r = new Random(seed);

		return generateBoard(x, y, tiles, r);
	}

	/**
	 * Generate a random NxN board.
	 * @param x The number of sub-boards across.
	 * @param y The number of sub-boards vertical.
	 * @param tiles The number of tiles along one edge of a sub-board.
	 *
	 * @return The resultant board as a [x*tiles][y*tiles] array.
	 */
	public static Tile[][] generateBoard(int x, int y, int tiles) {
		if (x%2 == 0 || y%2 == 0) {
			throw new IllegalArgumentException("Number of sub-boards must be odd.");
		}
		Random r = new Random();
		return generateBoard(x, y, tiles, r);
	}

	/**
	 * Generate a random NxN board.
	 * @param x The number of sub-boards across.
	 * @param y The number of sub-boards vertical.
	 * @param tiles The number of tiles along one edge of a sub-board.
	 * @param r The random number generator to use.
	 *
	 * @return The resultant board as a [x*tiles][y*tiles] array.
	 */
	public static Tile[][] generateBoard(int x, int y, int tiles, Random r) {
		Tile[][] board = new Tile[x*tiles][y*tiles];
		boolean placed = false;
		int gold_placed = 0;
		int silver_placed = 0;
		int copper_placed = 0;
		int diamond_placed = 0;
		
		int towns_placed = 0;
		int mines_placed = 0;
		int woods_placed = 0;
		int specials = 0;
		List<Pos> grass_tiles = new ArrayList<Pos>();
		
		// First pass.  Minimum number of each tile type.
		// Skip the center board in this case.
		for (int sub_x = 0; sub_x < x; sub_x++) {
			for (int sub_y = 0; sub_y < y; sub_y++) {	
				if (!(sub_x == x/2 && sub_y == y/2)) {
					specials = 0;
					mines_placed = 0;
					woods_placed = 0;
					towns_placed = 0;
					
					for (int sub_tile_x = 0; sub_tile_x < tiles; sub_tile_x++) {
						for (int sub_tile_y = 0; sub_tile_y < tiles; sub_tile_y++) {
							// Do we still have special tiles left to place?
							if (specials < MIN_MINES + MIN_WOODS + MIN_TOWNS) {
								// the first equation checks to see if the number of special tiles remaining to be placed
								// is equal to the number of tiles left to check.  If true, we place regardless of whether the Random says to.
								if ((MIN_MINES + MIN_WOODS + MIN_TOWNS - specials) == (tiles^2 - sub_tile_x*tiles + sub_tile_y) || r.nextBoolean()) {
									placed = false;
									while (!placed) {
										switch (r.nextInt(3)) {
										case 0:
											if (woods_placed < MIN_WOODS) {
												board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new WoodTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
												woods_placed++;
												placed = true;
											}
											break;
										case 1:
											if (towns_placed < MIN_TOWNS) {
												board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new VillageTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
												towns_placed++;
												placed = true;
											}
											break;
										case 2:
											if (mines_placed < MIN_MINES) {
												while (!placed) {
													switch (r.nextInt(4)) {
													case 0:
														if (silver_placed < MAX_MINE_TYPE) {
															board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new SilverTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
															silver_placed++;
															mines_placed++;
															placed = true;
														}
														break;
													case 1:
														if (gold_placed < MAX_MINE_TYPE) {
															board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new GoldTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
															gold_placed++;
															mines_placed++;
															placed = true;
														}
														break;
													case 2:
														if (copper_placed < MAX_MINE_TYPE) {
															board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new CopperTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
															copper_placed++;
															mines_placed++;
															placed = true;
														}
														break;
													case 3:
														if (diamond_placed < MAX_MINE_TYPE) {
															board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new DiamondTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
															diamond_placed++;
															mines_placed++;
															placed = true;
														}
														break;
													}
												}
											}
											break;
										}
									}
									specials++;
								} else {
									board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new GrassTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
									grass_tiles.add(new Pos(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y));
								}
							}  else {
								board[sub_tile_x + tiles*sub_x][sub_tile_y + tiles*sub_y] = new GrassTile(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y);
								grass_tiles.add(new Pos(sub_tile_x + tiles*sub_x, sub_tile_y + tiles*sub_y));
							}
						}
					}
				}
			}
		}
		
		// Center board. Only one pass.
		// Center board has a special thing involving its content.
		grass_tiles.clear();
		for (int i = 0; i < tiles; i++) {
			for (int j = 0; j < tiles; j++) {
				grass_tiles.add(new Pos(((x/2)*tiles) + i, ((y/2)*tiles) + j));
//				System.out.println(grass_tiles.get(grass_tiles.size()-1));
			}
		}
		//Place the capital.
		if (tiles%2 != 0) {
			board[((x/2)*tiles) + tiles/2+1][((y/2)*tiles) + tiles/2] = new CapitalTile(((x/2)*tiles) + tiles/2+1, ((y/2)*tiles) + tiles/2+1);
			grass_tiles.remove(new Pos(((x/2)*tiles) + tiles/2+1, ((y/2)*tiles) + tiles/2+1));
		} else {
			int temp_x = r.nextInt(2);
			int temp_y = r.nextInt(2);
			board[(x/2)*tiles + tiles/2 - temp_x][(y/2)*tiles + tiles/2 - temp_y] = new CapitalTile((x/2)*tiles + tiles/2 - temp_x, (y/2)*tiles + tiles/2 - temp_y);
			grass_tiles.remove(new Pos((x/2)*tiles + tiles/2 - temp_x, (y/2)*tiles + tiles/2 - temp_y));
		}
		// Place the mines.
		List<MineTile> corners = new ArrayList<MineTile>();
		corners.add(new GoldTile(-1,-1));
		corners.add(new SilverTile(-1,-1));
		corners.add(new DiamondTile(-1,-1));
		corners.add(new CopperTile(-1,-1));
		//Bottom-right corner
		board[(x/2)*tiles + tiles-1][(y/2)*tiles + tiles-1] = corners.remove(0);
		board[(x/2)*tiles + tiles-1][(y/2)*tiles + tiles-1].setX((x/2)*tiles + tiles-1);
		board[(x/2)*tiles + tiles-1][(y/2)*tiles + tiles-1].setY((y/2)*tiles + tiles-1);
		grass_tiles.remove(new Pos((x/2)*tiles + tiles-1, (y/2)*tiles + tiles-1));
		//Bottom-left corner
		board[(x/2)*tiles + tiles-1][(y/2)*tiles] = corners.remove(r.nextInt(corners.size()));
		board[(x/2)*tiles + tiles-1][(y/2)*tiles].setX((x/2)*tiles + tiles-1);
		board[(x/2)*tiles + tiles-1][(y/2)*tiles].setY((y/2)*tiles);
		grass_tiles.remove(new Pos((x/2)*tiles + tiles-1, (y/2)*tiles));
		//Top-right corner
		board[(x/2)*tiles][(y/2)*tiles + tiles-1] = corners.remove(r.nextInt(corners.size()));
		board[(x/2)*tiles][(y/2)*tiles + tiles-1].setX((x/2)*tiles);
		board[(x/2)*tiles][(y/2)*tiles + tiles-1].setY((y/2)*tiles + tiles-1);
		grass_tiles.remove(new Pos((x/2)*tiles, (y/2)*tiles + tiles-1));
		//Top-left corner
		board[(x/2)*tiles][(y/2)*tiles] = corners.remove(r.nextInt(corners.size()));
		board[(x/2)*tiles][(y/2)*tiles].setX((x/2)*tiles);
		board[(x/2)*tiles][(y/2)*tiles].setY((y/2)*tiles);
		grass_tiles.remove(0);
		//Place the trees.
		Pos temp_pos;
		for (int i = 0; i < MIN_WOODS; i++) {
			temp_pos = grass_tiles.remove(r.nextInt(grass_tiles.size()));
			board[temp_pos.getX()][temp_pos.getY()] = new WoodTile(temp_pos.getX(), temp_pos.getY());
		}
		//Place the remaining grass.
		for (Pos pos : grass_tiles) {
			board[pos.getX()][pos.getY()] = new GrassTile(pos.getX(), pos.getY());;
		}
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.println(i + ", " + j +": " + (board[i][j] == null ? null: board[i][j].getClass().getCanonicalName()));
			}
		}
		
		//Done.
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
	
	private static class Pos implements Comparable<Pos> {
		private int x;
		private int y;
		public Pos(int x, int y) {
			this.x  = x;
			this.y  = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}

		@Override
		public int compareTo(Pos arg0) {
			return (arg0.getX() == x && arg0.getY() == 0 ? 0 : arg0.getX() <= x && arg0.getY() < y ? -1 : 1);
		}
		
		public boolean equals(Pos arg0) {
			return arg0.getX() == x && arg0.getY() == y;
		}
		
		// Need to override the equals method from Object to get List.remove(o) to work properly.
		@Override
		public boolean equals(Object o) {
			return (o instanceof Pos ? ((Pos) o).equals(this) : false);
		}
		
		public String toString() {
			return "X: " + x + ", Y: " + y;
		}
	}

}