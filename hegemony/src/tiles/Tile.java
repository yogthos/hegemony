package tiles;

import game.Edge;
import game.ResourceLoader;
import gamepieces.GamePiece;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class Tile implements Comparable<Tile> {

	protected static String[] sprites = {"grass.png"};
	
	static {
		for (String sprite : sprites) {
			ResourceLoader.getInstance().getSprite(sprite);
		}
	}
	
	protected List<GamePiece> items = new ArrayList<GamePiece>();
	
	private int x;
	private int y;
	private Edge topEdge;	
	private Edge bottomEdge;
	private Edge leftEdge;
	private Edge rightEdge;
	
	private Tile topTile;
	private Tile bottomTile;
	private Tile leftTile;
	private Tile rightTile;
	
	public enum Type {
		FOREST,
		GRASS,
		VILLAGE,
		GOLD,
		SILVER,
		COPPER,
		DIAMOND,
		CAPITAL	
	}

	public abstract Type getType();
	public abstract int getValue();
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Image draw() {
		return ResourceLoader.getInstance().getSprite(sprites[0]);
	}
	
	public List<GamePiece> getItems() {
		return items;
	}	
	
	public void placeItem(GamePiece piece) {
		items.add(piece);
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
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public Tile getTopTile() {
		return topTile;
	}
	public void setTopTile(Tile topTile) {
		this.topTile = topTile;
	}
	public Tile getBottomTile() {
		return bottomTile;
	}
	public void setBottomTile(Tile bottomTile) {
		this.bottomTile = bottomTile;
	}
	public Tile getLeftTile() {
		return leftTile;
	}
	public void setLeftTile(Tile leftTile) {
		this.leftTile = leftTile;
	}
	public Tile getRightTile() {
		return rightTile;
	}
	public void setRightTile(Tile rightTile) {
		this.rightTile = rightTile;
	}
	
	public String toString() {
		return "Top edge: " + topEdge.toString() + 
		"\nBottom edge: " + bottomEdge.toString() +
		"\nLeft edge: " + leftEdge.toString() +
		"\nRight edge: " + rightEdge.toString();
	}
	
	@Override
	public int compareTo(Tile o) {
		if (this == o)
			return 0;
		else return this.hashCode() - o.hashCode();
	}
	
}
