package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Edge {
	
	public static int LENGTH;
	Vertex first = null;
	Vertex second = null;
	private int x;
	private int y;
	
	boolean vertical = false;
	boolean active = false;
	
	static {
		LENGTH = GameCore.HEIGHT/Board.size;
	}
	
	private static Image edgeHImage = new BufferedImage(LENGTH,LENGTH, BufferedImage.TYPE_INT_ARGB);
	private static Image edgeVImage = new BufferedImage(LENGTH,LENGTH, BufferedImage.TYPE_INT_ARGB);
	
	static {		
		Graphics g = edgeHImage.getGraphics();
		g.setColor(Color.green);
		g.drawLine(0, Vertex.SIZE/2, LENGTH, Vertex.SIZE/2);
		
		g = edgeVImage.getGraphics();
		g.setColor(Color.green);
		g.drawLine(0, Vertex.SIZE/2, Vertex.SIZE/2, LENGTH);
	} 

	
	public Edge(Vertex first, Vertex second, boolean vertical) {
		this.first = first;
		this.second = second;
		this.vertical = vertical;
		
		if (vertical) {
			first.setBottomEdge(this);
			second.setTopEdge(this);
		} else {
			first.setLeftEdge(this);
			second.setRightEdge(this);
		}
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}

	public int getPosX() {
		
		return first.getPosX(); 	
	}
	
	public int getPosY() {
		return first.getPosY();
	}
	
	public Vertex getFirst() {
		return first;
	}

	public Vertex getSecond() {
		return second;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Image draw() {
		return (vertical ? edgeVImage : edgeHImage);
	}
}
