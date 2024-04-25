import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SolidObject {

	private int x;
	private int y;
	private int width;
	private int height;
	

	public SolidObject (int xPos, int yPos, int width, int height) { 
		x = xPos;
		y = yPos;
		this.width = width;
		this.height = height;
	}


	public void draw (Graphics2D g2) {

		Rectangle2D.Double solidObject = new Rectangle2D.Double(x, y, width, height);
		
		g2.fill(solidObject);
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}