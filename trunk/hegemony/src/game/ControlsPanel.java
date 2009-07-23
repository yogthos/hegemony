package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlsPanel extends JFrame {


	private static final long serialVersionUID = 1L;

	private GameCanvas canvas;
	
	public ControlsPanel(GameCanvas canvas) {
		this.canvas = canvas;
				
		JPanel panel = new JPanel();
        panel.add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
        panel.add(new ModeButton(BoardController.MODE.PLACE_WALL));
        panel.add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
        panel.add(new ModeButton(BoardController.MODE.EXPAND_AREA));
        panel.add(new TurnButton());
        add(panel);
        pack();
        /*
		add(new ModeButton(BoardController.MODE.PLACE_CASTLE));
        add(new ModeButton(BoardController.MODE.PLACE_WALL));
        add(new ModeButton(BoardController.MODE.PLACE_KNIGHT));
        add(new ModeButton(BoardController.MODE.EXPAND_AREA));
        */
	    setVisible(true);
		
	}
	
	private class TurnButton extends JButton implements ActionListener {

		public TurnButton() {
			super();
			setText("Next Turn");
			addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getBoard().updateCurrentTurn();
			
		}
		
	}
	
	private class ModeButton  extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		private BoardController.MODE mode;
		
		public ModeButton(BoardController.MODE mode) {
			super();
			this.mode = mode;
			if (BoardController.MODE.PLACE_CASTLE == mode)
				setText("Castle Mode");
			else if (BoardController.MODE.EXPAND_AREA == mode)
				setText("Expand Mode");
			else if (BoardController.MODE.PLACE_KNIGHT == mode)
				setText("Knight Mode");
			else if (BoardController.MODE.PLACE_WALL == mode)
				setText("Wall Mode");
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.setCurrentMode(mode);		
		}
		
	}
}
