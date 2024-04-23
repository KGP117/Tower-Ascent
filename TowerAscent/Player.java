import java.awt.Image;

import javax.swing.JFrame;

public class Player{
    private int health;
    private boolean collision;
    
    public Player(JFrame window, TileMap tileMap, BackgroundManager bgManager){
        health=5;
        collision=false;
    }

    public void draw(){

    }

    public void erase(){

    }

    public void update(){
        
    }

    public void jump(){

    }

    public void fall(){

    }
    
    public void shoot(){

    }
    
    public boolean collide(int item, int itemX,int itemY){
        switch(item){
            case 1: //ground
                break;
            
            case 2: //projectile
                break;
            
            case 3: // platform side;    
                break;
            
            case 4: //platform top
                break;
            
            case 5: // platform bottom
                break;     

            case 6: // enemy
                break;
            
            case 7: //coin
                break;    


        }
        return collision;
    }

    public void move(int directions){
        switch(directions) {
        case 1: //move left
            break;

        case 2: //move right
            break;
        
        case 3: //jump
            break;
        
        }
    }

    public float getX() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getX'");
    }

    public float getY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }

    public void setX(int x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setX'");
    }

    public void setY(int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setY'");
    }

    public Image getImage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getImage'");
    }

    
}