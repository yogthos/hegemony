package game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.util.Map;

public class ImageButton extends JButton {

	private static final long serialVersionUID = 1L;


	  public ImageButton(Map<String,String> icons) {
	    	   
		ImageIcon icon = new ImageIcon(icons.get("icon.png")); 
	    setIcon(icon);
	    setPressedIcon(new ImageIcon(icons.get("down.png")));
	    setRolloverIcon(new ImageIcon(icons.get("over.png")));
	    setSelectedIcon(new ImageIcon(icons.get("sel.png")));
	    setRolloverSelectedIcon(new ImageIcon(icons.get("sel-over.png")));
	    setDisabledIcon(new ImageIcon(icons.get("disabled.png")));
	    setDisabledSelectedIcon(new ImageIcon(icons.get("disabled-selected.png")));
	    
	    setMargin(new Insets(0, 0, 0, 0));
	    setIconTextGap(0);
	    setBorderPainted(false);
	    setBorder(null);
	    setText(null);
	    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
	    
	  }

}
