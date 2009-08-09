package game;

import gamepieces.Castle;
import gamepieces.ExpandPiece;
import gamepieces.GamePiece;
import gamepieces.Knight;
import gamepieces.Mine;
import gamepieces.OverlayPiece;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import tiles.GameBoard;
import tiles.Tile;

public class BoardController {

	public static int size;
	
	private Vertex[][] vertices = null;
	private Tile[][] tiles = null;

	private Edge lastSelected;
	private Player[] players = null;
	private int currentTurn = 0;
	private GamePhase gamePhase;
	
	private MODE currentMode;
	public enum MODE {
		PLACE_CASTLE,
		PLACE_KNIGHT_SIMPLE,
		PLACE_KNIGHT,
		PLACE_WALL,
		EXPAND_AREA,
		EMPTY
	}
	
	public enum GamePhase {
		SETUP,
		MAIN
	}
	
	public enum TurnPhase {
		DRAW_CARD,
		ACT,
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
			}
		}

		tiles = GameBoard.generateBoard(3, 3, (size - 1)/3); 
		
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
	public boolean handlePlayerAction(int x, int y, boolean clicked) {
		deselectEdges();
		highlightedPiece.setActive(false);		
		if (clicked) {
			if (MODE.PLACE_CASTLE == currentMode)
				return placeCastle(x, y);					
			else if (MODE.EXPAND_AREA == currentMode)
				return expandTerritory(x, y);
			else if (MODE.PLACE_KNIGHT_SIMPLE == currentMode) 
				return placeKnightSimple(x, y);
			else if (MODE.PLACE_KNIGHT == currentMode)
				return placeKnight(x, y);
			else if (MODE.PLACE_WALL == currentMode)
				return placeEdge(x, y);
		}
		else {
			if (MODE.PLACE_WALL == currentMode)			
				createWallOverlay(x,y);					
			else if (MODE.PLACE_CASTLE == currentMode)
				createCastleOverlay(x, y);
			else if (MODE.PLACE_KNIGHT == currentMode || MODE.PLACE_KNIGHT_SIMPLE == currentMode)
				createKnightOverlay(x, y);
			else if (MODE.EXPAND_AREA == currentMode)
				createTileOverlay(x, y);
			
		}
		return true;
	}
	
	/*
	private boolean findFriendlyCastle(Tile t, int range) {
		if (null != t.getCastle() && ((Castle)t.getCastle()).getPlayer().equals(players[currentTurn]))
			return true;
		else if(range < 0)
			return false;
		else {
			findFriendlyCastle(t.getBottomTile(), --range);
			findFriendlyCastle(t.getBottomTile(), --range);
		}
	}
	*/
	private boolean friendlyCastlesInRange(Tile t) {
		
		
		/*
		int xStart = (t.getX() - 5 > -1? t.getX() - 5: 0);
		int yStart = (t.getY() - 5 > -1? t.getY() - 5: 0);
		int xEnd = (xStart + 10 < tiles.length ? xStart + 10: tiles.length);
		int yEnd = (yStart + 10 < tiles[xStart].length ? yStart + 10: tiles.length);
				
		for (int x = xStart; x < xEnd; x++) {
			for (int y = yStart; y < yEnd; y++) {
				Castle castle = (Castle)tiles[x][y].getCastle(); 
				if (null != castle && castle.getPlayer().equals(players[currentTurn]))
					return true;
			}
		}
		
		*/
		return false;
	}
	
	public boolean placeCastle(int x, int y) {
				
		int xPos = (int)(x/(double)Edge.LENGTH);
		int yPos = (int)(y/(double)Edge.LENGTH);
		
		if (xPos > tiles.length || yPos > tiles[xPos].length)
			return false;
		
		Tile tile = tiles[xPos][yPos];
		
		if(tile.getItems().size() > 0)
			return false;

		if (friendlyCastlesInRange(tile))
			return false;

		Castle castle = players[currentTurn].placeCastle();
		if (null == castle)
			return false;
		
		tile.setCastle(castle);
		return true;
	}
	
	public boolean placeKnightSimple(int x, int y) {
		int xPos = (int)(x/(double)Edge.LENGTH);
		int yPos = (int)(y/(double)Edge.LENGTH);
		Tile tile = tiles[xPos][yPos];
		
		if(null != tile.getCastle())
			return false;
		if (null != tile.getKnight())
			return false;
		if (null != tile.getCapital())
			return false;
		if (null != tile.getVillage())
			return false;
		if (null != tile.getMine())
			return false;
		
		Tile top = tile.getTopTile();
	    Tile bottom = tile.getBottomTile();
	    Tile left = tile.getLeftTile();
	    Tile right = tile.getRightTile();
	    
	    boolean adjacentFriendlyCastle = false;
	    if (null != top && null != top.getCastle() && ((Castle)top.getCastle()).getPlayer().equals(players[currentTurn]))
	    	adjacentFriendlyCastle = true;
	    if (null != bottom && null != bottom.getCastle() && ((Castle)bottom.getCastle()).getPlayer().equals(players[currentTurn]))
	    	adjacentFriendlyCastle = true;
	    if (null != left && null != left.getCastle() && ((Castle)left.getCastle()).getPlayer().equals(players[currentTurn]))
	    	adjacentFriendlyCastle = true;
	    if (null != right && null != right.getCastle() && ((Castle)right.getCastle()).getPlayer().equals(players[currentTurn]))
	    	adjacentFriendlyCastle = true;
	    	
	    if(!adjacentFriendlyCastle)
	    	return false;
	    
		tile.setKnight(players[currentTurn].placeKnight());
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
		if (null != tile.getCapital())
			return false;
		if (null != tile.getVillage())
			return false;
		if (null != tile.getMine())
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
	    
	    Knight playerKnight = players[currentTurn].placeKnight();
	    if (null == playerKnight)
	    	return false;
	    
		tile.setKnight(playerKnight);
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
	    
	    Set<Tile> expandArea = null;
	    //check if the area is valid for expansion
	    if (null != areaOwner && !players[currentTurn].equals(areaOwner)) {
	    	int numOpposingKnights = countKnightsInArea(tileArea);
	    	int numFriendlyKnights = 0;
	    	if (topFriendly) {
	    		int knights = countKnightsInArea(topArea);
	    		if (knights >= numFriendlyKnights) {
	    			numFriendlyKnights = knights;
	    			expandArea = topArea;
	    		}
	    	}
	    	if (bottomFriendly) {
	    		int knights = countKnightsInArea(bottomArea);
	    		if (knights >= numFriendlyKnights) {
	    			numFriendlyKnights = knights;
	    			expandArea = bottomArea;
	    		}
	    	}
	    	if (leftFriendly) {
	    		int knights = countKnightsInArea(leftArea);
	    		if (knights >= numFriendlyKnights) {
	    			numFriendlyKnights = knights;
	    			expandArea = leftArea;
	    		}
	    	}
	    	if (rightFriendly) {
	    		int knights = countKnightsInArea(rightArea);
	    		if (knights >= numFriendlyKnights) {
	    			numFriendlyKnights = knights;
	    			expandArea = rightArea;
	    		}
	    	}
	    	if (numFriendlyKnights <= numOpposingKnights)
	    		return false;	    	
	    } else {
	    	expandArea = (topFriendly?topArea:(leftFriendly?leftArea:(rightFriendly?rightArea:bottomArea)));
	    }
	    
	    //expand area
	    tile.getTopEdge().setActive(true);
    	tile.getLeftEdge().setActive(true);
    	tile.getRightEdge().setActive(true);
    	tile.getBottomEdge().setActive(true);
    	
	    if (expandArea.contains(top)) {
	    	tile.getTopEdge().setActive(false);
	    } 
	    if (expandArea.contains(bottom)) {
	    	tile.getBottomEdge().setActive(false);
	    }
	    if (expandArea.contains(left)) {
	    	tile.getLeftEdge().setActive(false);
	    }
	    if (expandArea.contains(right)) {
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
	
	public boolean placeEdge(int x, int y) {
		
		double xPos = x/(double)Edge.LENGTH;
		double yPos = y/(double)Edge.LENGTH;
		
		boolean placed = placeWall(xPos,yPos);
		deselectEdgesInTerritories();
		return placed;
	}
	
	private boolean isValidWallPlacement(Tile t1, Tile t2) {

		if (null == t2)
			return true;
		
		Knight knight = (Knight)t1.getKnight();
		Castle castle = (Castle)t1.getCastle();
		
		if (null == knight && null == castle)
			return true;
		
		Player p1 = (null == knight?castle.getPlayer():knight.getPLayer());
		
		knight = (Knight)t2.getKnight();
		castle = (Castle)t2.getCastle();
		
		if (null == knight && null == castle)
			return true;
		
		Player p2 = (null == knight?castle.getPlayer():knight.getPLayer());
		
		return !p1.equals(p2);
	}
	
	public boolean placeWall(double x, double y) {

		Edge edge = findSelectedEdge(x, y);
		if (edge.isActive())
			return false;
		
		int xi = (int)x;
		int yi = (int)y;
		Tile t1 = tiles[xi][yi];
		Tile t2 = null;
		
		if (t1.getTopEdge().equals(edge)) {
			t2 = t1.getTopTile();
		}
		else if (t1.getBottomEdge().equals(edge)) {
			t2 = t1.getBottomTile();
		}
		else if (t1.getRightEdge().equals(edge)) {
			t2 = t1.getRightTile();
		}
		else if (t1.getLeftEdge().equals(edge)) {
			t2 = t1.getLeftTile();
		}
		
		if (null != t2 && !isValidWallPlacement(t1, t2))
			return false;
		
		edge.setActive(true);
		return true;
	}
	
	private Edge findSelectedEdge(double x, double y){
		int xi = (int)x;
		int yi = (int)y;

		Vertex v = vertices[xi][yi];
		double xOffset = x - xi;
		double yOffset = y - yi;
		
		if (Math.abs(0.5 - xOffset) > Math.abs(0.5 - yOffset)) {
			if (0.5 > xOffset) {
				return v.getBottomEdge();				
			} else {				
				Vertex v1 = v.getLeftEdge().getSecond(); 
				if (null == v1.getBottomEdge())
					return null;
				return v1.getBottomEdge();				
			}
		}
		else {
			if (yOffset > 0.5) {			
				Vertex v1 = v.getBottomEdge().getSecond();
				if (null == v1.getLeftEdge())
					return null;
				return v1.getLeftEdge();
			} else {
				return v.getLeftEdge();
			}
		}	
	}				
	
	private void deselectEdges() {
		for (int x = 0; x < vertices.length; x++) {
			for (int y = 0; y < vertices[x].length ; y++) {
				if (null != vertices[x][y].getLeftEdge())
					vertices[x][y].getLeftEdge().setSelected(false);
				if (null != vertices[x][y].getBottomEdge()) 
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
	
	private void createTileOverlay(int x, int y) {
		highlightedPiece.setXY(x,y);
		highlightedPiece.setPiece(new ExpandPiece(players[currentTurn].getColor()));
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
	
	private void deselectEdgesInTerritories() {
		Map<Player, List<Set<Tile>>> playerAreas = getPlayerAreas();
		for (Player player : playerAreas.keySet()) {
			for (Set<Tile> tiles : playerAreas.get(player)) {
				for (Tile tile : tiles) {
					Edge topEdge = tile.getTopEdge();
					Edge bottomEdge = tile.getBottomEdge();
					Edge leftEdge = tile.getLeftEdge();
					Edge rightEdge = tile.getRightEdge();
					
					if (topEdge.isActive()) {
						if (tiles.contains(tile.getTopTile())) {
							topEdge.setActive(false);
						}
					}
					if (bottomEdge.isActive()) {
						if (tiles.contains(tile.getBottomTile())) {
							bottomEdge.setActive(false);
						}
					}
					if (leftEdge.isActive()) {
						if (tiles.contains(tile.getLeftTile())) {
							leftEdge.setActive(false);
						}
					}
					if (rightEdge.isActive()) {
						if (tiles.contains(tile.getRightTile())) {
							rightEdge.setActive(false);
						}
					}
				}
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
	
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	public void updateCurrentPlayerResources() {
		Player player = players[currentTurn];
		boolean hasGold = false;
		boolean hasSilver = false;
		boolean hasDiamond = false;
		boolean hasCopper = false;
	
		List<Set<Tile>> areas = getPlayerAreas().get(player);
		if (null == areas)
			return;
		
		for (Set<Tile> area : areas) {
			for (Tile tile : area) {
				Mine mine = (Mine)tile.getMine();
				if (null == mine) continue;
					
				if (!hasCopper && Mine.Types.COPPER == mine.getType()) {
					player.addResources(mine.getValue());
					hasCopper = true;
				}
				else if (!hasGold && Mine.Types.GOLD == mine.getType()) {
					player.addResources(mine.getValue());
					hasGold = true;
				}
				else if (!hasSilver && Mine.Types.SILVER == mine.getType()) {
					player.addResources(mine.getValue());
					hasSilver = true;
				}
				else if (!hasDiamond && Mine.Types.DIAMOND == mine.getType()) {
					player.addResources(mine.getValue());
					hasDiamond = true;
				}
			}
		}				
	}
	
	public void updateCurrentPlayerScore() {
		Player player = players[currentTurn];
		
		List<Mine> gold = new ArrayList<Mine>();
		List<Mine> silver = new ArrayList<Mine>();
		List<Mine> diamond = new ArrayList<Mine>();
		List<Mine> copper = new ArrayList<Mine>();
		
		List<Set<Tile>> areas = getPlayerAreas().get(player);
		for (Set<Tile> area : areas) {
			for (Tile tile : area) {
				for (GamePiece item : tile.getItems()) {
					if (item instanceof Mine) {
						Mine mine = (Mine)item;
						if (Mine.Types.COPPER == mine.getType())
							copper.add(mine);
						else if (Mine.Types.GOLD == mine.getType())
							gold.add(mine);
						else if (Mine.Types.SILVER == mine.getType())
							silver.add(mine);
						else if (Mine.Types.DIAMOND == mine.getType())
							diamond.add(mine);
					} else {
						player.updateScore(item.getValue());
					}
				}
			}
		}
		player.addResources(gold.size() < 1 ? 0 : (gold.size() >= 3 ? Mine.MONOPOLY_VALUE : gold.get(0).getValue()));
		player.addResources(gold.size() < 1 ? 0 : (silver.size() >= 3 ? Mine.MONOPOLY_VALUE : silver.get(0).getValue()));
		player.addResources(gold.size() < 1 ? 0 : (diamond.size() >= 3 ? Mine.MONOPOLY_VALUE : diamond.get(0).getValue()));
		player.addResources(gold.size() < 1 ? 0 : (copper.size() >= 3 ? Mine.MONOPOLY_VALUE : copper.get(0).getValue()));
	}
} 
