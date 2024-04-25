
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Vector;

public class SolidObjectManager {

   private Vector<SolidObject> solidObjects;
   private SolidObject solidObject;
   private int vectorSize=2;
  
   public SolidObjectManager () {
      solidObjects = new Vector <SolidObject> ();
      solidObject = new SolidObject (160, 225, 40, 75);
      solidObjects.add(solidObject);

   }

   public void addObject(int xPos, int yPos, int width, int height){
      SolidObject a = new SolidObject (xPos, yPos, width, height);
      vectorSize++;
      solidObjects.add(a);
   }

   public void draw (Graphics2D g2) {
	
      for (int i=0; i<vectorSize; i++) {
	      SolidObject solidObject = solidObjects.get(i);
	      solidObject.draw (g2);
      }

   }
   


   public SolidObject collidesWith(Rectangle2D.Double boundingRectangle) {

      for (int i=0; i<vectorSize; i++) {
	      SolidObject solidObject = solidObjects.get(i);
	      Rectangle2D.Double rect = solidObject.getBoundingRectangle();
	         if (rect.intersects (boundingRectangle)) {
	            return solidObjects.get(i);
	         }
      }
      return null;
   }


   public boolean onSolidObject(int x, int width) {

      for (int i=0; i<vectorSize; i++) {
	      SolidObject solidObject = solidObjects.get(i);
	      int solidRight = solidObject.getX() + solidObject.getWidth() - 1;
	         if (x + width > solidObject.getX() && x <= solidRight) {
		         return true;
	         }
      }
      return false;
   }

   public boolean underSolidObject(){
      

      return false;
   }

}