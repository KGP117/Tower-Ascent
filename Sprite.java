import java.awt.*;

public class Sprite {

    private Animation animation;
    private int x;          // x-position of sprite
    private int y;          // y-position of sprite
    private int dx;
    private int dy;

    public Sprite(Animation animation) {
        this.animation = animation;
    }

    public void update(long elapsedTime) {
        // Update the sprite's position based on its velocity
        x += dx * elapsedTime;
        y += dy * elapsedTime;

        // Update the animation
        //animation.update(elapsedTime);
    }

    public void draw(Graphics2D g) {
        // Draw the current frame of the animation at the sprite's position
        g.drawImage(animation.getImage(), (int) x, (int) y, null);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return animation.getImage().getWidth(null);
    }

    public int getHeight() {
        return animation.getImage().getHeight(null);
    }

    @Override
    public Object clone() {
        try {
            // Perform a shallow copy
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since Sprite implements Cloneable
            throw new InternalError(e);
        }
    }

}
