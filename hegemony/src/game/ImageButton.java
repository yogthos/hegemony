package game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.util.Map;

public class ImageButton extends JButton {

	private static final long serialVersionUID = 1L;


	  public ImageButton(String[] icons) {
	    	   
		ImageIcon icon = new ImageIcon(icons[0]); 
	    setIcon(icon);
	    if (null != icons[1])
	    	setPressedIcon(new ImageIcon(icons[1]));
	    if (null != icons[2])
	    	setRolloverIcon(new ImageIcon(icons[2]));
	    if (null != icons[3])
	    	setSelectedIcon(new ImageIcon(icons[3]));
	    if (null != icons[4])
	    	setRolloverSelectedIcon(new ImageIcon(icons[4]));
	    if (null != icons[5])
	    	setDisabledIcon(new ImageIcon(icons[5]));
	    if (null != icons[6])
	    	setDisabledSelectedIcon(new ImageIcon(icons[6]));
	    
	    setMargin(new Insets(0, 0, 0, 0));
	    setIconTextGap(0);
	    setBorderPainted(false);
	    setBorder(null);
	    setText(null);
	    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
	    
	  }

}
