import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;


public class Player {			

	private static final int playerWidth = 70;		// width of the image
	private static final int playerHeight = 80;		// height of the image

   	private static final int DX = 8;	// amount of X pixels to move in one keystroke

   	private static final int TILE_SIZE = 64;

   	private JFrame window;		// reference to the JFrame on which player is drawn
   	private TileMap tileMap;
   	private BackgroundManager bgManager;

   	private int playerX;			// x-position of player's sprite
   	private int playerY;			// y-position of player's sprite

   	Graphics2D g2;

	private Animation playerLeftAnim;
	private Animation playerRightAnim;

	private Animation playerJumpLeftAnim;
	private Animation playerJumpRightAnim;

	private Animation playerFallLeftAnim;
	private Animation playerFallRightAnim;
	
	private Animation playerIdleLeftAnim;
	private Animation playerIdleRightAnim;

   	private boolean jumping;
   	private int timeElapsed;
   	private int startY;

	private int direction = 0;
   	private boolean goingUp;
   	private boolean goingDown;

   	private boolean inAir;
   	private int initialVelocity;

	private int lives;
	private int numCoins;
	private int score;

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;


	// Player Constructor

   	public Player (JFrame window, TileMap t, BackgroundManager b) {
      	this.window = window;

      	tileMap = t;			// tile map on which the player's sprite is displayed
      	bgManager = b;			// instance of BackgroundManager

      	goingUp = false;
		goingDown = false;
      	inAir = false;

		lives = 3;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

		Image playerLeft1 = ImageManager.loadImage("images/player/runLeft_1.png");
        Image playerLeft2 = ImageManager.loadImage("images/player/runLeft_2.png");
        Image playerLeft3 = ImageManager.loadImage("images/player/runLeft_3.png");
        Image playerLeft4 = ImageManager.loadImage("images/player/runLeft_4.png");
		Image playerLeft5 = ImageManager.loadImage("images/player/runLeft_5.png");
        Image playerLeft6 = ImageManager.loadImage("images/player/runLeft_6.png");
        Image playerLeft7 = ImageManager.loadImage("images/player/runLeft_7.png");
        Image playerLeft8 = ImageManager.loadImage("images/player/runLeft_8.png");

        playerLeftAnim = new Animation(true);
		playerLeftAnim.addFrame(playerLeft1, 100);
        playerLeftAnim.addFrame(playerLeft2, 100);
        playerLeftAnim.addFrame(playerLeft3, 100);
        playerLeftAnim.addFrame(playerLeft4, 100);
		playerLeftAnim.addFrame(playerLeft5, 100);
        playerLeftAnim.addFrame(playerLeft6, 100);
        playerLeftAnim.addFrame(playerLeft7, 100);
        playerLeftAnim.addFrame(playerLeft8, 100);


		Image playerRight1 = ImageManager.loadImage("images/player/runRight_1.png");
        Image playerRight2 = ImageManager.loadImage("images/player/runRight_2.png");
        Image playerRight3 = ImageManager.loadImage("images/player/runRight_3.png");
        Image playerRight4 = ImageManager.loadImage("images/player/runRight_4.png");
		Image playerRight5 = ImageManager.loadImage("images/player/runRight_5.png");
        Image playerRight6 = ImageManager.loadImage("images/player/runRight_6.png");
        Image playerRight7 = ImageManager.loadImage("images/player/runRight_7.png");
        Image playerRight8 = ImageManager.loadImage("images/player/runRight_8.png");

        playerRightAnim = new Animation(true);
        playerRightAnim.addFrame(playerRight1, 100);
        playerRightAnim.addFrame(playerRight2, 100);
        playerRightAnim.addFrame(playerRight3, 100);
        playerRightAnim.addFrame(playerRight4, 100);
		playerRightAnim.addFrame(playerRight5, 100);
        playerRightAnim.addFrame(playerRight6, 100);
        playerRightAnim.addFrame(playerRight7, 100);
        playerRightAnim.addFrame(playerRight8, 100);


        Image playerJumpLeft1 = ImageManager.loadImage("images/player/jumpLeft_1.png");
		Image playerJumpLeft2 = ImageManager.loadImage("images/player/jumpLeft_2.png");
		Image playerJumpLeft3 = ImageManager.loadImage("images/player/jumpLeft_3.png");

        playerJumpLeftAnim = new Animation(true);
        playerJumpLeftAnim.addFrame(playerJumpLeft1, 100);
		playerJumpLeftAnim.addFrame(playerJumpLeft2, 100);
		playerJumpLeftAnim.addFrame(playerJumpLeft3, 100);


        Image playerJumpRight1 = ImageManager.loadImage("images/player/jumpRight_1.png");
		Image playerJumpRight2 = ImageManager.loadImage("images/player/jumpRight_2.png");
		Image playerJumpRight3 = ImageManager.loadImage("images/player/jumpRight_3.png");

        playerJumpRightAnim = new Animation(true);
        playerJumpRightAnim.addFrame(playerJumpRight1, 100);
		playerJumpRightAnim.addFrame(playerJumpRight2, 100);
		playerJumpRightAnim.addFrame(playerJumpRight3, 100);


        Image playerFallLeft1 = ImageManager.loadImage("images/player/fallLeft_1.png");
		Image playerFallLeft2 = ImageManager.loadImage("images/player/fallLeft_2.png");
		Image playerFallLeft3 = ImageManager.loadImage("images/player/fallLeft_3.png");

        playerFallLeftAnim = new Animation(true);
        playerFallLeftAnim.addFrame(playerFallLeft1, 100);
		playerFallLeftAnim.addFrame(playerFallLeft2, 100);
		playerFallLeftAnim.addFrame(playerFallLeft3, 100);


        Image playerFallRight1 = ImageManager.loadImage("images/player/fallRight_1.png");
		Image playerFallRight2 = ImageManager.loadImage("images/player/fallRight_2.png");
		Image playerFallRight3 = ImageManager.loadImage("images/player/fallRight_3.png");

        playerFallRightAnim = new Animation(true);
        playerFallRightAnim.addFrame(playerFallRight1, 100);
		playerFallRightAnim.addFrame(playerFallRight2, 100);
		playerFallRightAnim.addFrame(playerFallRight3, 100);


		Image playerIdleLeft1 = ImageManager.loadImage("images/player/idleLeft_1.png");
		Image playerIdleLeft2 = ImageManager.loadImage("images/player/idleLeft_2.png");
		Image playerIdleLeft3 = ImageManager.loadImage("images/player/idleLeft_3.png");
		Image playerIdleLeft4 = ImageManager.loadImage("images/player/idleLeft_4.png");
		Image playerIdleLeft5 = ImageManager.loadImage("images/player/idleLeft_5.png");
		Image playerIdleLeft6 = ImageManager.loadImage("images/player/idleLeft_6.png");
		Image playerIdleLeft7 = ImageManager.loadImage("images/player/idleLeft_7.png");
		Image playerIdleLeft8 = ImageManager.loadImage("images/player/idleLeft_8.png");

		playerIdleLeftAnim = new Animation(true);
		playerIdleLeftAnim.addFrame(playerIdleLeft1, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft2, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft3, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft4, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft5, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft6, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft7, 100);
		playerIdleLeftAnim.addFrame(playerIdleLeft8, 100);


		Image playerIdleRight1 = ImageManager.loadImage("images/player/idleRight_1.png");
		Image playerIdleRight2 = ImageManager.loadImage("images/player/idleRight_2.png");
		Image playerIdleRight3 = ImageManager.loadImage("images/player/idleRight_3.png");
		Image playerIdleRight4 = ImageManager.loadImage("images/player/idleRight_4.png");
		Image playerIdleRight5 = ImageManager.loadImage("images/player/idleRight_5.png");
		Image playerIdleRight6 = ImageManager.loadImage("images/player/idleRight_6.png");
		Image playerIdleRight7 = ImageManager.loadImage("images/player/idleRight_7.png");
		Image playerIdleRight8 = ImageManager.loadImage("images/player/idleRight_8.png");

		playerIdleRightAnim = new Animation(true);
		playerIdleRightAnim.addFrame(playerIdleRight1, 100);
		playerIdleRightAnim.addFrame(playerIdleRight2, 100);
		playerIdleRightAnim.addFrame(playerIdleRight3, 100);
		playerIdleRightAnim.addFrame(playerIdleRight4, 100);
		playerIdleRightAnim.addFrame(playerIdleRight5, 100);
		playerIdleRightAnim.addFrame(playerIdleRight6, 100);
		playerIdleRightAnim.addFrame(playerIdleRight7, 100);
		playerIdleRightAnim.addFrame(playerIdleRight8, 100);

   	}


