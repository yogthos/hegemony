package cards;


import game.BoardController;
import game.ControlsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Card extends JButton implements ActionListener {

	
	private static final long serialVersionUID = 1L;

	private int cost;
	private BoardController.MODE mode;
	private ControlsPanel controller;		
	private String modeName;
	
	public Card(ControlsPanel controller, BoardController.MODE mode) {
	
		this.controller = controller;
		this.mode = mode;		
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
		setText(modeName);
		addActionListener(this);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {		
		controller.handleAction(mode, modeName);
	}
	
	public int getCost() {
		return cost;
	}
}
