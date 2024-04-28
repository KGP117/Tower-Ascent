import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Player{
    private int health,x,y,imageCounter,look;
    private Image current;
    private int width,height;
    private Image[] right,left;
    private boolean onGround,hit  ;
    private Projectile proj;
    private JFrame game;   
    private SolidObject player; 
    private Graphics2D g2;
    

    public Player(JFrame panel,int xPos, int yPos){
        x=xPos;
        y=yPos;
        width=;
        height=;
        health=5;
        imageCounter=0;
        current =left[imageCounter];
        game=panel;
        loadMove();
        player = new SolidObject(x,y,width,height);
    }

    public void loadMove(){
        right = new Image[5];
        right[0] = new ImageIcon("images\\player1_right.png").getImage();
        right[1] = new ImageIcon("images\\player2_right.png").getImage();
        right[2] = new ImageIcon("images\\player3_right.png").getImage();
        right[3] = new ImageIcon("images\\player4_right.png").getImage();
        right[4] = new ImageIcon("images\\player5_right.png").getImage();
        
        left = new Image[5];
        left[0] = new ImageIcon("images\\player1_left.png").getImage();
        left[1] = new ImageIcon("images\\player2_left.png").getImage();
        left[2] = new ImageIcon("images\\player3_left.png").getImage();
        left[3] = new ImageIcon("images\\player4_left.png").getImage();
        left[4] = new ImageIcon("images\\player5_left.png").getImage();
    }

    public void draw(Graphics2D g2){
       g2.drawImage(current,x,y,width,height,null);
    }

    public void shoot(){
        if(look==1){             //shoots left
            proj=new Projectile(x-10,y, "player");
            proj.shootLeft();
            proj.shootLeft();
            proj.shootLeft();
        }
        if(look==2){             //shoots right    
            proj=new Projectile(x+10,y, "player");
            proj.shootRight(game.getWidth());
            proj.shootRight(game.getWidth());
            proj.shootRight(game.getWidth());
        }
    }
   

    public void move(int directions){
        switch(directions) {
        case 1:{
            imageCounter++;
            x=x-10;
            player.move(-10, 0);
            if(imageCounter>4){
                imageCounter=0;
            }
            current=left[imageCounter];
            break;
        }
    
        case 2:{
            imageCounter++;
            x=x+10;
                player.move(10,0);
                if(imageCounter>4){
                    imageCounter=0;
                }

                current=right[imageCounter];
                break;
        }
    }
    }

    public int getLives(){
        return health;
    }
    
}