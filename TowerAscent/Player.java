import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player{
    private int health,x,y,current;
    private int width,height;
    private Image[] right,left;
    private boolean onGround,hit  ;
    private Projectile proj;
    private JPanel game;    

    public Player(JPanel panel,int xPos, int yPos){
        x=xPos;
        y=yPos;
        health=5;
        current =2;
        game=panel;
        loadMove();
    }

    public void loadMove(){
        right = new Image[5];
        right[0] = new ImageIcon("player1_right.png").getImage();
        right[1] = new ImageIcon("player2_right.png").getImage();
        right[2] = new ImageIcon("player3_right.png").getImage();
        right[3] = new ImageIcon("player4_right.png").getImage();
        right[4] = new ImageIcon("player5_right.png").getImage();
        
        left = new Image[5];
        left[0] = new ImageIcon("player1_left.png").getImage();
        left[1] = new ImageIcon("player2_left.png").getImage();
        left[2] = new ImageIcon("player3_left.png").getImage();
        left[3] = new ImageIcon("player4_left.png").getImage();
        left[4] = new ImageIcon("player5_left.png").getImage();
    }

    public void draw(){
       
        
    }

    public void erase(){
        // Add code to erase the player from the screen
    }

    public void update(){
        // Add code to update the player's state
    }

    public void jump(){
        // Add code to make the player jump
    }

    public void fall(){
        // Add code to make the player fall
    }
    
    public void shoot(){
        if(current==1){             //shoots left
            proj=new Projectile(x-10,y, "player");
            proj.shootLeft();
            proj.shootLeft();
            proj.shootLeft();
        }
        if(current==2){             //shoots right    
            proj=new Projectile(x+10,y, "player");
            proj.shootRight(game.getWidth());
            proj.shootRight(game.getWidth());
            proj.shootRight(game.getWidth());
        }
    }
   

    public void move(int directions){
        switch(directions) {
        case 1: //move left
            // Add code to move left
            break;

        case 2: //move right
            // Add code to move right
            break;

        case 3: //jump
            jump();
            break;

        }
    }

    
}