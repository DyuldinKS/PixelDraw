import java.awt.Dimension;
import java.awt.Insets;

public class Config {
	
	final static protected String name = "PixelDraw";
	
	final static Dimension frame = new Dimension(1280, 720);

	final static Insets minInsets = new Insets(5, 5, 5, 5);
	final static protected int borderTop = 30;
	
	final static protected int cellAmountToPaintForKeyPress = 3;
	
	final static protected int pxSize = 12;
	final static protected Dimension colorButton = new Dimension(48, 48);
	
	final static protected Dimension field = new Dimension(
			(frame.width - minInsets.left - minInsets.right) / pxSize,
			(frame.height - minInsets.top - minInsets.bottom - borderTop - colorButton.height) / pxSize
		);
	
	final static protected String colorsPath = "./src/materialColors.txt";
	final static protected String paintIconPath = "./src/images/reset.png";
	final static protected String colorfulIconPath = "./src/images/colorful.png";
	
}
