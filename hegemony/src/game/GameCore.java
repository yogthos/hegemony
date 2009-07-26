package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.applet.Applet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameCore extends Applet implements Runnable {
	
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
		
		setLayout (new BorderLayout ());
		
		
		InfoPanel infoPanel = new InfoPanel();
	    add(infoPanel,BorderLayout.PAGE_START);
	    
		gameCanvas = new GameCanvas(board, BOARD_SIZE);						
		add (gameCanvas, BorderLayout.CENTER);

	    JPanel gamePanel = new ControlsPanel(gameCanvas, infoPanel); 
		add(gamePanel,BorderLayout.PAGE_END);
	    
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
		Graphics g = bufferStrategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(renderer.draw(), 0, 0, this);
		if (!bufferStrategy.contentsLost())
			bufferStrategy.show();	
		getToolkit().sync();
	}

	// Update any sprites, images, or primitives
	public void updateWorld(long time) {
		board.updateWorld();
	}
	
	// Do not override this method
	public void run() {
		setSize(WIDTH, HEIGHT);
		setBounds(0, 0, WIDTH, HEIGHT);
		setBackground(Color.black);

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
			
			// Update any sprites or other graphical objects
			updateWorld(elapsedTime);
			
			// Handle Drawing
			update();
		}	
	}
}
