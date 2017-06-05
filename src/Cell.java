import java.awt.Point;

public class Cell extends Point {
	
	public Cell(int size) {
		super();
		this.size = size;
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected int size;
	
}
