package game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlsPanel extends JPanel {

		
	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private BoardController board;
	private GameCore mainWindow;
	private GameController controller;
	
	private List<Card> bazaarCards = new ArrayList<Card>();
	
	private JButton drawButton = new JButton("Draw Card");
	JPanel deckPanel = new JPanel();
	JPanel bazaar = new JPanel();
	JPanel playerHand = new JPanel();
	JPanel controls = new JPanel();
	
	public ControlsPanel(GameCore mainWindow, InfoPanel infoPanel, final GameController controller, BoardController board) {

		this.mainWindow = mainWindow;	
		this.board = board;
		this.controller = controller;
		this.infoPanel = infoPanel;
	    
		bazaar.add(new JLabel("The Bazaar"));
		
	    ActionListener drawListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.drawCardFromDeck();
				
			}	    	
	    };
	    drawButton.addActionListener(drawListener);		
		controls.add(bazaar);		
		playerHand.setVisible(true);
		playerHand.setVisible(false);

		add(drawButton);
		add(controls);
        add(playerHand);	
        add(bazaar);
	}
	
	public void setCardsEnabled(int availableResources) {
		for (Component cardComponent : playerHand.getComponents()) {
			Card card = (Card)cardComponent;
			card.setActive(card.getCost() > availableResources? false: true);
		}
		mainWindow.validate();
	}
	
	public void showDrawControls(boolean show) {
		
		drawButton.setVisible(show);
		controls.setVisible(show);
		bazaar.setVisible(show);		
		mainWindow.validate();
		
	}
	
	public void showMainPhaseControls() {
		playerHand.setVisible(true);
		mainWindow.validate();
	}
	
	
	public BoardController.GamePhase getPhase() {
		return board.getGamePhase();
	}
	
	public void setPlayerCards(List<Card> cards) {
		playerHand.removeAll();
		for (Card card : cards) {
			playerHand.add(card);
		}		
		mainWindow.validate();
	}
	
	private void repaintBazaar() {
		bazaar.removeAll();
		for (Card card : bazaarCards) {
			bazaar.add(new BazaarButton(card));
		}	
		bazaar.repaint();
	}
	
	public void addCardToBazaar(Card card) {
		card.setActive(false);		
		bazaarCards.add(card);
		repaintBazaar();			
	}
	
	public void removeCardFromBazaar(Card card) {
		bazaarCards.remove(card);
		repaintBazaar();			
	}
	
	public void updateMode(String modeName) {
		infoPanel.updateMode(modeName);
		mainWindow.validate();
	}
	
	public void updateInfoPanel() {
		infoPanel.updatePlayer(board.getCurrentPlayer(), board.getCurrentTurn());
		mainWindow.validate();
	}
	
	private class BazaarButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		
		private Card card;
		public BazaarButton (Card card) {
			this.card = card;
			setText(card.getName() + ": " + card.getCost());
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			controller.takeFromBazaar(card);
		}
	}
}
