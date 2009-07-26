package game;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel currentPlayer = new JLabel("Current player");
	private JLabel score = new JLabel("Score");
	private JLabel currentMode = new JLabel("CurrentMode");
	
	public InfoPanel() {	
		add(currentPlayer);
		add(score);
		add(currentMode);		
		setVisible(true);
	}
	
	public void updatePlayer(Player player) {
		currentPlayer.setText("Player: " + player.getColor().toString());
		score.setText("Score: " + player.getScore());
	}
	
	public void updateMode(String mode) {
		currentMode.setText("Action: " + mode);
	}
}
