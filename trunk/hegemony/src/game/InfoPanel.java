package game;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel currentPlayer = new JLabel();
	private JLabel score = new JLabel();
	private JLabel resources = new JLabel();
	private JLabel currentMode = new JLabel();
	private JLabel knightsRemaining = new JLabel();
	private JLabel castlesRemaining = new JLabel();
	private Box bv = Box.createVerticalBox();
	
	public InfoPanel(int width, int height) {
		
		currentPlayer.setPreferredSize(new Dimension(width, 10));
		score.setPreferredSize(new Dimension(width, 10));
		resources.setPreferredSize(new Dimension(width, 10));
		currentMode.setPreferredSize(new Dimension(width, 10));
		
		
		bv.setPreferredSize(new Dimension(width, height));
		bv.add(currentPlayer);
		bv.add(resources);
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
		resources.setText("Resources: " + player.getResources());
		score.setText("Score: " + player.getScore());
	}
	
	public void addComponent(Component c) {
		bv.add(c);
	}
	
	public void updateMode(String mode) {
		currentMode.setText("Action: " + mode);
	}
}
