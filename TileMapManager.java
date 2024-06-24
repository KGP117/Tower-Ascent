import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.imageio.ImageIO;


public class TileMapManager {

    private ArrayList<Image> tiles;
    private JFrame window;


    public TileMapManager(JFrame window) {
	    this.window = window;

        loadTileImages();
    }


    public TileMap loadMap(String filename) throws IOException {
        
        ArrayList<String> lines = new ArrayList<String>();
        int mapWidth = 0;
        int mapHeight = 0;

        InputStream inputStream = getClass().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("Map file not found: " + filename);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("#")) {
                lines.add(line);
                mapWidth = Math.max(mapWidth, line.length());
            }
        }

        mapHeight = lines.size();

        TileMap newMap = new TileMap(window, mapWidth, mapHeight);
        
        for (int y=0; y<mapHeight; y++) {
            String line = lines.get(y);
            
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, tiles.get(tile));
                }
            }
        }

        return newMap;
    }



    public void loadTileImages() {
        tiles = new ArrayList<Image>();
        char ch = 'A';
        
        while (true) {
            String filename = "/images/tiles/Tile_" + ch + ".png";
            InputStream inputStream = getClass().getResourceAsStream(filename);
            
            if (inputStream == null) {
                System.err.println("Tile image not found: " + filename);
                break;
            } else {
                try {
                    Image tileImage = ImageIO.read(inputStream);
                    tiles.add(tileImage);
                    ch++;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
