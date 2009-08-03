package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	private BoardRenderer renderer;
	
	public GameCanvas(GameController gameController, BoardRenderer renderer, int size) {
		this.gameController = gameController;
		this.renderer = renderer;	
		
		setSize(size,size);		
		addMouseListener(this);
		addMouseMotionListener(this);				
		setIgnoreRepaint(true);
	}
				
	public void draw() {
	
		renderer.setDrawOverlay(gameController.drawOverlay());
		BufferStrategy bufferStrategy = getBufferStrategy();
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(renderer.draw(), 0, 0, this);
		if (!bufferStrategy.contentsLost())
			bufferStrategy.show();	
		getToolkit().sync();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX() - GameCore.BOARD_OFFSET;
		int y = e.getY() - GameCore.BOARD_OFFSET;
		
		gameController.handleBoardAction(x, y, true);
	    e.consume();		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		int x = e.getX() - GameCore.BOARD_OFFSET;
		int y = e.getY() - GameCore.BOARD_OFFSET;
		gameController.handleBoardAction(x, y, false);		
		e.consume();		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
		
	@Override
	public void mouseDragged(MouseEvent e) {
	}			
}
