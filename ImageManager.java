import java.awt.Image;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class ImageManager {
    
	
   	public ImageManager () {
		// Private constructor to prevent instantiation
	}


	public static Image loadImage(String fileName) {
		
		Image image = null;
		InputStream inputStream = ImageManager.class.getResourceAsStream(fileName);
		
		if (inputStream != null) {
			try {
				image = ImageIO.read(inputStream);
			} catch (IOException e) {
				System.out.println("Error loading image: " + e.getMessage());
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Error closing input stream: " + e.getMessage());
				}
			}
		} else {
			System.out.println("Error loading image: " + fileName + " not found");
		}
		return image;
	}



	public static BufferedImage loadBufferedImage(String filename) {
		
		BufferedImage image = null;
		InputStream inputStream = ImageManager.class.getResourceAsStream(filename);
		
		if (inputStream != null) {
			try {
				image = ImageIO.read(inputStream);
			} catch (IOException e) {
				System.out.println("Error loading image: " + e.getMessage());
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("Error closing input stream: " + e.getMessage());
				}
			}
		} else {
			System.out.println("Error loading image: " + filename + " not found");
		}
		return image;
	}



	public static BufferedImage copyImage(BufferedImage src) {
		
		if (src == null)
			return null;

		int imWidth = src.getWidth();
		int imHeight = src.getHeight();

		BufferedImage copy = new BufferedImage (imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);

    	Graphics2D g2d = copy.createGraphics();

    	g2d.drawImage(src, 0, 0, null);
    	g2d.dispose();

    	return copy;  
	}

}