	// checks if the player collides with a tile

	public Point collidesWithTile(int newX, int newY) { 	

    	int offsetY = tileMap.getOffsetY();
		int xTile = tileMap.pixelsToTiles(newX);
		int yTile = tileMap.pixelsToTiles(newY - offsetY);

		if (tileMap.getTile(xTile, yTile) != null) {
	    	Point tilePos = new Point (xTile, yTile);
	  		return tilePos;
		}
		else {
			return null;
		}
   	}


	
	public Point collidesWithTileDown(int newX, int newY) {
		int offsetY = tileMap.getOffsetY();
		int xTile = tileMap.pixelsToTiles(newX);
		int yTileFrom = tileMap.pixelsToTiles(playerY - offsetY);
		int yTileTo = tileMap.pixelsToTiles(newY - offsetY + playerHeight);

		for (int yTile = yTileFrom; yTile <= yTileTo; yTile++) {
			if (tileMap.getTile(xTile, yTile) != null) {
				Point tilePos = new Point(xTile, yTile);
				return tilePos;
			} else {
				if (tileMap.getTile(xTile + 1, yTile) != null) {
					int leftSide = (xTile + 1) * TILE_SIZE;
					if (newX + playerWidth > leftSide) {
						Point tilePos = new Point(xTile + 1, yTile);
						return tilePos;
					}
				}
			}
		}
		return null;
	}


