package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlsPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	private GameCanvas canvas;
	private JLabel playerLabel = null;
	private JLabel modeLabel = null;
	public ControlsPanel(GameCanvas canvas) {
		this.canvas = canvas;				
		
		JPanel buttons = new JPanel();
		buttons.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
		buttons.add(new ModeButton(BoardController.MODE.PLACE_WALL));
		buttons.add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
		buttons.add(new ModeButton(BoardController.MODE.EXPAND_AREA));
        buttons.add(new TurnButton());
        add(buttons);
        JPanel labels = new JPanel();
        playerLabel = new JLabel("Current player: " + canvas.getBoard().getCurrentPlayer().getColor());
        modeLabel = new JLabel("Current mode: " + canvas.getBoard().getCurrentMode());
        labels.add(playerLabel);
        labels.add(modeLabel);        
        add(labels);

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
			playerLabel.setText("Current player: " + canvas.getBoard().getCurrentPlayer().getColor());
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
			modeLabel.setText("Current mode: " +modeName);
		}
		
	}
}
