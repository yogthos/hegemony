package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.applet.Applet;

public class GameCore extends Applet implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int BOARD_SIZE = 550;
	public static final int BOARD_OFFSET = 30;
	
	public BoardController board = null;
	
	
	private GameCanvas gameCanvas;/* Drawing Canvas */
	private boolean stopped = false;/* True if the applet has been destroyed */
	
	public void init() {	
		ResourceManager.initialize();
		board = new BoardController(13, 2);			
		Thread t = new Thread(this);
		
		setLayout (new BorderLayout ());
				
		InfoPanel infoPanel = new InfoPanel(WIDTH - GameCore.BOARD_SIZE, GameCore.BOARD_SIZE);
		add(infoPanel,BorderLayout.WEST);
	    gameCanvas = new GameCanvas(new GameController(this, infoPanel, board), new BoardRenderer(board), BOARD_SIZE + BOARD_OFFSET);						
		add (gameCanvas, BorderLayout.CENTER);
	    	    			    
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
		gameCanvas.draw();
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
