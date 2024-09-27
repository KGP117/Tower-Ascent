import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;



public class Player {			

	private int playerCollisionWidth = 50;		// width of the image
	private int playerCollisionHeight = 80;		// height of the image

	private int playerSpriteWidth = 450;		// width of the image
	private int playerSpriteHeight = 225;		// height of the image

	private int playerAttackWidth = 50;		// width of the image
	private int playerAttackHeight = 80;		// height of the image

   	private int DX = 20;	// amount of X pixels to move in one keystroke

   	private int TILE_SIZE = 64;

   	private JFrame window;		// reference to the JFrame on which player is drawn
   	private TileMap tileMap;
   	private BackgroundManager bgManager;

   	private int playerX;			// x-position of player's sprite
   	private int playerY;			// y-position of player's sprite

	private double displayScale;

	private Animation playerLeftAnim;
	private Animation playerRightAnim;

	private Animation playerJumpLeftAnim;
	private Animation playerJumpRightAnim;

	private Animation playerFallLeftAnim;
	private Animation playerFallRightAnim;
	
	private Animation playerIdleLeftAnim;
	private Animation playerIdleRightAnim;

	private Animation playerAttackLeftAnim;
	private Animation playerAttackRightAnim;

	private Animation playerAirAttackLeftAnim;
	private Animation playerAirAttackRightAnim;

	private Animation playerHitLeftAnim;
	private Animation playerHitRightAnim;

   	private boolean jumping;
   	private int timeElapsed;
   	private int startY;

	private int direction = 0;

   	private boolean goingUp;
   	private boolean goingDown;

	private boolean facingLeft;
	private boolean facingRight;

   	private boolean inAir;
   	private int initialVelocity;

	private int lives;
	private int numCoins;
	private int score;

	private int character;
	private int hitFrame;

	private boolean attack;
	private boolean airAttack;

	private boolean isHit;

	private String basePath = null;



	// Player Constructor

	public Player (JFrame window, TileMap t, BackgroundManager b, int characterIndex, double displayScale) {

		playerCollisionHeight = (int)(playerCollisionHeight*displayScale);
		playerCollisionWidth = (int)(playerCollisionWidth*displayScale);

		playerSpriteHeight = (int)(playerSpriteHeight*displayScale);
		playerSpriteWidth = (int)(playerSpriteWidth*displayScale);

		DX = (int)(DX*displayScale);

		TILE_SIZE = (int)(TILE_SIZE*displayScale);

		this.window = window;
		this.displayScale = displayScale;

		tileMap = t;            // tile map on which the player's sprite is displayed
		bgManager = b;          // instance of BackgroundManager

		goingUp = false;
		goingDown = false;

		facingRight = true;
		facingLeft = false;

		inAir = false;

		lives = 3;

		character = characterIndex;

		
		switch (character) {
			case 1: basePath = "images/player/wind"; 	playerAttackWidth = 45; hitFrame = 1; break;
			case 2: basePath = "images/player/fire"; 	playerAttackWidth = 60; hitFrame = 5; break;
			case 3: basePath = "images/player/water"; 	playerAttackWidth = 60; hitFrame = 3; break;
			case 4: basePath = "images/player/leaf"; 	playerAttackWidth = 90; hitFrame = 4; break;
			case 5: basePath = "images/player/metal"; 	playerAttackWidth = 50; hitFrame = 2; break;
			case 6: basePath = "images/player/crystal"; playerAttackWidth = 45; hitFrame = 5; break;
			case 7: basePath = "images/player/ground"; 	playerAttackWidth = 45; hitFrame = 2; break;
		}

		playerAttackHeight = (int)(playerAttackHeight*displayScale);
		playerAttackWidth = (int)(playerAttackWidth*displayScale);

		playerLeftAnim = loadAnimation(basePath, "runLeft", 8, 100, true);
		playerRightAnim = loadAnimation(basePath, "runRight", 8, 100, true);

		playerJumpLeftAnim = loadAnimation(basePath, "jumpLeft", 3, 100, true);
		playerJumpRightAnim = loadAnimation(basePath, "jumpRight", 3, 100, true);
		
		playerFallLeftAnim = loadAnimation(basePath, "fallLeft", 3, 100, true);
		playerFallRightAnim = loadAnimation(basePath, "fallRight", 3, 100, true);
		
		playerIdleLeftAnim = loadAnimation(basePath, "idleLeft", 8, 100, true);
		playerIdleRightAnim = loadAnimation(basePath, "idleRight", 8, 100, true);
		
		playerAttackLeftAnim = loadAnimation(basePath, "attackLeft", 8, 70, false);
		playerAttackRightAnim = loadAnimation(basePath, "attackRight", 8, 70, false);
		
		playerAirAttackLeftAnim = loadAnimation(basePath, "airAttackLeft", 8, 100, false);
		playerAirAttackRightAnim = loadAnimation(basePath, "airAttackRight", 8, 100, false);
		
		playerHitLeftAnim = loadAnimation(basePath, "takeHitLeft", 6, 100, false);
		playerHitRightAnim = loadAnimation(basePath, "takeHitRight", 6, 100, false);

	}



