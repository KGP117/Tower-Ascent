public class Projectile {
        private int x;
        private int y;
        private int speed;
        private int whom;
        private boolean collide;
    
        public Projectile(int initX,int initY,String who){
        x = initX;
        y = initY;
        whom=whoProjectile(who);

    }

    public int whoProjectile(String who){
        switch(who){
            case "Player":
                speed=10;
            whom= 1;
            
            case "Ground":
                speed=15;
            whom=2;

            case "Sky":
                speed=5;
            whom=3;

            case "Boss":
                speed=20;
            whom=4;
        }
        return whom;
    }

    public void draw(){

    }

    public void erase(){

    }

    public boolean collision(int item, int itemX,int itemY){
        switch(item){
            case 1: //ground
                break;
            
            case 2: //player
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
        return collide;
    }

    public void shootLeft(){

    }

    public void shootRight(){

    }

    public void shootUp(){

    }

        
}
