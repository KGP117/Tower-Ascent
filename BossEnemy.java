import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public class BossEnemy {

	private static final int XSIZE = 300;		// width of the image
	private static final int YSIZE = 300;		// height of the image

    private Animation bossWalkLeftAnim;
    private Animation bossWalkRightAnim;
    private Animation bossAttackLeftAnim;
    private Animation bossAttackRightAnim;
    private Animation bossHitLeftAnim;
    private Animation bossHitRightAnim;
    

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite

    private int initialX;
    private int initialY;

    private int dx;

    private int direction = 1;
    private boolean hit = false;
    private boolean attack = false;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public BossEnemy (Player player, int x, int y) {
			
		this.x = x;
		this.y = y;

        initialX = x;
        initialY = y;

		dx = 6;

		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;


        Image bossEnemyLeft1 = ImageManager.loadImage("images/boss1_left.png");
        Image bossEnemyLeft2 = ImageManager.loadImage("images/boss2_left.png");
        Image bossEnemyLeft3 = ImageManager.loadImage("images/boss3_left.png");
        Image bossEnemyLeft4 = ImageManager.loadImage("images/boss4_left.png");

        bossWalkLeftAnim = new Animation(true);

        bossWalkLeftAnim.addFrame(bossEnemyLeft1, 100);
        bossWalkLeftAnim.addFrame(bossEnemyLeft2, 100);
        bossWalkLeftAnim.addFrame(bossEnemyLeft3, 100);
        bossWalkLeftAnim.addFrame(bossEnemyLeft4, 100);


        Image bossEnemyRight1 = ImageManager.loadImage("images/boss1_right.png");
        Image bossEnemyRight2 = ImageManager.loadImage("images/boss2_right.png");
        Image bossEnemyRight3 = ImageManager.loadImage("images/boss3_right.png");
        Image bossEnemyRight4 = ImageManager.loadImage("images/boss4_right.png");

        bossWalkRightAnim = new Animation(true);

        bossWalkRightAnim.addFrame(bossEnemyRight1, 100);
        bossWalkRightAnim.addFrame(bossEnemyRight2, 100);
        bossWalkRightAnim.addFrame(bossEnemyRight3, 100);
        bossWalkRightAnim.addFrame(bossEnemyRight4, 100);


        Image bossAttackLeft1 = ImageManager.loadImage("images/bossAttack1_left.png");
        Image bossAttackLeft2 = ImageManager.loadImage("images/bossAttack2_left.png");
        Image bossAttackLeft3 = ImageManager.loadImage("images/bossAttack3_left.png");

        bossAttackLeftAnim = new Animation(true);

        bossAttackLeftAnim.addFrame(bossAttackLeft1, 100);
        bossAttackLeftAnim.addFrame(bossAttackLeft2, 100);
        bossAttackLeftAnim.addFrame(bossAttackLeft3, 100);


        Image bossAttackRight1 = ImageManager.loadImage("images/bossAttack1_right.png");
        Image bossAttackRight2 = ImageManager.loadImage("images/bossAttack2_right.png");
        Image bossAttackRight3 = ImageManager.loadImage("images/bossAttack3_right.png");

        bossAttackRightAnim = new Animation(true);

        bossAttackRightAnim.addFrame(bossAttackRight1, 100);
        bossAttackRightAnim.addFrame(bossAttackRight2, 100);
        bossAttackRightAnim.addFrame(bossAttackRight3, 100);


        Image bossHitLeft1 = ImageManager.loadImage("images/bossHit_left.png");

        bossHitLeftAnim = new Animation(true);

        bossHitLeftAnim.addFrame(bossHitLeft1, 100);

        Image bossHitRight1 = ImageManager.loadImage("images/bossHit_right.png");

        bossHitRightAnim = new Animation(true);

        bossHitRightAnim.addFrame(bossHitRight1, 100);

    }

    public void draw (Graphics2D g2, int offsetX) {
        
        if (direction == 1){
            
            if (!attack && !hit){
                g2.drawImage(bossWalkLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else
            if (attack){
                g2.drawImage(bossAttackLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else 
            if (hit){
                g2.drawImage(bossHitLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                g2.drawImage(bossWalkRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else
            if (attack){
                g2.drawImage(bossAttackRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }

            else 
            if (hit){
                g2.drawImage(bossHitRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
            }
        }
	}


    public void start(int direction) {
		
        if (direction == 1){
            
            if (!attack && !hit){
                bossWalkLeftAnim.start();
            }

            else
            if (attack){
                bossAttackLeftAnim.start();
            }

            else 
            if (hit){
                bossHitLeftAnim.start();
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                bossWalkRightAnim.start();
            }

            else
            if (attack){
                bossAttackRightAnim.start();
            }

            else 
            if (hit){
                bossHitRightAnim.start();
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
		return new Rectangle2D.Double (x-200, y-200, XSIZE+400, YSIZE+400);
	}


	public void update() {	
        
        if (direction == 1){
            
            if (!attack && !hit){
                if (!bossWalkLeftAnim.isStillActive()){
                    return;
                }
                bossWalkLeftAnim.update();
            }

            else
            if (attack){
                if (!bossAttackLeftAnim.isStillActive()){
                    return;
                }
                bossAttackLeftAnim.update();
            }

            else 
            if (hit){
                if (!bossHitLeftAnim.isStillActive()){
                    return;
                }
                bossHitLeftAnim.update();
            }
        }

        if (direction == 2){
            
            if (!attack && !hit){
                if (!bossWalkRightAnim.isStillActive()){
                    return;
                }
                bossWalkRightAnim.update();
            }

            else
            if (attack){
                if (!bossAttackRightAnim.isStillActive()){
                    return;
                }
                bossAttackRightAnim.update();
            }

            else 
            if (hit){
                if (!bossHitRightAnim.isStillActive()){
                    return;
                }
                bossHitRightAnim.update();
            }
        }

        if (isInRange()){

            initialX = getX();

            if (player.getX() < getX()){
                if (direction == 1 && dx > 0) {
                    bossWalkLeftAnim.stop();
                    bossWalkRightAnim.start();
                    direction = 2;
                }
                dx = -10;
            }
            else
            if (player.getX() > getX()){
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
            bossWalkLeftAnim.stop();
            bossWalkRightAnim.start();
            direction = 2;
        } else if (direction == 2 && dx < 0) {
            bossWalkRightAnim.stop();
            bossWalkLeftAnim.start();
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
