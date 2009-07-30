package game;

import javax.swing.JPanel;

import cards.Card;

public class ControlsPanel extends JPanel {


	public enum Phase {
		SETUP,
		MAIN
	}
	
	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private BoardController board;
	private GameCore mainWindow; 
	private Phase phase;
	
	JPanel buttonsPanel = new JPanel();
	
	public ControlsPanel(GameCore mainWindow, BoardController board, InfoPanel infoPanel) {

		this.mainWindow = mainWindow;
		this.board = board;
		this.infoPanel = infoPanel;
		
		buttonsPanel.removeAll();
		/*
		buttonsPanel.add(new ModeButton(BoardController.MODE.PLACE_WALL));
		buttonsPanel.add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
		buttonsPanel.add(new ModeButton(BoardController.MODE.EXPAND_AREA));		
		*/
		buttonsPanel.add(new Card(this, BoardController.MODE.PLACE_WALL));
		buttonsPanel.add(new Card(this, BoardController.MODE.PLACE_KNIGHT));
		buttonsPanel.add(new Card(this, BoardController.MODE.EXPAND_AREA));
		
		buttonsPanel.setVisible(true);
		buttonsPanel.setVisible(false);

        add(buttonsPanel);		
	}
	
	public void initSetupPhase() {	
		phase = Phase.SETUP;
		//buttons.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
	}
	
	public void initMainPhase() {
		phase = Phase.MAIN;
		buttonsPanel.setVisible(true);
		mainWindow.validate();
	}
	
	public void handleAction(BoardController.MODE mode, String modeName) {
		board.setCurrentMode(mode);
		infoPanel.updateMode(modeName);	
	}

	public Phase getPhase() {
		return phase;
	}	
}
