import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Background {
  	private Image bgImage;
  	private int bgImageWidth;      		// width of the background (>= panel Width)
	private int bgImageHeight;      	// height of the background (>= panel Height)

	private Dimension dimension;

 	private int bgX;
	private int backgroundX;
	private int backgroundX2;
	private int bgDX;			// size of the background move (in pixels)

	private int bgY;
	private int backgroundY;
	private int backgroundY2;
	private int bgDY;			// size of the background move (in pixels)


	public Background(JFrame window, String imageFile, int bgDX, int bgDY) {

    		this.bgImage = loadImage(imageFile);
    		bgImageWidth = bgImage.getWidth(null);	// get width of the background

		//System.out.println ("bgImageWidth = " + bgImageWidth);

		dimension = window.getSize();

		if (bgImageWidth < dimension.width)
      			//System.out.println("Background width < panel width");

    		this.bgDX = bgDX;

  	}


  	public void moveRight() {

		if (bgX == 0) {
			backgroundX = 0;
			backgroundX2 = bgImageWidth;			
		}

		bgX = bgX - bgDX;

		backgroundX = backgroundX - bgDX;
		backgroundX2 = backgroundX2 - bgDX;

		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
			//System.out.println ("Background change: bgX = " + bgX); 
			backgroundX = 0;
			backgroundX2 = bgImageWidth;
		}
  	}


  	public void moveLeft() {
	
		if (bgX == 0) {
			backgroundX = bgImageWidth * -1;
			backgroundX2 = 0;			
		}

		bgX = bgX + bgDX;
				
		backgroundX = backgroundX + bgDX;	
		backgroundX2 = backgroundX2 + bgDX;

		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
			//System.out.println ("Background change: bgX = " + bgX); 
			backgroundX = bgImageWidth * -1;
			backgroundX2 = 0;
		}			
   	}


	public void moveUp() {

		if (bgY == 0) {
			backgroundY = 0;
			backgroundY2 = bgImageHeight;			
		}

		bgY = bgY - bgDY;

		backgroundY = backgroundY - bgDY;
		backgroundY = backgroundY2 - bgDY;

		if ((bgY + bgImageHeight) % bgImageHeight == 0) {
			//System.out.println ("Background change: bgY = " + bgY); 
			backgroundY = 0;
			backgroundY2 = bgImageHeight;
		}
  	}


	public void moveDown() {

		if (bgX == 0) {
			backgroundX = 0;
			backgroundX2 = bgImageWidth;			
		}

		bgX = bgX - bgDX;

		backgroundX = backgroundX - bgDX;
		backgroundX2 = backgroundX2 - bgDX;

		if ((bgX + bgImageWidth) % bgImageWidth == 0) {
			//System.out.println ("Background change: bgX = " + bgX); 
			backgroundX = 0;
			backgroundX2 = bgImageWidth;
		}
  	}
 

  	public void draw (Graphics2D g2) {
		g2.drawImage(bgImage, backgroundX, 0, null);
		g2.drawImage(bgImage, backgroundX2, 0, null);
  	}


  	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
  	}

}
