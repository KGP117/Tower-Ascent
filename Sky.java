import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Sky extends Enemy {
    private int health,x,y,look;
    private Image image;
    private Animation  animFlyRight,animFlyLeft;
    private Projectile proj;
    private JPanel game;
    private Boolean onGround,hit,directionChange;

    public Sky(JPanel panel,int xPos, int yPos) {
        super(xPos, yPos);
        health=super.setHealth();
        directionChange=false; 
    }

    public void setUpAnimation(){
        animFlyRight=new Animation(true);
        Image flyRight1 = ImageManager.loadImage("flying1_right.png");
        Image flyRight2 = ImageManager.loadImage("flying2_right.png");
        Image flyRight3 = ImageManager.loadImage("flying3_right.png");
        Image flyRight4 = ImageManager.loadImage("flying4_right.png");

        animFlyRight.addFrame(flyRight1,50);
        animFlyRight.addFrame(flyRight2,50);
        animFlyRight.addFrame(flyRight3, 50); 
        animFlyRight.addFrame(flyRight4, 50);  

        animFlyLeft=new Animation(true);
        Image flyLeft1 = ImageManager.loadImage("flying1_left.png");
        Image flyLeft2 = ImageManager.loadImage("flying2_left.png");
        Image flyLeft3 = ImageManager.loadImage("flying3_left.png");
        Image flyLeft4 = ImageManager.loadImage("flying4_left.png");

        animFlyLeft.addFrame(flyLeft1,50);
        animFlyLeft.addFrame(flyLeft2,50);
        animFlyLeft.addFrame(flyLeft3, 50); 
        animFlyLeft.addFrame(flyLeft4, 50);  
    }

    public void start(int direction) {
		switch(direction){
            case 1:{
                animFlyRight.start();
            }
            case 2:{
                animFlyLeft.start();
            }   
        
        }
	}

    public void attack() {
           
        if(look==1){             //shoots left
            proj=new Projectile(x-10,y, "fly");
            proj.shootLeft();
        }
        if(look==2){             //shoots right    
            proj=new Projectile(x+10,y, "fly");
            proj.shootRight(game.getWidth());
        }
    }

    public void draw(Graphics2D g2) {
		if (!animFlyRight.isStillActive()&&!animFlyLeft.isStillActive()) {
			return;
		}
		if(animFlyRight.isStillActive()&&animFlyLeft.isStillActive()){
		    return;
		}
        if(animFlyRight.isStillActive()){
            g2.drawImage(animFlyRight.getImage(), x, y, 150, 125, null);
        }
        if(animFlyLeft.isStillActive()){
            g2.drawImage(animFlyLeft.getImage(), x, y, 150, 125, null);
        }
		
	}

    public void erase() {

    }

    public void move(int action) {
        if(action==1){               //left
            look=1;
            x=x-10;
        }
        if(action==2){
            look=2;
            x=x+10;
        }
        if(action==3 && !onGround){
            while(!onGround){
                y=y+10;
                   
            }
        }
    }

    public void groundCheck(boolean toggle){
        if(toggle){
            onGround=true;
        }
    }

    

  
}
