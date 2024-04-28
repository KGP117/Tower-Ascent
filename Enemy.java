import java.awt.Graphics2D;
import java.util.Random;

public abstract class Enemy {
    private int health;
    private int x;
    private int y;
    private Random  rand;
    private boolean collision,agro;
    protected SolidObject enemy;

    public Enemy(int x,int y){
        this.x=x;
        this.y=y;
        rand=new Random();
        agro=false;
        enemy=new SolidObject(x,y,10,10);
    }

    public boolean getAgro(){
        return agro;
    }
   
    public void toggleAgro(boolean newAgro){
        this.agro=newAgro;
    }

    public int setHealth(){
       return health=1+rand.nextInt(9);
    }

    public void hit(SolidObjectManager entities,SolidObject projectile){
        entities.addObject(enemy);
        if(entities.collision(enemy,projectile)){
            health--;
        }
    } 

    public abstract void attack();
    public abstract void draw(Graphics2D g2);

}
