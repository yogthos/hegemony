package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import cards.Deck;

import tiles.GameBoard;
import tiles.Tile;

import network.ConnectionManager;
import network.MessageHandler;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MessageHandler messageHandler = null;
	private String localPlayer = null;
	private GameController controller = null;
	private Deck deck = null;
	private Tile[][] tiles = null;
	private Player[] players = null;
	
	private JTextField userId = new JTextField();
	private JTextField hostId = new JTextField();
	private JPasswordField password = new JPasswordField();	
	JSlider numPlayers = new JSlider(JSlider.HORIZONTAL,2, 4, 2);
	Box buttons = Box.createHorizontalBox();
	
	private boolean host = false;
	private GameCore core = null;
	
	public LoginPanel(GameCore core) {
						
		//init board and deck		
		controller = new GameController();
		deck = new Deck(controller);
		tiles = GameBoard.generateBoard();		
		this.core = core;
				
		try {
			messageHandler = new MessageHandler(controller);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		numPlayers.setMajorTickSpacing(1);
		numPlayers.setSnapToTicks(true);
		numPlayers.setPaintTicks(true);
				
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(2, new JLabel("2") );
		labelTable.put(3, new JLabel("3") );
		labelTable.put(4, new JLabel("4") );
		numPlayers.setLabelTable( labelTable );
		numPlayers.setPaintLabels(true);

		
		buttons.add(new ActionButton(ActionType.HOST));
		buttons.add(new ActionButton(ActionType.JOIN));
		
		
		setLayout(new BorderLayout());
		add(buttons, BorderLayout.SOUTH);		
		
		core.setSize(new Dimension(GameCore.WIDTH,GameCore.HEIGHT));
		core.add(this);
		setVisible(true);		
		core.validate();
	}

	private void showClientUI() {
		remove(buttons);
		
		Box rowOne = Box.createHorizontalBox();
		rowOne.add(new JLabel("Host GTalk ID:"));
		rowOne.add(hostId);

		Box rowTwo = Box.createHorizontalBox();
		rowTwo.add(new JLabel("GTalk ID:"));
		rowTwo.add(userId);
				
		Box rowThree = Box.createHorizontalBox();
		rowThree.add(new JLabel("password:"));
		rowThree.add(password);
		
		add(rowOne, BorderLayout.NORTH);
		add(rowTwo, BorderLayout.CENTER);
		add(rowThree, BorderLayout.SOUTH);
		add(new StartButton(), BorderLayout.SOUTH);
		
		core.validate();
	}
	
	private void showHostUI() {
		
		remove(buttons);
		
		Box rowOne = Box.createHorizontalBox();
		rowOne.add(new JLabel("GTalk ID:"));
		rowOne.add(userId);
		
		Box rowTwo = Box.createHorizontalBox();
		rowTwo.add(new JLabel("password:"));
		rowTwo.add(password);
		
		Box rowThree = Box.createHorizontalBox();
		rowThree.add(new JLabel("Number of players: "));
		rowThree.add(numPlayers);
		
		Box vbox = Box.createVerticalBox();

		vbox.add(rowOne);
		vbox.add(rowTwo);
		vbox.add(rowThree);
		vbox.add(new StartButton());
		add(vbox, BorderLayout.CENTER);
		
		setSize(new Dimension(GameCore.WIDTH,GameCore.HEIGHT));
		
		core.validate();
		
	}
	
	private void initClient() throws InterruptedException {
				
	}
	
	private void initHost() {
					
		players = new Player[numPlayers.getValue()];		
		players[0] = new Player(userId.getText());
		
		try {
			ConnectionManager.INSTANCE.getConnection(userId.getText(), new String(password.getPassword()));
		
			int numJoined = 0;
			while (numJoined < numPlayers.getValue() - 1) {
				String playerId = null;
				try {
					playerId = messageHandler.handleJoinRequest();
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
				players[++numJoined] = new Player(playerId);
				System.out.println(playerId + " has joined the game");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		initGame();
	}
	
	public void initGame() {

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
		HOST
	}
	
	private class StartButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public StartButton() {
			setText("Start game");
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (host)
					if (userId.getText().length() > 0 && password.getPassword().length > 0)
						initHost();
				else
					initClient();
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}			
		}
	}
	
	private class ActionButton extends JButton implements ActionListener {
	
		private static final long serialVersionUID = 1L;
		
		private ActionType action;
		
		public ActionButton(ActionType action) {
			super(ActionType.JOIN == action ? "Join game" : (ActionType.HOST == action ? "Host game" : null));
			this.action = action;
			addActionListener(this);
		}		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ActionType.HOST == action) {
				host = true;
				showHostUI();
			}
			else {
				host = false;
				showClientUI();
			}
		}
	}
}
