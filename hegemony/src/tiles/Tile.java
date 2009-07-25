package tiles;

import game.Edge;
import game.ResourceLoader;
import gamepieces.GamePiece;
import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

public abstract class Tile implements Comparable<Tile> {

	protected static String[] sprites = {"grass.png"};
	
	static {
		for (String sprite : sprites) {
			ResourceLoader.INSTANCE.getSprite(sprite);
		}
	}
		
	protected int x;
	protected int y;
	protected Edge topEdge;	
	protected Edge bottomEdge;
	protected Edge leftEdge;
	protected Edge rightEdge;
	
	protected Tile topTile;
	protected Tile bottomTile;
	protected Tile leftTile;
	protected Tile rightTile;
	
	protected GamePiece castle = null;
	protected GamePiece knight = null;
	protected GamePiece forest = null;
	protected GamePiece village = null;
	protected GamePiece mine = null;
	protected GamePiece capital = null;
	
	protected Set<GamePiece> items = new HashSet<GamePiece>();
	
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
	public int getValue() {
		int value = 0;
		for (GamePiece item : items) {
			value += item.getValue();	
		}
		
		return value;
	}
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Image draw() {
		return ResourceLoader.INSTANCE.getSprite(sprites[0]);
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
	
	
	
	public GamePiece getCastle() {
		return castle;
	}
	public void setCastle(GamePiece castle) {
		items.add(castle);
		this.castle = castle;
	}
	public GamePiece getKnight() {		
		return knight;
	}
	public void setKnight(GamePiece knight) {
		items.add(knight);
		this.knight = knight;
	}
	
	public void removeKnight() {
		items.remove(knight);
		knight = null;		
	}
	
	public GamePiece getForest() {
		return forest;
	}
	public void setForest(GamePiece forest) {
		items.add(forest);
		this.forest = forest;
	}
	public GamePiece getVillage() {		
		return village;
	}
	public void setVillage(GamePiece village) {
		items.add(village);
		this.village = village;
	}
	public GamePiece getMine() {
		return mine;
	}
	public void setMine(GamePiece mine) {
		items.add(mine);
		this.mine = mine;
	}
	public GamePiece getCapital() {
		return capital;
	}
	
	public void setCapital(GamePiece capital) {
		items.add(capital);
		this.capital = capital;
	}
	
	public Set<GamePiece> getItems() {
		return items;
	}
	
	public int getPosX() {
		return x*Edge.LENGTH;
	}
	
	public int getPosY() {
		return y*Edge.LENGTH;
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
