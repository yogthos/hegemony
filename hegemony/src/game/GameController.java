package game;

import java.awt.BorderLayout;

import cards.Card;
import cards.Deck;

public class GameController {

	private Deck deck;
	private BoardController board;
	private ControlsPanel controlsPanel;
	
	public GameController(GameCore gameCore, InfoPanel infoPanel, BoardController board) {
	
		controlsPanel = new ControlsPanel(gameCore, infoPanel, this, board);
		gameCore.add(controlsPanel, BorderLayout.PAGE_END);
		
		this.board = board;
		this.deck = new Deck(this);
		
		initSetupPhase();		
	}
	
	private void initSetupPhase() {	
		controlsPanel.enableDrawControls(false);
		board.setGamePhase(BoardController.GamePhase.SETUP);
		board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
	}
	
	private void initMainPhase() {
		
		board.setGamePhase(BoardController.GamePhase.MAIN);
		Player[] players = board.getPlayers();
		for (Player player : players) {
			for (int i = 0; i < 2; i++) {
				player.drawCard(deck.draw());
			}
		}
				
		controlsPanel.showMainPhaseControls();
		startTurn();
	}
	
		
	private void startTurn() {
		
		board.updateCurrentTurn();
		board.updateCurrentPlayerResources();
		board.setCurrentMode(BoardController.MODE.EMPTY);
		
		controlsPanel.setPlayerCards(board.getCurrentPlayer().getCards());
		controlsPanel.setCardsEnabled(-1);		
		controlsPanel.enableDrawControls(true);
		
		System.out.println("Current turn: " + board.getCurrentTurn());
	}
	
	//Handle card actions
	public void handleModeChangeAction(BoardController.MODE mode, String modeName, Card card) {
		Player player = board.getCurrentPlayer();
		int playerResources = player.getResources();
		if (card.getCost() > playerResources)
			return;
		
		player.subtractResources(card.getCost());
		player.playCard(card);
		board.setCurrentMode(mode);		
		controlsPanel.updateMode(modeName);		
	}

	public void handleSellAction(Card card) {	
		
		Player player = board.getCurrentPlayer();		
		player.removeCard(card);
		player.addResources(card.getResellValue());
		player.setLastSold(card);
		controlsPanel.addCardToBazaar(card);
		startTurn();
	}

	//handle control actions
	public void takeFromBazaar(Card card) {
		Player player = board.getCurrentPlayer();
		if (card.equals(player.getLastSold()))
			return;
		
		controlsPanel.removeCardFromBazaar(card);
		card.setActive(true);
		player.drawCard(card);		
		updatePlayerCardsInControlPanel(player);		
	}
			
	public void drawCardFromDeck() {
		if (deck.isEmpty())
			return;
		Player player = board.getCurrentPlayer();
		
		player.drawCard(deck.draw());
		updatePlayerCardsInControlPanel(player);
	}
	
	private void updatePlayerCardsInControlPanel(Player player) {
		controlsPanel.enableDrawControls(false);
		controlsPanel.setPlayerCards(player.getCards());		
		controlsPanel.setCardsEnabled(player.getResources());
	}
	
	//utility method for checking if setup phase is over
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
	
	//handle clicks on the board
	public void handleBoardAction(int x, int y, boolean clicked) {
		if (board.getCurrentMode().equals(BoardController.MODE.EMPTY))
			return;
		if (x/Edge.LENGTH > BoardController.size - 1 || y/Edge.LENGTH > BoardController.size - 1)
			return;
	
		if (clicked) {
			if(!board.handlePlayerAction(x, y, true)) {
				board.getCurrentPlayer().undoPlayCard();
				return;
			}
			
			controlsPanel.updateInfoPanel();
			
			//Setup phase of the game where players place their initial castles and knights
			if (board.getCurrentMode() == BoardController.MODE.PLACE_CASTLE) {				
				board.setCurrentMode(BoardController.MODE.PLACE_KNIGHT_SIMPLE);
			}
			else if (board.getCurrentMode() == BoardController.MODE.PLACE_KNIGHT_SIMPLE) {
				board.setCurrentMode(BoardController.MODE.PLACE_CASTLE);
				if (moreCastlesToPlace()) {
					board.updateCurrentTurn();				
				}
				else {
					board.setCurrentMode(BoardController.MODE.EMPTY);
					initMainPhase();
				}
			}
			else if (BoardController.GamePhase.MAIN == board.getGamePhase()) {				
				Player player = board.getCurrentPlayer();
				
				player.commitPlay();
				startTurn();
			}
		}
		else {
			//paint the overlay
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
