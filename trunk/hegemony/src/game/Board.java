package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Board {

	public static int size;
	
	Vertex[][] vertices = null;
	Tile[][] tiles = null;
	Image boardImage = null;
	
	public Board(int size) {
		Board.size = size;	
		tiles = new Tile[size-1][size-1];
		boardImage = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		generateEdgesAndVertices(size);
		drawTiles(vertices[0][0], boardImage.getGraphics());
	}
	
	public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentBoard.getGraphics();
		g.drawImage(boardImage,size,size,null);
		draw(vertices[0][0], g);
		
		return currentBoard;		
	}
	
	private void drawTiles(Vertex v, Graphics g) {
		if (v.getX() < size - 1 && v.getY() < size - 1) {			
			g.drawImage(tiles[v.getX()][v.getY()].draw(),v.getPosX(),v.getPosY(), null);
		}
		
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {
			drawTiles(leftEdge.getSecond(), g);
		}
		
		Edge bottomEdge = v.getBottomEdge();
		if (null != bottomEdge) {
			drawTiles(bottomEdge.getSecond(), g);
		}
	}
	
	private void draw(Vertex v, Graphics g) {
										
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {			
			if (leftEdge.isActive()) {
				g.drawImage(leftEdge.draw(),leftEdge.getPosX(), leftEdge.getPosY(), null);
			}
			draw(leftEdge.getSecond(), g);
		}
		
		Edge bottomEdge = v.getBottomEdge();
		if (null != bottomEdge) {
			if (bottomEdge.isActive()) {
				g.drawImage(bottomEdge.draw(),bottomEdge.getPosX(), bottomEdge.getPosY() , null);
			}
			draw(bottomEdge.getSecond(), g);
		}	
		
		if (v.hasActiveEdges()) {
			g.drawImage(v.draw(), v.getPosX() - v.SIZE/2, v.getPosY() - v.SIZE/2, null);
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
					tiles[i][j] = new Tile(Tile.Type.FOREST);
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
		System.out.println(xPos + "," + yPos);
		findEdge(xPos,yPos);
	}
	
	private void findEdge(double x, double y){
		int xi = (int)x;
		int yi = (int)y;

		Vertex v = vertices[xi][yi];
		double xOffset = x - xi;
		double yOffset = y - yi;
		System.out.println(xOffset + ","+yOffset);
		if (Math.abs(0.5 - xOffset) > Math.abs(0.5 - yOffset)) {
			if (0.5 > xOffset) {
				v.getBottomEdge().setActive(!v.getBottomEdge().isActive());				
			} else {				
				Vertex v1 = v.getLeftEdge().getSecond(); 
				v1.getBottomEdge().setActive(!v1.getBottomEdge().isActive());
			}
		}
		else {
			if (yOffset > 0.5) {			
				Vertex v1 = v.getBottomEdge().getSecond();
				v1.getLeftEdge().setActive(!v1.getLeftEdge().isActive());
			} else {
				v.getLeftEdge().setActive(!v.getLeftEdge().isActive());
			}
		}	
	}
} 
