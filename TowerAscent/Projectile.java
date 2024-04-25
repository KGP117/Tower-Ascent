import java.awt.Image;

import javax.swing.ImageIcon;

public class Projectile {
        private int x;
        private int y;
        private int speed;
        private int whom;
        private boolean collide;
        private Image projRight;
        private Image projLeft;
        private boolean collision;

        public Projectile(int initX,int initY,String who){
        x = initX;
        y = initY;
        whom=whoProjectile(who);
        collision=false;
    }

    public int whoProjectile(String who){
        switch(who){
            case "Player":
                speed=10;
            whom= 1;
            projRight=ImageManager.loadImage("playerProjRight.png");
            projLeft=ImageManager.loadImage("playerProjLeft.png");

            case "Sky":
                speed=5;
            whom=2;
            projRight=ImageManager.loadImage("flyingProjRight.png");
            projLeft=ImageManager.loadImage("flyingProjLeft.png");

            case "Boss":
                speed=20;
            whom=3;
            projRight=ImageManager.loadImage("bossProjRight.png");
            projLeft=ImageManager.loadImage("bossProjLeft.png");
        }
        return whom;
    }

    public void draw(){
        switch(whom){
            case 1:{
                break;
            }
                

            case 2:{
                 break;
            }

            case 3:{
                break;
            }

                
        }
    }

    public void erase(){
        
    }

   

    public void shootLeft(){
            while(collision!=true ||x<0){
                x-=speed;
            }
    }

    public void shootRight(int screenLength){
        while(collision!=true ||x>screenLength){
            x+=speed;
        }
    }
    
       
}
