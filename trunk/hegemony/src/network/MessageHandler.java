package network;

import tiles.CapitalTile;
import tiles.CopperTile;
import tiles.DiamondTile;
import tiles.GameBoard;
import tiles.GoldTile;
import tiles.GrassTile;
import tiles.SilverTile;
import tiles.Tile;
import tiles.VillageTile;
import tiles.WoodTile;

public class MessageHandler {

	private static final String SEPARATOR = "-";
	private static final char CAPITAL = 'A';
	private static final char COPPER = 'C';
	private static final char DIAMOND = 'D';
	private static final char FOREST = 'F';
	private static final char GOLD = 'O';
	private static final char  GRASS = 'G';
	private static final char  SILVER = 'S';
	private static final char  VILLAGE = 'V';
	
	public static String serializeBoard(Tile[][] tiles) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int x = 0; x < tiles.length; x++) {
			String row = new String();
			for (int y = 0; y < tiles[x].length; y++) {
				row += (Tile.Type.CAPITAL ==tiles[x][y].getType() ? CAPITAL :
									(Tile.Type.COPPER ==tiles[x][y].getType() ? COPPER :
										(Tile.Type.DIAMOND ==tiles[x][y].getType() ? DIAMOND : 
											(Tile.Type.FOREST ==tiles[x][y].getType()? FOREST :
												(Tile.Type.GOLD ==tiles[x][y].getType() ? GOLD : 
													(Tile.Type.GRASS ==tiles[x][y].getType() ? GRASS :
														(Tile.Type.SILVER ==tiles[x][y].getType() ? SILVER :
															(Tile.Type.VILLAGE ==tiles[x][y].getType() ? VILLAGE : null))))))));
			}
			sb.append(row);
			sb.append(SEPARATOR);
		}
		
		return sb.toString();
	}

	public static Tile[][] deserializeBoard(String board) {
		
		String[] rows = board.split(SEPARATOR);
		Tile[][] tiles = new Tile[rows.length][rows.length];

		for (int y = 0; y < rows.length; y ++) {
			for (int x = 0; x < rows[y].length(); x++) {
				char c = rows[y].charAt(x);	
				tiles[y][x] = (CAPITAL == c ? new CapitalTile(x,y) :
					 			(COPPER == c ? new CopperTile(x,y) :
					 				(DIAMOND == c ? new DiamondTile(x,y) :
					 					(FOREST == c ? new WoodTile(x,y) :
					 						(GOLD == c ? new GoldTile(x,y) :
					 							(SILVER == c ? new SilverTile(x,y) :
					 								(VILLAGE == c ? new VillageTile(x,y) : 
					 									(GRASS == c ? new GrassTile(x,y) : null))))))));
			}
		}		
		return tiles;
	}
	
	public static void main(String[] args) {
		
		Tile[][] tiles = GameBoard.generateBoard(3, 3, (12 - 1)/3);
		System.out.println(serializeBoard(tiles));
		System.out.println(serializeBoard(deserializeBoard(serializeBoard(tiles))));
		
	}
}
