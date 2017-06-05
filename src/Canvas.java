import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;



public class Canvas extends JPanel {
	
	public Canvas(DrawingPanel drawingPanel) {
		super();
		
		this.dp = drawingPanel;
		this.px = new Cell(Config.cellSize);
		addMouseListener(new MouseHandler()); // extends MouseAdapter
		addMouseMotionListener(new MouseMotionHandler());
//		addKeyListener(new KeyHandler());
		setFocusable(true);
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		int[][] field = dp.getField();
		List<Color> colors = dp.getColors();
		
		if(dp.repaint) {            
			int colorIndex = field[0][0];
			g2D.setColor( dp.getColor(colorIndex) );
			
			for(int i = 0; i < field.length; i++){
				for(int j = 0; j < field[i].length; j++) {
					if( colorIndex != field[i][j] ) {
						colorIndex = field[i][j];
						g2D.setColor( dp.getColor(colorIndex) );
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
		dp.updateCellColor(x, y);
		dp.repaint = false;
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
			paintCell(e.getX() / px.size, e.getY() / px.size);
		}

		public void mouseMoved(MouseEvent e) {}
		
	}
	
	
	private Cell px;
	private DrawingPanel dp;
	
}
