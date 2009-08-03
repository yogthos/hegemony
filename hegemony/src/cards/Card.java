package cards;


import game.BoardController;
import game.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Card extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private int cost;
	private BoardController.MODE mode;
	private GameController controller;		
	
	public Card(GameController controller, BoardController.MODE mode) {
		
		this.controller = controller;
		this.mode = mode;
		
		String modeName = "";
		if (BoardController.MODE.EXPAND_AREA == mode) {
			modeName = "Expand Mode";
			cost = 1;			
		}
		else if (BoardController.MODE.PLACE_KNIGHT == mode) {
			modeName = "Knight Mode";
			cost = 2;
		}
		else if (BoardController.MODE.PLACE_WALL == mode) {
			modeName = "Wall Mode";
			cost = 1;
		}
		
		Box bv = Box.createVerticalBox();
		bv.add(new ActionButton(ButtonType.SELL, "Sell"));
		bv.add(new ActionButton(ButtonType.DISCARD, "Discard"));
		bv.add(new ActionButton(ButtonType.ACTION, modeName));
		add(bv);
	}
	
	private enum ButtonType {
		SELL,
		DISCARD,
		ACTION
	}
	
	private class ActionButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		private ButtonType type;
		private String label;
		
		public ActionButton(ButtonType type, String label) {
			this.label = label;
			this.type = type;
			setText(label);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ButtonType.ACTION == type)
				controller.handleModeChangeAction(mode, label);
			else if (ButtonType.DISCARD == type)
				controller.handleDiscardAction();
			else if (ButtonType.SELL == type) {
				controller.handleSellAction();
			}
		}
	
	}
	public int getCost() {
		return cost;
	}
}
