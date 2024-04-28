import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class SolidObjectManager {

    private List<SolidObject> solidObjects;

    public SolidObjectManager() {
        solidObjects = new ArrayList<>();
    }

    public void addObject(int x, int y, int width, int height) {
        solidObjects.add(new SolidObject(x, y, width, height));
    }

    public void addObject(SolidObject a){
        solidObjects.add(a);
    }


    public SolidObject collidesWith(Rectangle2D.Double boundingRectangle) {
        for (SolidObject solidObject : solidObjects) {
            Rectangle2D.Double rect = solidObject.getBoundingRectangle();
            if (rect.intersects(boundingRectangle)) {
                return solidObject;
            }
        }
        return null;
    }

    public boolean onSolidObject(int x, int width) {
        for (SolidObject solidObject : solidObjects) {
            int solidRight = solidObject.getX() + solidObject.getWidth() - 1;
            if (x + width > solidObject.getX() && x <= solidRight) {
                return true;
            }
        }
        return false;
    }

    public boolean collision(SolidObject collider,SolidObject collidee){
        Rectangle2D.Double rect = collider.getBoundingRectangle();
            if (rect.intersects(collidee.getBoundingRectangle())) {
                return true;
            }
            else 
            return false;

    }
}
