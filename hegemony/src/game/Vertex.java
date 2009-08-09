package game;

import java.awt.image.BufferedImage;

import gamepieces.GamePiece;


public class Vertex extends GamePiece {
	
	public static int SIZE = 4;
	
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
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getPosX() {
		return x*Edge.LENGTH + GameCore.BOARD_OFFSET;
	}
	
	public int getPosY() {
		return y*Edge.LENGTH + GameCore.BOARD_OFFSET;
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

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public void act() {
		ResourceManager.TOWER.updateFrame();
		ResourceManager.TOWER.playSound();
	}

	@Override
	public BufferedImage draw() {
		return ResourceManager.TOWER.getSprite();
	}
}
