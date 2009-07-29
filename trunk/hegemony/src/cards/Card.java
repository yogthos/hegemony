package cards;

import game.BoardController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Card {

	int cost;
	enum TYPE {
		
	}
	/*
	private class ModeButton  extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		private BoardController.MODE mode;
		String modeName;
		public ModeButton(BoardController.MODE mode) {
			super();
			this.mode = mode;
			if (BoardController.MODE.PLACE_CASTLE == mode)
				modeName = "Castle Mode";		
			else if (BoardController.MODE.EXPAND_AREA == mode)
				modeName = "Expand Mode";
			else if (BoardController.MODE.PLACE_KNIGHT == mode)
				modeName = "Knight Mode";
			else if (BoardController.MODE.PLACE_WALL == mode)
				modeName = "Wall Mode";
			setText(modeName);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {		
			canvas.setCurrentMode(mode);
			infoPanel.updateMode(modeName);
		}
		
	}
	*/
}
