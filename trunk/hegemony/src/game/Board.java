package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Board {

	public static int size;
	
	Vertex[][] vertices = null;
	Tile[][] tiles = null;
	Image boardImage = null;

	private int tile_height = 0;
	private int tile_width = 0;
	
	public Board(int size) {
		Board.size = size;	
		tiles = new Tile[size][size];
		this.boardImage = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		generateEdgesAndVertices(size);
		
		tile_height = (GameCore.HEIGHT - (GameCore.HEIGHT%(size - 1)))/(size - 1);
		tile_width = (GameCore.HEIGHT - (GameCore.HEIGHT%(size - 1)))/(size - 1);
	}
	
	public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentBoard.getGraphics();		
		draw(vertices[0][0], g);		
		return currentBoard;		
	}
	
	private void draw(Vertex v, Graphics g) {
		g.drawImage(Vertex.draw(), v.getPosX(), v.getPosY(), null);
		
		if (v.getX() < size - 1 && v.getY() < size - 1) {			
			g.drawImage(tiles[v.getX()][v.getY()].draw(),v.getPosX(),v.getPosY(), null);
		}
		
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
	}
	
	public void update(int x, int y) {
		Graphics g = boardImage.getGraphics();
	}
	
	public void handleAction(int x, int y) {
		
		if (tile_height != 0 && tile_width != 0) {
			System.out.println("X tile: " + x/tile_width + ", Y tile: " + y/tile_height);	
		}
		System.out.println(x/(double)Edge.LENGTH + "," + y/(double)Edge.LENGTH);
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
} 
