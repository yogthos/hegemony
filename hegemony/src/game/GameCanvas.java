package game;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private BoardController board;
	private InfoPanel infoPanel;
	private ControlsPanel controlsPanel;
	
	public GameCanvas(InfoPanel infoPanel, ControlsPanel controlsPanel, BoardController board, int size) {
		this.infoPanel = infoPanel;
		this.controlsPanel = controlsPanel;
		this.board = board;
		setSize(size,size);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		controlsPanel.initSetupPhase();
		board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
		setIgnoreRepaint(true);
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

	
	private boolean moreCastlesToPlace() {
		boolean castlesPlaced = false;
		for (Player player : board.getPlayers()) {
			if (player.getCastlesRemainig() > 0) {
				castlesPlaced = true;
				break;
			}								
		}
		return castlesPlaced;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX() - GameCore.BOARD_OFFSET;
		int y = e.getY() - GameCore.BOARD_OFFSET;
		
		
		boolean result = board.handlePlayerAction(x, y, true);
		infoPanel.updatePlayer(board.getCurrentPlayer(), board.getCurrentTurn());
		if (board.getCurrentMode() == BoardController.MODE.PLACE_CASTLE && result) {
			
			board.setCurrentMode(BoardController.MODE.PLACE_KNIGHT_SIMPLE);
		}
		else if (board.getCurrentMode() == BoardController.MODE.PLACE_KNIGHT_SIMPLE && result) {
			board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
			if (moreCastlesToPlace()) board.updateCurrentTurn();
			else {
				board.setCurrentMode(BoardController.MODE.EMPTY);
				controlsPanel.initMainPhase();
			}
		}
		if (result && BoardController.GamePhase.MAIN == controlsPanel.getPhase()) {
			board.updateCurrentTurn();
		}
	    e.consume();		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		int x = e.getX() - GameCore.BOARD_OFFSET;
		int y = e.getY() - GameCore.BOARD_OFFSET;
		if (x/Edge.LENGTH > BoardController.size - 1 || y/Edge.LENGTH > BoardController.size - 1) {
			return;
		}
		board.handlePlayerAction(x, y, false);		
		e.consume();		
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}	
}
