import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ColorButton extends JButton {
		
	public ColorButton(Color bgColor) {
		super();
		setStyle(bgColor);
	}
	
	public ColorButton(Color bgColor, ImageIcon icon) {
		super(icon);
		setStyle(bgColor);
	}
	
	private void setStyle(Color bgColor) {
		setPreferredSize(Config.colorButton);
		setBorderPainted(false);
		setFocusPainted(false);
		setBackground(bgColor);
		setFocusable(false);
	}
	
}