	// Loads the needed animations

	private Animation loadAnimation(String basePath, String action, int frameCount, int frameDuration, boolean loop) {
		Animation anim = new Animation(loop);
		for (int i = 1; i <= frameCount; i++) {
			Image frame = ImageManager.loadImage(basePath + "/" + action + "_" + i + ".png");
			anim.addFrame(frame, frameDuration);
		}
		return anim;
	}



	// Checks if the player collides with a tile

	public Point collidesWithTile(int playerX, int playerY) { 	

    	int offsetY = tileMap.getOffsetY();

		int xTile = tileMap.pixelsToTiles(playerX);
		int yTile = tileMap.pixelsToTiles(playerY - offsetY);

		if (tileMap.getTile(xTile, yTile) != null) {
	    	Point tilePos = new Point (xTile, yTile);
	  		return tilePos;
		}

		else {
			yTile = tileMap.pixelsToTiles(playerY - offsetY + (playerCollisionHeight - TILE_SIZE));

			if (tileMap.getTile(xTile, yTile) != null) {
				Point tilePos = new Point (xTile, yTile);
				return tilePos;
			}
			else {
				return null;
			}
			
		}

   	}



	// Checks if the player collides with a tile below them
	
	public Point collidesWithTileDown(int playerX, int playerY) {

		int offsetY = tileMap.getOffsetY();
		int xTile = tileMap.pixelsToTiles(playerX);

		int yTileFrom = tileMap.pixelsToTiles(playerY - offsetY);
		int yTileTo = tileMap.pixelsToTiles(playerY - offsetY + playerCollisionHeight);

		for (int yTile = yTileFrom; yTile <= yTileTo; yTile++) {

			if (tileMap.getTile(xTile, yTile) != null) {
				Point tilePos = new Point(xTile, yTile);
				return tilePos;
			} 
			
			else {
				if (tileMap.getTile(xTile + 1, yTile) != null) {

					int leftSide = (xTile + 1) * TILE_SIZE;
					
					if (playerX + playerCollisionWidth > leftSide) {
						Point tilePos = new Point(xTile + 1, yTile);
						return tilePos;
					}
				}
			}
		}

		return null;
	}



	// Checks if the player collides with a tile above them

   	public Point collidesWithTileUp (int newPlayerX, int newPlayerY) {

      	int offsetY = tileMap.getOffsetY();
	  	int xTile = tileMap.pixelsToTiles(newPlayerX);

	  	int yTileFrom = tileMap.pixelsToTiles(playerY - offsetY);
	  	int yTileTo = tileMap.pixelsToTiles(newPlayerY - offsetY);
	 
	  	for (int yTile=yTileFrom; yTile>=yTileTo; yTile--) {

			if (tileMap.getTile(xTile, yTile) != null) {
	        	Point tilePos = new Point (xTile, yTile);
	  			return tilePos;
			}

			else {
				if (tileMap.getTile(xTile+1, yTile) != null) {
					int leftSide = (xTile + 1) * TILE_SIZE;

					if (newPlayerX + playerCollisionWidth > leftSide) {
				    	Point tilePos = new Point (xTile+1, yTile);
				    	return tilePos;
			        }
				}
			}	    
	  	}

	  	return null;
   	}


	
	// Handles player movement based on inputs