	// checks if the player collides with a tile above them

   	public Point collidesWithTileUp (int newX, int newY) {

      	int offsetY = tileMap.getOffsetY();
	  	int xTile = tileMap.pixelsToTiles(newX);

	  	int yTileFrom = tileMap.pixelsToTiles(playerY - offsetY);
	  	int yTileTo = tileMap.pixelsToTiles(newY - offsetY);
	 
	  	for (int yTile=yTileFrom; yTile>=yTileTo; yTile--) {
			if (tileMap.getTile(xTile, yTile) != null) {
	        	Point tilePos = new Point (xTile, yTile);
	  			return tilePos;
			}
			else {
				if (tileMap.getTile(xTile+1, yTile) != null) {
					int leftSide = (xTile + 1) * TILE_SIZE;
					if (newX + playerWidth > leftSide) {
				    	Point tilePos = new Point (xTile+1, yTile);
				    	return tilePos;
			        }
				}
			}	    
	  	}

	  	return null;
   	}


	// handles player movement

   	public synchronized void move (int direction) {

      	int newX = playerX;
		int newY = playerY;
      	Point tilePos = null;

      	if (!window.isVisible ()) return;
      
      	if (direction == 1) {		// move left
			if (jumping){
				newX = playerX - 2*DX;
			}
			else {
				newX = playerX - DX;
			}
          	
	  		
			if (newX < 0) {
				playerX = 0;
				return;
	  		}
		
	  		tilePos = collidesWithTile(newX, playerY);
      	}	

      	else if (direction == 2) {		// move right
			if (jumping){
				newX = playerX + 2*DX;
			}
			else {
				newX = playerX + DX;
			}

      	  	int tileMapWidth = tileMap.getWidthPixels();

			if (newX + playerWidth >= tileMapWidth) {
				playerX = tileMapWidth - playerWidth;
				return;
			}

	  		tilePos = collidesWithTile(newX+playerWidth, playerY);			
      	}

      	else if (direction == 3 && !jumping) {	// jump
          	jump();

	  		return;
      	}
    
      	if (tilePos != null) {  
         	if (direction == 1) {
             	playerX = ((int) tilePos.getX() + 1) * TILE_SIZE;	   // keep flush with right side of tile
	 		}
        	else if (direction == 2) {
             	playerX = ((int) tilePos.getX()) * TILE_SIZE - playerWidth; // keep flush with left side of tile
	 		}
      	}

      	else {
			if (direction == 1) {

				if (goingUp){
					if (!playerJumpLeftAnim.isStillActive()){
						playerJumpLeftAnim.start();
					}
				}

				else if (goingDown){
					if (!playerFallLeftAnim.isStillActive()){
						playerFallLeftAnim.start();
					}
				}

				else if (!playerLeftAnim.isStillActive()){
					playerLeftAnim.start();
				}

				playerX = newX;
				bgManager.moveLeft();
			}
			
			else if (direction == 2) {

				if (goingUp){
					if (!playerJumpRightAnim.isStillActive()){
						playerJumpRightAnim.start();
					}
				}

				else if (goingDown){
					if (!playerFallRightAnim.isStillActive()){
						playerFallRightAnim.start();
					}
				}

				else if (!playerRightAnim.isStillActive()){
					playerRightAnim.start();
				}

				playerX = newX;
				bgManager.moveRight();
			}
			
			else if (direction == 3) {
				playerY = newY;
			}

			if (isInAir()) {
				fall();
			}
      	}

		this.direction = direction;
   	}


