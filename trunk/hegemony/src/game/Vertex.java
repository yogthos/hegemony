package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Vertex {
	
	public static int SIZE = 4;
	private static final String[] sprites = {"tower.png"};
	
	
	private int x;
	private int y;
	
	private Edge topEdge = null;
	private Edge bottomEdge = null;
	private Edge leftEdge = null;
	private Edge rightEdge = null;	
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;				
	}
	
	public Edge getTopEdge() {
		return topEdge;
	}

	public void setTopEdge(Edge topEdge) {
		this.topEdge = topEdge;
	}

	public Edge getBottomEdge() {
		return bottomEdge;
	}

	public void setBottomEdge(Edge bottomEdge) {
		this.bottomEdge = bottomEdge;
	}

	public Edge getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(Edge leftEdge) {
		this.leftEdge = leftEdge;
	}

	public Edge getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(Edge rightEdge) {
		this.rightEdge = rightEdge;
	}
	
	public static Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getPosX() {
		return x*Edge.LENGTH;
	}
	
	public int getPosY() {
		return y*Edge.LENGTH;
	}
	
	public boolean hasActiveEdges() {
		boolean activeEdges = false;
		if (null != leftEdge) {
			if (leftEdge.isActive()) {
				activeEdges = true;
			}
		}
		if (null != rightEdge) {
			if (rightEdge.isActive()) {
				activeEdges = true;
			}
		}
		if (null != bottomEdge) {
			if (bottomEdge.isActive()) {
				activeEdges = true;
			}
		}
		if (null != topEdge) {
			if (topEdge.isActive()) {
				activeEdges = true;
			}
		}
		
		return activeEdges;			
	}
	
	public String toString() {
			return "[" + x + "," + y + "]";
	}
}
