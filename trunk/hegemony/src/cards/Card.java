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
	private BoardController.MODE mode;
	private GameController controller;		
	private Box buttons = Box.createVerticalBox();
	
	public Card(GameController controller, BoardController.MODE mode) {
		
		this.controller = controller;
		this.mode = mode;
		
		String modeName = "";
		if (BoardController.MODE.EXPAND_AREA == mode) {			
			modeName = "Expand Mode";			
			cost = 1;	
			resellValue = 1;
		}
		else if (BoardController.MODE.PLACE_KNIGHT == mode) {
			modeName = "Knight Mode";
			cost = 2;
			resellValue = 1;
		}
		else if (BoardController.MODE.PLACE_WALL == mode) {
			modeName = "Wall Mode";
			cost = 1;
			resellValue = 1;
		}
		
		setName(modeName);
		buttons.add(new ActionButton(ButtonType.SELL, "Sell", this));
		buttons.add(new ActionButton(ButtonType.ACTION, modeName, this));
		add(buttons);
	}
		
	private enum ButtonType {
		SELL,
		ACTION
	}
	
	public int getResellValue() {
		return resellValue;
	}
	
	public BoardController.MODE getMode() {
		return mode;
	}
	
	
	
	private class ActionButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		private ButtonType type;
		private String label;
		private Card card; 
		
		public ActionButton(ButtonType type, String label, Card card) {
			this.card = card;
			this.label = label;
			this.type = type;
			setText(label);
			addActionListener(this);
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

	public void setActive(boolean isActive) {		
		for (Component button : buttons.getComponents()) {
			button.setEnabled(isActive);
		}
	}
}
