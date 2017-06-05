import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
	
	public ColorPanel(DrawingPanel dp, int colorButtonsAmount) {
		super();
		addColorButtons(dp, colorButtonsAmount);
		addResetButton(dp);
		addColorfulPaintingButton(dp);
	}
	
	// add color changing buttons 
	private void addColorButtons(DrawingPanel dp, int amount) {
		
		JButton btn;
        for(int i = 0; i < amount; i++) {
			btn = new ColorButton( dp.getColor(i) );
			
			final int colorIndex = i;
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dp.setColorfulPainting(false);
					dp.setCurrentColor(colorIndex);
				}	
			});
			
			add(btn);
		}
        
	}
	
	// add canvas clearing button
	private void addResetButton(DrawingPanel dp) {
		
		ImageIcon resetIcon = new ImageIcon("./src/images/reset.png");
		resetIcon = scale(
				resetIcon,
				Config.colorButton.width - 8,
				Config.colorButton.height - 8);
			
		JButton btn = new ColorButton( new Color(240, 240, 240), resetIcon );
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dp.resetField();
			}	
		});
		add(btn);
			
	}
	
	// add button for colorful painting
	private void addColorfulPaintingButton(DrawingPanel dp) {
		
		ImageIcon colorfulIcon = new ImageIcon("./src/images/colorful.png");
		colorfulIcon = scale(
				colorfulIcon,
				Config.colorButton.width - 8,
				Config.colorButton.height - 8);
		
		JButton btn = new ColorButton( new Color(240, 240, 240), colorfulIcon );
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dp.setColorfulPainting(true);
			}	
		});
		add(btn);
		
	}
	
	
	private ImageIcon scale(ImageIcon icon, int w, int h) {
		return new ImageIcon(icon
				.getImage()
        		.getScaledInstance( w, h, java.awt.Image.SCALE_SMOOTH ));
	}
	
}
