import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Point;

public class Sprite {

    private static final int DX = 6;	// amount of X pixels to move in one keystroke
   	private static final int DY = 32;	// amount of Y pixels to move in one keystroke

    private static final int TILE_SIZE = 64;

    //private Animation animation;
    private int x;          // x-position of sprite
    private int y;          // y-position of sprite

    private JFrame window;		// reference to the JFrame on which the sprite is drawn
    private TileMap tileMap;
    private BackgroundManager bgManager;

    Graphics2D g2;
    private Dimension dimension;

    private Image spriteImage, spriteLeftImage, spriteRightImage;
    

    public Sprite (JFrame window, TileMap t, BackgroundManager b) {
        this.window = window;

        tileMap = t;			// tile map on which the player's sprite is displayed
        bgManager = b;			// instance of BackgroundManager

        spriteLeftImage = ImageManager.loadImage("images/enemyLeft.gif");
        spriteRightImage = ImageManager.loadImage("images/enemyRight.gif");
        spriteImage = spriteLeftImage;

     }

    public void update(long elapsedTime) {
        // Update the sprite's position based on its velocity
        x += DX * elapsedTime;
        y += DY * elapsedTime;

        // Update the animation
        //animation.update(elapsedTime);
    }

    public void draw(Graphics2D g2) {
        spriteLeftImage = ImageManager.loadImage("images/enemyLeft.png");
        // Draw the current sprite
        g2.drawImage(spriteLeftImage, x, y, null);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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

/*     public int getWidth() {
        return animation.getImage().getWidth(null);
    } */

/*     public int getHeight() {
        return animation.getImage().getHeight(null);
    } */

    @Override
    public Object clone() {
        try {
            // Perform a shallow copy
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since Sprite implements Cloneable
            throw new InternalError(e);
        }
    }

    public Image getImage() {
        return spriteImage;
     }

}
