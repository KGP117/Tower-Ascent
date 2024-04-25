import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Boss extends  Enemy {
    private int health,x,y,look,moveCounter;
    private Image right[],left[];
    private Animation attackLeft,attackRight;
    private Image hitleft,hitright,current;
    private Projectile proj;
    private Random rand;
    private JPanel game;

        public Boss(JPanel panel,int xPos, int yPos) {
            super(xPos, yPos);
            x=xPos;
            y=yPos;
            this.health = 15 + super.setHealth();
            rand=new Random();
            game=panel;
            loadImages();
            current=left[0];
        }

        public void attack() {
           
            if(look==1){             //shoots left
                proj=new Projectile(x-10,y, "Boss");
                attackLeft.start();
                proj.shootLeft();

            }

            if(look==2){             //shoots right    
                proj=new Projectile(x+10,y, "Boss");
                attackRight.start();
                proj.shootRight(game.getWidth());
            }
        }

        public void loadImages(){
            right = new Image[5];
            right[0] =  ImageManager.loadImage("boss1_right.png");
            right[1] = ImageManager.loadImage("boss2_right.png");
            right[2] = ImageManager.loadImage("boss3_right.png");
            right[3] = ImageManager.loadImage("boss4_right.png");
            right[4] = ImageManager.loadImage("boss5_right.png");
            
            left = new Image[5];
            left[0] = ImageManager.loadImage("boss1_left.png");
            left[1] = ImageManager.loadImage("boss2_left.png");
            left[2] = ImageManager.loadImage("boss3_left.png");
            left[3] = ImageManager.loadImage("boss4_left.png");
            left[4] = ImageManager.loadImage("boss5_left.png");
            
            attackRight = new Animation(false);
            attackRight.addFrame( ImageManager.loadImage("bossAttack1_right.png"), 50);
            attackRight.addFrame( ImageManager.loadImage("bossAttack2_right.png"),50);
            attackRight.addFrame(ImageManager.loadImage("bossAttack3_right.png"),50);
            attackRight.addFrame(ImageManager.loadImage("bossAttack4_right.png"),50);
            attackRight.addFrame(ImageManager.loadImage("bossAttack5_right.png"),50);
            
        

            attackLeft= new Animation(false);
            attackLeft.addFrame(ImageManager.loadImage("bossAttack1_left.png"),50);
            attackLeft.addFrame(ImageManager.loadImage("bossAttack2_left.png"),50);
            attackLeft.addFrame(ImageManager.loadImage("bossAttack3_left.png"),50);
            attackLeft.addFrame(ImageManager.loadImage("bossAttack4_left.png"),50);
            attackLeft.addFrame(ImageManager.loadImage("bossAttack5_left.png"),50);
            
            hitleft=ImageManager.loadImage("bossHitleft");
            hitright=ImageManager.loadImage("bossHitright");    
                
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
