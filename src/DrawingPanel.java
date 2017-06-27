import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

public class DrawingPanel extends JPanel{
	
	// field contains color indexes of the color list
	private int[][] field;
	// the last painted cell
	protected Cell currCell;
	// color list
	private ArrayList<Color> colors;
	// index of the currently used color
	private int currColorIndex;
	// index of the background color
	private int bgColorIndex;
	// the canvas repaint mode switcher
	private boolean repaint;
	
	protected enum DrawingTool { BRUSH, COLORFUL_BRUSH, ERASER };
	// current drawing tool
	private DrawingTool drawingTool;
	
	private Canvas canvas;

	
	public DrawingPanel() {
		
		super();
		
		field = new int[Config.field.width][Config.field.height];
		currCell = new Cell(Config.pxSize);
		colors = readColors(Config.colorsPath);
		currColorIndex = (int)(Math.random() * colors.size());
		drawingTool = DrawingTool.BRUSH;
		eraseField();
		
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
    	    	keyPressHandler( e.getKeyCode() );
    	    }
    	    public void keyReleased(KeyEvent e) { }
    	    public void keyTyped(KeyEvent e) { }
    	});
        
	}
	
	
	protected void eraseField() {
		for(int[] row : field) {
			for (int i = 0; i < row.length; i++) {
				row[i] = currColorIndex;
				// uncomment the line below to fill the canvas by random color pixels
				// setCurrentColor( (int )(Math.random() * colors.size()) );
			}
			// uncomment the line below to fill the canvas by random colorful columns
			// setCurrentColor( (int )(Math.random() * colors.size()) );
		}
		bgColorIndex = currColorIndex;
		currColorIndex = (currColorIndex + 1) % colors.size();
		currCell.setCoords(Config.field.width / 2, Config.field.height / 2);
		enableRepaint();
		repaint();
	}
	
	
	private ArrayList<Color> readColors(java.net.URL url) {
		
		ArrayList<Color> colors;
		try ( InputStream stream = url.openStream() ) {
			BufferedReader reader = new BufferedReader( new InputStreamReader(stream) );
			colors = (ArrayList<Color>) reader.lines()
					.map(this::parseStringToColor)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Colors file not found. Default colors has used.");
			e.printStackTrace();
			colors = new ArrayList<Color>(
				Arrays.asList( 
						new Color(244, 67, 54), new Color(33, 150, 243), 
						new Color(76, 175, 80), new Color(255, 235, 59) 
					)
			);
		}
		return colors;
		
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
		if(drawingTool == DrawingTool.COLORFUL_BRUSH) {
			setCurrentColor( (currColorIndex + 1) % colors.size() );
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
	
	
	protected void pickColorfulBrush() { drawingTool = DrawingTool.COLORFUL_BRUSH; }
	protected void pickBrush() { drawingTool = DrawingTool.BRUSH; }
	protected void pickEraser() { 
		drawingTool = DrawingTool.ERASER;
		setCurrentColor( bgColorIndex );
	}
	
	
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
				
			case KeyEvent.VK_SPACE:
				pickEraser();
				System.out.println("space:" + keyCode );
				return;
			// erase field
			case KeyEvent.VK_BACK_SPACE:
				eraseField();
				return;
    	}
    	
    	if(49 <= keyCode && keyCode <= 57) {
    		setCurrentColor( (keyCode - 49) % colors.size() );
    	}
    	
	}
	
}
