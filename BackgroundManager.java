/* BackgroundManager manages many backgrounds (wraparound images 
   used for the game's background). 

   Backgrounds 'further back' move slower than ones nearer the
   foreground of the game, creating a parallax distance effect.

   When a sprite is instructed to move left or right, the sprite
   doesn't actually move, instead the backgrounds move in the 
   opposite direction (right or left).

*/

import java.awt.Graphics2D;
import javax.swing.JFrame;


public class BackgroundManager {

	private String bgImages[] = {"images/layer_01.png"};

  	private int moveAmountX[] = {0};  
						// pixel amounts to move each background left or right
     					// a move amount of 0 makes a background stationary

  	private int moveAmountY[] = {0};  
						// pixel amounts to move each background left or right
     					// a move amount of 0 makes a background stationary	

  	private Background[] backgrounds;
  	private int numBackgrounds;

  	private JFrame window;			// JFrame on which backgrounds are drawn

  	public BackgroundManager(JFrame window, int moveSize) {
						// ignore moveSize
    	this.window = window;

    	numBackgrounds = bgImages.length;
    	backgrounds = new Background[numBackgrounds];

    	for (int i = 0; i < numBackgrounds; i++) {
       		backgrounds[i] = new Background(window, bgImages[i], moveAmountX[i], moveAmountY[i]);
    	}
  	} 


  	public void moveRight() { 
		for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].moveRight();
  	}


  	public void moveLeft() {
		for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].moveLeft();
  	}

	public void moveUp() {
		for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].moveUp();
  	}

	public void moveDown() {
		for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].moveDown();
  	}


  	// The draw method draws the backgrounds on the screen. The
  	// backgrounds are drawn from the back to the front.

  	public void draw (Graphics2D g2) { 
		for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].draw(g2);
  	}

}
