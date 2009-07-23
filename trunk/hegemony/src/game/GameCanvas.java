package game;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private BoardController board;
	
	public GameCanvas(BoardController board, int size) {
		this.board = board;
		setSize(size,size);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		board.setCurrentMode(BoardController.MODE.PLACE_WALL);
	}
	
	public void setCurrentMode(BoardController.MODE mode) {
		board.setCurrentMode(mode);
	}
	
	public BoardController getBoard() {
		return board;
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
		board.handlePlayerAction(e.getX(), e.getY(), true);
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
		board.handlePlayerAction(e.getX(), e.getY(), false);
		e.consume();		
	}

}
