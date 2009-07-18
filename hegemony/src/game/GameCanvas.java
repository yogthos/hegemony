package game;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	public BoardController.MODE currentMode;
	private BoardController board;
	private int size;
	
	public GameCanvas(BoardController board, int size) {
		this.board = board;
		this.size = size;
		setSize(size,size);		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (BoardController.MODE.PLACE_CASTLE == currentMode)
			board.placeCastle(e.getX(), e.getY());
		else if (BoardController.MODE.EXPAND_AREA == currentMode)
			board.expandTerritory(e.getX(), e.getY());
		else if (BoardController.MODE.PLACE_KNIGHT == currentMode)
			board.placeKnight(e.getX(), e.getY());
		else if (BoardController.MODE.PLACE_WALL == currentMode)
			board.placeEdge(e.getX(), e.getY());

	    e.consume();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {

		if (e.getX()/Edge.LENGTH > BoardController.size - 1 || e.getY()/Edge.LENGTH > BoardController.size - 1) {
			return;
		}
		
		if (BoardController.MODE.PLACE_WALL == currentMode){			
			board.createOverlay(e.getX(), e.getY());
			
		}
		e.consume();		
	}

}
