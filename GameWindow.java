import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;


public class GameWindow extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final int NUM_BUFFERS = 2;
	
	// Thread and State Control
	private Thread gameThread = null;
	private volatile boolean isRunning = false;
	private volatile boolean isPaused = false;
	private volatile boolean isOverPauseButton = false;
	private volatile boolean isOverRestartButton = false;
	private volatile boolean isOverQuitButton = false;
	
	// Graphics and Display
	private Graphics gScr;
	private GraphicsDevice device;
	private BufferStrategy bufferStrategy;
	private BufferedImage image;
	private int pWidth, pHeight;
	private double displayScale;
	
	// UI Elements
	private Rectangle pauseButtonArea, restartButtonArea, quitButtonArea;
	private Image pause1Image, pause2Image;
	private Image restart1Image, restart2Image;
	private Image quit1Image, quit2Image;
	private Image fullHeart, emptyHeart;
	private Image movementKeys, jumpKeys, attackKeys;
	
	// Game State
	private int level;
	private int totalScore;
	private int characterIndex;
	private boolean bossMusicSound = false;
	private boolean walkSound = false;
	private boolean fullscreen = false;
	private boolean finishedOff = false;
	private boolean levelChange = false;
	private boolean upPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean attackPressed = false;
	private boolean pauseButtonHoverSoundPlayed = false;  // To track if hover sound is played for Play button
	private boolean restartButtonHoverSoundPlayed = false;  // To track if hover sound is played for Quit button
    private boolean quitButtonHoverSoundPlayed = false;  // To track if hover sound is played for Quit button
	
	// Managers
	SoundManager soundManager;
	TileMapManager tileManager;
	TileMap tileMap;
	
	// Title Screen
    private TitleScreen titleScreen;



	// GameWindow Constructor
	public GameWindow() {
 
		super("Tower Ascent");

		initFullScreen();
		//initWindowedMode();
		setDisplayScale();
		loadImages();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		showTitleScreen();
	}



    // Initialize and show the title screen
    private void showTitleScreen() {
        titleScreen = new TitleScreen(this, displayScale);
		soundManager = SoundManager.getInstance();
		soundManager.playSound ("titlescreen", true);
        setContentPane(titleScreen);
        revalidate();
    }



	// Standard procedure to get into FSEM

	private void initFullScreen() {

		fullscreen = true;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();

		setUndecorated(true);
		setIgnoreRepaint(true);
		setResizable(false);
		
		if (!device.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			System.exit(0);
		}

		device.setFullScreenWindow(this);

		try {
			createBufferStrategy(NUM_BUFFERS);
		}
		catch (Exception e) {
			System.out.println("Error while creating buffer strategy " + e); 
			System.exit(0);
		}

		bufferStrategy = getBufferStrategy();

		if (bufferStrategy == null) {
			System.out.println("BufferStrategy is null");
			System.exit(0);
		}
	}

	

	// Performs some tasks before closing the game

	private void finishOff() { 
		
    	if (!finishedOff) {
			finishedOff = true;
			restoreScreen();

			System.exit(0);
		}
	}



	// This method switches off full screen mode
	
	private void restoreScreen() { 
		
		Window w = device.getFullScreenWindow();
		
		if (w != null)
			w.dispose();
		
		device.setFullScreenWindow(null);
	}



	// Launch in Windowed Mode

	private void initWindowedMode() {

		pWidth = 1280;
		pHeight = 720;

        setSize(pWidth, pHeight);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);

        createBufferStrategy(NUM_BUFFERS);
        bufferStrategy = getBufferStrategy();

        if (bufferStrategy == null) {
            System.out.println("BufferStrategy is null");
            System.exit(0);
        }
    }



	// Adjusts the Display Scale based on Resolution

	public void setDisplayScale(){

		pWidth = getBounds().width;
		pHeight = getBounds().height;

		displayScale = 1;

		if (pWidth == 800 && pHeight == 600){
			displayScale = 0.625;
		}
		if (pWidth == 1024 && pHeight == 768){
			displayScale = 0.8;
		}
		if (pWidth == 1280 && pHeight == 600){
			displayScale = 0.834;
		}
		if (pWidth == 1152 && pHeight == 864){
			displayScale = 0.9;
		}
		if (pWidth == 1360 && pHeight == 768){
			displayScale = 1.0667;
		}
		if (pWidth == 1366 && pHeight == 768){
			displayScale = 1.0671875;
		}
		if (pWidth == 1400 && pHeight == 1050){
			displayScale = 1.09375;
		}
		if (pWidth == 1440 && pHeight == 900){
			displayScale = 1.125;
		}
		if (pWidth == 1600 && pHeight == 900){
			displayScale = 1.25;
		}
		if (pWidth == 1680 && pHeight == 1050){
			displayScale = 1.3125;
		}
		if (pWidth == 1920 && pHeight == 1080){
			displayScale = 1.5;
		}
	}


	// Loads all required UI Images

	public void loadImages(){
		pause1Image = ImageManager.loadImage("images/userInterface/pause/Pause1.png");
		pause2Image = ImageManager.loadImage("images/userInterface/pause/Pause2.png");

		restart1Image = ImageManager.loadImage("images/userInterface/restart/Restart1.png");
		restart2Image = ImageManager.loadImage("images/userInterface/restart/Restart2.png");

		quit1Image = ImageManager.loadImage("images/userInterface/quit/Quit1.png");
		quit2Image = ImageManager.loadImage("images/userInterface/quit/Quit2.png");

		fullHeart = ImageManager.loadImage("images/userInterface/lives/full_heart.png");
		emptyHeart = ImageManager.loadImage("images/userInterface/lives/empty_heart.png");

		movementKeys = ImageManager.loadImage("images/userInterface/keys/arrows.png");
		jumpKeys = ImageManager.loadImage("images/userInterface/keys/space.png");
		attackKeys = ImageManager.loadImage("images/userInterface/keys/z.png");
	}



	// Starts the game

	public void startGame(int characterIndex) { 

		requestFocus();
		titleScreen.hideComponents();

		resetGameState(characterIndex);

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		this.characterIndex = characterIndex;

		level = 1;
		soundManager = SoundManager.getInstance();
		soundManager.stopSound("titlescreen");
		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);

		if (gameThread == null) {
			soundManager.playSound("intro", false);
			soundManager.playSound ("background", true);

		 	tileManager = new TileMapManager (this, characterIndex, displayScale);

			try {
				tileMap = tileManager.loadMap("/maps/map1.txt");
			}
			
			catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}

			resetPauseScreenState();

			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	
	private void resetGameState(int characterIndex) {
		// Ensure that any previous game thread is stopped
		if (gameThread != null && gameThread.isAlive()) {
			isRunning = false;  // Stop the loop in the run method
			try {
				gameThread.join();  // Wait for the game thread to terminate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameThread = null;  // Clean up the thread reference
		}
	
		isPaused = false;
		isRunning = true;
		isOverPauseButton = false;
		isOverRestartButton = false;
		isOverQuitButton = false;
		level = 1;  // Reset to level 1 or appropriate level
		totalScore = 0;  // Reset score or other game state variables
	
		// Clear any old map or game objects
		tileMap = null;
		tileManager = null;

		tileManager = new TileMapManager(this, characterIndex, displayScale);
		try {
			tileMap = tileManager.loadMap("/maps/map1.txt");
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	



	// Reset the state of the pause screen
	
	private void resetPauseScreenState() {
		isOverPauseButton = false;
		isOverRestartButton = false;
		isOverQuitButton = false;
	
		int buttonWidth = (int)(180*displayScale);
		int buttonHeight = (int)(60*displayScale);
		int leftOffset = (pWidth / 2) - (buttonWidth / 2);
		int topOffset = (pHeight / 2) - (buttonHeight / 2);
	
		pauseButtonArea = new Rectangle(leftOffset - 200, topOffset, buttonWidth, buttonHeight);
		restartButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);
		quitButtonArea = new Rectangle(leftOffset + 200, topOffset, buttonWidth, buttonHeight);
	}

	

	// Implementation of Runnable interface

	public void run () {
		
		try {
			isRunning = true;

			while (isRunning) {
				if (!isPaused) {
					synchronized (this) {				
						gameUpdate();
					}
				}
				synchronized (this) {
					screenUpdate();
				}
				Thread.sleep (60);
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		//finishOff();
		
	}



	// Updates the game

	public void gameUpdate () {
		
		Graphics2D imageContext = null;

		if (leftPressed) {
            tileMap.moveLeft();
        }

        if (rightPressed) {
            tileMap.moveRight();
        }

        if (upPressed) {
            tileMap.jump();
        }

        if (attackPressed) {
            tileMap.attack();
            soundManager.playSound("pew", false);
        }

		if (!leftPressed && !rightPressed && !upPressed && !attackPressed) {
			tileMap.playIdle();
		}

		try {
			imageContext = (Graphics2D) image.getGraphics();
			tileMap.update();
	
			if (!bossMusicSound) {
				if (tileMap.getPlayerX() >= (int)(4500*displayScale)) {
					soundManager.stopSound("background");
					soundManager.playSound("boss", true);
					bossMusicSound = true;
				}
			}

			if ((rightPressed || leftPressed) && !tileMap.isPlayerInAir()) {
				if (!walkSound) {
					soundManager.playSound("walk", true);
					walkSound = true;
				}
			}
			else {
				soundManager.stopSound("walk");
				walkSound = false;
			}
	
			if (levelChange) {
				totalScore = totalScore + tileMap.getScore();
				levelChange = false;
				tileManager = new TileMapManager(this, characterIndex, displayScale);
	
				try {
					String filename = "maps/map" + level + ".txt";
					tileMap = tileManager.loadMap(filename);
				} catch (Exception e) {
					e.printStackTrace();
					gameOver(imageContext);
					return;
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			isRunning = false;
		}
		 
		finally {
			if (imageContext != null) {
				imageContext.dispose();
			}
		}
	}



	// Updates the screen

	private void screenUpdate() { 

		try {
			gScr = bufferStrategy.getDrawGraphics();
			gameRender(gScr);
			gScr.dispose();
			
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents of buffer lost.");
      
			Toolkit.getDefaultToolkit().sync();
		}
		
		catch (Exception e) { 
			e.printStackTrace();  
			isRunning = false; 
		} 
	}



	// Draws the game objects

	public void gameRender (Graphics gScr) {		

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		tileMap.draw(imageContext);
		drawUIElements(imageContext);
		drawLives(imageContext);
		drawInputKeys(imageContext);
		drawPauseButtons(imageContext);

		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);

		imageContext.dispose();
		g2.dispose();
	}



	// Draws player lives

	private void drawUIElements (Graphics g) {

		Font newFont = new Font ("TimesRoman", Font.BOLD, (int)(25*displayScale));
		g.setFont(newFont);
		
		g.setColor(Color.WHITE);
		
		g.drawString("LIVES", (int)(108*displayScale), (int)(40*displayScale));

		g.drawString("LEVEL - " + Integer.toString(level), (int)(600*displayScale), (int)(40*displayScale));

		g.drawString("SCORE", (int)(1100*displayScale), (int)(40*displayScale));
		g.drawString(Integer.toString(totalScore + tileMap.getScore()), (int)(1100*displayScale), (int)(80*displayScale));


		if (level == 1){
			g.drawString(" MOVEMENT", (int)(200*displayScale), (int)(200*displayScale));
			g.drawImage(movementKeys, (int)(200*displayScale), (int)(210*displayScale), (int)(150*displayScale), (int)(100*displayScale), null);

			g.drawString("JUMP", (int)(600*displayScale), (int)(200*displayScale));
			g.drawImage(jumpKeys, (int)(560*displayScale), (int)(225*displayScale), (int)(150*displayScale), (int)(50*displayScale), null);

			g.drawString(" ATTACK", (int)(900*displayScale), (int)(200*displayScale));
			g.drawImage(attackKeys, (int)(930*displayScale), (int)(225*displayScale), (int)(50*displayScale), (int)(50*displayScale), null);
		}


		newFont = new Font ("TimesRoman", Font.BOLD, (int)(20*displayScale));
		g.setFont(newFont);

		g.drawString("Input", (int)(30*displayScale), (int)(700*displayScale));

		newFont = new Font ("TimesRoman", Font.BOLD, (int)(15*displayScale));
		g.setFont(newFont);

		g.drawString("Pause", (int)(10*displayScale), (int)(20*displayScale));
		g.drawString("Esc", (int)(10*displayScale), (int)(30*displayScale));
	}



	// Draws player lives

	private void drawLives(Graphics g) {

		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		
		int lives = tileMap.getPlayerLives();
		
		for (int i = 0; i < 3; i++) {
			Image heartImage = (i < lives) ? fullHeart : emptyHeart;
			g.drawImage(heartImage, (int)(50 + i * 70 * displayScale), (int)(50 * displayScale), (int)(50 * displayScale), (int)(50 * displayScale), null);
		}
		
		if (lives <= 0){
			isPaused = true;
			gameOver(imageContext);
		}
		
		imageContext.dispose();
	}



	// Draw key images if the corresponding flags are set

	private void drawInputKeys(Graphics2D imageContext) {
		
		if (rightPressed || leftPressed) {
			imageContext.drawImage(movementKeys, (int)(100*displayScale), (int)(660*displayScale), (int)(75*displayScale), (int)(50*displayScale), null);
		}
		if (upPressed) {
			imageContext.drawImage(jumpKeys, (int)(200*displayScale), (int)(675*displayScale), (int)(75*displayScale), (int)(25*displayScale), null);
		}
		if (attackPressed) {
			imageContext.drawImage(attackKeys, (int)(300*displayScale), (int)(675*displayScale), (int)(25*displayScale), (int)(25*displayScale), null);
		}
	}



	// Draw buttons when the game is paused

	private void drawPauseButtons (Graphics g) {

		int buttonWidth = (int)(180*displayScale);
		int buttonHeight = (int)(60*displayScale);

		int leftOffset = (pWidth / 2) - (buttonWidth / 2);
		int topOffset = (pHeight / 2) - (buttonHeight / 2);
		
		pauseButtonArea = new Rectangle(leftOffset - (int)(200*displayScale), topOffset, buttonWidth, buttonHeight);
		restartButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);
		quitButtonArea = new Rectangle(leftOffset + (int)(200*displayScale), topOffset, buttonWidth, buttonHeight);


		if (isPaused == true){

			if (isOverPauseButton) g.drawImage(pause1Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);
			else g.drawImage(pause2Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);

			if (isOverRestartButton) g.drawImage(restart1Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);
			else g.drawImage(restart2Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);

			if (isOverQuitButton) g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
			else g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
		}

	}



	// Restarts the game

	private void restartGame() {

		totalScore = 0;
		level = 1;

		soundManager.stopSound("boss");
		soundManager.stopSound("background");
		soundManager.playSound ("background", true);
		
		try{
			tileMap = tileManager.loadMap("/maps/map1.txt");
		}
		catch (Exception f) {
			System.out.println(f);
			System.exit(0);
		}
	}



	// Displays a message to the screen when the user stops the game

	private void gameOver(Graphics g) {
		
		soundManager.stopSound("background");
		soundManager.stopSound("boss");
		soundManager.stopSound("walk");

		if (tileMap.getPlayerLives() > 0)
			soundManager.playSound("win", false);
		else 
			soundManager.playSound("lose", false);

		isPaused = true;

		Font font = new Font("SansSerif", Font.BOLD, (int)(24*displayScale));
		FontMetrics metrics = this.getFontMetrics(font);

		String msg1 = "GAME OVER";
		String msg2 = "THANKS FOR PLAYING!";

		int x1 = (pWidth - metrics.stringWidth(msg1)) / 2;
		int x2 = (pWidth - metrics.stringWidth(msg2)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg1, x1, y-(int)(150*displayScale));
		g.drawString(msg2, x2, y-(int)(100*displayScale));
	}



	// Returns the current level

	public int getLevel(){
		return level;
	}



	// Ends the level
	
	public void endLevel() {
		level = level + 1;
		levelChange = true;
	}



	// Implementation of methods in KeyListener interface

	public void keyPressed(KeyEvent e) {
	    if (isPaused) return;

		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_Q:
			case KeyEvent.VK_END:
				isRunning = false;
				return;

			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_P:
				isPaused = true;
				soundManager.stopSound("walk");
				return;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				leftPressed = true; break;

			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightPressed = true; break;

			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_X:
			case KeyEvent.VK_J:
				upPressed = true; break;

			case KeyEvent.VK_Z:
			case KeyEvent.VK_K:
				attackPressed = true; break;

			case KeyEvent.VK_R:
				restartGame(); break;
		}
	}
	

	
	// Implementation of methods in KeyListener interface

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				leftPressed = false;
				break;
	
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightPressed = false;
				break;
	
			case KeyEvent.VK_SPACE:	
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_X:
			case KeyEvent.VK_J:
				upPressed = false;
				break;
	
			case KeyEvent.VK_Z:
			case KeyEvent.VK_K:
				attackPressed = false;
				break;
		}
	}

	

	public void keyTyped (KeyEvent e) {}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}	


	public void mousePressed(MouseEvent e) {
		testMousePress(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		testMouseMove(e.getX(), e.getY()); 
	}

	
	private void testMousePress(int x, int y) {

		if (isPaused){
			if (isOverPauseButton) {
				soundManager.playSound("click", false); // Play click sound
				isPaused = !isPaused;
			}
			else if (isOverRestartButton) {
				soundManager.playSound("click", false); // Play click sound
				restartGame();
				isPaused = false;
			}
			else if (isOverQuitButton) {
				soundManager.playSound("click", false); // Play click sound
				isRunning = false;
				gameThread.stop();
				showTitleScreen();
			}
		}
  	}

	public void startover(){
		//isRunning = true;
	}


	private void testMouseMove(int x, int y) { 
		
		boolean wasOverPauseButton = isOverPauseButton;
		boolean wasOverRestartButton = isOverRestartButton;
        boolean wasOverQuitButton = isOverQuitButton;

		if (isRunning) {
			isOverPauseButton = pauseButtonArea.contains(x,y) ? true : false;
			isOverRestartButton = restartButtonArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}


		// Play hover sound when first moving over a button
		if (isPaused){

			if (isOverPauseButton && !wasOverPauseButton && !pauseButtonHoverSoundPlayed) {
				soundManager.playSound("click", false); // Play hover sound for Play button
				pauseButtonHoverSoundPlayed = true;
			} else if (!isOverPauseButton) {
				pauseButtonHoverSoundPlayed = false; // Reset when not hovering
			}

			if (isOverRestartButton && !wasOverRestartButton && !restartButtonHoverSoundPlayed) {
				soundManager.playSound("click", false); // Play hover sound for Quit button
				restartButtonHoverSoundPlayed = true;
			} else if (!isOverRestartButton) {
				restartButtonHoverSoundPlayed = false; // Reset when not hovering
			}

			if (isOverQuitButton && !wasOverQuitButton && !quitButtonHoverSoundPlayed) {
				soundManager.playSound("click", false); // Play hover sound for Quit button
				quitButtonHoverSoundPlayed = true;
			} else if (!isOverQuitButton) {
				quitButtonHoverSoundPlayed = false; // Reset when not hovering
			}
		}

	}
	
}
