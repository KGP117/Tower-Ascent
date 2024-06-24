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



	public Background(JFrame window, Image image, int bgDX, int bgDY) {

    	this.bgImage = image;
    	bgImageWidth = bgImage.getWidth(null);

		dimension = window.getSize();

		if (bgImageWidth < dimension.width)
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
			backgroundX = bgImageWidth * -1;
			backgroundX2 = 0;
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
