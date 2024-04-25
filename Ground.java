import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Ground extends Enemy{
        private int health,x, y ,look,moveCounter;
        private Image[] left,right;
        private Animation attackLeft,attackRight;
        private Image hitleft,hitright,current;
        private JPanel game;
        
    public Ground(JPanel panel,int xPos, int yPos) {
        super(xPos, yPos);
        x=xPos;
        y=yPos;
        this.health = super.setHealth(); 
        game = panel;
        loadImages();
        current=left[0];
    }

    public void loadImages(){
        right = new Image[5];
        right[0] =  ImageManager.loadImage("ground1_right.png");
        right[1] = ImageManager.loadImage("ground2_right.png");
        right[2] = ImageManager.loadImage("ground3_right.png");
        right[3] = ImageManager.loadImage("ground4_right.png");
        right[4] = ImageManager.loadImage("ground5_right.png");
        
        left = new Image[5];
        left[0] = ImageManager.loadImage("ground1_left.png");
        left[1] = ImageManager.loadImage("ground2_left.png");
        left[2] = ImageManager.loadImage("ground3_left.png");
        left[3] = ImageManager.loadImage("ground4_left.png");
        left[4] = ImageManager.loadImage("ground5_left.png");
        
        attackRight = new Animation(false);
        attackRight.addFrame( ImageManager.loadImage("groundAttack1_right.png"), 50);
        attackRight.addFrame( ImageManager.loadImage("groundAttack2_right.png"),50);
        attackRight.addFrame(ImageManager.loadImage("groundAttack3_right.png"),50);
        attackRight.addFrame(ImageManager.loadImage("groundAttack4_right.png"),50);
        attackRight.addFrame(ImageManager.loadImage("groundAttack5_right.png"),50);
    
        attackLeft= new Animation(false);
        attackLeft.addFrame(ImageManager.loadImage("groundAttack1_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("groundAttack2_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("groundAttack3_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("groundAttack4_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("groundAttack5_left.png"),50);
        
        hitleft=ImageManager.loadImage("groundHitleft");
        hitright=ImageManager.loadImage("groundHitright");    
            
    }
 
    public void attack() {
        if(look==1){
            attackLeft.start();
        }

        if(look==2){
            attackRight.start();
        }
    }

    public void draw(Graphics2D g2) {
        
    }

 
    public void erase() {
       
    }

    public void move(int action) {
        if(action==1){                  //left
            look=1;
            current=left[moveCounter];
            x=x-10;
            draw(null);
            moveCounter++;
            if(moveCounter>4){
                moveCounter=0;
            }
        }
        
        if(action==2){                  //right
            look=2;
            current=right[moveCounter];
            x=x+10;
            draw(null);
            moveCounter++;
            if(moveCounter>4){
                moveCounter=0;
            }
        }   

    }
    


    

    
    
}
