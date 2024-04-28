import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Player {			

	private static final int XSIZE = 60;		// width of the image
	private static final int YSIZE = 100;		// height of the image

   	private static final int DX = 8;	// amount of X pixels to move in one keystroke
   	private static final int DY = 32;	// amount of Y pixels to move in one keystroke

   	private static final int TILE_SIZE = 64;

   	private JFrame window;		// reference to the JFrame on which player is drawn
   	private TileMap tileMap;
   	private BackgroundManager bgManager;

   	private int x;			// x-position of player's sprite
   	private int y;			// y-position of player's sprite

   	Graphics2D g2;

	private Animation playerLeftAnim;
	private Animation playerRightAnim;
	private Animation playerJumpLeftAnim;
	private Animation playerJumpRightAnim;

   	private Image playerImage, playerLeftImage, playerRightImage;

   	private boolean jumping;
   	private int timeElapsed;
   	private int startY;

   	private boolean goingUp;
   	private boolean goingDown;

   	private boolean inAir;
   	private int initialVelocity;

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;


	// Player Constructor

   	public Player (JFrame window, TileMap t, BackgroundManager b) {
      	this.window = window;

      	tileMap = t;			// tile map on which the player's sprite is displayed
      	bgManager = b;			// instance of BackgroundManager

      	goingUp = goingDown = false;
      	inAir = false;

      	playerLeftImage = ImageManager.loadImage("images/playerLeft.gif");
      	playerRightImage = ImageManager.loadImage("images/playerRight.gif");
      	playerImage = playerRightImage;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

		Image playerLeft1 = ImageManager.loadImage("images/player1_left.png");
        Image playerLeft2 = ImageManager.loadImage("images/player2_left.png");
        Image playerLeft3 = ImageManager.loadImage("images/player3_left.png");
        Image playerLeft4 = ImageManager.loadImage("images/player4_left.png");

        playerLeftAnim = new Animation(true);

        playerLeftAnim.addFrame(playerLeft1, 100);
        playerLeftAnim.addFrame(playerLeft2, 100);
        playerLeftAnim.addFrame(playerLeft3, 100);
        playerLeftAnim.addFrame(playerLeft4, 100);


		Image playerRight1 = ImageManager.loadImage("images/player1_right.png");
        Image playerRight2 = ImageManager.loadImage("images/player2_right.png");
        Image playerRight3 = ImageManager.loadImage("images/player3_right.png");
        Image playerRight4 = ImageManager.loadImage("images/player4_right.png");

        playerRightAnim = new Animation(true);

        playerRightAnim.addFrame(playerRight1, 100);
        playerRightAnim.addFrame(playerRight2, 100);
        playerRightAnim.addFrame(playerRight3, 100);
        playerRightAnim.addFrame(playerRight4, 100);

        Image playerJumpLeft = ImageManager.loadImage("images/player5_left.png");

        playerJumpLeftAnim = new Animation(true);

        playerJumpLeftAnim.addFrame(playerJumpLeft, 100);

        Image playerJumpRight = ImageManager.loadImage("images/player5_right.png");

        playerJumpRightAnim = new Animation(true);

        playerJumpRightAnim.addFrame(playerJumpRight, 100);

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


	// checks if the player collides with a tile below them

   	public Point collidesWithTileDown (int newX, int newY) {

	  	int playerWidth = playerImage.getWidth(null);
      	int playerHeight = playerImage.getHeight(null);

      	int offsetY = tileMap.getOffsetY();
	  	int xTile = tileMap.pixelsToTiles(newX);
	  	int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
	  	int yTileTo = tileMap.pixelsToTiles(newY - offsetY + playerHeight);

	  	for (int yTile=yTileFrom; yTile<=yTileTo; yTile++) {
			
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


	// checks if the player collides with a tile above them

   	public Point collidesWithTileUp (int newX, int newY) {

	  	int playerWidth = playerImage.getWidth(null);

      	int offsetY = tileMap.getOffsetY();
	  	int xTile = tileMap.pixelsToTiles(newX);

	  	int yTileFrom = tileMap.pixelsToTiles(y - offsetY);
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

      	int newX = x;
		int newY = y;
      	Point tilePos = null;

      	if (!window.isVisible ()) return;
      
      	if (direction == 1) {		// move left
	  		playerImage = playerLeftImage;
			if (jumping){
				newX = x - 2*DX;
			}
			else{
				newX = x - DX;
			}
          	
	  		
			if (newX < 0) {
				x = 0;
				return;
	  		}
		
	  	tilePos = collidesWithTile(newX, y);
      	}	
      	else				
      	if (direction == 2) {		// move right
	  		playerImage = playerRightImage;
      	  	int playerWidth = playerImage.getWidth(null);
			if (jumping){
				newX = x + 2*DX;
			}
			else{
				newX = x + DX;
			}

      	  	int tileMapWidth = tileMap.getWidthPixels();

			if (newX + playerImage.getWidth(null) >= tileMapWidth) {
				x = tileMapWidth - playerImage.getWidth(null);
				return;
			}

	  	tilePos = collidesWithTile(newX+playerWidth, y);			
      	}
      	else				// jump
      	if (direction == 3 && !jumping) {	
          	jump();
	  	return;
      	}
    
      	if (tilePos != null) {  
         	if (direction == 1) {
             	x = ((int) tilePos.getX() + 1) * TILE_SIZE;	   // keep flush with right side of tile
	 		}
        	else
        	if (direction == 2) {
      	     	int playerWidth = playerImage.getWidth(null);
             	x = ((int) tilePos.getX()) * TILE_SIZE - playerWidth; // keep flush with left side of tile
	 		}
      	}
      	else {
        if (direction == 1) {
	      	x = newX;
	      	bgManager.moveLeft();
        }
	  	else
	  	if (direction == 2) {
	      	x = newX;
	      	bgManager.moveRight();
   	  	}
		else
	  	if (direction == 3) {
	      	y = newY;
	      	bgManager.moveUp();
   	  	}

			if (isInAir()) {
				if (direction == 1) {				// make adjustment for falling on left side of tile
					int playerWidth = playerImage.getWidth(null);
					x = x - playerWidth + DX;
				}
				fall();
			}
      	}
   	}


   	public boolean isInAir() {

      	int playerHeight;
      	Point tilePos;

      	if (!jumping && !inAir) {   
	  		playerHeight = playerImage.getHeight(null);
	  		tilePos = collidesWithTile(x, y + playerHeight + 1); 	// check below player to see if there is a tile
	
  	  	if (tilePos == null)				   	// there is no tile below player, so player is in the air
			return true;
	  	else							// there is a tile below player, so the player is on a tile
			return false;
      	}

      	return false;
   	}


   	private void fall() {

      	jumping = false;
      	inAir = true;
      	timeElapsed = 0;

      	goingUp = false;
      	goingDown = true;

      	startY = y;
      	initialVelocity = 0;
   	}


   	public void jump () {  

      	if (!window.isVisible ()) return;

      	jumping = true;
      	timeElapsed = 0;

      	goingUp = true;
      	goingDown = false;

      	startY = y;
      	initialVelocity = 70;
   	}


   	public void update () {
      	
		int distance = 0;
      	int newY = 0;

      	timeElapsed++;

      	if (jumping || inAir) {
	   		distance = (int) (initialVelocity * timeElapsed - 
									4.9 * timeElapsed * timeElapsed);
			newY = startY - distance;

			if (newY > y && goingUp) {
				goingUp = false;
				goingDown = true;
			}

			if (goingUp) {
				Point tilePos = collidesWithTileUp (x, newY);	
			
				if (tilePos != null) {				// hits a tile going up

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;
					int bottomTileY = topTileY + TILE_SIZE;

					y = bottomTileY;
					fall();
				}
				else {
					y = newY;
				}
			}
			else
			if (goingDown) {			
				
				Point tilePos = collidesWithTileDown (x, newY);
				
				if (tilePos != null) {				// hits a tile going down
					int playerHeight = playerImage.getHeight(null);
					goingDown = false;

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;

					y = topTileY - playerHeight;
					jumping = false;
					inAir = false;
				}

				else {
					y = newY;
				}
			}
      	}
   	}


   	public void moveUp () {

      	if (!window.isVisible ()) return;

      	y = y - DY;
   	}

	
	public void draw (Graphics2D g2, int direction, boolean isJumping) {
        
        if (direction == 1){
            
            if (isJumping){
                g2.drawImage(playerJumpLeftAnim.getImage(), x, y, XSIZE, YSIZE, null);
            }

			g2.drawImage(playerLeftAnim.getImage(), x, y, XSIZE, YSIZE, null);
        }

        if (direction == 2){
            
            if (isJumping){
                g2.drawImage(playerJumpRightAnim.getImage(), x, y, XSIZE, YSIZE, null);
            }

			g2.drawImage(playerRightAnim.getImage(), x, y, XSIZE, YSIZE, null);
        }
	}


    public void start(int direction, boolean isJumping) {
		
        if (direction == 1){
            
            if (isJumping){
                g2.drawImage(playerJumpLeftAnim.getImage(), x, y, XSIZE, YSIZE, null);
            }

			g2.drawImage(playerLeftAnim.getImage(), x, y, XSIZE, YSIZE, null);
        }

        if (direction == 2){
            
            if (isJumping){
                g2.drawImage(playerJumpRightAnim.getImage(), x, y, XSIZE, YSIZE, null);
            }

			g2.drawImage(playerRightAnim.getImage(), x, y, XSIZE, YSIZE, null);
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


   	public Image getImage() {
      	return playerImage;
   	}


	public Rectangle2D.Double getBoundingRectangle() {
		int playerWidth = playerImage.getWidth(null);
		int playerHeight = playerImage.getHeight(null);
  
		return new Rectangle2D.Double (x, y, playerWidth, playerHeight);
	}

}