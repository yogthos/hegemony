package cards;


import game.BoardController;
import game.GameController;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Card extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private int cost;
	private int resellValue;
	private BoardController.MODE[] modes;
	private String modeNames = "";
	private GameController controller;	

	private Box buttons = Box.createVerticalBox();
	
	public Card(GameController controller, BoardController.MODE[] modes) {
		
		this.controller = controller;
		this.modes = modes;
		for (BoardController.MODE mode : modes) {
			addActionButton(mode);
		}

		buttons.add(new ActionButton(ButtonType.SELL, null, "Sell: " + this.resellValue, this));		
		add(buttons);
	}
	
	@Override
	public String getName() {
		return modeNames;
	}
	
	private void addActionButton(BoardController.MODE mode) {
		String modeName = "";
		if (BoardController.MODE.EXPAND_AREA == mode) {								
			cost = 1;	
			resellValue = 1;
			modeName = "Expand Mode: " + cost;
		}
		else if (BoardController.MODE.PLACE_KNIGHT == mode) {
			cost = 2;
			resellValue = 1;
			modeName = "Knight Mode: " + cost;

		}
		else if (BoardController.MODE.PLACE_WALL == mode) {
			cost = 1;
			resellValue = 1;
			modeName = "Wall Mode: " + cost;			
		}
		modeNames += modeName + "\n";
		buttons.add(new ActionButton(ButtonType.ACTION, mode, modeName, this));
	}
	
	private enum ButtonType {
		SELL,
		ACTION
	}
	
	public int getResellValue() {
		return resellValue;
	}
	
	public BoardController.MODE[] getModes() {
		
		return modes;
	}
	
	
	
	private class ActionButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		private ButtonType type;
		private String label;
		private Card card;
		private BoardController.MODE mode;
		
		public ActionButton(ButtonType type, BoardController.MODE mode, String label, Card card) {
			this.card = card;
			this.label = label;
			this.type = type;
			this.mode = mode;
			setText(label);
			addActionListener(this);
		}
		
		public BoardController.MODE getMode() {
			return mode;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ButtonType.ACTION == type)
				controller.handleModeChangeAction(mode, label, card);
			else if (ButtonType.SELL == type) {
				controller.handleSellAction(card);
			}
		}
	
	}
	public int getCost() {
		return cost;
	}

	public void setActionActive(boolean active) {
		for (Component button : buttons.getComponents()) {
			ActionButton actionButton = (ActionButton)button;
			if (null != actionButton.getMode()) {
				actionButton.setEnabled(active);
			}
		}
	}
	
	public void setActive(boolean active) {		
		for (Component button : buttons.getComponents()) {			
			button.setEnabled(active);
		}
	}
}