	public boolean isInAir() {

		Point tilePos;
		
		if (!jumping && !inAir) {
			tilePos = collidesWithTile(playerX, playerY + playerHeight + 1);  // check below back of player to see if there is a tile
			
		if (tilePos == null)  	// there is no tile below player, so player is in the air
				return true;
			else  					// there is a tile below player, so the player is on a tile
				return false;
		}

		return false;
	}
	

	public boolean isGoingUp() {
		return goingUp;
	}

	public boolean isGoingDown() {
		return goingDown;
	}

   	private void fall() {

      	jumping = false;
      	inAir = true;
      	timeElapsed = 0;

      	goingUp = false;
      	goingDown = true;

      	startY = playerY;
      	initialVelocity = 0;
	}


   	public void jump () {  

      	if (!window.isVisible ()) return;

      	jumping = true;
      	timeElapsed = 0;

      	goingUp = true;
      	goingDown = false;

      	startY = playerY;
      	initialVelocity = 70;
   	}


	public void attack(){

		System.out.println("attack called");

		return;
	}


   	public void update (int direction, boolean isJumping) {
      	
		int distance = 0;
      	int newY = 0;

      	timeElapsed++;

      	if (jumping || inAir) {
	   		distance = (int) (initialVelocity * timeElapsed - 4.9 * timeElapsed * timeElapsed);
			newY = startY - distance;

			if (newY > playerY && goingUp) {
				goingUp = false;
				goingDown = true;
			}

			if (goingUp) {
				Point tilePos = collidesWithTileUp (playerX, newY);	
			
				if (tilePos != null) {				// hits a tile going up

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;
					int bottomTileY = topTileY + TILE_SIZE;

					playerY = bottomTileY;
					fall();
				}
				else {
					playerY = newY;
				}
			}
			else if (goingDown) {			
				
				Point tilePos = collidesWithTileDown (playerX, newY);
				
				if (tilePos != null) {				// hits a tile going down
					goingDown = false;

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;

					playerY = topTileY - playerHeight;
					jumping = false;
					inAir = false;
				}

				else {
					playerY = newY;
				}
			}
      	}

		if (direction == 0 && !goingUp && !goingDown){
			playerIdleRightAnim.update();
		}

		if (direction == 1){
            
            if (goingUp){
                playerJumpLeftAnim.update();
            }
			if (goingDown){
				playerFallLeftAnim.update();
			}
			playerLeftAnim.update();
        }

        if (direction == 2){
            
            if (goingUp){
            	playerJumpRightAnim.update();
            }
			if (goingDown){
				playerFallRightAnim.update();
			}
			playerRightAnim.update();
        }

		if (direction == 3){
			playerJumpLeftAnim.update();
            playerJumpRightAnim.update();
        }
   	}


	
	public void draw (Graphics2D g2, int offSetX, int direction, boolean isJumping) {
        
		if (direction == 0){
			g2.drawImage(playerIdleRightAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
        }

        if (direction == 1){
            
            if (goingUp){
                g2.drawImage(playerJumpLeftAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
            }
			else if (goingDown){
				g2.drawImage(playerFallLeftAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
			}
			else {
				g2.drawImage(playerLeftAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
			}
        }

        if (direction == 2){
            
            if (goingUp){
                g2.drawImage(playerJumpRightAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
            }
			else if (goingDown){
				g2.drawImage(playerFallRightAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
			}
			else {
				g2.drawImage(playerRightAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
			}
        }

		if (direction == 3){

			g2.drawImage(playerJumpRightAnim.getImage(), playerX+offSetX, playerY, playerWidth, playerHeight, null);
		}
	}


    public void start(int direction, boolean isJumping) {
		
		if (direction == 0){
			playerIdleRightAnim.start();
		}

        if (direction == 1){
            
            if (goingUp){
				playerLeftAnim.stop();
				playerRightAnim.stop();
				playerJumpRightAnim.stop();
				playerFallLeftAnim.stop();
            	playerFallRightAnim.stop();
                playerJumpLeftAnim.start();
            }
			else if (goingDown){
				playerLeftAnim.stop();
				playerRightAnim.stop();
				playerJumpRightAnim.stop();
                playerJumpLeftAnim.stop();
				playerFallRightAnim.stop();
                playerFallLeftAnim.start();
			}
			else {
				playerRightAnim.stop();
				playerJumpRightAnim.stop();
				playerJumpLeftAnim.stop();
				playerFallLeftAnim.stop();
            	playerFallRightAnim.stop();
				playerLeftAnim.start();
			}
        }

        if (direction == 2){
            
            if (goingUp){
				playerLeftAnim.stop();
				playerRightAnim.stop();
                playerJumpLeftAnim.stop();
				playerFallLeftAnim.stop();
            	playerFallRightAnim.stop();
            	playerJumpRightAnim.start();
            }
			if (goingDown){
				playerLeftAnim.stop();
				playerRightAnim.stop();
                playerJumpLeftAnim.stop();
            	playerJumpRightAnim.stop();
				playerFallLeftAnim.stop();
            	playerFallRightAnim.start();
            }
			else {
				playerLeftAnim.stop();
				playerJumpRightAnim.stop();
				playerJumpLeftAnim.stop();
				playerFallLeftAnim.stop();
            	playerFallRightAnim.stop();
				playerRightAnim.start();
			}
        }
	}

	
   	public int getX() {
      	return playerX;
   	}


   	public void setX(int x) {
      	this.playerX = x;
   	}


   	public int getY() {
      	return playerY;
   	}


   	public void setY(int y) {
      	this.playerY = y;
   	}


	public int getDirection(){
		return direction;
	}

	public void minusLive(){
		lives = lives - 1;
	}

	public int getLives(){
		return lives;
	}

	public void addCoin(int coin){
		numCoins = numCoins + coin;
	}

	public int getCoins(){
		return numCoins;
	}

	public int getScore(){

		score = numCoins * 100;

		return score;
	}


	public Rectangle2D.Double getBoundingRectangle() {

		return new Rectangle2D.Double (playerX, playerY, playerWidth, playerHeight);
	}


/*     public void playIdle() {
		direction = 0;

		if (playerIdleRightAnim != null && !playerIdleRightAnim.isStillActive()) {
			playerIdleRightAnim.start();
		}
		playerIdleRightAnim.update();
    } */

}