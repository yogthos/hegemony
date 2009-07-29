package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.applet.Applet;

public class GameCore extends Applet implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int BOARD_SIZE = 550;
	public static final int BOARD_OFFSET = 30;
	
	public BoardController board = null;
	public BoardRenderer renderer = null;
	private BufferStrategy bufferStrategy;
	private GameCanvas gameCanvas;/* Drawing Canvas */
	private boolean stopped = false;/* True if the applet has been destroyed */
	
	public void init() {	
		ResourceManager.initialize();
		board = new BoardController(13, 2);	
		renderer = new BoardRenderer(board);
		Thread t = new Thread(this);
		
		setLayout (new BorderLayout ());
		
		

		InfoPanel infoPanel = new InfoPanel(WIDTH - BOARD_SIZE, BOARD_SIZE);
	    add(infoPanel,BorderLayout.WEST);
	    
	    ControlsPanel controlPanel = new ControlsPanel(this, board, infoPanel);
	    
	    gameCanvas = new GameCanvas(infoPanel, controlPanel, board, BOARD_SIZE + BOARD_OFFSET);						
		add (gameCanvas, BorderLayout.CENTER);
	    	    
		add(controlPanel, BorderLayout.PAGE_END);
	    
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
