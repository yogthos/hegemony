package game;

import gamepieces.GamePiece;

import java.awt.image.BufferedImage;


public class Edge extends GamePiece {
			
	public static int LENGTH;
	Vertex first = null;
	Vertex second = null;
	private int x;
	private int y;
	
	private boolean vertical = false;
	private boolean active = false;
	private boolean selected = false;
	
	static {
		LENGTH = GameCore.BOARD_SIZE/BoardController.size;
	}
	
	public Edge(Vertex first, Vertex second, boolean vertical) {

		super(vertical?ResourceManager.V_WALL.getMediaController():ResourceManager.H_WALL.getMediaController()); 
		
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
		if (active) {
			if (vertical)
				mc.playSound();
			else
				mc.playSound();
		}
		this.active = active;
	}
	
	public BufferedImage draw() {

		
		return (vertical ? 
				(selected && !active?
						mc.getSprite(0.5f):
						mc.getSprite()): 
					(selected && !active?
							mc.getSprite(0.5f):
							mc.getSprite()));	    						
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public String toString() {
		return "[" + first.getX() + "," + first.getY() + "]-" + (active?"active":"inactive") + "->[" + second.getX() + "," + second.getY() + "]";
	}

	@Override
	public int getValue() {		
		return 0;
	}

	@Override
	public void act() {
		if (vertical) {
			mc.updateFrame();
			mc.playSound();
		} else {
			mc.updateFrame();
			mc.playSound();
		}
	}	
}
