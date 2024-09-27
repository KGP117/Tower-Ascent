import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Point;


public class FlyingEnemy {

    private TileMap tileMap;

	private int XSIZE = 200;		// width of the image
	private int YSIZE = 170;		// height of the image

    private int TILE_SIZE = 64;

    private Animation flyLeftAnim;
    private Animation flyRightAnim;
    

    private int x;          // x-position of sprite
    private int y;          // y-position of sprite

    private int initialX;

    private int dx;

    private int direction = 1;

    private double displayScale;

    private Player player;

    int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;
    

    // Sprite Constructor

    public FlyingEnemy (TileMap tileMap, Player player, int x, int y, double displayScale) {

        XSIZE = (int)(200*displayScale);
        YSIZE = (int)(170*displayScale);
    
        TILE_SIZE = (int)(64*displayScale);
    
        dx = (int)(7*displayScale);
			
		this.x = x;
		this.y = y;
        this.displayScale = displayScale;

        initialX = x;

        this.tileMap = tileMap;
		this.player = player;

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;


        Image enemyLeft1 = ImageManager.loadImage("images/enemy/flying/flyingLeft1.png");
        Image enemyLeft2 = ImageManager.loadImage("images/enemy/flying/flyingLeft2.png");
        Image enemyLeft3 = ImageManager.loadImage("images/enemy/flying/flyingLeft3.png");
        Image enemyLeft4 = ImageManager.loadImage("images/enemy/flying/flyingLeft4.png");
        Image enemyLeft5 = ImageManager.loadImage("images/enemy/flying/flyingLeft5.png");
        Image enemyLeft6 = ImageManager.loadImage("images/enemy/flying/flyingLeft6.png");

        flyLeftAnim = new Animation(true);

        flyLeftAnim.addFrame(enemyLeft1, 100);
        flyLeftAnim.addFrame(enemyLeft2, 100);
        flyLeftAnim.addFrame(enemyLeft3, 100);
        flyLeftAnim.addFrame(enemyLeft4, 100);
        flyLeftAnim.addFrame(enemyLeft5, 100);
        flyLeftAnim.addFrame(enemyLeft6, 100);


        Image enemyRight1 = ImageManager.loadImage("images/enemy/flying/flyingRight1.png");
        Image enemyRight2 = ImageManager.loadImage("images/enemy/flying/flyingRight2.png");
        Image enemyRight3 = ImageManager.loadImage("images/enemy/flying/flyingRight3.png");
        Image enemyRight4 = ImageManager.loadImage("images/enemy/flying/flyingRight4.png");
        Image enemyRight5 = ImageManager.loadImage("images/enemy/flying/flyingRight5.png");
        Image enemyRight6 = ImageManager.loadImage("images/enemy/flying/flyingRight6.png");

        flyRightAnim = new Animation(true);

        flyRightAnim.addFrame(enemyRight1, 100);
        flyRightAnim.addFrame(enemyRight2, 100);
        flyRightAnim.addFrame(enemyRight3, 100);
        flyRightAnim.addFrame(enemyRight4, 100);
        flyRightAnim.addFrame(enemyRight5, 100);
        flyRightAnim.addFrame(enemyRight6, 100);

    }


        // Checks if the enemy collides with a tile

	public Point collidesWithTile(int enemyX, int enemyY) { 	

    	int offsetY = tileMap.getOffsetY();

		int xTile = tileMap.pixelsToTiles(enemyX);
		int yTile = tileMap.pixelsToTiles(enemyY - offsetY);

		if (tileMap.getTile(xTile, yTile) != null) {
	    	Point tilePos = new Point (xTile, yTile);
	  		return tilePos;
		}

		else {
			yTile = tileMap.pixelsToTiles(enemyY - offsetY + (YSIZE - TILE_SIZE));

			if (tileMap.getTile(xTile, yTile) != null) {
				Point tilePos = new Point (xTile, yTile);
				return tilePos;
			}
			else {
				return null;
			}
			
		}

   	}



	// Checks if the enemy collides with a tile below them
	
