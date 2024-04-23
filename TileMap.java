import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.JFrame;

/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Images are used multiple times in the tile map.
    map.
*/

public class TileMap {

    private static final int TILE_SIZE = 64; // set according to tile images
    private static final int TILE_SIZE_BITS = 6;

    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY;

    private LinkedList sprites;
    private Player player;
    private Sprite sprite1;
    private Sprite sprite2;

    BackgroundManager bgManager;

    private JFrame window;
    private Dimension dimension;

    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public TileMap(JFrame window, int width, int height) {

	this.window = window;
	dimension = window.getSize();

	screenWidth = dimension.width;
	screenHeight = dimension.height;

	mapWidth = width;
	mapHeight = height;

    // get the y offset to draw all sprites and tiles

    offsetY = screenHeight - tilesToPixels(mapHeight);
	//System.out.println("offsetY: " + offsetY);

	bgManager = new BackgroundManager (window, 12);

        tiles = new Image[mapWidth][mapHeight];
	    player = new Player (window, this, bgManager);
        sprite1 = new Sprite (window, this, bgManager);
        sprite2 = new Sprite (window, this, bgManager);
        sprites = new LinkedList();

	Image playerImage = player.getImage();
	int playerHeight = playerImage.getHeight(null);

    Image spriteImage1 = sprite1.getImage();
    Image spriteImage2 = sprite2.getImage();
	int spriteHeight1 = spriteImage1.getHeight(null);
    int spriteHeight2 = spriteImage2.getHeight(null);

	int x, y;

	x = 192;					// position player in 'random' location
	y = dimension.height - (TILE_SIZE + playerHeight);

        player.setX(x);
        player.setY(y);

	//System.out.println("Player coordinates: " + x + "," + y);

    }


    /**
        Gets the width of this TileMap (number of pixels across).
    */
    public int getWidthPixels() {
	    return tilesToPixels(mapWidth);
    }


    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return mapWidth;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return mapHeight;
    }


    public int getOffsetY() {
        System.out.println("Return offset: " + offsetY);
	    return offsetY;
    }

    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
        
        if (x < 0 || x >= mapWidth || 
            y < 0 || y >= mapHeight)
        {
            return null;
        }

        else {
            return tiles[x][y];
        }
    }


    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */

    public Iterator getSprites() {
        return sprites.iterator();
    }

    /**
        Class method to convert a pixel position to a tile position.
    */

    public int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    /**
        Class method to convert a pixel position to a tile position.
    */

    public int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    /**
        Class method to convert a tile position to a pixel position.
    */

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    /**
        Draws the specified TileMap.
    */
    public void draw(Graphics2D g2)
    {
        int mapWidthPixels = tilesToPixels(mapWidth);
        int mapHeightPixels = tilesToPixels(mapHeight);

        // get the scrolling position of the map
        // based on player's position

        // Calculate horizontal offset
        int offsetX = screenWidth / 2 - Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

        // Calculate vertical offset
        int offsetY = screenHeight / 2 - Math.round(player.getY()) - TILE_SIZE;
        System.out.println("offsetY1: " + offsetY);
        offsetY = Math.min(offsetY, 0);
        System.out.println("offsetY2: " + offsetY);
        offsetY = Math.max(offsetY, screenHeight - mapHeightPixels);
        System.out.println("offsetY3: " + offsetY);

	    // draw the background first
	    bgManager.draw (g2);

        // draw the visible tiles
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(screenHeight) + 1;
        
        for (int y = firstTileY; y <= lastTileY; y++) {
            
            for (int x=firstTileX; x <= lastTileX; x++) {
                Image image = getTile(x, y);
                
                if (image != null) {
                    g2.drawImage(image, 
                        tilesToPixels(x) + offsetX, 
                        tilesToPixels(y) + offsetY, 
                        null);
                }
            }
        }


        // draw player

        g2.drawImage(player.getImage(),
            Math.round(player.getX()) + offsetX,
            Math.round(player.getY()), //+ offsetY,
            null);

        // draw sprite

        g2.drawImage(sprite1.getImage(),
            850,
            570, //+ offsetY,
            null);

        g2.drawImage(sprite2.getImage(),
            600,
            70, //+ offsetY,
            null);    


        // draw sprites
        Iterator i = getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            int x = Math.round(sprite.getX()) + offsetX;
            int y = Math.round(sprite.getY()) + offsetY;
            g2.drawImage(sprite.getImage(), x, y, null);

            // wake up the creature when it's on screen
            if (//sprite instanceof Creature &&
                x >= 0 && x < screenWidth)
            {
                //((Creature)sprite).wakeUp();
            }
        }


    }


    public void moveLeft() {
/*         int x, y;
        x = player.getX();
        y = player.getY(); */

        player.move(1);

    }


    public void moveRight() {
/*         int x, y;
        x = player.getX();
        y = player.getY(); */

        player.move(2);

    }


    public void jump() {
/*         int x, y;
        x = player.getX();
        y = player.getY(); */

        player.move(3);

    }

    public void jumpLeft() {
/*         int x, y;
        x = player.getX();
        y = player.getY(); */

        player.move(4);
        //System.out.println("Jump Right reached in TileMap");

    }


    public void jumpRight() {
/*         int x, y;
        x = player.getX();
        y = player.getY(); */

        player.move(5);

        //System.out.println("Jump Right reached in TileMap");
    }

    

    public void update() {
	    player.update();
    }


    public void addSprite(Sprite sprite) {
        
    }

}
