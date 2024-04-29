import java.awt.Graphics2D;
import javax.swing.JFrame;

/* BackgroundManager manages many backgrounds (wraparound images 
   used for the game's background). 

   Backgrounds 'further back' move slower than ones nearer the
   foreground of the game, creating a parallax distance effect.

   When a sprite is instructed to move left or right, the sprite
   doesn't actually move, instead the backgrounds move in the 
   opposite direction (right or left).

*/


public class BackgroundManager {

	private String bgImages[] = {"images/background1.png", "images/clouds.png"};
	private String bgImages1[] = {"images/background2.png"};
	private String bgImages2[] = {"images/background3.jpeg"};

  	private int moveAmountX[] = {10, 1};  
						// pixel amounts to move each background left or right
     					// a move amount of 0 makes a background stationary

  	private int moveAmountY[] = {50, 0};  
						// pixel amounts to move each background left or right
     					// a move amount of 0 makes a background stationary	

  	private Background[] backgrounds;
	private Background[] backgrounds1;
	private Background[] backgrounds2;
  	private int numBackgrounds;

  	private JFrame window;			// JFrame on which backgrounds are drawn

	
  	public BackgroundManager(JFrame window, int moveSize) {
						// ignore moveSize
    	this.window = window;

    	numBackgrounds = bgImages.length;
    	backgrounds = new Background[numBackgrounds];
		backgrounds1 = new Background[numBackgrounds];
		backgrounds2 = new Background[numBackgrounds];

    	for (int i = 0; i < numBackgrounds; i++) {
       		backgrounds[i] = new Background(window, bgImages[i], moveAmountX[i], moveAmountY[i]);
    	}
		for (int i = 0; i < 1; i++) {
			backgrounds1[i] = new Background(window, bgImages1[i], moveAmountX[i], moveAmountY[i]);
		}
		for (int i = 0; i < 1; i++) {
			backgrounds2[i] = new Background(window, bgImages2[i], moveAmountX[i], moveAmountY[i]);
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

  	public void draw (Graphics2D g2, int level) { 

		if (level == 1){
			for (int i=0; i < numBackgrounds; i++)
      		backgrounds[i].draw(g2);
		}
		if (level == 2){
			for (int i=0; i < 1; i++)
      		backgrounds1[i].draw(g2);
		}
		if (level == 3){
			for (int i=0; i < 1; i++)
      		backgrounds2[i].draw(g2);
		}
  	}

}
