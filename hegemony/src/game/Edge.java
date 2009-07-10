package game;

import java.awt.Image;


public class Edge {
		
	protected static String[] sprites = {"wallv.png","wallh.png"};
	protected static String placeSound = "place_wall.wav";
	
	public static int LENGTH;
	Vertex first = null;
	Vertex second = null;
	private int x;
	private int y;
	
	private boolean vertical = false;
	private boolean active = false;
	private boolean selected = false;
	
	static {
		LENGTH = GameCore.HEIGHT/(Board.size - 1);
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
		if (active)
			ResourceLoader.getInstance().getSound(placeSound).play();
		
		this.active = active;
	}
	
	public Image draw() {

		//return (vertical ? ResourceLoader.getInstance().getSprite(sprites[0]) : ResourceLoader.getInstance().getSprite(sprites[1]));

		
		return (vertical ? 
				(selected?
						ResourceLoader.getInstance().getSprite(sprites[0], 0.5f):
						ResourceLoader.getInstance().getSprite(sprites[0])): 
				(selected?
						ResourceLoader.getInstance().getSprite(sprites[1], 0.5f):
						ResourceLoader.getInstance().getSprite(sprites[1])));
	    
						
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
