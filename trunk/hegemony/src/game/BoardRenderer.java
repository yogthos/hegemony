package game;

import gamepieces.GamePiece;
import gamepieces.OverlayPiece;
import gamepieces.Tree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tiles.Tile;

public class BoardRenderer {

	private BoardController board;
	private Image boardImage = null;
	private Image terrainImage = null;
	
	public BoardRenderer(BoardController board) {
		this.board = board;
		boardImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		terrainImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		drawTilesAndTerrain(board.getVertices()[0][0], boardImage.getGraphics(), terrainImage.getGraphics());
	}
		
public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = currentBoard.getGraphics();
		g.drawImage(boardImage,0,0,null);

		Image highlightsImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		paintTiles(highlightsImage.getGraphics());
		g.drawImage(highlightsImage,0,0,null);
		
		Vertex[][] vertices = board.getVertices();
		draw(vertices[0][0], g);
		g.drawImage(terrainImage,0,0,null);
		drawTileItems(g);
			
		drawHighlightedPiece(g);
		return currentBoard;		
	}
	
	private void drawTilesAndTerrain(Vertex v, Graphics boardG, Graphics terrainG) {
		
		drawTileTerrainColumn(v, boardG, terrainG);
		
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {			
			drawTilesAndTerrain(leftEdge.getSecond(), boardG, terrainG);			
		}				
	}
	
	private void drawHighlightedPiece(Graphics g) {
		
		OverlayPiece overlay = board.getCurrentPiece();
		if (overlay.isActive())
			g.drawImage(overlay.draw(), overlay.getX(), overlay.getY(), null);
	}
	
	private void drawTileTerrainColumn(Vertex v, Graphics boardG, Graphics terrainG) {
		int x = v.getX();
		int y = v.getY();
		int posX = v.getPosX();
		int posY = v.getPosY();

		Tile[][] tiles = board.getTiles();
		int size = BoardController.size;
		if (x < size - 1 && y < size - 1) {
			boardG.drawImage(tiles[x][y].draw(), posX, posY, null);
			if (null != tiles[x][y].getForest())
				terrainG.drawImage(tiles[x][y].getForest().draw(), posX, posY, null);
			if (null != tiles[x][y].getMine())
				terrainG.drawImage(tiles[x][y].getMine().draw(), posX, posY, null);			
			if (null != tiles[x][y].getCapital())
				terrainG.drawImage(tiles[x][y].getCapital().draw(), posX, posY, null);
			if (null != tiles[x][y].getVillage())
				terrainG.drawImage(tiles[x][y].getVillage().draw(), posX, posY, null);
			
			Edge bottomEdge = v.getBottomEdge();
			if (null != bottomEdge) {
				drawTileTerrainColumn(bottomEdge.getSecond(), boardG, terrainG);
			}
		}
	}

	private void drawTileItems(Graphics g) {
		Tile[][] tiles = board.getTiles();
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles.length; y++) {
				
				for (GamePiece piece : tiles[x][y].getItems()) {
					if (!(piece instanceof Tree))
						g.drawImage(piece.draw(),tiles[x][y].getPosX(), tiles[x][y].getPosY(), null);
				}
				
			}
		}
	}
	
	private void draw(Vertex v, Graphics g) {
		
			
		drawEdgeColumn(v, g);
		Edge leftEdge = v.getLeftEdge(); 
		if (null != leftEdge) {
			draw(leftEdge.getSecond(), g);
		}
	}
	
	
	private void drawEdgeColumn(Vertex v, Graphics g) {
		Edge leftEdge = v.getLeftEdge();
		if (null != leftEdge) {
			if ((leftEdge.isActive() || leftEdge.isSelected())) {
				g.drawImage(leftEdge.draw(),leftEdge.getPosX() - Vertex.SIZE, leftEdge.getPosY() - Vertex.SIZE*2, null);
			}
		}
		
		if (v.hasActiveEdges()) {
			g.drawImage(Vertex.draw(), v.getPosX() - Vertex.SIZE*2, v.getPosY() - Vertex.SIZE*2, null);
		}
		
		Edge bottomEdge = v.getBottomEdge();
		if (null != bottomEdge) {
			if ((bottomEdge.isActive() || bottomEdge.isSelected())) {
				g.drawImage(bottomEdge.draw(),bottomEdge.getPosX()  - Vertex.SIZE, bottomEdge.getPosY() - Vertex.SIZE, null);
			}
			drawEdgeColumn(bottomEdge.getSecond(), g);
		}	
	}
	
	private void paintTiles(Graphics g) {

		Map<Player, List<Set<Tile>>> currentAreas = board.getPlayerAreas();

		Vertex[][] vertices = board.getVertices();
		
		for (Player p : currentAreas.keySet()) {
			List<Set<Tile>> playerAreas = currentAreas.get(p);
			if (null == playerAreas)
				continue;
			for (Set<Tile> tiles : playerAreas) {
				for (Tile t : tiles) {
					Vertex v = vertices[t.getX()][t.getY()];
					g.drawImage(createOverlay(p.getColor()), v.getPosX(), v.getPosY(), null);
				}
			}			
		}
	}
	
	private Image createOverlay(Color color) {
						
		BufferedImage image = ResourceLoader.createCompatible(Edge.LENGTH, Edge.LENGTH, BufferedImage.TYPE_INT_ARGB);
		Graphics g =image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Edge.LENGTH, Edge.LENGTH);
		
		return ResourceLoader.getImageWithOpacity(image, 0.3f);
	}
	
}
