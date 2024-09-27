import java.awt.Image;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class TileMap {

    private int TILE_SIZE = 64;

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

    BackgroundManager bgManager;
    SoundManager soundManager;


    public TileMap(JFrame window, int width, int height, int characterIndex, double displayScale) {

        TILE_SIZE = (int)(TILE_SIZE*displayScale);

        this.window = window;

        soundManager = SoundManager.getInstance();

        screenWidth = (int)(1280 * displayScale);
        screenHeight = (int)(720 * displayScale);

        mapWidth = width;
        mapHeight = height;

        offsetY = screenHeight - tilesToPixels(mapHeight);

        bgManager = new BackgroundManager (window, 12, displayScale);
        tiles = new Image[mapWidth][mapHeight];
        player = new Player (window, this, bgManager, characterIndex, displayScale);
        coin = new Coin[10];
        groundEnemy = new GroundEnemy[3];
        flyingEnemy = new FlyingEnemy[2];
        bossEnemy = new BossEnemy[2];

        int x = (int)(150*displayScale);					// position player in 'random' location
        int y = screenHeight - ((TILE_SIZE + (int)(80*displayScale)));

        player.setX(x);
        player.setY(y);


        // Object Positioning

        if (((GameWindow) window).getLevel() == 1){
            coin[1] = new Coin (player, (int)(1000*displayScale), (int)(600*displayScale), displayScale);
            coin[2] = new Coin (player, (int)(900*displayScale), (int)(600*displayScale), displayScale);
            coin[3] = new Coin (player, (int)(800*displayScale), (int)(600*displayScale), displayScale);
            door = new DungeonDoor(player, (int)(1131*displayScale), (int)(556*displayScale), displayScale);

            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
        }

        else if (((GameWindow) window).getLevel() == 2){
            coin[1] = new Coin (player, (int)(500*displayScale), (int)(150*displayScale), displayScale);
            coin[2] = new Coin (player, (int)(1300*displayScale), (int)(345*displayScale), displayScale);
            coin[3] = new Coin (player, (int)(1125*displayScale), (int)(475*displayScale), displayScale);
            door = new DungeonDoor(player, (int)(350*displayScale), (int)(110*displayScale), displayScale);
            groundEnemy[1] = new GroundEnemy (this, player, (int)(800*displayScale), screenHeight-TILE_SIZE-(int)(100*displayScale), displayScale);

            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
        }

        else if (((GameWindow) window).getLevel() == 3){
            coin[1] = new Coin (player, (int)(6100*displayScale), (int)(350*displayScale), displayScale);
            coin[2] = new Coin (player, (int)(900*displayScale), (int)(25*displayScale), displayScale);
            coin[3] = new Coin (player, (int)(3850*displayScale), (int)(275*displayScale), displayScale);
            door = new DungeonDoor(player, (int)(6100*displayScale), (int)(556*displayScale), displayScale);
            groundEnemy[1] = new GroundEnemy (this, player, (int)(1600*displayScale), screenHeight-TILE_SIZE-(int)(164*displayScale), displayScale);
            flyingEnemy[1] = new FlyingEnemy(this, player, (int)(3400*displayScale), (int)(250*displayScale), displayScale);
            bossEnemy[1] = new BossEnemy(this, player, (int)(5500*displayScale), (int)(325*displayScale), displayScale);

            coin[1].start();
            coin[2].start();
            coin[3].start();
            door.start();
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
        
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight){
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

    
    // Class method to convert a pixel position to a tile position.
    
    public int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    
    // Class method to convert a pixel position to a tile position.

    public int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    
    // Class method to convert a tile position to a pixel position.

    public int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    
    // Draws the specified TileMap.
    
    public void draw(Graphics2D g2) {

        int mapWidthPixels = tilesToPixels(mapWidth);

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
                    g2.drawImage(image, tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
        
        player.draw(g2, offsetX, player.getDirection());


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
        if (!player.isAttacking() && !player.isHit()){
            player.move(1);
        }
    }

    public void moveRight() {
        if (!player.isAttacking() && !player.isHit()){
            player.move(2);
        }
    }

    public void jump() {
        if (!player.isGoingUp() && !player.isGoingDown() && !player.isAttacking() && !player.isHit()){
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
            
            door.update();

            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }
        }

        else if (((GameWindow) window).getLevel() == 2){

            door.update();
            groundEnemy[1].update();

            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }

            if (groundEnemy[1].attackHitPlayer()) {
            
                player.getHit();

                player.minusLive();

                soundManager.playSound("hurt", false);
                
                if (player.getX() < groundEnemy[1].getX()){
                    player.setX(player.getX()-100);
                    player.update(player.getDirection(), player.isInAir());
                }
                else if (player.getX() > groundEnemy[1].getX()){
                    player.setX(player.getX()+100);
                    player.update(player.getDirection(), player.isInAir());
                }
            }

            if (player.isAttacking()){
                if (groundEnemy[1].gotHit()){
                    soundManager.playSound("hurt", false);
                    if (player.getX() < groundEnemy[1].getX()){
                        groundEnemy[1].setX(groundEnemy[1].getX()+100);
                    }
                    else if (player.getX() > groundEnemy[1].getX()){
                        groundEnemy[1].setX(groundEnemy[1].getX()-100);
                    }
                    //groundEnemy[1] = null; // This crashes is enemy is set to null, use a boolean like isDead
                }
            }
        }

        else if (((GameWindow) window).getLevel() == 3){

            door.update();
            groundEnemy[1].update();
            flyingEnemy[1].update();
            bossEnemy[1].update();


            if (door.collidesWithPlayer()){
                ((GameWindow) window).endLevel();
            }

            if (groundEnemy[1].attackHitPlayer()) {
            
                player.getHit();

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

            if (player.isAttacking()){
                if (groundEnemy[1].gotHit()){
                    if (player.getX() < groundEnemy[1].getX()){
                        groundEnemy[1].setX(groundEnemy[1].getX()+100);
                    }
                    else if (player.getX() > groundEnemy[1].getX()){
                        groundEnemy[1].setX(groundEnemy[1].getX()-100);
                    }
                }
            }
            
            if (flyingEnemy[1].collidesWithPlayer()) {
            
                player.getHit();

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
            
                player.getHit();

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
        player.playIdle();
    }

}
