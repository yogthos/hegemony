package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.applet.Applet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameCore extends Applet implements Runnable, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;

	public static final int BOARD_SIZE = 400;	

	public BoardController board = null;
	
	private Player[] players = null;
	private int turn;
	
	private BufferStrategy bufferStrategy;
	private GameCanvas drawArea;/* Drawing Canvas */
	private boolean stopped = false;/* True if the applet has been destroyed */
	
	public void init() {
		
		players = new Player[4];
		turn = 0;
		
		board = new BoardController(12);		
		Thread t = new Thread(this);
		
		drawArea = new GameCanvas(board, BOARD_SIZE);
		drawArea.currentMode = BoardController.MODE.PLACE_WALL;		
		
		setIgnoreRepaint(true);
		t.start();
	}
	
	public void updateCurrentTurn() {
		turn = (turn + 1) % players.length;
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

	// Return drawArea's BufferStrategy
	public BufferStrategy getBufferStrategy() {
		return bufferStrategy;
	}

	// Create drawArea's BufferStrategies
	public void createBufferStrategy(int numBuffers) {
		drawArea.createBufferStrategy(numBuffers);
	}

	// Subclasses should override this method to do any drawing
	public void draw(Graphics2D g) {

	}

	public void update(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(board.draw(), 0, 0, this);
		getToolkit().sync();
	}

	// Update any sprites, images, or primitives
	public void update(long time) {

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
		add(drawArea);				
	    add(new java.awt.Button("OK"));
		
	    /*
		add(drawArea, BorderLayout.CENTER);		
				
		JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());
				
        controls.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
        controls.add(new ModeButton(BoardController.MODE.PLACE_WALL));
	    add(controls, BorderLayout.SOUTH);
	    controls.setVisible(true);
	    //setVisible(true);
		*/
	    
		createBufferStrategy(2);
		bufferStrategy = drawArea.getBufferStrategy();

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
			update(elapsedTime);

			// Handle Drawing
			Graphics2D g = getGraphics();
			update(g);
			draw(g);

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

	private class ModeButton  extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		private BoardController.MODE mode;
		
		public ModeButton(BoardController.MODE mode) {
			super();
			this.mode = mode;
			if (BoardController.MODE.PLACE_CASTLE == mode)
				setText("Castle Mode");
			else if (BoardController.MODE.EXPAND_AREA == mode)
				setText("Expand Mode");
			else if (BoardController.MODE.PLACE_KNIGHT == mode)
				setText("Knight Mode");
			else if (BoardController.MODE.PLACE_WALL == mode)
				setText("Wall Mode");
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			drawArea.currentMode = mode;		
		}
		
	}
}
