package game;

import javax.swing.JPanel;

import cards.Card;

public class ControlsPanel extends JPanel {

		
	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private BoardController board;
	private GameCore mainWindow; 
	
	JPanel deckPanel = new JPanel();
	JPanel bazaar = new JPanel();
	JPanel playerHand = new JPanel();
	
	public ControlsPanel(GameCore mainWindow, BoardController board, InfoPanel infoPanel) {

		this.mainWindow = mainWindow;
		this.board = board;
		this.infoPanel = infoPanel;
		
		playerHand.removeAll();

		playerHand.add(new Card(this, BoardController.MODE.PLACE_WALL));
		playerHand.add(new Card(this, BoardController.MODE.PLACE_KNIGHT));
		playerHand.add(new Card(this, BoardController.MODE.EXPAND_AREA));
		
		playerHand.setVisible(true);
		playerHand.setVisible(false);

        add(playerHand);		
	}
	
	public void initSetupPhase() {	
		board.setGamePhase(BoardController.GamePhase.SETUP);
	}
	
	public void initMainPhase() {
		board.setGamePhase(BoardController.GamePhase.MAIN);
		playerHand.setVisible(true);
		mainWindow.validate();
	}
	
	public void handleAction(BoardController.MODE mode, String modeName) {
		board.setCurrentMode(mode);
		infoPanel.updateMode(modeName);	
	}

	public BoardController.GamePhase getPhase() {
		return board.getGamePhase();
	}	
}
