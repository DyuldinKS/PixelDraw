import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

public class DrawingPanel extends JPanel{
	
	public DrawingPanel() {
		
		super();
		
		readColors(Config.colorsPath);
		currColorIndex = (int)(Math.random() * colors.size());
		colorfulPainting = false;
		field = new int[Config.field.width][Config.field.height];
		resetField();
		
		Canvas canvas = new Canvas(this);
		canvas.setPreferredSize(
				new Dimension(
						Config.field.width * Config.pxSize, 
						Config.field.height * Config.pxSize));
		add(canvas);
		
		ColorPanel colorPanel = new ColorPanel(this, colors.size());
        colorPanel.setLayout(new GridLayout(1, colors.size()));
        add(colorPanel);
        
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
		field[x][y] = currColorIndex;
		if(colorfulPainting) {
			currColorIndex = (currColorIndex + 1) % colors.size();
		}
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
	
	protected void enableRepaint() { repaint = true; }
	protected void disableRepaint() { repaint = false; }
	
	protected void enableColorfulPainting() { colorfulPainting = true; }
	protected void disableColorfulPainting() { colorfulPainting = false; }
	
	private int[][] field;
	private List<Color> colors;
	private int currColorIndex;
	protected boolean repaint;
	private boolean colorfulPainting;
	
}
