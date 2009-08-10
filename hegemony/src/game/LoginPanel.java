package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String localPlayer = null;
	private BoardController board = null;
	private Player[] players = null;
	
	public LoginPanel() {
		new JLabel("GTalk ID:");
		new JLabel("password:");
	}

	public void startTurn() {
		if (board.getCurrentPlayer().getName().equals(localPlayer)) {
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Player[] getPlayers() {
		return players;
	}
}