	public Point collidesWithTileDown(int enemyX, int enemyY) {

		int offsetY = tileMap.getOffsetY();
		int xTile = tileMap.pixelsToTiles(enemyX);

		int yTileFrom = tileMap.pixelsToTiles(enemyY - offsetY);
		int yTileTo = tileMap.pixelsToTiles(enemyY - offsetY + YSIZE);

		for (int yTile = yTileFrom; yTile <= yTileTo; yTile++) {

			if (tileMap.getTile(xTile, yTile) != null) {
				Point tilePos = new Point(xTile, yTile);
				return tilePos;
			} 
			
			else {
				if (tileMap.getTile(xTile + 1, yTile) != null) {

					int leftSide = (xTile + 1) * TILE_SIZE;
					
					if (enemyX + XSIZE > leftSide) {
						Point tilePos = new Point(xTile + 1, yTile);
						return tilePos;
					}
				}
			}
		}

		return null;
	}



	// Checks if the enemy collides with a tile above them

   	public Point collidesWithTileUp (int newEnemyX, int newEnemyY) {

      	int offsetY = tileMap.getOffsetY();
	  	int xTile = tileMap.pixelsToTiles(newEnemyX);

	  	int yTileFrom = tileMap.pixelsToTiles(YSIZE - offsetY);
	  	int yTileTo = tileMap.pixelsToTiles(newEnemyY - offsetY);
	 
	  	for (int yTile=yTileFrom; yTile>=yTileTo; yTile--) {

			if (tileMap.getTile(xTile, yTile) != null) {
	        	Point tilePos = new Point (xTile, yTile);
	  			return tilePos;
			}

			else {
				if (tileMap.getTile(xTile+1, yTile) != null) {
					int leftSide = (xTile + 1) * TILE_SIZE;

					if (newEnemyX + XSIZE > leftSide) {
				    	Point tilePos = new Point (xTile+1, yTile);
				    	return tilePos;
			        }
				}
			}	    
	  	}

	  	return null;
   	}


    public void draw (Graphics2D g2, int offsetX) {
        
        if (direction == 1){
            g2.drawImage(flyLeftAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
        }
        else
        if (direction == 2){
            g2.drawImage(flyRightAnim.getImage(), x + offsetX, y, XSIZE, YSIZE, null);
        }

        g2.setColor(Color.red);
		//g2.drawRect(x+offsetX, y, XSIZE, YSIZE);

        g2.setColor(Color.blue);
		//g2.drawRect(x+offsetX-(int)(200*displayScale), y-(int)(100*displayScale), XSIZE+(int)(400*displayScale), YSIZE+(int)(200*displayScale));
	}


    public void start(int direction) {
		
        if (direction == 1){  
            flyLeftAnim.start();
        }
        else
        if (direction == 2){
            flyRightAnim.start();
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
		return new Rectangle2D.Double (x-(int)(200*displayScale), y-(int)(100*displayScale), XSIZE+(int)(400*displayScale), YSIZE+(int)(200*displayScale));
	}


	public void update() {	
        
        if (direction == 1){
            
            if (!flyLeftAnim.isStillActive()){
                return;
            }
            flyLeftAnim.update();
        }

        if (direction == 2){
            
            if (!flyRightAnim.isStillActive()){
                return;
            }
            flyRightAnim.update();
        }

        if (isInRange()){

            initialX = getX();

            if (player.getX() < getX()) {
                if (direction != 1) {
                    flyRightAnim.stop();
                    flyLeftAnim.start();
                    direction = 1;
                }
                dx = -10;
            } else {
                if (direction != 2) {
                    flyLeftAnim.stop();
                    flyRightAnim.start();
                    direction = 2;
                }
                dx = 10;
            }
        }
        else {
            if (x < initialX-150 || x > initialX+150)
            
			dx = dx * -1;
            changeDirection();
        }
		x = x + dx;

	}

    public void changeDirection(){
        
        if (direction == 1 && dx > 0) {
            flyLeftAnim.stop();
            flyRightAnim.start();
            direction = 2;
        } 
        else 
        if (direction == 2 && dx < 0) {
            flyRightAnim.stop();
            flyLeftAnim.start();
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
