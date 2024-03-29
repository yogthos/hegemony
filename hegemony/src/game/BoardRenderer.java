package game;

import gamepieces.GamePiece;
import gamepieces.OverlayPiece;
import gamepieces.Tree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tiles.Tile;

public class BoardRenderer {

	private BoardController board = null;
	private Image boardImage = null;
	private Image terrainImage = null;
	private boolean drawOverlay = false;
	private Map<Player,Image> playerOverlays = new HashMap<Player,Image>();
	
	public BoardRenderer(BoardController board) {
		this.board = board;
		for (Player player : board.getPlayers()) {
			playerOverlays.put(player, createOverlay(player.getColor()));
		}
		
		boardImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		terrainImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		drawTilesAndTerrain(board.getVertices()[0][0], boardImage.getGraphics(), terrainImage.getGraphics());
	}
		
	public Image draw() {
		
		Image currentBoard = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = currentBoard.getGraphics();
		g.drawImage(boardImage,0,0,null);

		if (drawOverlay) {
			BufferedImage highlightsImage = ResourceLoader.createCompatible(GameCore.BOARD_SIZE,GameCore.BOARD_SIZE, BufferedImage.TYPE_INT_ARGB);
			paintTiles(highlightsImage.getGraphics());
			g.drawImage(ResourceLoader.getImageWithOpacity(highlightsImage, 0.3f),0,0,null);
		}
		g.drawImage(terrainImage,0,0,null);
		
		Vertex[][] vertices = board.getVertices();
		draw(vertices, g);
		
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
	
	private void drawTileItems(Tile tile, Graphics g) {
		
		for (GamePiece piece : tile.getItems()) {						
			if (!(piece instanceof Tree))
				g.drawImage(piece.draw(),tile.getPosX(), tile.getPosY(), null);				
		}
	}
	
	private void draw(Vertex[][] vertices, Graphics g) {
		for (int x = 0; x < vertices.length; x ++) {
			for (int y = 0; y < vertices[x].length; y ++) {
																
				Vertex v = vertices[x][y];				
				Edge leftEdge = vertices[x][y].getLeftEdge();
				Edge bottomEdge = vertices[x][y].getBottomEdge();
				
																
				if (null != leftEdge && (leftEdge.isActive() || leftEdge.isSelected())) {
					g.drawImage(leftEdge.draw(),leftEdge.getPosX() - Vertex.SIZE, leftEdge.getPosY() - Vertex.SIZE*2, null);
				}
				
				if (v.hasActiveEdges()) {
					g.drawImage(v.draw(), v.getPosX() - Vertex.SIZE*2, v.getPosY() - Vertex.SIZE*2, null);
				}
				
				if (x < vertices.length -1 && y < vertices[x].length -1) {
					Tile[][] tiles = board.getTiles();
					Tile tile = tiles[x][y];
					GamePiece tree = tile.getForest();
					if (null != tree) {
						g.drawImage(tree.draw(),tiles[x][y].getPosX(), tiles[x][y].getPosY(), null);
					}
					drawTileItems(tile,g);
				}
				if (null != bottomEdge && (bottomEdge.isActive() || bottomEdge.isSelected())) {
					g.drawImage(bottomEdge.draw(),bottomEdge.getPosX() - Vertex.SIZE, bottomEdge.getPosY() - Vertex.SIZE*2, null);
				}								
			}			
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
					g.drawImage(playerOverlays.get(p), v.getPosX(), v.getPosY(), null);
				}
			}			
		}
	}
	
	private Image createOverlay(Color color) {
						
		BufferedImage image = ResourceLoader.createCompatible(Edge.LENGTH, Edge.LENGTH, BufferedImage.TYPE_INT_ARGB);
		Graphics g =image.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Edge.LENGTH, Edge.LENGTH);
		
		return image;
	}

	public void setDrawOverlay(boolean drawOverlay) {
		this.drawOverlay = drawOverlay;
	}

	public boolean isDrawOverlay() {
		return drawOverlay;
	}
	
}
