import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

public class Canvas extends JPanel {
	
	public Canvas(DrawingPanel drawingPanel) {
		super();
		
		this.dp = drawingPanel;
		this.px = new Cell(Config.pxSize);
		addMouseListener(new MouseHandler()); // extends MouseAdapter
		addMouseMotionListener(new MouseMotionHandler());
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		if(dp.isRepaintMode()) {            
			Color pixelColor = dp.getCurrColor();
			g2D.setColor( pixelColor );

			for(int i = 0; i < Config.field.width; i++){
				for(int j = 0; j < Config.field.height; j++) {
					if( pixelColor != dp.getColor(i, j) ) {
						pixelColor = dp.getColor(i, j);
						g2D.setColor( pixelColor );
					}
					g2D.fill(new Rectangle2D.Double( i * px.size, j * px.size, px.size, px.size ));
				}
			}
        } else {
            g2D.setColor( dp.getCurrColor() );
            g2D.fill(new Rectangle2D.Double( px.x * px.size, px.y * px.size, px.size, px.size ));
        }

	}
	
	public void paintCell(int x, int y) {
		dp.updatePixelColor(x, y);
		dp.disableRepaint();
		px.setCoords(x, y);
		paintImmediately( x * px.size, y * px.size, px.size, px.size );
	}
	
	
	private class MouseHandler implements MouseListener {
		
		public void mousePressed(MouseEvent e) {};
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent e) {
			paintCell(e.getX() / px.size, e.getY() / px.size);
		}
		
	}

	
	private class MouseMotionHandler implements MouseMotionListener {

		public void mouseDragged(MouseEvent e) {
			int x = e.getX() / px.size;
			int y = e.getY() / px.size;
			if(x != dp.currCell.x || y != dp.currCell.y) {
				paintCell(x, y);
			}
		}

		public void mouseMoved(MouseEvent e) {}
		
	}
	
	
	private Cell px;
	private DrawingPanel dp;
	
}
