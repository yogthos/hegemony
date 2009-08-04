package game;

import java.awt.BorderLayout;

import cards.Card;
import cards.Deck;

public class GameController {

	private Deck deck;
	private BoardController board;
	private ControlsPanel controlsPanel;
	
	public enum Actions {
		DRAW,
		PLAY_CARD,
		DISCARD
	}
	
	public GameController(GameCore gameCore, InfoPanel infoPanel, BoardController board) {
	
		controlsPanel = new ControlsPanel(gameCore, infoPanel, this, board);
		gameCore.add(controlsPanel, BorderLayout.PAGE_END);
		
		this.board = board;
		this.deck = new Deck(this);
		
		initSetupPhase();				
	}
	
	private void initSetupPhase() {	
		controlsPanel.showDrawControls(false);
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
		
		controlsPanel.showMainPhaseControls();
		nextTurn();
	}
	
		
	private void nextTurn() {
		board.updateCurrentTurn();
		board.updateCurrentPlayerResources();
		Player player = board.getCurrentPlayer();
		int resources = player.getResources();
		for (Card card : player.getCards()) {
			card.setActive((card.getCost() > resources? false: true));
		}
		controlsPanel.setPlayerCards(board.getCurrentPlayer().getCards());
		controlsPanel.updateInfoPanel();
		controlsPanel.showDrawControls(true);
		System.out.println("Current turn: " + board.getCurrentTurn());
	}
	
	public void handleModeChangeAction(BoardController.MODE mode, String modeName, Card card) {
		Player player = board.getCurrentPlayer();
		int playerResources = player.getResources();
		if (card.getCost() > playerResources)
			return;
		
		player.subtractResources(card.getCost());
		player.removeCard(card);
		board.setCurrentMode(mode);
		controlsPanel.updateMode(modeName);	
	}
			
	
	public void handleDiscardAction(Card card) {		
		nextTurn();
	}
	
	public void handleSellAction(Card card) {
		System.out.println("Current turn: " + board.getCurrentTurn());
		board.getCurrentPlayer().removeCard(card);
		board.getCurrentPlayer().addResources(card.getResellValue());
		board.getCurrentPlayer().setLastSold(card);
		nextTurn();
	}

	public void takeFromBazaar(Card card) {
		if (card.equals(board.getCurrentPlayer().getLastSold()))
			return;
		board.getCurrentPlayer().drawCard(card);
		controlsPanel.showDrawControls(false);
	}
			
	public void drawCardFromDeck() {
		if (deck.isEmpty())
			return;
		board.getCurrentPlayer().drawCard(deck.draw());
		controlsPanel.showDrawControls(false);
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
	
	public void handleBoardAction(int x, int y, boolean clicked) {
		if (clicked) {
			boolean result = board.handlePlayerAction(x, y, true);
			controlsPanel.updateInfoPanel();
			
			//Setup phase of the game where players place their initial castles and knights
			if (board.getCurrentMode() == BoardController.MODE.PLACE_CASTLE && result) {				
				board.setCurrentMode(BoardController.MODE.PLACE_KNIGHT_SIMPLE);
			}
			else if (board.getCurrentMode() == BoardController.MODE.PLACE_KNIGHT_SIMPLE && result) {
				board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
				if (moreCastlesToPlace()) {
					board.updateCurrentTurn();				
				}
				else {
					board.setCurrentMode(BoardController.MODE.EMPTY);
					initMainPhase();
				}
			}
			//Main phase of the game, where players draw cards and play them
			//if (result && BoardController.GamePhase.MAIN == controlsPanel.getPhase()) {
				
				//board.updateCurrentTurn();
			//}
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
