import java.awt.Dimension;

public class Config {
	
	final static protected String name = "PixelDraw";
	
	final static Dimension frame = new Dimension(1280, 720);
	
	final static protected int minHorizontalPaddings = 10;
	final static protected int minVerticalPaddings = 10;
	final static protected int borderTop = 30;
	
	final static protected int cellSize = 12;
	final static protected Dimension colorButton = new Dimension(48, 48);
	
	final static protected Dimension field = new Dimension(
			(frame.width - minHorizontalPaddings) / cellSize,
			(frame.height - minVerticalPaddings - borderTop - colorButton.height) / cellSize
		);
	
	final static protected String colorsPath = "./src/materialColors.txt";
	final static protected String paintIconPath = "./src/images/reset.png";
	final static protected String colorfulIconPath = "./src/images/colorful.png";
	
}
