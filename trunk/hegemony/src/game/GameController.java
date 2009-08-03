package game;

import cards.Deck;

public class GameController {

	private Deck deck;
	private BoardController board;
	private InfoPanel infoPanel;
	private ControlsPanel controlsPanel;
	
	public enum Actions {
		DRAW,
		PLAY_CARD,
		DISCARD
	}
	
	public GameController(InfoPanel infoPanel, ControlsPanel controlsPanel, BoardController board) {
	
		this.infoPanel = infoPanel;
		this.controlsPanel = controlsPanel;
		this.board = board;
		this.deck = new Deck(this);
		
		initSetupPhase();				
	}
	
	private void initSetupPhase() {	
		board.setGamePhase(BoardController.GamePhase.SETUP);
		board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
	}
	
	private void initMainPhase() {
		
		board.setGamePhase(BoardController.GamePhase.MAIN);
		Player[] players = board.getPlayers();
		for (Player player : players) {
			for (int i = 0; i < 3; i++) {
				player.drawCard(deck.draw());
			}
		}
		displayPlayerHand();
		controlsPanel.showMainPhaseControls();		
	}
	
	private boolean moreCastlesToPlace() {
		boolean castlesPlaced = false;
		for (Player player : board.getPlayers()) {
			if (player.getCastlesRemainig() > 0) {
				castlesPlaced = true;
				break;
			}								
		}
		return castlesPlaced;
	}
	
	private void displayPlayerHand() {
		
		controlsPanel.setPlayerCards(board.getCurrentPlayer().getCards());
	}
	
	public void handleModeChangeAction(BoardController.MODE mode, String modeName) {
		board.setCurrentMode(mode);
		infoPanel.updateMode(modeName);	
	}
			
	
	public void handleDiscardAction() {
		
	}
	
	public void handleSellAction() {
		
	}
	
	public void handleBoardAction(int x, int y, boolean clicked) {
		if (clicked) {
			boolean result = board.handlePlayerAction(x, y, true);
			infoPanel.updatePlayer(board.getCurrentPlayer(), board.getCurrentTurn());
			
			//Setup phase of the game where players place their initial castles and knights
			if (board.getCurrentMode() == BoardController.MODE.PLACE_CASTLE && result) {				
				board.setCurrentMode(BoardController.MODE.PLACE_KNIGHT_SIMPLE);
			}
			else if (board.getCurrentMode() == BoardController.MODE.PLACE_KNIGHT_SIMPLE && result) {
				board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
				if (moreCastlesToPlace()) {
					board.updateCurrentTurn();
					displayPlayerHand();				
				}
				else {
					board.setCurrentMode(BoardController.MODE.EMPTY);
					initMainPhase();
				}
			}
			//Main phase of the game, where players draw cards and play them
			if (result && BoardController.GamePhase.MAIN == controlsPanel.getPhase()) {
				
				board.updateCurrentTurn();
				displayPlayerHand();
			}
		}
		else {
			if (x/Edge.LENGTH > BoardController.size - 1 || y/Edge.LENGTH > BoardController.size - 1) {
				return;
			}
			board.handlePlayerAction(x, y, false);
		}
	}
	
	/**
	 * specifies if the territory ownership overlay should be drawn
	 * @return false if the game is in the setup phase, true otherwise
	 */
	public boolean drawOverlay() {
		return (BoardController.GamePhase.SETUP == board.getGamePhase()? false : true);
	}
}
