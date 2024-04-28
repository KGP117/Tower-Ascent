import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Flying extends Enemy {
    private int health,x,y,look;
    private Animation  animFlyRight,animFlyLeft;
    private Projectile proj;
    private JPanel game;
    private SolidObject detection;

    public Flying(JPanel panel,int xPos, int yPos) {
        super(xPos, yPos);
        health=super.setHealth();
     
        detection= new SolidObject(xPos, yPos, 50, 50);
    }

    public void setUpAnimation(){
        animFlyRight=new Animation(true);
        Image flyRight1 = ImageManager.loadImage("images\\flying1_right.png");
        Image flyRight2 = ImageManager.loadImage("images\\flying2_right.png");
        Image flyRight3 = ImageManager.loadImage("images\\flying3_right.png");
        Image flyRight4 = ImageManager.loadImage("images\\flying4_right.png");

        animFlyRight.addFrame(flyRight1,50);
        animFlyRight.addFrame(flyRight2,50);
        animFlyRight.addFrame(flyRight3, 50); 
        animFlyRight.addFrame(flyRight4, 50);  

        animFlyLeft=new Animation(true);
        Image flyLeft1 = ImageManager.loadImage("images\\flying1_left.png");
        Image flyLeft2 = ImageManager.loadImage("images\\flying2_left.png");
        Image flyLeft3 = ImageManager.loadImage("images\\flying3_left.png");
        Image flyLeft4 = ImageManager.loadImage("images\\flying4_left.png");

        animFlyLeft.addFrame(flyLeft1,50);
        animFlyLeft.addFrame(flyLeft2,50);
        animFlyLeft.addFrame(flyLeft3, 50); 
        animFlyLeft.addFrame(flyLeft4, 50);  
    }

    public void startAni() {
		switch(look){
            case 1:{
                animFlyRight.start();
                animFlyLeft.stop();
            }
            case 2:{
                animFlyLeft.start();
                animFlyRight.stop();
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

    public void move(int action) {
        if(action==1){               //left
            look=1;
            x=x-10;
        }
        if(action==2){
            look=2;
            x=x+10;
        }
        if(action==3){
                y=y+10;           
        }
        if(action==4){
            y-=10;
        }
    }

    public boolean checkAlive(){
        if(health>0){
            return true;
        }
        return false;
    }

    public void changeDirection(){
        if(look==1){
            startAni();
            look=2;

        }
        if(look==2){
            startAni();
            look=1;
        }
    }

    public void start(SolidObjectManager manager,SolidObject player){
        manager.addObject(detection);
        while(checkAlive()){
            if(!getAgro()){
               for(int i=0;i<3;i++){
                move(look);
                   if( manager.collision(detection,player)){
                     toggleAgro(true);
                        break;
                   }
    
               }
               changeDirection(); 
            }

            if(getAgro()){
                while(getAgro()){
                    if(y>player.getY()){
                        move(3);
                    }
                    if(y<player.getY()){
                        move(4);
                    }
                    if(player.getX()<=x&& look==2){
                        changeDirection();
                        attack();
                    }
                    if(player.getX()>=x&& look==1){
                        changeDirection();
                        attack();
                    }
                    else
                        attack();
                    if( manager.collision(detection,player)){
                        toggleAgro(false);
                    }
                }
               
            }
            checkAlive();
        }
        
    }

   
}
