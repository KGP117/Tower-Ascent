import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Projectile {
        private int x,y ,speed,whom,direction;
        private Image projRight,projLeft;
        private boolean collision;
        public Graphics2D g2;
        private SolidObject projectile;
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
            projRight=ImageManager.loadImage("images\\playerProjRight.png");
            projLeft=ImageManager.loadImage("images\\playerProjLeft.png");

            case "Sky":
                speed=5;
            whom=2;
            projRight=ImageManager.loadImage("images\\flyingProjRight.png");
            projLeft=ImageManager.loadImage("images\\flyingProjLeft.png");

            case "Boss":
                speed=20;
            whom=3;
            projRight=ImageManager.loadImage("images\\bossProjRight.png");
            projLeft=ImageManager.loadImage("images\\bossProjLeft.png");
        }
        return whom;
    }

    public void draw(Graphics2D g,int direction){
        g2=g;
        switch(direction){
            case 1:{               //right
                g2.drawImage(projRight,x,y,null);
            }
            case 2:{               //left
                g2.drawImage(projLeft,x,y,null);
            }
        }
    }

    public void shootLeft(){
            while(!getCollision() ||x<0){
                x-=speed;
                projectile.move(speed,0);
            }
    }

    public void shootRight(int screenLength){
        while(!getCollision() ||x>screenLength){
            x+=speed;
            projectile.move(speed,0);
        }
    }

    public boolean getCollision(){
        return collision;
    }

    public boolean hit(SolidObjectManager manager){
        SolidObject a=manager.collidesWith(projectile.getBoundingRectangle());
        if(manager.collision(projectile, a)){
            collision=true;
            return true;
        }
        else{
            collision=false;
            return false;
        }
    }
    
       
}
