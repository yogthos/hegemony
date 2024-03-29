package game;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;

import java.awt.Component;
import java.awt.Dimension;
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
	private JPanel playerHand = new JPanel();
	private JPanel controls = new JPanel();	
	private Box bazaar = Box.createVerticalBox();
	
	private int WIDTH;
	
	public ControlsPanel(GameCore mainWindow, InfoPanel infoPanel, final GameController controller, BoardController board) {

		this.mainWindow = mainWindow;	
		this.board = board;
		this.controller = controller;
		this.infoPanel = infoPanel;
		WIDTH = this.getWidth();
		
		bazaar.add(new JLabel("The Bazaar"));
		
		
	    ActionListener drawListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.drawCardFromDeck();
				
			}	    	
	    };
	    drawButton.addActionListener(drawListener);
	    infoPanel.addComponent(drawButton);
	    infoPanel.addComponent(bazaar);
	    				
		add(controls);
        add(playerHand);	
	}
	
	public void setCardActionsEnabled(int availableResources) {
		setCardsEnabled(true);
		for (Component cardComponent : playerHand.getComponents()) {
			Card card = (Card)cardComponent;
			card.setActionActive(card.getCost() > availableResources? false: true);
		}
		mainWindow.validate();
	}
	
	public void setCardsEnabled(boolean enabled) {
		for (Component cardComponent : playerHand.getComponents()) {
			Card card = (Card)cardComponent;
			card.setActive(enabled);
		}
		mainWindow.validate();
	}
	
	public void enableDrawControls(boolean enable) {
		
		drawButton.setEnabled(enable);
		controls.setEnabled(enable);
		for (Component c : bazaar.getComponents()) {
			c.setEnabled(enable);
		}
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
		bazaar.add(new JLabel("The Bazaar"));
		for (Card card : bazaarCards) {
			bazaar.add(new BazaarButton(card));
		}	
		bazaar.repaint();
	}
	
	public int bazaarSize() {
		return bazaarCards.size();
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
		infoPanel.updatePlayer(board.getCurrentPlayer());
		mainWindow.validate();
	}
	
	private class BazaarButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		
		private Card card;
		public BazaarButton (Card card) {
			this.card = card;
			setText(card.getName());
			int numLines = card.getName().split("\n").length;
			setPreferredSize(new Dimension(WIDTH, numLines*10));
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			controller.takeFromBazaar(card);
		}
	}
}
