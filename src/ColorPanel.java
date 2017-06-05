import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
	
	public ColorPanel(DrawingPanel dp) {
		super();
		
		addColorButtons(dp);
		addResetButton(dp);
		addColorfulPaintingButton(dp);
        
	}
	
	// add color changing buttons 
	private void addColorButtons(DrawingPanel dp) {
		
		List<Color> colors = dp.getColors();
		JButton btn;
		
        for(int i = 0; i < colors.size(); i++) {
			btn = createButton(colors.get(i));
			
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
			
		JButton btn = createButton(resetIcon, new Color(240, 240, 240));
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
			
		JButton btn = createButton(colorfulIcon, new Color(240, 240, 240));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dp.setColorfulPainting(true);
			}	
		});
		add(btn);
		
	}
	
	// create and initialize button with background icon
	private JButton createButton(ImageIcon icon, Color bgColor) {
		return initButton(new JButton(icon), bgColor);
	}
	
	// create and initialize button	
	private JButton createButton(Color bgColor) {
		return initButton(new JButton(), bgColor);
	}
	
	
	private JButton initButton(JButton btn, Color bgColor) {
		btn.setPreferredSize(Config.colorButton);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setBackground(bgColor);
		return btn;
	}
	
	
	private ImageIcon scale(ImageIcon icon, int w, int h) {
		Image img = icon
				.getImage()
        		.getScaledInstance( w, h, java.awt.Image.SCALE_SMOOTH );
		return new ImageIcon(img);
	}
	
}
