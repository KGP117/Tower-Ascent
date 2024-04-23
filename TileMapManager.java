import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.ImageIcon;


/**
    The ResourceManager class loads and manages tile Images and
    "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class TileMapManager {

    private ArrayList<Image> tiles;
    private JFrame window;


    private GraphicsConfiguration gc;

    // host sprites used for cloning
    private Player playerSprite;
    private Sprite enemySprite;
    private Sprite coinSprite;


    public TileMapManager(JFrame window) {
	this.window = window;

        loadTileImages();

        loadCreatureSprites();
    }


     public TileMap loadMap(String filename)
        throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();
        int mapWidth = 0;
        int mapHeight = 0;

        // read every line in the text file into the list

        BufferedReader reader = new BufferedReader(
        
        new FileReader(filename));

        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                mapWidth = Math.max(mapWidth, line.length());
            }
        }

        // parse the lines to create a TileMap
        mapHeight = lines.size();

        TileMap newMap = new TileMap(window, mapWidth, mapHeight);
        
        for (int y=0; y<mapHeight; y++) {
            String line = lines.get(y);
            
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, tiles.get(tile));
                }

                // check if the char represents a sprite
                else if (ch == 'o') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, enemySprite, x, y);
                }
            }
        }

        return newMap;
    }



    private void addSprite(TileMap map,
        Sprite hostSprite, int tileX, int tileY)
    {
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite)hostSprite.clone();

            // center the sprite
/*             sprite.setX(
                TileMap.tilesToPixels(tileX) +
                (TileMap.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMap.tilesToPixels(tileY + 1) -
                sprite.getHeight()); */

            // add it to the map
            map.addSprite(sprite);
        }
    }



    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ folder

	File file;

	//System.out.println("loadTileImages called.");

        tiles = new ArrayList<Image>();
        char ch = 'A';
        while (true) {
            String filename = "images/tile_" + ch + ".png";
	    file = new File(filename);
            if (!file.exists()) {
		System.out.println("Image file could not be opened: " + filename);
                break;
            }
	    else{
		//System.out.println("Image file opened: " + filename);
		Image tileImage = new ImageIcon(filename).getImage();
           	tiles.add(tileImage);
            ch++;
        }
        }
    }


    public void loadCreatureSprites() {

        Image[][] images = new Image[4][];

        // load left-facing images
        images[0] = new Image[] {
            ImageManager.loadImage("images/enemyLeft.gif"),
            ImageManager.loadImage("images/coin1.png"),
            ImageManager.loadImage("images/coin2.png"),
            ImageManager.loadImage("images/coin3.png"),
        };

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];

        //System.out.println("loadCreatureSprites successfully executed.");
    }


}
