package network;

import java.awt.Color;
import java.util.Stack;

import game.BoardController;
import game.GameController;
import game.Player;
import cards.Deck;
import cards.Card;
import tiles.CapitalTile;
import tiles.CopperTile;
import tiles.DiamondTile;
import tiles.GoldTile;
import tiles.GrassTile;
import tiles.SilverTile;
import tiles.Tile;
import tiles.VillageTile;
import tiles.WoodTile;

public class MessageHandler {

	private static final char DRAW_ACTION = 'A';
	private static final char BAZAAR_ACTION = 'Z';
	private static final char SELL_ACTION = 'S';
	private static final char EXPAND_ACTION = 'E';
	private static final char KNIGHT_ACTION = 'K';
	private static final char WALL_ACTION = 'W';
	private static final char CASTLE_ACTION = 'C';
	private static final char KNIGHT_SIMPLE_ACTION = 'N';
	
	private static final String SEPARATOR = "-";
	private static final char CAPITAL = 'A';
	private static final char COPPER = 'C';
	private static final char DIAMOND = 'D';
	private static final char FOREST = 'F';
	private static final char GOLD = 'O';
	private static final char  GRASS = 'G';
	private static final char  SILVER = 'S';
	private static final char  VILLAGE = 'V';
	
	private BoardController bc = null;
	private GameController controller = null;
	private ConnectionManager cm = null;
	
	public MessageHandler(BoardController bc, GameController controller, String[] users) throws InterruptedException {
		this.bc = bc;
		this.controller = controller;
		cm = ConnectionManager.INSTANCE;
		int i = 0;
		while(i < users.length) {
			cm.getConnection(users[i++], users[i++]);
		}
	}
	
	public String serializeDeck(Deck deck) {
		StringBuffer sb = new StringBuffer();
		for (Card card : deck.getCards()) {
			sb.append((BoardController.MODE.EXPAND_AREA == card.getMode() ? EXPAND_ACTION :
						(BoardController.MODE.PLACE_KNIGHT == card.getMode() ? KNIGHT_ACTION :
							BoardController.MODE.PLACE_WALL == card.getMode() ? WALL_ACTION : null)));
		}
		return sb.toString();
	}
	
	public Deck deserializeDeck(String deck) {
		Stack<Card> cards = new Stack<Card>();
		for (int i = 0; i < deck.length(); i++) {
			char c = deck.charAt(i);
	
			cards.push((EXPAND_ACTION == c ? new Card(controller, BoardController.MODE.EXPAND_AREA) :
							(KNIGHT_ACTION == c ? new Card(controller, BoardController.MODE.PLACE_KNIGHT) :
								(WALL_ACTION == c ? new Card(controller, BoardController.MODE.PLACE_WALL) : null))));
		}
		return new Deck(cards);
	}
	
	public String serializeBoard(Tile[][] tiles) {
		
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

	public Tile[][] deserializeBoard(String board) {
		
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
	
	public void handleAction() throws IllegalStateException, InterruptedException {
		handleAction(cm.getQueue().take());
	}
	
	public void handleAction(String action) throws IllegalStateException {

		int x = -1;
		int y = -1;
		try {
			int separator = action.indexOf(','); 
			x = Integer.parseInt(action.substring(1,separator));
			y = Integer.parseInt(action.substring(separator + 1,action.length()));
		} catch (Exception ex) {
			throw new IllegalStateException("Invalid action: " + action);
		}
		System.out.println(x + "," + y);
		
		if (DRAW_ACTION == action.charAt(0)) {
			
		}
		else if (BAZAAR_ACTION == action.charAt(0)) {
			
		}
		else if (SELL_ACTION == action.charAt(0)) {
			
		}
		else if (EXPAND_ACTION == action.charAt(0)) {						
			bc.expandTerritory(x, y);
		}
		else if (KNIGHT_ACTION == action.charAt(0)) {
			bc.placeKnight(x,y);
		}
		else if (KNIGHT_SIMPLE_ACTION == action.charAt(0)) {
			bc.placeCastle(x,y);
		}
		else if (CASTLE_ACTION == action.charAt(0)) {
			bc.placeKnightSimple(x,y);
		}
		else if (WALL_ACTION == action.charAt(0)) {
			bc.placeEdge(x, y);
		}		
		else throw new IllegalStateException("Unknown action: " + action);		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		int numPlayers = 2;
		Player[] players = new Player[numPlayers];
		
		players[0] = new Player("Kirk");
		players[1] = new Player("Spock");
		
		//TODO: find a way to generate colors on the fly
		Color[] playerColors = {Color.red, Color.blue, Color.green, Color.black};
		for (int i = 0; i < players.length; i++) {
			players[i].setColor(playerColors[i]);
		}
		BoardController board = new BoardController(12,players);
		MessageHandler mh = new MessageHandler(board, null, new String[]{});
		Tile[][] tiles = board.getTiles();
		System.out.println(mh.serializeBoard(tiles));
		System.out.println(mh.serializeBoard(mh.deserializeBoard(mh.serializeBoard(tiles))));
		mh.handleAction(EXPAND_ACTION + "10,11");
		
		//Deck deck = new Deck(new GameController(null, null, null));
		//System.out.println(mh.serializeDeck(deck));
		//System.out.println(mh.serializeDeck(mh.deserializeDeck(mh.serializeDeck(deck))));
		
		
	}
}
