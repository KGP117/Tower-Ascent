import java.io.*;
import java.util.HashMap;
import javax.sound.sampled.*;


public class SoundManager {	
	
	HashMap<String, Clip> clips;
	private static SoundManager instance = null;


	// Private constructor to initialize the SoundManager

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("/sounds/background.wav");
		clips.put("background", clip);

		clip = loadClip("/sounds/titlescreen.wav");
		clips.put("titlescreen", clip);

		clip = loadClip("/sounds/click.wav");
		clips.put("click", clip);

		clip = loadClip("/sounds/characterSelect.wav");
		clips.put("characterSelect", clip);

		clip = loadClip("/sounds/boss_battle.wav");
		clips.put("boss", clip);

		clip = loadClip("/sounds/intro.wav");
		clips.put("intro", clip);

		clip = loadClip("/sounds/jump.wav");
		clips.put("jump", clip);

		clip = loadClip("/sounds/hurt.wav");
		clips.put("hurt", clip);

		clip = loadClip("/sounds/lose.wav");
		clips.put("lose", clip);

		clip = loadClip("/sounds/pew_pew_pew.wav");
		clips.put("pew", clip);

		clip = loadClip("/sounds/win.wav");
		clips.put("win", clip);

		clip = loadClip("/sounds/footsteps.wav");
		clips.put("walk", clip);

		clip = loadClip("/sounds/coin.wav");
		clips.put("coin", clip);
	}



	// Returns the instance of the SoundManager

	public static SoundManager getInstance() {
		
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		



	// Retrieves an audio clip by its title

	public Clip getClip (String title) {
		return clips.get(title);
	}



	// Loads an audio clip from the specified file path

    public Clip loadClip(String fileName) {
        AudioInputStream audioIn;
        Clip clip = null;

        try {
            InputStream audioSrc = getClass().getResourceAsStream(fileName);
            if (audioSrc == null) {
                throw new FileNotFoundException("Sound file not found: " + fileName);
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        }

		catch (Exception e) {
            System.out.println("Error opening sound files: " + e);
        }
        
        return clip;
    }


	
	// Plays the specified audio clip

    public void playSound(String title, boolean looping) {
		
        Clip clip = getClip(title);
		
        if (clip != null) {
			clip.setFramePosition(0);
			
            if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    }



	// Stops the specified audio clip from playing

    public void stopSound(String title) {
		
        Clip clip = getClip(title);
		
        if (clip != null) {
			clip.stop();
		}
    }

}
