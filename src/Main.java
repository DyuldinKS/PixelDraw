import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
	public static void main(String[] args) {
        JFrame frame = new JFrame(Config.name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Config.frame);
        frame.setIconImage((new ImageIcon(Config.paintIconPath)).getImage());
		frame.setLocationRelativeTo(null);  // *** this will center your app ***
			
		DrawingPanel dp = new DrawingPanel();
		frame.addComponentListener(new ComponentListener() {
		    
			public void componentResized(ComponentEvent e) {
		        dp.repaint = true;
		    }

			public void componentHidden(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentShown(ComponentEvent arg0) {}
			
		});
		frame.add(dp);
        frame.setVisible(true);      
    }
	
}
