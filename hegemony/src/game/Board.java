package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

public class Board {

	public static int size;
	
	private List<Tile> tiles;
	Vertex[][] vertices = null;
	Vertex root = null;
	
	Image boardImage = null;
	
	public Board(int size) {
		this.size = size;	
		this.boardImage = new BufferedImage(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);

		root = new Vertex(0,0);
		generateEdgesAndVertices(root, size);
	}
	
	public Image draw() {
		Image currentBoard = new BufferedImage(GameCore.WIDTH,GameCore.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentBoard.getGraphics();
			
		//draw(vertices[0][0], g);
		draw(root, g);
		return currentBoard;				
	}
	
	private void draw(Vertex v, Graphics g) {
		g.drawImage(Vertex.draw(), v.getPosX(), v.getPosY(), null);
		
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
	
	
	
	private void generateEdgesAndVertices(Vertex v, int size) {
		int x = v.getX();
		int y = v.getY();
		if (x < size - 1) {
			Vertex left = new Vertex(x +1, y);
			new Edge(v, left, false);
			generateEdgesAndVertices(left, size);
		}
		if (y < size - 1) {
			Vertex bottom = new Vertex(x, y + 1);
			new Edge(v, bottom, true);
			generateEdgesAndVertices(bottom, size);
		}
	}
	
	private void makeTile() {
		
	}
} 
