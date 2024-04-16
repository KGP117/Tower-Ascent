import java.awt.Image;

public class Boss extends  Enemy {
    private int health;
    private int x;
    private int y;
    private Image image;

        public Boss(int xPos, int yPos) {
            super(xPos, yPos);
            this.health = 15 + super.setHealth();
            super.setAgro(2);
        }

        public void attack() {
      
        }

        public void draw() {
        
        }

    
        public void erase() {
        
        }

    
        public void move() {
        
        }

    
        public void run() {
        
        }
    
}
