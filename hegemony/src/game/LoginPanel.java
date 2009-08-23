package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cards.Deck;

import tiles.GameBoard;
import tiles.Tile;

import network.ConnectionManager;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private String localPlayer = null;
	private GameController controller = null;
	private Deck deck = null;
	private Tile[][] tiles = null;
	private Player[] players = null;
	private JTextField userId = new JTextField();
	private JPasswordField password = new JPasswordField();
	private boolean host = false;
	private GameCore core = null;
	
	public LoginPanel(GameCore core) {
		
		
		
		//init board and deck		
		controller = new GameController();
		deck = new Deck(controller);
		tiles = GameBoard.generateBoard();		
		this.core = core;
				
		setLayout(new BorderLayout());
		Box rowOne = Box.createHorizontalBox();
		rowOne.add(new JLabel("GTalk ID:"));
		rowOne.add(userId);
		
		Box rowTwo = Box.createHorizontalBox();
		rowTwo.add(new JLabel("password:"));
		rowTwo.add(password);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		
				
			}
		});
		
		Box rowThree = Box.createHorizontalBox();
		rowThree.add(new ActionButton(ActionType.HOST));
		rowThree.add(new ActionButton(ActionType.JOIN));
		rowThree.add(new StartButton());
		
		
		add(rowOne, BorderLayout.NORTH);
		add(rowTwo, BorderLayout.CENTER);
		add(rowThree, BorderLayout.SOUTH);		
		
		core.setPreferredSize(new Dimension(GameCore.WIDTH,GameCore.HEIGHT));
		core.add(this);
		setVisible(true);		
		core.validate();
	}

	public void initGame() {				
		int numPlayers = 2;
		players = new Player[numPlayers];
		
		players[0] = new Player("Kirk");
		players[1] = new Player("Spock");
		
		//TODO: find a way to generate colors on the fly
		Color[] playerColors = {Color.red, Color.blue, Color.green, Color.black};
		for (int i = 0; i < players.length; i++) {
			players[i].setColor(playerColors[i]);
		}
		
		core.initGame(controller, deck, tiles);
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	private enum ActionType {
		JOIN,
		HOST,
		START
	}
	
	private class StartButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public StartButton() {
			setText("Start game");
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			initGame();
		}
	}
	
	private class ActionButton extends JRadioButton implements ActionListener {
	
		private static final long serialVersionUID = 1L;
		
		private ActionType action;
		
		public ActionButton(ActionType action) {
			super(ActionType.JOIN == action ? "Join game" : (ActionType.HOST == action ? "Host game" : null));
			this.action = action;
		}		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ActionType.JOIN == action)
				initClient();
			else if (ActionType.HOST == action) {
							
				try {
					initHost();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		private void initClient() {
			
		}
		
		private void initHost() throws InterruptedException {
			players = new Player[2];
			players[0] = new Player(userId.getText());
			
			ConnectionManager.INSTANCE.getConnection(userId.getText(), new String(password.getPassword()));
			BlockingQueue<String> messageQueue = ConnectionManager.INSTANCE.getQueue();
			
			players[1] = new Player(messageQueue.take());
		}
		
	}
}
