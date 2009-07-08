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
		tiles = new Tile[size][size];
		this.boardImage = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		generateEdgesAndVertices(size);
	}
	
	public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentBoard.getGraphics();		
		draw(vertices[0][0], g);		
		return currentBoard;		
	}
	
	private void draw(Vertex v, Graphics g) {
		g.drawImage(Vertex.draw(), v.getPosX(), v.getPosY(), null);
		
		if (v.getX() < size && v.getY() < size) {			
			g.drawImage(tiles[v.getX()][v.getY()].draw(),v.getPosX(),v.getPosY(), null);
		}
		
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {			
			g.drawImage(leftEdge.draw(),leftEdge.getPosX(), leftEdge.getPosY(), null);
			draw(leftEdge.getSecond(), g);
		}
		
		Edge bottomEdge = v.getBottomEdge();
		if (null != bottomEdge) {			
			g.drawImage(bottomEdge.draw(),bottomEdge.getPosX(), bottomEdge.getPosY() , null);		
			draw(bottomEdge.getSecond(), g);
		}		
	}
	
	public void update(int x, int y) {
		Graphics g = boardImage.getGraphics();
	}
	
	public void handleAction(int x, int y) {
		
		System.out.println(x/(double)Edge.LENGTH + "," + y/(double)Edge.LENGTH);
	}
	
	private void generateEdgesAndVertices(Vertex v, int size) {
		int x = v.getX();
		int y = v.getY();
		
		System.out.println("adding tile: " + x + "," + y);
		tiles[x][y] = new Tile(Tile.Type.FOREST);
		
		if (x < size - 1) {
			
			Vertex left = new Vertex(x + 1, y);
			new Edge(v, left, false);			
			generateEdgesAndVertices(left, size);
			
		}
		if (y < size - 1) {
			Vertex bottom = new Vertex(x, y + 1);
			new Edge(v, bottom, true);
			generateEdgesAndVertices(bottom, size);
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
				
				tiles[i][j] = new Tile(Tile.Type.FOREST);
			}
		}					
	}	
} 
