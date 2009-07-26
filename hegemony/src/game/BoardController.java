package game;

import gamepieces.Castle;
import gamepieces.GamePiece;
import gamepieces.Knight;
import gamepieces.OverlayPiece;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import tiles.SnowTile;
import tiles.Tile;

public class BoardController {

	public static int size;
	
	private Vertex[][] vertices = null;
	private Tile[][] tiles = null;

	private Edge lastSelected;
	private Player[] players = null;
	private int currentTurn = 0;

	private MODE currentMode;
	public enum MODE {
		PLACE_CASTLE,
		PLACE_KNIGHT,
		PLACE_WALL,
		EXPAND_AREA
	}
	
	public enum PHASE {
		
	}
	
		
	public BoardController(int size, int numPlayers) {
		BoardController.size = size;	
		players = new Player[numPlayers];
				
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player();
		}
		//TODO: find a way to generate colors on the fly
		Color[] playerColors = {Color.red, Color.blue, Color.green, Color.black};
		for (int i = 0; i < players.length; i++) {
			players[i].setColor(playerColors[i]);
		}
		
		//TODO: comment out
		tiles = new Tile[size-1][size-1];				
		generateEdgesAndVertices(size);
	}
	
	public void updateWorld() {

		for (int x = 0; x < vertices.length; x++) {
			for (int y = 0; y < vertices[x].length; y++) {
				if (x < vertices.length -1 && y < vertices[x].length -1) {
					for (GamePiece item : tiles[x][y].getItems()) {
						item.act();
					}
				}
				vertices[x][y].act();
			}
		}
		
	}

	////Initialize the board
				
	private void generateEdgesAndVertices(int size) {
		
		//make vertices
		vertices = new Vertex[size][size]; 
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				vertices[i][j] = new Vertex(i,j);
			}
		}
		//make edges and tiles
		for (int x = 0; x < vertices.length; x++) {
			for (int y = 0; y < vertices[x].length; y++) {
				
				if (x < vertices.length - 1) {					
					new Edge(vertices[x][y], vertices[x + 1][y], false);								
				}
				if (y < vertices[x].length - 1) {					
					new Edge(vertices[x][y], vertices[x][y + 1], true);
				}
				
				//TODO: remove and replace with the generator
				if (x < vertices.length - 1 && y < vertices[x].length - 1) {
					tiles[x][y] = new SnowTile(x,y);					
					//tiles[x][y] = new GrassTile(x,y);
					//tiles[x][y] = new WoodTile(x,y);
				}
			}
		}
		//TODO: generate tiles with tile generator:
		//tiles = new GameBoard(size - 1).getBoard(); 
		
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				Vertex topLeft = vertices[x][y];
				Vertex bottomRight = vertices[x+1][y+1];
				tiles[x][y].setBottomEdge(bottomRight.getRightEdge());
				tiles[x][y].setRightEdge(bottomRight.getTopEdge());
				tiles[x][y].setLeftEdge(topLeft.getBottomEdge());
				tiles[x][y].setTopEdge(topLeft.getLeftEdge());
				
				if (y < tiles.length - 1) 
					tiles[x][y].setBottomTile(tiles[x][y + 1]);
				if (x > 0)
					tiles[x][y].setLeftTile(tiles[x - 1][y]);
				if (x < tiles.length - 1)
					tiles[x][y].setRightTile(tiles[x + 1][y]);
				if (y > 0)
					tiles[x][y].setTopTile(tiles[x][y - 1]);
			}
		}
	}

	///////////Mode Actions/////////////
	public void handlePlayerAction(int x, int y, boolean clicked) {
		highlightedPiece.setActive(false);
		deselectEdges();
		if (clicked) {
			if (MODE.PLACE_CASTLE == currentMode)
				placeCastle(x, y);
			else if (MODE.EXPAND_AREA == currentMode)
				expandTerritory(x, y);
			else if (MODE.PLACE_KNIGHT == currentMode)
				placeKnight(x, y);
			else if (MODE.PLACE_WALL == currentMode)
				placeEdge(x, y);
		}
		else {
			if (MODE.PLACE_WALL == currentMode)			
				createWallOverlay(x,y);					
			else if (MODE.PLACE_CASTLE == currentMode)
				createCastleOverlay(x, y);
			else if (MODE.PLACE_KNIGHT == currentMode)
				createKnightOverlay(x, y);
		}
	}
	
	public boolean placeCastle(int x, int y) {
		
		int xPos = (int)(x/(double)Edge.LENGTH);
		int yPos = (int)(y/(double)Edge.LENGTH);
		Tile tile = tiles[xPos][yPos];
		
		if(null != tile.getCastle())
			return false;

		//TODO: check that castles aren't too close when placing
		
		tile.setCastle(new Castle(players[currentTurn]));
		return true;
	}
	
	public boolean placeKnight(int x, int y) {
		
		int xPos = (int)(x/(double)Edge.LENGTH);
		int yPos = (int)(y/(double)Edge.LENGTH);
		Tile tile = tiles[xPos][yPos];
		
		if(null != tile.getCastle())
			return false;
		if (null != tile.getKnight())
			return false;
		
		boolean isInArea = false;
		
	    List<Set<Tile>> playerAreas = getPlayerAreas().get(players[currentTurn]);
	    if (null == playerAreas)
	    	return false;
	    
		for (Set<Tile> area : playerAreas) {
			if (area.contains(tile))
				isInArea = true;
		}
		if (!isInArea)
			return false;
		
		//check that knight is connected to a castle or a knight
		Knight knight = null;
		Castle castle = null;
		Tile top = tile.getTopTile();
	    Tile bottom = tile.getBottomTile();
	    Tile left = tile.getLeftTile();
	    Tile right = tile.getRightTile();
	    
	    if (null != top && null != top.getCastle() && !tile.getTopEdge().isActive())
	    	castle = (Castle)top.getCastle();
	    else if (null != bottom && null != bottom.getCastle() && !tile.getBottomEdge().isActive())
	    	castle = (Castle)bottom.getCastle();
	    else if(null != left && null != left.getCastle() && !tile.getLeftEdge().isActive())
	    	castle = (Castle)left.getCastle();
	    else if(null != right && null != right.getCastle() && !tile.getRightEdge().isActive())
	    	castle = (Castle)right.getCastle();
	    
	    if (null != top && null != top.getKnight() && !tile.getTopEdge().isActive())
	    	knight = (Knight)top.getKnight();
	    else if (null != bottom && null != bottom.getKnight() && !tile.getBottomEdge().isActive())
	    	knight = (Knight)bottom.getKnight();
	    else if(null != left && null != left.getKnight() && !tile.getLeftEdge().isActive())
	    	knight = (Knight)left.getKnight();
	    else if(null != right && null != right.getKnight() && !tile.getRightEdge().isActive())
	    	knight = (Knight)right.getKnight();
	    
	    if (null == castle && null == knight)
	    	return false;	    	    	
	    
		tile.setKnight(new Knight(players[currentTurn]));
		return true;
	}
	
	public boolean expandTerritory(int x, int y) {
				
		int xPos = (int)(x/(double)Edge.LENGTH);
		int yPos = (int)(y/(double)Edge.LENGTH);
		Tile tile = tiles[xPos][yPos];
		
		if (null != tile.getKnight() || null != tile.getCastle())
			return false;
		
		Tile top = tile.getTopTile();
	    Tile bottom = tile.getBottomTile();
	    Tile left = tile.getLeftTile();
	    Tile right = tile.getRightTile();
	    
	    Player areaOwner = null;
	    Set<Tile> tileArea = null; 
	    Set<Tile> topArea = null;
	    Set<Tile> bottomArea = null;
	    Set<Tile> leftArea = null;
	    Set<Tile> rightArea = null;
	   
	    boolean topFriendly = false;
	    boolean bottomFriendly = false;
	    boolean leftFriendly = false;
	    boolean rightFriendly = false;
	    
	    
	    Map<Player, List<Set<Tile>>> currentAreas = getPlayerAreas();
	    for (Player p :currentAreas.keySet()) {
	    	for (Set<Tile> area : currentAreas.get(p)) {
	    		
	    		if (area.contains(tile)) {
	    			tileArea = area;
	    			areaOwner = p;	    	
	    		}
	    		if (area.contains(top)) {	    			
	    			if (p.equals(players[currentTurn])) { 
	    				topFriendly = true;	    				
	    			}
	    			topArea = area;
	    		}
	    		if (area.contains(bottom)) {	    			
	    			if (p.equals(players[currentTurn])) {
	    				bottomFriendly = true;	    				
	    			}
	    			bottomArea = area;
	    		}
	    		if (area.contains(left)) {
	    			if (p.equals(players[currentTurn])) {
	    				leftFriendly = true;	    				
	    			}
	    			leftArea = area;
	    		}
	    		if (area.contains(right)) {  	
	    			if (p.equals(players[currentTurn])) {
	    				rightFriendly = true;	    				
	    			}
	    			rightArea = area;
	    		}
	    	}
	    }
	    
	    if (!(topFriendly  || bottomFriendly || leftFriendly  || rightFriendly))
	    	return false;
	    	   
	    
	    //check if the area is valid for expansion
	    if (null != areaOwner && !players[currentTurn].equals(areaOwner)) {
	    	int numOpposingKnights = countKnightsInArea(tileArea);
	    	int numFriendlyKnights = 0;
	    	if (topFriendly) {
	    		int knights = countKnightsInArea(topArea);
	    		if (knights > numFriendlyKnights)
	    			numFriendlyKnights = knights;
	    	}
	    	if (bottomFriendly) {
	    		int knights = countKnightsInArea(bottomArea);
	    		if (knights > numFriendlyKnights)
	    			numFriendlyKnights = knights;
	    	}
	    	if (leftFriendly) {
	    		int knights = countKnightsInArea(leftArea);
	    		if (knights > numFriendlyKnights)
	    			numFriendlyKnights = knights;
	    	}
	    	if (rightFriendly) {
	    		int knights = countKnightsInArea(rightArea);
	    		if (knights > numFriendlyKnights)
	    			numFriendlyKnights = knights;
	    	}
	    	if (numFriendlyKnights <= numOpposingKnights)
	    		return false;
	    	
	    }
	    
	    //expand area
	    tile.getTopEdge().setActive(true);
    	tile.getLeftEdge().setActive(true);
    	tile.getRightEdge().setActive(true);
    	tile.getBottomEdge().setActive(true);
    	
	    if (topFriendly) {
	    	tile.getTopEdge().setActive(false);
	    } 
	    if (bottomFriendly) {
	    	tile.getBottomEdge().setActive(false);
	    }
	    if (leftFriendly) {
	    	tile.getLeftEdge().setActive(false);
	    }
	    if (rightFriendly) {
	    	tile.getRightEdge().setActive(false);
	    }

	    return true;
	}
	
	private int countKnightsInArea(Set<Tile> area) {
		int numKnights = 0;
    	for (Tile t : area) {
    		if (null != t.getKnight())
    			numKnights++;
    	}
    	return numKnights; 
	}
	
	public void placeEdge(int x, int y) {
		double xPos = x/(double)Edge.LENGTH;
		double yPos = y/(double)Edge.LENGTH;
		
		updateEdgeStatus(xPos,yPos);									
	}
	
	private void updateEdgeStatus(double x, double y){
		int xi = (int)x;
		int yi = (int)y;

		Vertex v = vertices[xi][yi];
		double xOffset = x - xi;
		double yOffset = y - yi;
		
		if (Math.abs(0.5 - xOffset) > Math.abs(0.5 - yOffset)) {
			if (0.5 > xOffset) {
				v.getBottomEdge().setActive(!v.getBottomEdge().isActive());				
			} else {				
				Vertex v1 = v.getLeftEdge().getSecond(); 
				if (null == v1.getBottomEdge())
					return;
				v1.getBottomEdge().setActive(!v1.getBottomEdge().isActive());				
			}
		}
		else {
			if (yOffset > 0.5) {			
				Vertex v1 = v.getBottomEdge().getSecond();
				if (null == v1.getLeftEdge())
					return;
				v1.getLeftEdge().setActive(!v1.getLeftEdge().isActive());
			} else {
				v.getLeftEdge().setActive(!v.getLeftEdge().isActive());
			}
		}	
	}
	
	
	private void deselectEdges() {
		for (int x = 0; x < vertices.length -1; x++) {
			for (int y = 0; y < vertices[x].length - 1; y++) {
				vertices[x][y].getLeftEdge().setSelected(false);
				vertices[x][y].getBottomEdge().setSelected(false);
			}
		}
	}

	
		
	private void selectEdge(double x, double y){
		int xi = (int)x;
		int yi = (int)y;
				
		Vertex v = vertices[xi][yi];
		double xOffset = x - xi;
		double yOffset = y - yi;
		
		if (Math.abs(0.5 - xOffset) > Math.abs(0.5 - yOffset)) {
			if (0.5 > xOffset) {
				Edge toSelect = v.getBottomEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				toSelect.setSelected(!v.getBottomEdge().isSelected());				
			} else {		
				if (null == v.getLeftEdge())
					return;
				Vertex v1 = v.getLeftEdge().getSecond();
				Edge toSelect = v1.getBottomEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				toSelect.setSelected(!v1.getBottomEdge().isSelected());
			}
		}
		else {
			if (yOffset > 0.5) {	
				if (null == v.getBottomEdge())
					return;
				Vertex v1 = v.getBottomEdge().getSecond();
				Edge toSelect = v1.getLeftEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				v1.getLeftEdge().setSelected(!v1.getLeftEdge().isSelected());
			} else {		
				Edge toSelect = v.getLeftEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				v.getLeftEdge().setSelected(!v.getLeftEdge().isSelected());
			}
		}	
	}

	private OverlayPiece highlightedPiece = new OverlayPiece();
	
	public OverlayPiece getCurrentPiece() {
		return highlightedPiece;
	}
	
	private void createCastleOverlay(int x, int y) {		
		highlightedPiece.setXY(x,y);
		highlightedPiece.setPiece(new Castle(players[currentTurn]));
		highlightedPiece.setActive(true);
	}
	
	private void createKnightOverlay(int x, int y) {		
		highlightedPiece.setXY(x,y);
		highlightedPiece.setPiece(new Knight(players[currentTurn]));
		highlightedPiece.setActive(true);
	}
	
	private void createWallOverlay(int x, int y) {
		
		double xPos = x/(double)Edge.LENGTH;
		double yPos = y/(double)Edge.LENGTH;
				
		selectEdge(xPos,yPos);		
	}
	
	public Map<Player, List<Set<Tile>>> getPlayerAreas() {
		
		Map<Player, List<Set<Tile>>> areas =  new HashMap<Player, List<Set<Tile>>>();
	    TreeSet<Tile> toVisit = new TreeSet<Tile>();
	    Set<Tile> visited = new HashSet<Tile>();	    
	    toVisit.add(tiles[0][0]);
	    
	    while(!toVisit.isEmpty()) 
	    {
	        Set<Tile> traversed = new HashSet<Tile>();
	        List<Castle> castles = new ArrayList<Castle>();
	        findConnected(toVisit.first(), traversed, visited, toVisit, castles);
	        
	        if (castles.size() == 1) {
	        	List<Set<Tile>> playerAreas = areas.get(castles.get(0).getPlayer());
	        	if (null == playerAreas) {
	        		playerAreas = new ArrayList<Set<Tile>>();
	        		areas.put(castles.get(0).getPlayer(), playerAreas);
	        	}
	        	
	        	playerAreas.add(traversed);
	        }
	    } 
	    return areas;
	}
	
	private void findConnected(Tile tile, Set<Tile> traversed, Set<Tile> visited, Set<Tile> toVisit, List<Castle> castles) {
	    traversed.add(tile);
	    visited.add(tile);
	    toVisit.remove(tile);
	    
	    if (null != tile.getCastle())
	    	castles.add((Castle)tile.getCastle());
	    
	    Tile top = tile.getTopTile();
	    Tile bottom = tile.getBottomTile();
	    Tile left = tile.getLeftTile();
	    Tile right = tile.getRightTile();
	    
	    if (null != top && !visited.contains(top)) {
	        toVisit.add(top);
	        if(!tile.getTopEdge().isActive()) {
	            findConnected(top, traversed, visited, toVisit, castles);
	        }
	    }
	    if (null != bottom && !visited.contains(bottom)) {
	        toVisit.add(bottom);
	        if(!tile.getBottomEdge().isActive()) {
	            findConnected(bottom, traversed, visited, toVisit, castles);
	        }
	    }
	    if (null != left && !visited.contains(left)) {
	        toVisit.add(left);
	        if(!tile.getLeftEdge().isActive()) {
	            findConnected(left, traversed, visited, toVisit, castles);
	        }
	    }
	    if (null != right && !visited.contains(right)) {
	        toVisit.add(right);
	        if(!tile.getRightEdge().isActive()) {
	            findConnected(right, traversed, visited, toVisit, castles);
	        }
	    }
	}
	
	public void setCurrentMode(MODE currentMode) {
		this.currentMode = currentMode;
	}

	public Player getCurrentPlayer() {
		return players[currentTurn];
	}
	
	public MODE getCurrentMode() {
		return currentMode;
	}

	public void updateCurrentTurn() {
		this.currentTurn = (currentTurn + 1) % players.length;			
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public Vertex[][] getVertices() {
		return vertices;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Player[] getPlayers() {
		return players;
	}
} 
