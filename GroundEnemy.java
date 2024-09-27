import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class GroundEnemy {

	private int enemyCollisionWidth = 92;		// width of the image
	private int enemyCollisionHeight = 100;		// height of the image

    private int enemyHitboxWidth = 92;
    private int enemyHitboxHeight = 100;

    private int enemyAttackHitboxWidth = 100;
    private int enemyAttackHitboxHeight = 120;

    private int enemySpriteWidth = 184;		// width of the image
	private int enemySpriteHeight = 200;		// height of the image


    private Animation groundWalkLeftAnim;
    private Animation groundWalkRightAnim;
    private Animation groundAttackLeftAnim;
    private Animation groundAttackRightAnim;
    private Animation groundHitLeftAnim;
    private Animation groundHitRightAnim;
    

    private int enemyX;          // x-position of sprite
    private int enemyY;          // y-position of sprite

    private int initialX;

    private int dx;

    private int direction = 2;
    private boolean hit = false;
    private boolean attack = false;
    private boolean isHit = false;

    private double displayScale;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public GroundEnemy (TileMap tileMap, Player player, int x, int y, double displayScale) {

        enemyCollisionWidth = (int)(enemyCollisionWidth*displayScale);
        enemyCollisionHeight = (int)(enemyCollisionHeight*displayScale);

        enemyHitboxWidth = (int)(enemyHitboxWidth*displayScale);
        enemyHitboxHeight = (int)(enemyHitboxHeight*displayScale);
    
        enemyAttackHitboxWidth = (int)(enemyAttackHitboxWidth*displayScale);
        enemyAttackHitboxHeight = (int)(enemyAttackHitboxHeight*displayScale);

        enemySpriteWidth = (int)(enemySpriteWidth*displayScale);
        enemySpriteHeight = (int)(enemySpriteHeight*displayScale);
    
        dx = (int)(2*displayScale);
			
		enemyX = x;
		enemyY = y;

        initialX = x;

        this.displayScale = displayScale;
		this.player = player;


        String basePath = "images/enemy/ground";

        groundWalkLeftAnim = loadAnimation(basePath, "skeletonWalkLeft", 12, 100, true);
        groundWalkRightAnim = loadAnimation(basePath, "skeletonWalkRight", 12, 100, true);
        
        groundAttackLeftAnim = loadAnimation(basePath, "skeletonAttackLeft", 12, 170, false);
        groundAttackRightAnim = loadAnimation(basePath, "skeletonAttackRight", 12, 170, false);
        
        groundHitLeftAnim = loadAnimation(basePath, "skeletonHitLeft", 3, 200, false);
        groundHitRightAnim = loadAnimation(basePath, "skeletonHitRight", 3, 200, false);        

    }



    // Loads the needed animations

    private Animation loadAnimation(String basePath, String action, int frameCount, int frameDuration, boolean loop) {
        Animation animation = new Animation(loop);
        for (int i = 1; i <= frameCount; i++) {
            Image frame = ImageManager.loadImage(basePath + "/" + action + i + ".png");
            animation.addFrame(frame, frameDuration);
        }
        return animation;
    }
    


    public void draw (Graphics2D g2, int offsetX) {

        if (direction == 1){
            
            if (isHit){
                g2.drawImage(groundHitLeftAnim.getImage(), enemyX + offsetX-(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundHitLeftAnim.isStillActive()){
                    groundHitLeftAnim.start();
                    isHit = false;
                }
                groundHitLeftAnim.update();
            }

            else if (attack){
                g2.drawImage(groundAttackLeftAnim.getImage(), enemyX + offsetX-(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundAttackLeftAnim.isStillActive()){
                    groundAttackLeftAnim.start();
                }
                groundAttackLeftAnim.update();
            }

            else {
                g2.drawImage(groundWalkLeftAnim.getImage(), enemyX + offsetX -(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundWalkLeftAnim.isStillActive()){
                    groundWalkLeftAnim.start();
                }
                groundWalkLeftAnim.update();
            }


        }

        if (direction == 2){

            if (isHit){
                g2.drawImage(groundHitRightAnim.getImage(), enemyX + offsetX-(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundHitRightAnim.isStillActive()){
                    groundHitRightAnim.start();
                    isHit = false;
                }
                groundHitRightAnim.update();
            }

            else if (attack){
                g2.drawImage(groundAttackRightAnim.getImage(), enemyX + offsetX-(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundAttackRightAnim.isStillActive()){
                    groundAttackRightAnim.start();
                }
                groundAttackRightAnim.update();
            }
            
            else {
                g2.drawImage(groundWalkRightAnim.getImage(), enemyX + offsetX-(int)(50*displayScale), enemyY-(int)(50*displayScale), enemySpriteWidth, enemySpriteHeight, null);
                if (!groundWalkRightAnim.isStillActive()){
                    groundWalkRightAnim.start();
                }
                groundWalkRightAnim.update();
            }

        }

/*         // Collision Box
        g2.setColor(Color.RED);
		g2.drawRect(enemyX+offsetX + enemyCollisionWidth/4, enemyY, enemyCollisionWidth/2, enemyCollisionHeight);

        // Chase Trigger Range
        g2.setColor(Color.BLUE);
		g2.drawRect(enemyX-(int)(200*displayScale)+offsetX, enemyY-(int)(50*displayScale), enemyCollisionWidth+(int)(400*displayScale), enemyCollisionHeight);

        // Attack Trigger Range
        g2.setColor(Color.GREEN);
        g2.drawRect(enemyX-(int)(25*displayScale)+offsetX, enemyY, enemyCollisionWidth+(int)(50*displayScale), enemyCollisionHeight);

        // Enemy Attack Hitbox
        g2.setColor(Color.ORANGE);
        g2.drawRect(enemyX-(int)(45*displayScale)+offsetX, enemyY-(int)(20*displayScale), enemyAttackHitboxWidth, enemyAttackHitboxHeight);

        g2.drawRect(enemyX+(int)(35*displayScale)+offsetX, enemyY-(int)(20*displayScale), enemyAttackHitboxWidth, enemyAttackHitboxHeight); */

	}


    public boolean collidesWithPlayer() {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (myRect.intersects(playerRect)) {
			return true;
		}
		else
			return false;
	}


    public boolean gotHit() {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double playerRect = player.getAttackHitBox();
		
		if (myRect.intersects(playerRect) && player.isInHitFrame()) {
            isHit = true;
            if (direction == 1){
                initialX = initialX + 100;
            }
            else if (direction == 2){
                initialX = initialX - 100;
            }
			return true;
		}
		else {
            return false;
        }
	}


    public boolean attackHitPlayer() {

        if (attack) {
            
            if (groundAttackLeftAnim.isStillActive()) {
                if (groundAttackLeftAnim.getCurrentFrameIndex() == 5 || groundAttackLeftAnim.getCurrentFrameIndex() == 9) {
                    Rectangle2D.Double myRect = getLeftAttackHitRectangle();
                    Rectangle2D.Double playerRect = player.getBoundingRectangle();
                    return myRect.intersects(playerRect);
                }
            } 
            
            else if (groundAttackRightAnim.isStillActive()) {
                if (groundAttackRightAnim.getCurrentFrameIndex() == 5 || groundAttackRightAnim.getCurrentFrameIndex() == 9) {
                    Rectangle2D.Double myRect = getRightAttackHitRectangle();
                    Rectangle2D.Double playerRect = player.getBoundingRectangle();
                    return myRect.intersects(playerRect);
                }
            }
        }

        return false;
    }


    public boolean isInRange() {
        Rectangle2D.Double myRect = getRangeRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }


    public boolean isInAttackRange () {
		Rectangle2D.Double myRect = getAttackRangeRectangle();
		Rectangle2D.Double playerRect = player.getBoundingRectangle();
		
		if (myRect.intersects(playerRect)) {
            attack = true;
			return true;
		}
		else {
            attack = false;
			return false;
        }
	}


    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (enemyX+enemyCollisionWidth/4, enemyY, enemyCollisionWidth/2, enemyCollisionHeight);
	}

    public Rectangle2D.Double getRangeRectangle() {
		return new Rectangle2D.Double (enemyX-(int)(200*displayScale), enemyY-(int)(50*displayScale), enemyCollisionWidth+(int)(400*displayScale), enemyCollisionHeight);
	}

    public Rectangle2D.Double getAttackRangeRectangle() {
		return new Rectangle2D.Double (enemyX-(int)(25*displayScale), enemyY, enemyCollisionWidth+(int)(50*displayScale), enemyCollisionHeight);
	}

    public Rectangle2D.Double getLeftAttackHitRectangle() {
		return new Rectangle2D.Double (enemyX-(int)(45*displayScale), enemyY-(int)(20*displayScale), enemyAttackHitboxWidth, enemyAttackHitboxHeight);
	}

    public Rectangle2D.Double getRightAttackHitRectangle() {
		return new Rectangle2D.Double (enemyX+(int)(35*displayScale), enemyY-(int)(20*displayScale), enemyAttackHitboxWidth, enemyAttackHitboxHeight);
	}


	public void update() {	

        
        if (initialX < (int)(1280*displayScale)){
            if (getX() > (int)(1000*displayScale)){
                setX((int)(999*displayScale));
            }
        }

        if (initialX > (int)(1280*displayScale) && initialX < (int)(2200*displayScale)){
            if (getX() > (int)(2050*displayScale)){
                setX((int)(2050*displayScale));
            }
        }

        
        if (isHit) {
            // Enemy is currently playing hit animation, do not move
            dx = 0;
            return;
        }


        if (groundAttackLeftAnim.isStillActive() || groundAttackRightAnim.isStillActive()) {
            initialX = getX();
            dx = 0;
            return;
        }

        if (isInAttackRange()){
            initialX = getX();
            dx = 0;
            return;
        }

        else if (isInRange()){

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

            if (direction == 1){
                dx = -4;
            }
            else {
                dx = 4;
            }

            if (enemyX < initialX-50 || enemyX > initialX+50)
            
			dx = dx * -1;
            changeDirection();
        }
		enemyX = enemyX + dx;
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
        return enemyX;
    }


    public void setX(int x) {
        enemyX = x;
    }


    public int getY() {
        return enemyY;
    }


    public void setY(int y) {
        enemyY = y;
    }

}
