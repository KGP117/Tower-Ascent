import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public class Enemy {

	private static final int XSIZE = 60;		// width of the image
	private static final int YSIZE = 100;		// height of the image

    private Animation groundWalkLeftAnim;
    private Animation groundWalkRightAnim;
    private Animation groundAttackLeftAnim;
    private Animation groundAttackRightAnim;
    private Animation groundHitLeftAnim;
    private Animation groundHitRightAnim;
    

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite
    private int dx;

    private int direction = 2;
    private boolean hit = false;
    private boolean attack = false;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public Enemy (Player player) {
			
		x = 900;
		y = 565;
		dx = 2;

		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;


        Image groundEnemyLeft1 = ImageManager.loadImage("images/ground1_left.png");
        Image groundEnemyLeft2 = ImageManager.loadImage("images/ground2_left.png");
        Image groundEnemyLeft3 = ImageManager.loadImage("images/ground3_left.png");
        Image groundEnemyLeft4 = ImageManager.loadImage("images/ground4_left.png");

        groundWalkLeftAnim = new Animation(true);

        groundWalkLeftAnim.addFrame(groundEnemyLeft1, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft2, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft3, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft4, 100);


        Image groundEnemyRight1 = ImageManager.loadImage("images/ground1_right.png");
        Image groundEnemyRight2 = ImageManager.loadImage("images/ground2_right.png");
        Image groundEnemyRight3 = ImageManager.loadImage("images/ground3_right.png");
        Image groundEnemyRight4 = ImageManager.loadImage("images/ground4_right.png");

        groundWalkRightAnim = new Animation(true);

        groundWalkRightAnim.addFrame(groundEnemyRight1, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight2, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight3, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight4, 100);


        Image groundAttackLeft1 = ImageManager.loadImage("images/groundAttack_1_left.png");
        Image groundAttackLeft2 = ImageManager.loadImage("images/groundAttack_2_left.png");
        Image groundAttackLeft3 = ImageManager.loadImage("images/groundAttack_3_left.png");

        groundAttackLeftAnim = new Animation(true);

        groundAttackLeftAnim.addFrame(groundAttackLeft1, 100);
        groundAttackLeftAnim.addFrame(groundAttackLeft2, 100);
        groundAttackLeftAnim.addFrame(groundAttackLeft3, 100);


        Image groundAttackRight1 = ImageManager.loadImage("images/groundAttack_1_right.png");
        Image groundAttackRight2 = ImageManager.loadImage("images/groundAttack_2_right.png");
        Image groundAttackRight3 = ImageManager.loadImage("images/groundAttack_3_right.png");

        groundAttackRightAnim = new Animation(true);

        groundAttackRightAnim.addFrame(groundAttackRight1, 100);
        groundAttackRightAnim.addFrame(groundAttackRight2, 100);
        groundAttackRightAnim.addFrame(groundAttackRight3, 100);


        Image groundHitLeft1 = ImageManager.loadImage("images/groundhit_left.png");

        groundHitLeftAnim = new Animation(true);

        groundHitLeftAnim.addFrame(groundHitLeft1, 100);

        Image groundHitRight1 = ImageManager.loadImage("images/groundhit_right.png");

        groundHitRightAnim = new Animation(true);

        groundHitRightAnim.addFrame(groundHitRight1, 100);

    }

    public void draw (Graphics2D g2, int offsetX) {
        
        if (direction == 1){
            
            if (!attack && !hit){
                g2.drawImage(groundWalkLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else
            if (attack){
                g2.drawImage(groundAttackLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else 
            if (hit){
                g2.drawImage(groundHitLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                g2.drawImage(groundWalkRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else
            if (attack){
                g2.drawImage(groundAttackRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else 
            if (hit){
                g2.drawImage(groundHitRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }
        }
	}


    public void start(int direction) {
		
        if (direction == 1){
            
            if (!attack && !hit){
                groundWalkLeftAnim.start();
            }

            else
            if (attack){
                groundAttackLeftAnim.start();
            }

            else 
            if (hit){
                groundHitLeftAnim.start();
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                groundWalkRightAnim.start();
            }

            else
            if (attack){
                groundAttackRightAnim.start();
            }

            else 
            if (hit){
                groundHitRightAnim.start();
            }
        }
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
        
        if (direction == 1){
            
            if (!attack && !hit){
                if (!groundWalkLeftAnim.isStillActive()){
                    return;
                }
                groundWalkLeftAnim.update();
            }

            else
            if (attack){
                if (!groundAttackLeftAnim.isStillActive()){
                    return;
                }
                groundAttackLeftAnim.update();
            }

            else 
            if (hit){
                if (!groundHitLeftAnim.isStillActive()){
                    return;
                }
                groundHitLeftAnim.update();
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                if (!groundWalkRightAnim.isStillActive()){
                    return;
                }
                groundWalkRightAnim.update();
            }

            else
            if (attack){
                if (!groundAttackRightAnim.isStillActive()){
                    return;
                }
                groundAttackRightAnim.update();
            }

            else 
            if (hit){
                if (!groundHitRightAnim.isStillActive()){
                    return;
                }
                groundHitRightAnim.update();
            }
        }

		x = x + dx;

		if (x < 850 || x > 950)
            //changeDirection();
			dx = dx * -1;

	}

    public int changeDirection(){
        
        if (direction == 1){
            //start(direction);
            direction = 2;

        }
        else
        if (direction == 2){
            //start(direction);
            direction = 1;
        }

        return direction;
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
