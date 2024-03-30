package Enemy;
import java.util.Random;
import javafx.scene.image.Image;

public abstract class Enemy extends Thread {
    private int health;
    private int x;
    private int y;
    private Random  rand;
    private boolean collision;
    private boolean agro;


    public Enemy(int x,int y){
        this.x=x;
        this.y=y;
        rand=new Random();
        agro=setAgro();
    }

    public int setHealth(){
       return health=1+rand.nextInt(9);
    }

    public void takeDamage(int projX,int projY){
        if(collide(projX,projY)){
            health-=1;
        }
    }

    public boolean setAgro(){
        int i=rand.nextInt(2);
        if(i==1)
        agro= false;

        if(i==1)
        agro=true;

        return agro;
    }

    


    public  boolean collide(int x,int y){
        
        return collision;
    }

    public abstract void attack();
    public abstract void draw();
    public abstract void erase();
    public abstract void move();
    public abstract void run();
}
