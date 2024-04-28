import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

public class GamePlay{
    private int coinCount,enemyCount;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Coin> coins;
    private SolidObjectManager agroManager,entityManager,boundaryManager;
    private SoundManager sManager;


    public GamePlay(JFrame screen){
        player=new Player(screen);
        agroManager=new SolidObjectManager();
        boundaryManager=new SolidObjectManager();
        entityManager=new SolidObjectManager();
        enemies= new ArrayList<Enemy>();
        coins=new ArrayList<Coin>();

    }

    public void loadMap(){
        
    }

    

}