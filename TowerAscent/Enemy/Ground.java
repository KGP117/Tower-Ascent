package Enemy;
public class Ground extends Enemy{
        private int health;
        private int x;
        private int y;

    public Ground(int xPos, int yPos) {
        super(xPos, yPos);
        this.health = super.setHealth(); 
    }

    @Override
    public void attack() {
   
    }

    @Override
    public void run() {
      
    }

    @Override
    public void draw() {
   
    }

    @Override
    public void erase() {
       
    }

    @Override
    public void move() {
       
    }

    
    
}
