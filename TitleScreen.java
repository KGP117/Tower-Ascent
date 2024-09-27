import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TitleScreen extends JPanel {

    private Image backgroundImage;
    private Image play1Image, play2Image; 
    private Image quit1Image, quit2Image;

    private Background background;
    
    private Rectangle playButtonArea, quitButtonArea;

    private boolean isOverPlayButton = false;
    private boolean isOverQuitButton = false;
    private boolean isPlayButtonVisible = true;
    private boolean isQuitButtonVisible = true;
    
    private JPanel characterSelectionPanel;

    private MouseAdapter mouseAdapter;
    private MouseAdapter mouseMotionAdapter;
    
    private SoundManager soundManager;  // SoundManager instance
    private boolean playButtonHoverSoundPlayed = false;  // To track if hover sound is played for Play button
    private boolean quitButtonHoverSoundPlayed = false;  // To track if hover sound is played for Quit button

    public TitleScreen(GameWindow gameWindow, double displayScale) {
        setLayout(null);

        backgroundImage = ImageManager.loadImage("images/background/titleScreen.png");
        background = new Background(gameWindow, backgroundImage, 0, 0, displayScale);

        play1Image = ImageManager.loadImage("images/userInterface/pause/Pause1.png");
        play2Image = ImageManager.loadImage("images/userInterface/pause/Pause2.png");
        quit1Image = ImageManager.loadImage("images/userInterface/quit/Quit1.png");
        quit2Image = ImageManager.loadImage("images/userInterface/quit/Quit2.png");

        playButtonArea = new Rectangle((int)(850 * displayScale), (int)(300 * displayScale), (int)(180 * displayScale), (int)(60 * displayScale));
        quitButtonArea = new Rectangle((int)(850 * displayScale), (int)(400 * displayScale), (int)(180 * displayScale), (int)(60 * displayScale));

        soundManager = SoundManager.getInstance();  // Initialize SoundManager instance

        mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (isPlayButtonVisible || isQuitButtonVisible) {
                    testMousePress(e.getX(), e.getY());
                }
            }
        };

        mouseMotionAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (isPlayButtonVisible || isQuitButtonVisible) {
                    testMouseMove(e.getX(), e.getY());
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseMotionAdapter);

        characterSelectionPanel = new CharacterSelect(gameWindow, displayScale);
        characterSelectionPanel.setBounds(0, 0, (int)(1280 * displayScale), (int)(720 * displayScale));
        add(characterSelectionPanel);
        characterSelectionPanel.setVisible(false);
    }

    // Handles Mouse press
    private void testMousePress(int x, int y) {
        if (isOverPlayButton) {
            soundManager.playSound("click", false); // Play click sound
            hideComponents();
            removeMouseListener(mouseAdapter);
            removeMouseMotionListener(mouseMotionAdapter);
            characterSelectionPanel.setVisible(true);
        } 
        else if (isOverQuitButton) {
            soundManager.playSound("click", false); // Play click sound
            removeMouseListener(mouseAdapter);
            removeMouseMotionListener(mouseMotionAdapter);
            System.exit(0);
        }
    }

    // Handle Mouse move
    private void testMouseMove(int x, int y) {
        boolean wasOverPlayButton = isOverPlayButton;
        boolean wasOverQuitButton = isOverQuitButton;

        isOverPlayButton = playButtonArea.contains(x, y);
        isOverQuitButton = quitButtonArea.contains(x, y);

        // Play hover sound when first moving over a button
        if (isOverPlayButton && !wasOverPlayButton && !playButtonHoverSoundPlayed) {
            soundManager.playSound("click", false); // Play hover sound for Play button
            playButtonHoverSoundPlayed = true;
        } else if (!isOverPlayButton) {
            playButtonHoverSoundPlayed = false; // Reset when not hovering
        }

        if (isOverQuitButton && !wasOverQuitButton && !quitButtonHoverSoundPlayed) {
            soundManager.playSound("click", false); // Play hover sound for Quit button
            quitButtonHoverSoundPlayed = true;
        } else if (!isOverQuitButton) {
            quitButtonHoverSoundPlayed = false; // Reset when not hovering
        }

        repaint();
    }

    // Hides the Title Screen
    public void hideComponents() {
        isPlayButtonVisible = false;
        isQuitButtonVisible = false;
        repaint();
    }

    // Displays the buttons on the screen
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        background.draw(g2d);

        if (isPlayButtonVisible) {
            if (isOverPlayButton)
                g.drawImage(play1Image, playButtonArea.x, playButtonArea.y, playButtonArea.width, playButtonArea.height, null);
            else
                g.drawImage(play2Image, playButtonArea.x, playButtonArea.y, playButtonArea.width, playButtonArea.height, null);
        }

        if (isQuitButtonVisible) {
            if (isOverQuitButton)
                g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, quitButtonArea.width, quitButtonArea.height, null);
            else
                g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, quitButtonArea.width, quitButtonArea.height, null);
        }
    }
}