   	public synchronized void move (int direction) {

      	Point tilePos = null;

      	if (!window.isVisible ()) return;
      
      	if (direction == 1) {		// move left
			
			playerX = playerX - DX;
	  		
			if (playerX < 0) {
				playerX = 0;
				return;
	  		}
		
			facingLeft = true;
			facingRight = false;

	  		tilePos = collidesWithTile(playerX, playerY);
      	}	

      	else if (direction == 2) {		// move right

      	  	int tileMapWidth = tileMap.getWidthPixels();
			playerX = playerX + DX;

			if (playerX + playerCollisionWidth >= tileMapWidth) {
				playerX = tileMapWidth - playerCollisionWidth;
				return;
			}

			facingLeft = false;
			facingRight = true;

	  		tilePos = collidesWithTile(playerX + playerCollisionWidth, playerY);			
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
             	playerX = ((int) tilePos.getX()) * TILE_SIZE - playerCollisionWidth; // keep flush with left side of tile
	 		}
      	}

      	else {
			if (direction == 1) {
				bgManager.moveLeft();
			}
			else if (direction == 2) {
				bgManager.moveRight();
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
			tilePos = collidesWithTile(playerX, playerY + playerCollisionHeight + 1);  // check below back of player to see if there is a tile
			
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

		if (isGoingUp() || isGoingDown()){
			airAttack = true;
		}
		else {
			attack = true;
		}

		return;
	}

	public void getHit(){
		isHit = true;
	}

	public boolean isHit(){
		return isHit;
	}


   	public void update (int direction, boolean isJumping) {
      	
		int distance = 0;
      	int newPlayerY = 0;

      	timeElapsed++;

      	if (jumping || inAir) {
	   		distance = (int) ((initialVelocity * timeElapsed - 4.9 * timeElapsed * timeElapsed));
			newPlayerY = startY - (int)(distance*displayScale);

			if (newPlayerY > playerY && goingUp) {
				goingUp = false;
				goingDown = true;
			}

			if (goingUp) {
				Point tilePos = collidesWithTileUp (playerX, newPlayerY);	
			
				if (tilePos != null) {				// hits a tile going up

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;
					int bottomTileY = topTileY + TILE_SIZE;

					playerY = bottomTileY;
					fall();
				}
				else {
					playerY = newPlayerY;
				}
			}
			else if (goingDown) {			
				
				Point tilePos = collidesWithTileDown (playerX, newPlayerY);
				
				if (tilePos != null) {				// hits a tile going down
					goingDown = false;

					int offsetY = tileMap.getOffsetY();
					int topTileY = ((int) tilePos.getY()) * TILE_SIZE + offsetY;

					playerY = topTileY - playerCollisionHeight;
					jumping = false;
					inAir = false;
				}

				else {
					playerY = newPlayerY;
				}
			}
      	}
   	}


	
	public void draw (Graphics2D g2, int offSetX, int direction) {
        
		if (isHit) {
			if (facingRight) {
				playAnimation(playerHitRightAnim, g2, offSetX);
			} 
			else if (facingLeft) {
				playAnimation(playerHitLeftAnim, g2, offSetX);
			}
		} else if (airAttack) {
			if (facingRight) {
				playAnimation(playerAirAttackRightAnim, g2, offSetX);
			} 
			else if (facingLeft) {
				playAnimation(playerAirAttackLeftAnim, g2, offSetX);
			}
		} 
		
		else if (attack) {
			if (facingRight) {
				playAnimation(playerAttackRightAnim, g2, offSetX);
			}
			else if (facingLeft) {
				playAnimation(playerAttackLeftAnim, g2, offSetX);
			}
		} 
		
		else {
			if (direction == 0) {
				
				if (facingRight) {
					if (goingUp) {
						playAnimation(playerJumpRightAnim, g2, offSetX);
					} 
					else if (goingDown) {
						playAnimation(playerFallRightAnim, g2, offSetX);
					} 
					else {
						playAnimation(playerIdleRightAnim, g2, offSetX);
					}
				}

				else if (facingLeft) {
					if (goingUp) {
						playAnimation(playerJumpLeftAnim, g2, offSetX);
					} 
					else if (goingDown) {
						playAnimation(playerFallLeftAnim, g2, offSetX);
					} 
					else {
						playAnimation(playerIdleLeftAnim, g2, offSetX);
					}
				}
			} 
			
			else if (direction == 1) {
				if (goingUp) {
					playAnimation(playerJumpLeftAnim, g2, offSetX);
				} 
				else if (goingDown) {
					playAnimation(playerFallLeftAnim, g2, offSetX);
				} 
				else {
					playAnimation(playerLeftAnim, g2, offSetX);
				}
			} 
			
			else if (direction == 2) {
				if (goingUp) {
					playAnimation(playerJumpRightAnim, g2, offSetX);
				} 
				else if (goingDown) {
					playAnimation(playerFallRightAnim, g2, offSetX);
				} 
				else {
					playAnimation(playerRightAnim, g2, offSetX);
				}
			}
		}

/* 		// Collision Box
		g2.setColor(Color.red);
		g2.drawRect(playerX+offSetX, playerY, playerCollisionWidth, playerCollisionHeight);

		// Attack HitBox
		g2.setColor(Color.blue);
		g2.drawRect(playerX+playerCollisionWidth, playerY, playerAttackWidth, playerAttackHeight);

		// Attack HitBox
		g2.setColor(Color.blue);
		g2.drawRect(playerX-playerAttackWidth, playerY, playerAttackWidth, playerAttackHeight); */
	}


	
	public void playAnimation(Animation animation, Graphics2D g2, int offSetX){

		g2.drawImage(animation.getImage(), playerX+offSetX-(int)(200*displayScale), playerY-(int)(143*displayScale), 
		playerSpriteWidth, playerSpriteHeight, null);

		if (!animation.isStillActive()) {
			animation.start();
		}
		animation.update();
		
		// Check if the animation is complete and reset the attack flag
		if (!animation.isStillActive() && (animation == playerAttackLeftAnim || animation == playerAttackRightAnim)) {
			attack = false;
		}
		if (!animation.isStillActive() && (animation == playerAirAttackLeftAnim || animation == playerAirAttackRightAnim)) {
			airAttack = false;
		}
		if (!animation.isStillActive() && (animation == playerHitLeftAnim || animation == playerHitRightAnim)) {
			isHit = false;
		}
	}


	public boolean isInHitFrame(){
		
		if (playerAttackRightAnim.isStillActive()){
			if (playerAttackRightAnim.getCurrentFrameIndex() >= hitFrame){
				
				return true;
			}
		}
		else if (playerAttackLeftAnim.isStillActive()){
			if (playerAttackLeftAnim.getCurrentFrameIndex() >= hitFrame){
				return true;
			}
		}

		return false;

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

	public boolean isAttacking(){
		if (attack || airAttack){
			return true;
		}
		else {
			return false;
		}
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
		return new Rectangle2D.Double(playerX, playerY, playerCollisionWidth, playerCollisionHeight);
	}

	public Rectangle2D.Double getAttackHitBox() {
		if (facingRight){
			return new Rectangle2D.Double(playerX+playerCollisionWidth, playerY, playerAttackWidth, playerAttackHeight);
		}
		else if (facingLeft){
			return new Rectangle2D.Double(playerX-playerAttackWidth, playerY, playerAttackWidth, playerAttackHeight);
		}
		return null;
	}


    public void playIdle() {
		direction = 0;
    }

}