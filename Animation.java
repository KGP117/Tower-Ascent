import java.awt.Image;
import java.util.ArrayList;


public class Animation {

    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    
    private long animTime;
    private long startTime;
    private long totalDuration;

    private boolean loop;
    private boolean isActive;

    
    
    // Constructor to create a new animation

    public Animation(boolean loop) {
        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        this.loop = loop;
        isActive = false;
    }


    
    // Adds a frame to the animation with the specified image and duration

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }
    


    // Starts playing the animation from the beginning

    public synchronized void start() {
        isActive = true;
        animTime = 0;
        currFrameIndex = 0;
        startTime = System.currentTimeMillis();
    }



    // Stops the animation

    public synchronized void stop() {
	    isActive = false;
    }



    // Updates the animation state based on the elapsed time
    
    public synchronized void update() {

        if (!isActive)
            return;

        long currTime = System.currentTimeMillis();
        long elapsedTime = currTime - startTime;
        startTime = currTime;

        if (frames.size() > 1) {
            animTime += elapsedTime;
                
            if (animTime >= totalDuration) {
                if (loop) {
                    animTime = animTime % totalDuration;
                    currFrameIndex = 0;
                }
                else { 
                    isActive = false;
                }
            }

            if (!isActive)
                return;

            while (animTime > getFrame(currFrameIndex).endTime) {
                    currFrameIndex++;
            }
        }
    }



    // Returns the index of the current frame being displayed

    public int getCurrentFrameIndex() {
        return currFrameIndex;
    }


    
    // Gets the image of the current frame

    public synchronized Image getImage() {
        
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }



    // Returns the total number of frames in the animation

    public int getNumFrames() {
	    return frames.size();
    }



    // Retrieves the frame at the specified index

    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }



    // Checks if the animation is currently active

    public boolean isStillActive () {
	    return isActive;
    }



    // Animation class representing frames

    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

    	// Loads the needed animations

/* 	private Animation loadAnimation(String basePath, String action, int frameCount, int frameDuration, boolean loop) {
		Animation anim = new Animation(loop);
		for (int i = 1; i <= frameCount; i++) {
			Image frame = ImageManager.loadImage(basePath + "/" + action + "_" + i + ".png");
			anim.addFrame(frame, frameDuration);
		}
		return anim;
	} */

}
