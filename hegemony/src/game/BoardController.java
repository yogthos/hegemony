package game;

import gamepieces.GamePiece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import tiles.Tile;
import tiles.WoodTile;

public class BoardController {

	public static int size;
	
	private Vertex[][] vertices = null;
	private Tile[][] tiles = null;
	private Image boardImage = null;
	private Image terrainImage = null;
	private Image highlightsImage = null;
	private Edge lastSelected;
	
	public enum MODE {
		PLACE_CASTLE,
		PLACE_KNIGHT,
		PLACE_WALL,
		EXPAND_AREA
	}
	
	public enum PHASE {
		
	}
	
	public BoardController(int size) {
		BoardController.size = size;	
		tiles = new Tile[size-1][size-1];
		boardImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		terrainImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
				
		generateEdgesAndVertices(size);
		
		drawTilesAndTerrain(vertices[0][0], boardImage.getGraphics(), terrainImage.getGraphics());
	}
	
	public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentBoard.getGraphics();
		g.drawImage(boardImage,size,size,null);
		if (null != highlightsImage) g.drawImage(highlightsImage,size,size,null);
		draw(vertices[0][0], g);
		g.drawImage(terrainImage,size,size,null);
						
		return currentBoard;		
	}
	
	private void drawTilesAndTerrain(Vertex v, Graphics boardG, Graphics terrainG) {

		
		drawTileTerrainColumn(v, boardG, terrainG);
		
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {			
			drawTilesAndTerrain(leftEdge.getSecond(), boardG, terrainG);			
		}				
	}
	
	private void drawTileTerrainColumn(Vertex v, Graphics boardG, Graphics terrainG) {
		int x = v.getX();
		int y = v.getY();
		int posX = v.getPosX();
		int posY = v.getPosY();

		if (x < size - 1 && y < size - 1) {
			boardG.drawImage(tiles[x][y].draw(), posX, posY, null);
			for (GamePiece piece : tiles[x][y].getItems()) {
				terrainG.drawImage(piece.draw(), posX, posY, null);
			}

			Edge bottomEdge = v.getBottomEdge();
			if (null != bottomEdge) {
				drawTileTerrainColumn(bottomEdge.getSecond(), boardG, terrainG);
			}
		}
	}

	private void draw(Vertex v, Graphics g) {
		
			
		drawEdgeColumn(v, g);
		Edge leftEdge = v.getLeftEdge(); 
		if (null != leftEdge) {
			draw(leftEdge.getSecond(), g);
		}
	}
	
	
	private void drawEdgeColumn(Vertex v, Graphics g) {
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {
			if ((leftEdge.isActive() || leftEdge.isSelected()) && !leftEdge.isHidden()) {
				g.drawImage(leftEdge.draw(),leftEdge.getPosX()  + Vertex.SIZE, leftEdge.getPosY(), null);
			}
		}
		
		if (v.hasActiveEdges()) {
			g.drawImage(Vertex.draw(), v.getPosX() + Vertex.SIZE/2, v.getPosY() + Vertex.SIZE/2, null);
		}
		
		Edge bottomEdge = v.getBottomEdge();
		if (null != bottomEdge) {
			if ((bottomEdge.isActive() || bottomEdge.isSelected()) && !bottomEdge.isHidden()) {
				g.drawImage(bottomEdge.draw(),bottomEdge.getPosX() + Vertex.SIZE, bottomEdge.getPosY() + Vertex.SIZE, null);
			}
			drawEdgeColumn(bottomEdge.getSecond(), g);
		}	
	}
	
				
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
				
				if (x < vertices.length - 1 && y < vertices[x].length - 1) {
				
					tiles[x][y] = new WoodTile(x,y);
				}
			}
		}
		
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
	public void placeCastle(int x, int y) {
		System.out.println("In castle mode");
	}
	
	public void placeKnight(int x, int y) {
		
	}
	
	public void expandTerritory(int x, int y) {
		
	}
	
	public void placeEdge(int x, int y) {
		double xPos = x/(double)Edge.LENGTH;
		double yPos = y/(double)Edge.LENGTH;
		
		updateEdgeStatus(xPos,yPos);
		
		
		highlightsImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);				
		findLoops(highlightsImage.getGraphics());
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
		
		deselectEdges();
		
		Vertex v = vertices[xi][yi];
		double xOffset = x - xi;
		double yOffset = y - yi;
		
		if (Math.abs(0.5 - xOffset) > Math.abs(0.5 - yOffset)) {
			if (0.5 > xOffset) {
				Edge toSelect = v.getBottomEdge();
				if (toSelect.equals(lastSelected))
					return;
				toSelect.setSelected(!v.getBottomEdge().isSelected());				
			} else {				
				Vertex v1 = v.getLeftEdge().getSecond();
				Edge toSelect = v1.getBottomEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				toSelect.setSelected(!v1.getBottomEdge().isSelected());
			}
		}
		else {
			if (yOffset > 0.5) {			
				Vertex v1 = v.getBottomEdge().getSecond();
				Edge toSelect = v1.getLeftEdge();
				if (null == toSelect || toSelect.equals(lastSelected))
					return;
				v1.getLeftEdge().setSelected(!v1.getLeftEdge().isSelected());
			} else {		
				Edge toSelect = v.getLeftEdge();
				if (toSelect.equals(lastSelected))
					return;
				v.getLeftEdge().setSelected(!v.getLeftEdge().isSelected());
			}
		}	
	}


	public void createOverlay(int x, int y) {
		
		double xPos = x/(double)Edge.LENGTH;
		double yPos = y/(double)Edge.LENGTH;
				
		selectEdge(xPos,yPos);		
	}
	
	private void findLoops(Graphics g) {
	    TreeSet<Tile> toVisit = new TreeSet<Tile>();
	    Set<Tile> visited = new HashSet<Tile>();	    
	    toVisit.add(tiles[0][0]);
	    
	    while(!toVisit.isEmpty()) 
	    {
	        Set<Tile> traversed = new HashSet<Tile>();
	        findConnected(toVisit.first(), traversed, visited, toVisit);
	        paintTiles(traversed, g);        
	    } 	    
	}

	private void findConnected(Tile tile, Set<Tile> traversed, Set<Tile> visited, Set<Tile> toVisit) {
	    traversed.add(tile);
	    visited.add(tile);
	    toVisit.remove(tile);
	    
	    Tile top = tile.getTopTile();
	    Tile bottom = tile.getBottomTile();
	    Tile left = tile.getLeftTile();
	    Tile right = tile.getRightTile();
	    
	    if (null != top && !visited.contains(top)) {
	        toVisit.add(top);
	        if(!tile.getTopEdge().isActive()) {
	            findConnected(top, traversed, visited, toVisit);
	        }
	    }
	    if (null != bottom && !visited.contains(bottom)) {
	        toVisit.add(bottom);
	        if(!tile.getBottomEdge().isActive()) {
	            findConnected(bottom, traversed, visited, toVisit);
	        }
	    }
	    if (null != left && !visited.contains(left)) {
	        toVisit.add(left);
	        if(!tile.getLeftEdge().isActive()) {
	            findConnected(left, traversed, visited, toVisit);
	        }
	    }
	    if (null != right && !visited.contains(right)) {
	        toVisit.add(right);
	        if(!tile.getRightEdge().isActive()) {
	            findConnected(right, traversed, visited, toVisit);
	        }
	    }
	}
	
	
	private void paintTiles(Set<Tile> tiles, Graphics g) {
		BufferedImage image = ResourceLoader.createCompatible(Edge.LENGTH, Edge.LENGTH, BufferedImage.TYPE_INT_ARGB);
		Graphics g1 =image.getGraphics();
		g1.setColor(Color.red);
		g1.fillRect(0, 0, Edge.LENGTH, Edge.LENGTH);
		
		Image overlay = ResourceLoader.getImageWithOpacity(image, 0.3f);
		
		System.out.println("found loop of size: " + tiles.size());
		for (Tile t : tiles) {
			Vertex v = vertices[t.getX()][t.getY()];
			g.drawImage(overlay, v.getPosX(), v.getPosY(), null);
		}
	}	
} 
