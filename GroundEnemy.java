import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public class GroundEnemy {

    // Not implemented yet
    // Use the tile map to do collision detection with enemies
    private TileMap tileMap;

	private static final int XSIZE = 100;		// width of the image
	private static final int YSIZE = 100;		// height of the image

    private Animation groundWalkLeftAnim;
    private Animation groundWalkRightAnim;
    private Animation groundAttackLeftAnim;
    private Animation groundAttackRightAnim;
    private Animation groundHitLeftAnim;
    private Animation groundHitRightAnim;
    

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite

    private int initialX;

    private int dx;

    private int direction = 2;
    private boolean hit = false;
    private boolean attack = false;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public GroundEnemy (TileMap tilemap, Player player, int x, int y) {
			
		this.x = x;
		this.y = y;

        initialX = x;

		dx = 2;

		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;


        Image groundEnemyLeft1 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft1.png");
        Image groundEnemyLeft2 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft2.png");
        Image groundEnemyLeft3 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft3.png");
        Image groundEnemyLeft4 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft4.png");
        Image groundEnemyLeft5 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft5.png");
        Image groundEnemyLeft6 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft6.png");
        Image groundEnemyLeft7 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft7.png");
        Image groundEnemyLeft8 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft8.png");
        Image groundEnemyLeft9 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft9.png");
        Image groundEnemyLeft10 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft10.png");
        Image groundEnemyLeft11 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft11.png");
        Image groundEnemyLeft12 = ImageManager.loadImage("images/enemy/ground/skeletonWalkLeft12.png");

        groundWalkLeftAnim = new Animation(true);

        groundWalkLeftAnim.addFrame(groundEnemyLeft1, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft2, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft3, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft4, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft5, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft6, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft7, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft8, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft9, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft10, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft11, 100);
        groundWalkLeftAnim.addFrame(groundEnemyLeft12, 100);


        Image groundEnemyRight1 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight1.png");
        Image groundEnemyRight2 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight2.png");
        Image groundEnemyRight3 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight3.png");
        Image groundEnemyRight4 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight4.png");
        Image groundEnemyRight5 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight5.png");
        Image groundEnemyRight6 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight6.png");
        Image groundEnemyRight7 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight7.png");
        Image groundEnemyRight8 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight8.png");
        Image groundEnemyRight9 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight9.png");
        Image groundEnemyRight10 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight10.png");
        Image groundEnemyRight11 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight11.png");
        Image groundEnemyRight12 = ImageManager.loadImage("images/enemy/ground/skeletonWalkRight12.png");

        groundWalkRightAnim = new Animation(true);

        groundWalkRightAnim.addFrame(groundEnemyRight1, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight2, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight3, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight4, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight5, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight6, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight7, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight8, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight9, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight10, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight11, 100);
        groundWalkRightAnim.addFrame(groundEnemyRight12, 100);


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


    public boolean isInRange () {
		Rectangle2D.Double myRect = getRangeRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (myRect.intersects(playerRect)) {
			return true;
		}
		else
			return false;
	}


    public Rectangle2D.Double getRangeRectangle() {
		return new Rectangle2D.Double (x-200, y-50, XSIZE+400, YSIZE);
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

        if (isInRange()){

            initialX = getX();

            if (player.getX() < getX()) {
                if (direction != 1) {
                    groundWalkRightAnim.stop();
                    groundWalkLeftAnim.start();
                    direction = 1;
                }
                dx = -10;
            } else {
                if (direction != 2) {
                    groundWalkLeftAnim.stop();
                    groundWalkRightAnim.start();
                    direction = 2;
                }
                dx = 10;
            }
        }
        else {
            if (x < initialX-50 || x > initialX+50)
            
			dx = dx * -1;
            changeDirection();
        }
		x = x + dx;
	}

    public void changeDirection(){
        
        if (direction == 1 && dx > 0) {
            groundWalkLeftAnim.stop();
            groundWalkRightAnim.start();
            direction = 2;
        } else if (direction == 2 && dx < 0) {
            groundWalkRightAnim.stop();
            groundWalkLeftAnim.start();
            direction = 1;
        }
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
