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
	private JLabel knightsRemaining = new JLabel("Knights");
	private JLabel castlesRemaining = new JLabel("Castles");
	
	public InfoPanel(int width, int height) {
		
		currentPlayer.setPreferredSize(new Dimension(width, 10));
		score.setPreferredSize(new Dimension(width, 10));
		currentMode.setPreferredSize(new Dimension(width, 10));
		
		Box bv = Box.createVerticalBox();
		bv.setPreferredSize(new Dimension(width, height));
		bv.add(currentPlayer);
		bv.add(score);
		bv.add(currentMode);
		bv.add(knightsRemaining);
		bv.add(castlesRemaining);
		add(bv);
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
	}
	
	public void updatePlayer(Player player, int turn) {
		currentPlayer.setText("Player: " + turn);
		knightsRemaining.setText("Knights: " + player.getKnightsRemaining());
		castlesRemaining.setText("Castles: " + player.getCastlesRemainig());
		score.setText("Score: " + player.getScore());
	}
	
	public void updateMode(String mode) {
		currentMode.setText("Action: " + mode);
	}
}
