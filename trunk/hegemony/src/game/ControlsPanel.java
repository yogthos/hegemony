package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlsPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private BoardController board;
	
	JPanel buttonsPanel = new JPanel();
	
	public ControlsPanel(BoardController board, InfoPanel infoPanel) {

		this.board = board;
		this.infoPanel = infoPanel;
		
		buttonsPanel.removeAll();
		buttonsPanel.add(new ModeButton(BoardController.MODE.PLACE_WALL));
		buttonsPanel.add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
		buttonsPanel.add(new ModeButton(BoardController.MODE.EXPAND_AREA));		
		buttonsPanel.setVisible(true);
		buttonsPanel.setVisible(false);

        add(buttonsPanel);		
	}
	
	public void initSetupPhase() {		
		//buttons.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
	}
	
	public void initMainPhase() {
		buttonsPanel.setVisible(true);
		repaint();
	}
	
	
	/*
	private class TurnButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		public TurnButton() {
			super();
			setText("Next Turn");
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			board.updateCurrentTurn();
			infoPanel.updatePlayer(board.getCurrentPlayer(), board.getCurrentTurn());
		}
		
	}
	*/
	
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
			board.setCurrentMode(mode);
			infoPanel.updateMode(modeName);		
		}
		
	}
	
}
