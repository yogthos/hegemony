package game;

import javax.swing.JButton;
import javax.swing.JPanel;

import cards.Card;

import java.util.List;

public class ControlsPanel extends JPanel {

		
	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private BoardController board;
	private GameCore mainWindow; 
	
	JPanel deckPanel = new JPanel();
	JPanel bazaar = new JPanel();
	JPanel playerHand = new JPanel();
	JPanel controls = new JPanel();
	
	public ControlsPanel(GameCore mainWindow, BoardController board, InfoPanel infoPanel) {

		this.mainWindow = mainWindow;
		this.board = board;
		this.infoPanel = infoPanel;
		

		controls.add(new JButton("Draw Card"));
	
		playerHand.setVisible(true);
		playerHand.setVisible(false);

        add(playerHand);		
	}
	
	public void showMainPhaseControls() {
		playerHand.setVisible(true);
		mainWindow.validate();
	}
	
	
	public BoardController.GamePhase getPhase() {
		return board.getGamePhase();
	}
	
	public void setPlayerCards(List<Card> cards) {
		for (Card card : cards) {
			playerHand.add(card);
		}
		//repaint();
		//mainWindow.validate();
	}
	
}
