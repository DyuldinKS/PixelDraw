import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
	
	public ColorPanel(DrawingPanel dp, int colorButtonsAmount) {
		super();
		addColorButtons(dp, colorButtonsAmount);
		addResetButton(dp);
		addEraserButton(dp);
		addColorfulBrushButton(dp);
	}
	
	// add color changing buttons 
	private void addColorButtons(DrawingPanel dp, int amount) {
		
		JButton btn;
        for(int i = 0; i < amount; i++) {
			btn = new ColorButton( dp.getColor(i) );
			
			final int colorIndex = i;
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dp.pickBrush();
					dp.setCurrentColor(colorIndex);
				}	
			});
			
			add(btn);
		}
        
	}
	
	
	// add canvas clearing button
		private void addEraserButton(DrawingPanel dp) {
			
			ImageIcon resetIcon = new ImageIcon("./src/images/eraser.png");
			resetIcon = scale(
					resetIcon,
					Config.colorButton.width - 8,
					Config.colorButton.height - 8);
				
			JButton btn = new ColorButton( new Color(240, 240, 240), resetIcon );
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dp.pickEraser();
				}	
			});
			add(btn);
				
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
				dp.eraseField();
			}	
		});
		add(btn);
			
	}
	
	// add button for colorful painting
	private void addColorfulBrushButton(DrawingPanel dp) {
		
		ImageIcon colorfulIcon = new ImageIcon("./src/images/colorful.png");
		colorfulIcon = scale(
				colorfulIcon,
				Config.colorButton.width - 8,
				Config.colorButton.height - 8);
		
		JButton btn = new ColorButton( new Color(240, 240, 240), colorfulIcon );
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dp.pickColorfulBrush();
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
