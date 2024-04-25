import javax.swing.JFrame;

public class GamePlay {
    private JFrame screen;
    private Enemy[] enemies;
    private Player player;
    private Coin[] coins;
    private TileMap map;
    private BackgroundManager backgroundManager;

    public GamePlay() {
        screen=new JFrame();
        backgroundManager=new BackgroundManager(screen, 0);
        map=new TileMap(screen, 0, 0);
        player=new Player(null,map, backgroundManager);
        
    }
}
