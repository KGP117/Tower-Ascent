import java.awt.Graphics2D;
import java.util.Random;

public abstract class Enemy {
    private int health;
    private int x;
    private int y;
    private Random  rand;
    private boolean collision;

    public Enemy(int x,int y){
        this.x=x;
        this.y=y;
        rand=new Random();
      
    }

    public int setHealth(){
       return health=1+rand.nextInt(9);
    }

    

  

    


    public abstract void attack();
    public abstract void draw(Graphics2D g2);
    public abstract void erase();
    public abstract void move(int action);
}
