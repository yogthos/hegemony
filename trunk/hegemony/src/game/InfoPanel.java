package game;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel currentPlayer = new JLabel("Current player");
	private JLabel score = new JLabel("Score");
	private JLabel currentMode = new JLabel("CurrentMode");
	
	public InfoPanel(int width, int height) {
		
		
		Box bv = Box.createVerticalBox();
		bv.setPreferredSize(new Dimension(width, height));
		bv.add(currentPlayer);
		bv.add(score);
		bv.add(currentMode);		
		add(bv);
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
	}
	
	public void updatePlayer(Player player, int turn) {
		currentPlayer.setText("Player: " + turn);
		score.setText("Score: " + player.getScore());
	}
	
	public void updateMode(String mode) {
		currentMode.setText("Action: " + mode);
	}
}
