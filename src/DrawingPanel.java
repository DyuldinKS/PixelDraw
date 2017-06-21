import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

public class DrawingPanel extends JPanel{
	
	// field contains color indexes of the color list
	private int[][] field;
	protected Cell currCell;
	// color list
	private List<Color> colors;
	// index of the currently used color
	private int currColorIndex;
	// the canvas repaint mode switcher
	private boolean repaint;
	// the colorful painting switcher
	private boolean colorfulPainting;
	
	private Canvas canvas;
	
	public DrawingPanel() {
		
		super();
		
		readColors(Config.colorsPath);
		currColorIndex = (int)(Math.random() * colors.size());
		colorfulPainting = false;
		field = new int[Config.field.width][Config.field.height];
		currCell = new Cell(Config.pxSize);
		resetField();
		
		canvas = new Canvas(this);
		canvas.setPreferredSize(
				new Dimension(
						Config.field.width * Config.pxSize, 
						Config.field.height * Config.pxSize));
		add(canvas);
		
		ColorPanel colorPanel = new ColorPanel(this, colors.size());
        colorPanel.setLayout(new GridLayout(1, colors.size()));
        add(colorPanel);

        setFocusable(true);
        addKeyListener(new KeyListener() {
    	    public void keyPressed(KeyEvent e) {
    	    	System.out.println("ke pressed");
    	    	keyPressHandler( e.getKeyCode() );
    	    }
    	    public void keyReleased(KeyEvent e) {
    	    	System.out.println("key released");
    	    }
    	    public void keyTyped(KeyEvent e) {
    	    	System.out.println("key typed");
    	    }
    	});
        
	}
	
	
	protected void resetField() {
		for(int[] row : field) {
			for (int i = 0; i < row.length; i++) {
				row[i] = currColorIndex;
				// uncomment the line below to fill the canvas by random color pixels
				// currColorIndex = (int )(Math.random() * colors.size());
			}
		}
		currColorIndex = (currColorIndex + 1) % colors.size();
		currCell.setCoords(Config.field.width / 2, Config.field.height / 2);
		enableRepaint();
		repaint();
	}
	
	
	private void readColors(String fileName) {
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			colors = stream
					.map(this::parseStringToColor)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Colors file not found");
			e.printStackTrace();
		}
		
	}
	
	
	private Color parseStringToColor(String color) {
		int[] channels = Stream.of(color.split(","))
			.mapToInt(Integer::parseInt)
			.toArray();
		
		return new Color(channels[0], channels[1], channels[2]);
	}
	
	
	protected List<Color> getColorList() {
		return Collections.unmodifiableList(colors);
	}	
	
	protected void updatePixelColor(int x, int y) {
		if(colorfulPainting) {
			currColorIndex = (currColorIndex + 1) % colors.size();
		}
		field[x][y] = currColorIndex;
		currCell.setCoords(x, y);
	}
	
	protected Color getCurrColor() {
		return colors.get(currColorIndex);
	}
	
	protected Color getColor(int index) {
		return colors.get(index);
	}
	
	protected Color getColor(int x, int y) {
		return colors.get( field[x][y] );
	}
	
	protected void setCurrentColor(int colorIndex) {
		currColorIndex = colorIndex;
	}
	
	protected boolean isRepaintMode() { return repaint == true; }
	protected void enableRepaint() { repaint = true; }
	protected void disableRepaint() { repaint = false; }
	
	protected void enableColorfulPainting() { colorfulPainting = true; }
	protected void disableColorfulPainting() { colorfulPainting = false; }
	
	
	private void keyPressHandler(int keyCode) {
		
		int n = Config.cellAmountToPaintForKeyPress, i;
		
    	switch( keyCode ) {
    	case 65: // a
			case KeyEvent.VK_LEFT:
				for(i = 0; i < n && currCell.x > 0; i++) {
					canvas.paintCell(--currCell.x, currCell.y);
				}
				return;
				
			case 68: // d
			case KeyEvent.VK_RIGHT:
				for(i = 0; i < n && currCell.x < Config.field.width - 1; i++) {
					canvas.paintCell(++currCell.x, currCell.y);
				}
				return;
				
			case 87: // w
			case KeyEvent.VK_UP:
				for(i = 0; i < n && currCell.y > 0; i++) {
					canvas.paintCell(currCell.x, --currCell.y);
				}
				return;
				
			case 83: // s
			case KeyEvent.VK_DOWN:
				for(i = 0; i < n && currCell.y < Config.field.height - 1; i++) {
					canvas.paintCell(currCell.x, ++currCell.y);
				}
				return;
			// previous color	
			case 81: // q
				setCurrentColor( (currColorIndex - 1 + colors.size()) % colors.size() );
				return;
			// next color
			case 69: // e
				setCurrentColor( (currColorIndex + 1) % colors.size() );
				return;
			// erase field
			case 8: // backspace
				resetField();
				return;
    	}
    	
    	if(49 <= keyCode && keyCode <= 57) {
    		setCurrentColor( (keyCode - 49) % colors.size() );
    	}
    	
	}
	
}
