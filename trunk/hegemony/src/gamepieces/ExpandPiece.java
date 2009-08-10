package gamepieces;

import game.Edge;
import game.ResourceLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ExpandPiece extends GamePiece {

	private BufferedImage image = ResourceLoader.createCompatible(Edge.LENGTH,Edge.LENGTH, BufferedImage.TYPE_INT_ARGB);
	public ExpandPiece(Color color) {		
		super(null);
		Graphics  g = image.getGraphics();
		g.setColor(color);
		g.fillRoundRect(0, 0, Edge.LENGTH, Edge.LENGTH, 3, 3);
	}
	
	@Override
	public int getValue() {
		return 0;
	}
	
	@Override
	public BufferedImage draw() {
		return image;
	}

	@Override
	public void act() {
	}
}
