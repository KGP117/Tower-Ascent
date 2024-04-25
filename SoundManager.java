import javax.sound.sampled.AudioInputStream;		// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;

import java.util.HashMap;				// for storing sound clips


public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

   	Clip hitClip = null;				// played when bat hits ball
   	Clip appearClip = null;				// played when ball is re-generated
   	Clip backgroundClip = null;			// played continuously after ball is created

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/background.wav");
		clips.put("background", clip);		// background theme sound

		clip = loadClip("sounds/boss_battle.wav");
		clips.put("boss", clip);		// played when the boss makes an appearance

		clip = loadClip("sounds/ground_smash.wav");
		clips.put("groundSmash", clip);

		clip = loadClip("sounds/intro.wav");
		clips.put("intro", clip);

		clip = loadClip("sounds/jump.wav");
		clips.put("jump", clip);		// played for player jump

		clip = loadClip("sounds/lose.wav");
		clips.put("lose", clip);

		clip = loadClip("sounds/pew_pew_pew.wav");
		clips.put("pew", clip);		// played for player shoot

		clip = loadClip("sounds/projectile.wav");
		clips.put("projectile", clip);		// played for enemy shoots

		clip = loadClip("sounds/win.wav");
		clips.put("win", clip);
	}


	public static SoundManager getInstance() {	// class method to get Singleton instance
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


	public Clip getClip (String title) {

		return clips.get(title);		// gets a sound by supplying key
	}


    public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    		File file = new File(fileName);
    		audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    		clip = AudioSystem.getClip();
    		clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    	
        return clip;
    }


    public void playSound(String title, Boolean looping) {
		
        Clip clip = getClip(title);
		
        if (clip != null) {
			clip.setFramePosition(0);
			
            if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    }


    public void stopSound(String title) {
		
        Clip clip = getClip(title);
		
        if (clip != null) {
			clip.stop();
		}
    }

    public void toggleMute(Boolean muted) {
		
        muted = !muted;
			
        for (Clip clip : clips.values()) {
				
            if (clip != null) {
					
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					
                if (gainControl != null) {
					gainControl.setValue(muted ? 0.0f : 1.0f); // Set volume based on muted state
				} else {
					System.out.println("Volume control not supported for clip: " + clip);
				}
			}
		}
	}

}