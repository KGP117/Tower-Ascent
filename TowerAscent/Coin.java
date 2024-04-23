import java.awt.Graphics2D;
import java.awt.Image;

public class Coin {
        private SoundManager noise;
        private boolean collision;
        private int x;
        private int y;
        private Animation  animCoin;
        private long value;

    public Coin(int xPos,int yPos){
        this.x = xPos;
        this.y = yPos;
        animCoin=new Animation(true);
        Image coin1 = ImageManager.loadImage("coin1.png");
        Image coin2 = ImageManager.loadImage("coin2.png");
        Image coin3 = ImageManager.loadImage("coin3.png");

        animCoin.addFrame(coin1,50);
        animCoin.addFrame(coin2,50);
        animCoin.addFrame(coin3, 50);
    }

    public void start() {
		animCoin.start();
	}

	
	public void update() {
		if (!animCoin.isStillActive()) {
			return;
		}
		animCoin.update();
	}


	public void draw(Graphics2D g2) {
		if (!animCoin.isStillActive()) {
			return;
		}
		g2.drawImage(animCoin.getImage(), x, y, 150, 125, null);
	}

    public void erase() {
        animCoin.stop(); // Stop the animation    
        collision = false;  // Disable collision
        animCoin = null;
    }

    public boolean colide(int playerX,int playerY){
        return collision;
    }

    public int collected(int playerX,int playerY){
        if(colide(x,y)){
            erase();
        }
        return 1;
    }   


}
