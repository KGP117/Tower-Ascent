import java.awt.Graphics2D;
import javax.swing.JFrame;


public class BackgroundManager {

	private String bgImages[] = {"images/background/background1.png"};
	private String bgImages1[] = {"images/background/background2.png"};
	private String bgImages2[] = {"images/background/background2.png"};

  	private int moveAmountX[] = {10, 1};

  	private int moveAmountY[] = {50, 0};

  	private Background[] backgrounds;
	private Background[] backgrounds1;
	private Background[] backgrounds2;
  	private int numBackgrounds;
	


  	public BackgroundManager(JFrame window, int moveSize) {

    	numBackgrounds = bgImages.length;
    	backgrounds = new Background[numBackgrounds];
		backgrounds1 = new Background[numBackgrounds];
		backgrounds2 = new Background[numBackgrounds];

        for (int i = 0; i < numBackgrounds; i++) {
            backgrounds[i] = new Background(window, ImageManager.loadImage(bgImages[i]), moveAmountX[i], moveAmountY[i]);
        }
        for (int i = 0; i < bgImages1.length; i++) {
            backgrounds1[i] = new Background(window, ImageManager.loadImage(bgImages1[i]), moveAmountX[i], moveAmountY[i]);
        }
        for (int i = 0; i < bgImages2.length; i++) {
            backgrounds2[i] = new Background(window, ImageManager.loadImage(bgImages2[i]), moveAmountX[i], moveAmountY[i]);
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



  	public void draw(Graphics2D g2, int level) { 

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
      		backgrounds1[i].draw(g2);
		}
  	}

}
