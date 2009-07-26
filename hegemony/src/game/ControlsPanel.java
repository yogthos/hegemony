package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlsPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	private InfoPanel infoPanel;
	private GameCanvas canvas;

	public ControlsPanel(GameCanvas canvas, InfoPanel infoPanel) {
		this.canvas = canvas;				
		this.infoPanel = infoPanel;
		
		JPanel buttons = new JPanel();
		buttons.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
		buttons.add(new ModeButton(BoardController.MODE.PLACE_WALL));
		buttons.add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
		buttons.add(new ModeButton(BoardController.MODE.EXPAND_AREA));
        buttons.add(new TurnButton());
        add(buttons);


	    setVisible(true);
		
	}
	
	private class TurnButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;
		public TurnButton() {
			super();
			setText("Next Turn");
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getBoard().updateCurrentTurn();
			infoPanel.updatePlayer(canvas.getBoard().getCurrentPlayer(), canvas.getBoard().getCurrentTurn());
		}
		
	}
	
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
}
