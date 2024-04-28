import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Images are used multiple times in the tile map.
    map.
*/

public class TileMap {

    private static final int TILE_SIZE = 64; // set according to tile images

    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY;

    private Player player;
    private Coin coin = null;
    private Enemy enemy;

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

        bgManager = new BackgroundManager (window, 12);
        tiles = new Image[mapWidth][mapHeight];
        player = new Player (window, this, bgManager);
        coin = new Coin (player);
        enemy = new Enemy (player);

        Image playerImage = player.getImage();
        int playerHeight = playerImage.getHeight(null);

        int x, y;

        x = 192;					// position player in 'random' location
        y = dimension.height - (TILE_SIZE + playerHeight);

        player.setX(x);
        player.setY(y);


        coin.start();
        enemy.start(2);
    }


    
    // Gets the width of this TileMap (number of pixels across).
    
    public int getWidthPixels() {
	    return tilesToPixels(mapWidth);
    }


    
    // Gets the width of this TileMap (number of tiles across).
    
    public int getWidth() {
        return mapWidth;
    }


    
    // Gets the height of this TileMap (number of tiles down).
    
    public int getHeight() {
        return mapHeight;
    }


    public int getOffsetY() {
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


    
    //    Sets the tile at the specified location.
    
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */

    
    // Class method to convert a pixel position to a tile position.
    

    public int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    
    // Class method to convert a pixel position to a tile position.
    

    public int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    
    // Class method to convert a tile position to a pixel position.

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    
    // Draws the specified TileMap.
    
    public void draw(Graphics2D g2) {

        int mapWidthPixels = tilesToPixels(mapWidth);
        //int mapHeightPixels = tilesToPixels(mapHeight);

        // get the scrolling position of the map
        // based on player's position

        // Calculate horizontal offset
        int offsetX = screenWidth / 2 - Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

        // Calculate vertical offset
/*         int offsetY = 0;
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, screenHeight - mapHeightPixels); 
 */

	    // draw the background first
	    bgManager.draw (g2, ((GameWindow) window).getLevel());
        bgManager.draw (g2, ((GameWindow) window).getLevel());
        bgManager.draw (g2, ((GameWindow) window).getLevel());

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


        
        if (((GameWindow) window).getLevel() == 1){
            coin.draw(g2, offsetX);
        }

        else
        if (((GameWindow) window).getLevel() == 2){
            coin.draw(g2, offsetX);
            enemy.draw(g2, offsetX);
        }

        else 
        if (((GameWindow) window).getLevel() == 3){
            coin.draw(g2, offsetX);
            enemy.draw(g2, offsetX);
        }

    }


    public void moveLeft() {
        player.move(1);
    }


    public void moveRight() {
        player.move(2);
    }


    public void jump() {
        player.move(3);
    }


    public void update() {
	    player.update();
        coin.update();
        enemy.update();

        if (coin.collidesWithPlayer()) {
            ((GameWindow) window).endLevel();
            return;
        }
    }

}
