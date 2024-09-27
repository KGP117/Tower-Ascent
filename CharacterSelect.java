import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CharacterSelect extends JPanel {

    private Image backgroundImage;
    private BufferedImage[] characterImages;
    private BufferedImage[] greyedCharacterImages;

    private GameWindow gameWindow;
    private Background background;
    private double displayScale;
    private int hoveredCharacterIndex = -1; // To track which character is hovered over
    private SoundManager soundManager;
    private boolean soundPlayed = false; // Flag to track if the sound has been played for the current hover

    // CharacterSelect Constructor
    public CharacterSelect(GameWindow gameWindow, double displayScale) {
        this.gameWindow = gameWindow;
        this.displayScale = displayScale;

        backgroundImage = ImageManager.loadImage("images/background/background2.png");
        background = new Background(gameWindow, backgroundImage, 0, 0, displayScale);

        setLayout(new GridLayout(2, 4));
        setOpaque(false);

        characterImages = new BufferedImage[7];
        greyedCharacterImages = new BufferedImage[7];

        String[] characterPaths = {
            "images/player/wind/character1.png",
            "images/player/fire/character2.png",
            "images/player/water/character3.png",
            "images/player/leaf/character4.png",
            "images/player/metal/character5.png",
            "images/player/crystal/character6.png",
            "images/player/ground/character7.png"
        };

        for (int i = 0; i < characterImages.length; i++) {
            characterImages[i] = ImageManager.loadBufferedImage(characterPaths[i]);
            greyedCharacterImages[i] = createGreyedImage(characterImages[i]); // Create greyscale version
        }

        // Initialize SoundManager instance
        soundManager = SoundManager.getInstance();

        // Add mouse listeners
        addMouseListener(createMouseListener());
        addMouseMotionListener(createMouseMotionListener());
    }

    // Method to create greyscale version of an image
    private BufferedImage createGreyedImage(BufferedImage original) {
        BufferedImage greyedImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int rgb = original.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                // Convert to greyscale using luminance formula
                int grey = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
                int greyRGB = (alpha << 24) | (grey << 16) | (grey << 8) | grey;
                greyedImage.setRGB(x, y, greyRGB);
            }
        }
        return greyedImage;
    }

    // Draws Graphics on the Screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        background.draw(g2d);

        int playerWidth = (int) (characterImages[0].getWidth() * displayScale * 2);
        int playerHeight = (int) (characterImages[0].getHeight() * displayScale * 2);

        int x = playerWidth;
        int y = playerHeight;
        int spacing = (int) (200 * displayScale);

        for (int i = 0; i < 5; i++) {
            drawCharacter(g2d, i, x, y, playerWidth, playerHeight);
            x += spacing;
        }

        x = playerWidth;
        y += spacing;

        for (int i = 5; i < characterImages.length; i++) {
            drawCharacter(g2d, i, x, y, playerWidth, playerHeight);
            x += spacing;
        }
    }

    // Helper method to draw characters with greyscale or colored images and a border if hovered
    private void drawCharacter(Graphics2D g2d, int index, int x, int y, int width, int height) {
        BufferedImage imageToDraw = (hoveredCharacterIndex == index) ? characterImages[index] : greyedCharacterImages[index];
        g2d.drawImage(imageToDraw, x, y, width, height, null);
    }

    // Handles Mouse Input
    private MouseAdapter createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < characterImages.length; i++) {
                    if (isWithinImage(e.getX(), e.getY(), i)) {
                        handleCharacterSelection(i + 1);
                        break;
                    }
                }
            }
        };
    }

    // Handles Mouse Motion
    private MouseMotionAdapter createMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean repaintNeeded = false;
                int newHoveredCharacterIndex = -1;
                
                // Check which character (if any) is currently being hovered over
                for (int i = 0; i < characterImages.length; i++) {
                    if (isWithinImage(e.getX(), e.getY(), i)) {
                        newHoveredCharacterIndex = i;
                        break;
                    }
                }

                // Only update if the hovered character index has changed
                if (newHoveredCharacterIndex != hoveredCharacterIndex) {
                    hoveredCharacterIndex = newHoveredCharacterIndex;
                    repaintNeeded = true;
                    soundPlayed = false; // Reset sound played flag when hover changes
                }

                // Play sound if hovering over a new character and sound has not been played yet
                if (hoveredCharacterIndex != -1 && !soundPlayed) {
                    soundManager.playSound("characterSelect", false);
                    soundPlayed = true;
                }

                if (repaintNeeded) {
                    repaint();
                }
            }
        };
    }

    // Checks Mouse Location
    private boolean isWithinImage(int mouseX, int mouseY, int index) {
        int playerWidth = (int) (characterImages[0].getWidth() * displayScale * 2);
        int playerHeight = (int) (characterImages[0].getHeight() * displayScale * 2);
        int spacing = (int) (200 * displayScale);

        int row = index < 5 ? 0 : 1;
        int col = index % 5;

        int x = playerWidth + col * spacing;
        int y = playerHeight + row * spacing;

        Rectangle imageBounds = new Rectangle(x, y, playerWidth, playerHeight);
        return imageBounds.contains(mouseX, mouseY);
    }

    // Switches to GameWindow when a character is selected
    private void handleCharacterSelection(int characterIndex) {
        setVisible(false);
        gameWindow.getContentPane().removeAll();
        gameWindow.addMouseListener(gameWindow);
        gameWindow.addMouseMotionListener(gameWindow);
        gameWindow.addKeyListener(gameWindow);
        //gameWindow.startover();
        gameWindow.startGame(characterIndex);
        gameWindow.requestFocusInWindow();
    }
}
