import java.awt.*;
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

    public TileMapManager(JFrame window) {
	this.window = window;

        loadTileImages();
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

            }
        }

        return newMap;
    }



    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ folder

	File file;

        tiles = new ArrayList<Image>();
        char ch = 'A';
        
        while (true) {
            String filename = "images/tile_" + ch + ".png";
	        file = new File(filename);
            
            if (!file.exists()) {
                break;
            }
            else{
                Image tileImage = new ImageIcon(filename).getImage();
                tiles.add(tileImage);
                ch++;
            }
        }
    }

}
