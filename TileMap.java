import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class TileMap {

    private static final int TILE_SIZE = 64; // set according to tile images

    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY;

    private Player player;

    private Coin coin[];

    private DungeonDoor door;

    private GroundEnemy groundEnemy[];
    private FlyingEnemy flyingEnemy[];
    private BossEnemy bossEnemy[];

    private JFrame window;
    private Dimension dimension;

    BackgroundManager bgManager;
    SoundManager soundManager;


    public TileMap(JFrame window, int width, int height) {

        soundManager = SoundManager.getInstance();

        this.window = window;
        dimension = window.getSize();

        screenWidth = dimension.width;
        screenHeight = dimension.height;

        mapWidth = width;
        mapHeight = height;

        offsetY = screenHeight - tilesToPixels(mapHeight);

        bgManager = new BackgroundManager (window, 12);
        tiles = new Image[mapWidth][mapHeight];
        player = new Player (window, this, bgManager);
        coin = new Coin[10];
        groundEnemy = new GroundEnemy[3];
        flyingEnemy = new FlyingEnemy[2];
        bossEnemy = new BossEnemy[2];

        if (((GameWindow) window).getLevel() == 1){
            coin[1] = new Coin (player, 1000, 600);
            coin[2] = new Coin (player, 900, 600);
            coin[3] = new Coin (player, 800, 600);
            door = new DungeonDoor(player, 1131, 556);
        }

        else
        if (((GameWindow) window).getLevel() == 2){
            coin[1] = new Coin (player, 500, 150);
            coin[2] = new Coin (player, 1300, 345);
            coin[3] = new Coin (player, 1125, 475);
            door = new DungeonDoor(player, 350, 110);
            groundEnemy[1] = new GroundEnemy (this, player, 800, screenHeight-TILE_SIZE-95);
        }

        else 
        if (((GameWindow) window).getLevel() == 3){
            coin[1] = new Coin (player, 6100, 350);
            coin[2] = new Coin (player, 900, 25);
            coin[3] = new Coin (player, 3850, 275);
            door = new DungeonDoor(player, 6100, 556);
            groundEnemy[1] = new GroundEnemy (this, player, 1600, screenHeight-TILE_SIZE-155);
            flyingEnemy[1] = new FlyingEnemy(player, 3400, 250);
            bossEnemy[1] = new BossEnemy(player, 5500, 325);
        }
        

        int x, y;

        x = 192;					// position player in 'random' location
        y = dimension.height - (TILE_SIZE + 80);

        player.setX(x);
        player.setY(y);


        player.start(player.getDirection(), player.isInAir());
        
        
        if (((GameWindow) window).getLevel() == 1){
            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
        }

        else
        if (((GameWindow) window).getLevel() == 2){
            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
            groundEnemy[1].start(2);
        }

        else 
        if (((GameWindow) window).getLevel() == 3){
            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
            groundEnemy[1].start(2);
            flyingEnemy[1].start(1);
            bossEnemy[1].start(1);
        }
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
        
        player.draw(g2, offsetX, player.getDirection(), player.isInAir());


        if (((GameWindow) window).getLevel() == 1){

            for (int i=1; i<10; i++){
                if (coin[i] != null){
                    coin[i].draw(g2, offsetX);
                }
            }

            door.draw(g2, offsetX);
        }

        else if (((GameWindow) window).getLevel() == 2){
            
            for (int i=1; i<10; i++){
                if (coin[i] != null){
                    coin[i].draw(g2, offsetX);
                }
            }

            door.draw(g2, offsetX);
            groundEnemy[1].draw(g2, offsetX);
        }

        else if (((GameWindow) window).getLevel() == 3){
            
            for (int i=1; i<10; i++){
                if (coin[i] != null){
                    coin[i].draw(g2, offsetX);
                }
            }

            door.draw(g2, offsetX);
            groundEnemy[1].draw(g2, offsetX);
            flyingEnemy[1].draw(g2, offsetX);
            bossEnemy[1].draw(g2, offsetX);
        }

    }


    public void moveLeft() {
        player.move(1);
    }

    public void moveRight() {
        player.move(2);
    }

    public void jump() {
        if (!player.isGoingUp() && !player.isGoingDown()){
            soundManager.playSound("jump", false);
            player.move(3);
        }
    }

    public void attack() {
        player.attack();
    }

    public boolean isPlayerInAir(){
        if (player.isGoingUp() || player.isGoingDown()){
            return true;
        }
        else {
            return false;
        }
    }

    public int getPlayerLives(){
        return player.getLives();
    }

    public int getScore(){
        return player.getScore();
    }

    public int getPlayerX(){
        return player.getX();
    }


    public void update() {

	    player.update(player.getDirection(), player.isInAir());

        for (int i=1; i<10; i++){
                
            if (coin[i] != null){
                coin[i].update();

                if (coin[i].collidesWithPlayer()) {
                    soundManager.playSound("coin", false);
                    player.addCoin(1);
                    coin[i] = null;
                }
            }
        }

        if (((GameWindow) window).getLevel() == 1){

/*             for (int i=1; i<10; i++){
                
                if (coin[i] != null){
                    coin[i].update();

                    if (coin[i].collidesWithPlayer()) {
                        soundManager.playSound("coin", false);
                        player.addCoin(1);
                        coin[i] = null;
                    }
                }
            } */
            
            door.update();

            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }
        }

        else if (((GameWindow) window).getLevel() == 2){
            
/*             for (int i=1; i<10; i++){
                
                if (coin[i] != null){
                    coin[i].update();

                    if (coin[i].collidesWithPlayer()) {
                        soundManager.playSound("coin", false);
                        player.addCoin(1);
                        coin[i] = null;
                    }
                }
            } */

            door.update();
            groundEnemy[1].update();

            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }

            if (groundEnemy[1].collidesWithPlayer()) {
            
                player.minusLive();
                
                if (player.getX() < groundEnemy[1].getX()){
                    player.setX(player.getX()-100);
                    player.update(player.getDirection(), player.isInAir());
                }
                else if (player.getX() > groundEnemy[1].getX()){
                    player.setX(player.getX()+100);
                    player.update(player.getDirection(), player.isInAir());
                }
            }
        }

        else if (((GameWindow) window).getLevel() == 3){
            
/*             for (int i=1; i<10; i++){
                
                if (coin[i] != null){
                    coin[i].update();

                    if (coin[i].collidesWithPlayer()) {
                        soundManager.playSound("coin", false);
                        player.addCoin(1);
                        coin[i] = null;
                    }
                }
            } */

            door.update();
            groundEnemy[1].update();
            flyingEnemy[1].update();
            bossEnemy[1].update();


            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }

            if (groundEnemy[1].collidesWithPlayer()) {
            
                player.minusLive();
                
                if (player.getX() < groundEnemy[1].getX()){
                    player.setX(player.getX()-100);
                    player.update(player.getDirection(), player.isInAir());
                }
                else if (player.getX() > groundEnemy[1].getX()){
                    player.setX(player.getX()+100);
                    player.update(player.getDirection(), player.isInAir());
                }
            }
            
            if (flyingEnemy[1].collidesWithPlayer()) {
            
                player.minusLive();
                
                if (player.getX() < flyingEnemy[1].getX()){
                    player.setX(player.getX()-100);
                    player.update(player.getDirection(), player.isInAir());
                }
                else if (player.getX() > flyingEnemy[1].getX()){
                    player.setX(player.getX()+100);
                    player.update(player.getDirection(), player.isInAir());
                }
            }

            if (bossEnemy[1].collidesWithPlayer()) {
            
                player.minusLive();
                
                if (player.getX() < bossEnemy[1].getX()){
                    player.setX(player.getX()-100);
                    player.update(player.getDirection(), player.isInAir());
                }
                else if (player.getX() > bossEnemy[1].getX()){
                    player.setX(player.getX()+100);
                    player.update(player.getDirection(), player.isInAir());
                }
            }
        }
    }



    public void playIdle() {
        //player.playIdle();
    }

}
