public class Coin {
        private SoundManager noise;
        private boolean collision;
        private int x;
        private int y;
        private Image coin1;
        private Image coin2;
        private Image coin3;

    public Coin(int xPos,int yPos){
        this.x = xPos;
        this.y = yPos;
    }

    public void draw(){

    }

    public void erase(){
        
    }

    public boolean colide(int playerX,int playerY){
        return collision;
    }

    public int collected(int playerX,int playerY){
        if(colide(x,y)){
            erase();

        }
        return 1;
    }   


}
