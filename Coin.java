import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public class Coin {

	private static final int XSIZE = 50;		// width of the image
	private static final int YSIZE = 50;		// height of the image

    private Animation animCoin;

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite
    private int dx;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public Coin (Player player, int x, int y) {
			
		this.x = x;
        this.y = y;
		dx = 2;

		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

        Image coin1 = ImageManager.loadImage("images/coin1.png");
        Image coin2 = ImageManager.loadImage("images/coin2.png");
        Image coin3 = ImageManager.loadImage("images/coin3.png");

        animCoin = new Animation(true);

        animCoin.addFrame(coin1, 100);
        animCoin.addFrame(coin2, 100);
        animCoin.addFrame(coin3, 100);
        
    }

    public void draw (Graphics2D g2, int offSetX) {
        if (!animCoin.isStillActive()) {
			return;
		}
		g2.drawImage(animCoin.getImage(), x + offSetX, y, XSIZE, YSIZE, null);
	}

    public void start() {
		animCoin.start();
	}

    public boolean collidesWithPlayer () {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (myRect.intersects(playerRect)) {
			return true;
		}
		else
			return false;
	}

    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {		
        if (!animCoin.isStillActive()) {
			return;
		}
		animCoin.update();

/* 		x = x + dx;

		if (x < 650 || x > 750)
			dx = dx * -1; */

	}


    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

}
