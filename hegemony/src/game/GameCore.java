package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.applet.Applet;

public class GameCore extends Applet implements Runnable, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int BOARD_SIZE = 550;
	
	public BoardController board = null;
	public BoardRenderer renderer = null;
	private BufferStrategy bufferStrategy;
	private GameCanvas gameCanvas;/* Drawing Canvas */
	private boolean stopped = false;/* True if the applet has been destroyed */
	
	public void init() {						
		board = new BoardController(12, 2);	
		renderer = new BoardRenderer(board);
		Thread t = new Thread(this);
		
		gameCanvas = new GameCanvas(board, BOARD_SIZE);						
		
		setIgnoreRepaint(true);
		t.start();
	}
	
	public void updateCurrentTurn() {
		board.updateCurrentTurn();
	}

	public void destroy() {
		stopped = true;

		/* Allow Applet to destroy any resources used by this applet */
		super.destroy();
	}

	public void update() {
		if (!bufferStrategy.contentsLost()) {
			// Show bufferStrategy
			bufferStrategy.show();
		}
	}

	public void update(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(renderer.draw(), 0, 0, this);
		getToolkit().sync();
	}

	// Update any sprites, images, or primitives
	public void updateWorld(long time) {
		board.updateWorld();
	}

	public Graphics2D getGraphics() {				
		return (Graphics2D) bufferStrategy.getDrawGraphics();
	}

	// Do not override this method
	public void run() {
		setSize(WIDTH, HEIGHT);
		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(Color.black);
		
				
		setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		//javax.swing.JPanel gamePanel = new javax.swing.JPanel();
		//gamePanel.add(gameCanvas);
		//add(gamePanel);		
		//add(new ControlsPanel(gameCanvas));
		
	    
		add(gameCanvas);					
		new ControlsPanel(gameCanvas);
		

		gameCanvas.createBufferStrategy(2);
		bufferStrategy = gameCanvas.getBufferStrategy();
		
		long startTime = System.currentTimeMillis();
		long currTime = startTime;

		// animation loop
		while (!stopped) {
			//TODO: check phases
			
			// Get time past
			long elapsedTime = System.currentTimeMillis() - currTime;
			currTime += elapsedTime;

			// Flip or show the back buffer
			update();

			// Update any sprites or other graphical objects
			updateWorld(elapsedTime);

			// Handle Drawing
			Graphics2D g = getGraphics();
			update(g);

			// Dispose of graphics context
			g.dispose();
		}		
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
}
