import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public class DungeonDoor {

	private int XSIZE = 80;		// width of the image
	private int YSIZE = 100;		// height of the image

    private Animation animDoor;

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public DungeonDoor (Player player, int x, int y, double displayScale) {
			
		this.x = x;
        this.y = y;
		this.player = player;
        
        XSIZE = (int)(XSIZE*displayScale); 
        YSIZE = (int)(YSIZE*displayScale);

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

        Image door = ImageManager.loadImage("images/object/door/dungeon_door.png");

        animDoor = new Animation(true);

        animDoor.addFrame(door, 100);
        
    }

    public void draw (Graphics2D g2, int offSetX) {
        if (!animDoor.isStillActive()) {
			return;
		}
		g2.drawImage(animDoor.getImage(), x + offSetX, y, XSIZE, YSIZE, null);
	}

    public void start() {
		animDoor.start();
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
        if (!animDoor.isStillActive()) {
			return;
		}
		animDoor.update();

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
