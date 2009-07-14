package game;

import gamepieces.GamePiece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tiles.Tile;
import tiles.WoodTile;

public class Board {

	public static int size;
	
	private Vertex[][] vertices = null;
	private Tile[][] tiles = null;
	private Image boardImage = null;
	private Image terrainImage = null;
	private Image highlightsImage = null;
	private Edge lastSelected;
	
	public Board(int size) {
		Board.size = size;	
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
		for (int i = 0; i < vertices.length; i++) {
			for (int j = 0; j < vertices[i].length; j++) {
				if (i < vertices.length - 1) {					
					new Edge(vertices[i][j], vertices[i + 1][j], false);								
				}
				if (j < vertices[i].length - 1) {					
					new Edge(vertices[i][j], vertices[i][j + 1], true);
				}
				
				if (i < vertices.length - 1 && j < vertices[i].length - 1) {
					//tiles[i][j] = new GrassTile();
					tiles[i][j] = new WoodTile();
				}
			}
		}

	}
	
	public void update(int x, int y) {
		Graphics g = boardImage.getGraphics();
	}
	
	public void handleAction(int x, int y) {
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
	
	private void drawLoop(Set<Vertex> path, Graphics g) {
		Image overlay = ResourceLoader.getImageWithOpacity(ResourceLoader.createCompatible(Edge.LENGTH, Edge.LENGTH, BufferedImage.TYPE_INT_ARGB), 0.4f);
		Graphics g1 = overlay.getGraphics();
		g1.setColor(Color.red);
		g1.fillRect(0, 0, Edge.LENGTH, Edge.LENGTH);
		
		Vertex topLeft;
		Vertex topRight;
		Vertex bottomLeft;
		Vertex bottomRight;
		
		System.out.println("Found loop: ");
		for (Vertex v : path) {
			System.out.println(v.getX() + "," + v.getY());
			g.drawImage(overlay, v.getPosX(), v.getPosY(), null);
		}
		
		
	}
	
	private void findLoops(Graphics g) {
		Set<Vertex> traversed = new HashSet<Vertex>();		
		for (int x = 0; x < vertices.length; x++) {
			for (int y = 0; y < vertices.length; y++) {
				Vertex v = vertices[x][y];		
				
				if (!traversed.contains(v) && isTraversable(v.getLeftEdge())) {
					findLoops(null, v, null, new HashSet<Vertex>(), traversed, g);					
				}
			}
		}		
	}

	private boolean isTraversable(Edge e) {
		return null != e && e.isActive();
	}
		
	private boolean isDeadEnd(Vertex v) {
		int numTraversable = 
			(isTraversable(v.getBottomEdge()) ? 1 : 0) +
			(isTraversable(v.getLeftEdge()) ? 1 : 0) +
			(isTraversable(v.getRightEdge()) ? 1 : 0) +
			(isTraversable(v.getTopEdge()) ? 1 : 0);
		return numTraversable < 2 ? true : false;
		
	}

	private void findLoops(Vertex root, Vertex v, Edge incoming, Set<Vertex> traversed, Set<Vertex> traversals, Graphics g) {

		boolean rootVertex = false;
		if (null == root) {
			root = v;
			rootVertex = true;
		}		
		else if (root.equals(v)) {
			drawLoop(traversed, g);
			return;
		}
		else if (traversed.contains(v)) {
			return;
		}

		traversals.add(v);
		Set<Vertex> myTraversed = new HashSet<Vertex>();
		myTraversed.addAll(traversed);
		myTraversed.add(v);				
		
		Edge leftEdge = v.getLeftEdge();
		Edge topEdge = v.getTopEdge();
		Edge bottomEdge = v.getBottomEdge();
		Edge rightEdge = v.getRightEdge();
		
		if (isTraversable(leftEdge) && !isDeadEnd(leftEdge.getSecond())) {
			if (!leftEdge.equals(incoming))
				findLoops(root, leftEdge.getSecond(), leftEdge, myTraversed, traversals, g);			
		}
		if(isTraversable(topEdge) && !isDeadEnd(topEdge.getFirst())) {
			if (!topEdge.equals(incoming))
				findLoops(root, topEdge.getFirst(), topEdge, myTraversed, traversals, g);
		}
		if(isTraversable(bottomEdge) && !isDeadEnd(bottomEdge.getSecond()) && !rootVertex) {
			if (!bottomEdge.equals(incoming))
				findLoops(root, bottomEdge.getSecond(), bottomEdge, myTraversed, traversals, g);
		}
		if(isTraversable(rightEdge) && !isDeadEnd(rightEdge.getFirst())) {
			if (!rightEdge.equals(incoming))
				findLoops(root, rightEdge.getFirst(), rightEdge, myTraversed, traversals, g);
		}		
	}		
} 
