import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class PlayerProjectile {

    private int XSIZE = 20;
    private int YSIZE = 20;

    private int x; 
    private int y;
        
    private int dx = 20;
    private int direction;

    private Image projectileLeftImage;
    private Image projectileRightImage;

    private Player player;
    private GroundEnemy enemy;

    public Graphics2D g2;

    public PlayerProjectile(Player player, GroundEnemy[] groundEnemy, FlyingEnemy[] flyingEnemy, BossEnemy[] bossEnemy){
            
        this.player = player;

        x = player.getX() + 80;
        y = player.getY() + 5;

        projectileLeftImage = ImageManager.loadImage("images/playerProjectileLeft.png");
        projectileRightImage = ImageManager.loadImage("images/playerProjectileRight.png");
    }


    public void draw (Graphics2D g2, int direction, int offSetX) {

        //setCoordites();

        if (direction == 1){
            g2.drawImage(projectileLeftImage, x+offSetX, player.getY()+5, XSIZE, YSIZE, null);
        }

        if (direction == 2){
            g2.drawImage(projectileRightImage, x+offSetX, player.getY()+5, XSIZE, YSIZE, null);
        }
	}


    public void setCoordites(){
        this.x = player.getX() + 80;
        this.y = player.getY() + 5;
    }


    public void update() {		
        x = x + dx;
	}


    public boolean collidesWithEnemy () {
		Rectangle2D.Double projectileRect = getBoundingRectangle();
		Rectangle2D.Double enemyRect = enemy.getBoundingRectangle();
		
		if (projectileRect.intersects(enemyRect)) {
			return true;
		}
		else
			return false;
	}

    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

}
