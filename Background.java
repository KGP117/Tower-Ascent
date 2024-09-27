import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.ImageIcon;


public class Background {
	
  	private Image bgImage;
  	private int bgImageWidth;

	private Dimension dimension;

 	private int bgX;
	private int backgroundX;
	private int backgroundX2;
	private int bgDX;

	private double displayScale;



	// Constructor to initialize the Background with the window, image, scroll speed, and display scale

	public Background(JFrame window, Image image, int bgDX, int bgDY, double displayScale) {

    	this.bgImage = image;
    	bgImageWidth = bgImage.getWidth(null);

		dimension = window.getSize();

		this.displayScale = displayScale;

		if (bgImageWidth < dimension.width)
    		this.bgDX = bgDX;

  	}



	// Moves the background to the right, creating a scrolling effect

  	public void moveRight() {

		if (bgX == 0) {
			backgroundX = 0;
			backgroundX2 = bgImageWidth;			
		}

		bgX = bgX - bgDX;

		backgroundX = backgroundX - bgDX;
		backgroundX2 = backgroundX2 - bgDX;

		if ((bgX + bgImageWidth) % bgImageWidth == 0) { 
			backgroundX = 0;
			backgroundX2 = bgImageWidth;
		}
  	}



	// Moves the background to the left, creating a scrolling effect

  	public void moveLeft() {
	
		if (bgX == 0) {
			backgroundX = bgImageWidth * -1;
			backgroundX2 = 0;			
		}

		bgX = bgX + bgDX;
				
		backgroundX = backgroundX + bgDX;	
		backgroundX2 = backgroundX2 + bgDX;

		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
			backgroundX = bgImageWidth * -1;
			backgroundX2 = 0;
		}			
   	}
 

	
	// Draws the background image onto the screen

  	public void draw (Graphics2D g2) {
		g2.drawImage(bgImage, backgroundX, 0, (int)(1280*displayScale), (int)(720*displayScale), null);
		g2.drawImage(bgImage, backgroundX2, 0, (int)(1280*displayScale), (int)(720*displayScale), null);
  	}



	// Loads an image from the specified file path

  	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
  	}

}
