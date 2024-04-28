import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

public class Ground extends Enemy{
        private int health,x, y ,look,moveCounter;
        private Image[] left,right;
        private Animation attackLeft,attackRight;
        private Image hitLeft,hitRight,current;
        private JFrame game;
        private SolidObject detection;
        
    public Ground(JFrame panel,int xPos, int yPos) {
        super(xPos, yPos);
        x=xPos;
        y=yPos;
        this.health = super.setHealth(); 
        game = panel;
        loadImages();
        current=left[0];
        detection = new SolidObject(xPos,yPos,100,100);
    }

    public void loadImages(){
        right = new Image[4];
        right[0] =  ImageManager.loadImage("images\\ground1_right.png");
        right[1] = ImageManager.loadImage("images\\ground2_right.png");
        right[2] = ImageManager.loadImage("images\\ground3_right.png");
        right[3] = ImageManager.loadImage("images\\ground4_right.png");
       
        left = new Image[4];
        left[0] = ImageManager.loadImage("images\\ground1_left.png");
        left[1] = ImageManager.loadImage("images\\ground2_left.png");
        left[2] = ImageManager.loadImage("images\\ground3_left.png");
        left[3] = ImageManager.loadImage("images\\ground4_left.png");
        
        attackRight = new Animation(false);
        attackRight.addFrame( ImageManager.loadImage("images\\groundAttack1_right.png"), 50);
        attackRight.addFrame( ImageManager.loadImage("images\\groundAttack2_right.png"),50);
        attackRight.addFrame(ImageManager.loadImage("images\\groundAttack3_right.png"),50);
    
        attackLeft= new Animation(false);
        attackLeft.addFrame(ImageManager.loadImage("images\\groundAttack1_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("images\\groundAttack2_left.png"),50);
        attackLeft.addFrame(ImageManager.loadImage("images\\groundAttack3_left.png"),50);
        
        hitLeft=ImageManager.loadImage("images\\groundHitleft");
        hitRight=ImageManager.loadImage("images\\groundHitright");    
            
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
        g2.draw(current,x,y);
    }

    public void move(int action) {
        if(action==1){                  //left
            look=1;
            current=left[moveCounter];
            x=x-10;
            detection.move(-10,0);
            super.enemy.move(-10, 0);
            moveCounter++;
            if(moveCounter>4){
                moveCounter=0;
            }
        }
        
        if(action==2){                  //right
            look=2;
            current=right[moveCounter];
            x=x+10;
            detection.move(10,0);
            super.enemy.move(10, 0);
            moveCounter++;
            if(moveCounter>4){
                moveCounter=0;
            }
        }   

    }

    public void changeDirection(){
        if(look==1){
            look=2;
        }
        else if(look==2){
            look=1;
        }
    }

    public void hit(){
        if(look==1){
            current=hitLeft;
        }
        if(look==2){
            current=hitRight;
        }
        health--;
    }
    
    public boolean checkAlive(){
        if(health>0){
            return true;
        }
        return false;
    }

    public void start(SolidObjectManager agroManager,SolidObject player){
        agroManager.addObject(detection);
        while(checkAlive()){
            if(!getAgro()){
               for(int i=0;i<3;i++){
                move(look);
                   if( agroManager.collision(detection,player)){
                     toggleAgro(true);
                        break;
                   }
                   changeDirection();
               }
            }

            if(getAgro()){
                while(getAgro()){
                    if(player.getX()<=x&& look==2){
                        changeDirection();
                        move(look);
                    }
                    if(player.getX()>=x&& look==1){
                        changeDirection();
                        move(look);
                    }
                    
                    if( agroManager.collision(detection,player)){
                        toggleAgro(false);
                    }
                    if(agroManager.collision(detection, player)){
                       attack(); 
                    }
                }
               
            }
            checkAlive();
        }
        
    }  

    

    
    
}
