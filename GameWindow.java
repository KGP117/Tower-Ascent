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
	
	// UI Elements
	private Rectangle pauseButtonArea, restartButtonArea, quitButtonArea;
	private Image pause1Image, pause2Image;
	private Image restart1Image, restart2Image;
	private Image quit1Image, quit2Image;
	private Image fullHeart, emptyHeart;
	private Image movementKeys, jumpKeys, attackKeys;
	private boolean showMovementKeys = false;
	private boolean showJumpKeys = false;
	private boolean showAttackKeys = false;
	
	// Game State
	private boolean bossMusicSound = false;
	private boolean walkSound = false;
	private boolean fullscreen = false;
	private boolean finishedOff = false;
	private boolean levelChange = false;
	private int level;
	private int totalScore;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean attackPressed = false;
	
	// Managers
	SoundManager soundManager;
	TileMapManager tileManager;
	TileMap tileMap;	



	// GameWindow Constructor

	public GameWindow() {
 
		super("Tower Ascent");

		initFullScreen();
		//initWindowedMode();
		loadImages();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		startGame();
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

		pWidth = getBounds().width;
		pHeight = getBounds().height;

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

	private void startGame() { 

		level = 1;
		soundManager = SoundManager.getInstance();
		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);

		if (gameThread == null) {
			soundManager.playSound("intro", false);
			soundManager.playSound ("background", true);

		 	tileManager = new TileMapManager (this);

			try {
				tileMap = tileManager.loadMap("/maps/map1.txt");
			}
			
			catch (Exception e) {
				System.out.println(e);
				System.exit(0);
			}

			gameThread = new Thread(this);
			gameThread.start();	 		
		}
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

		if (fullscreen == true){
			finishOff();
		}
	}



	// Updates the game

	public void gameUpdate () {
		
		Graphics2D imageContext = null;

		try {
			imageContext = (Graphics2D) image.getGraphics();
			tileMap.update();
	
			if (!bossMusicSound) {
				if (tileMap.getPlayerX() >= 4500) {
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
				tileManager = new TileMapManager(this);
	
				try {
					String filename = "maps/map" + level + ".txt";
					tileMap = tileManager.loadMap(filename);
				} catch (Exception e) {
					e.printStackTrace();
					gameOver(imageContext);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isRunning = false;
		} finally {
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

		Font newFont = new Font ("TimesRoman", Font.BOLD, 25);
		g.setFont(newFont);
		
		g.setColor(Color.WHITE);
		
		g.drawString("LIVES", 108, 40);

		g.drawString("LEVEL - " + Integer.toString(level), 600, 40);

		g.drawString("SCORE", 1100, 40);
		g.drawString(Integer.toString(totalScore + tileMap.getScore()), 1100, 80);


		if (level == 1){
			g.drawString(" MOVEMENT", 200, 200);
			g.drawImage(movementKeys, 200, 210, 150, 100, null);

			g.drawString("JUMP", 600, 200);
			g.drawImage(jumpKeys, 560, 225, 150, 50, null);

			g.drawString(" ATTACK", 900, 200);
			g.drawImage(attackKeys, 930, 225, 50, 50, null);
		}


		newFont = new Font ("TimesRoman", Font.BOLD, 20);
		g.setFont(newFont);

		g.drawString("Input", 30, 700);

		newFont = new Font ("TimesRoman", Font.BOLD, 15);
		g.setFont(newFont);

		g.drawString("Pause", 10, 20);
		g.drawString("Esc", 10, 30);
	}



	// Draws player lives

	private void drawLives (Graphics g) {

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		if (tileMap.getPlayerLives() == 3){
			g.drawImage(fullHeart, 50, 50, 50, 50, null);
			g.drawImage(fullHeart, 120, 50, 50, 50, null);
			g.drawImage(fullHeart, 190, 50, 50, 50, null);
		}
		else if (tileMap.getPlayerLives() == 2){
			g.drawImage(fullHeart, 50, 50, 50, 50, null);
			g.drawImage(fullHeart, 120, 50, 50, 50, null);
			g.drawImage(emptyHeart, 190, 50, 50, 50, null);
		}
		else if (tileMap.getPlayerLives() == 1){
			g.drawImage(fullHeart, 50, 50, 50, 50, null);
			g.drawImage(emptyHeart, 120, 50, 50, 50, null);
			g.drawImage(emptyHeart, 190, 50, 50, 50, null);
		}
		else if (tileMap.getPlayerLives() <= 0){
			g.drawImage(emptyHeart, 50, 50, 50, 50, null);
			g.drawImage(emptyHeart, 120, 50, 50, 50, null);
			g.drawImage(emptyHeart, 190, 50, 50, 50, null);

			isPaused = true;
			gameOver(imageContext);
		}
	}



	// Draw key images if the corresponding flags are set

	private void drawInputKeys(Graphics2D imageContext) {
		
		if (showMovementKeys) {
			imageContext.drawImage(movementKeys, 100, 660, 75, 50, null);
		}
		if (showJumpKeys) {
			imageContext.drawImage(jumpKeys, 200, 675, 75, 25, null);
		}
		if (showAttackKeys) {
			imageContext.drawImage(attackKeys, 300, 675, 25, 25, null);
		}
	}



	// Draw buttons when the game is paused

	private void drawPauseButtons (Graphics g) {

		int buttonWidth = 180;
		int buttonHeight = 60;

		int leftOffset = (pWidth / 2) - (buttonWidth / 2);
		int topOffset = (pHeight / 2) - (buttonHeight / 2);
		
		pauseButtonArea = new Rectangle(leftOffset - 200, topOffset, buttonWidth, buttonHeight);

		restartButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);

		quitButtonArea = new Rectangle(leftOffset + 200, topOffset, buttonWidth, buttonHeight);

		if (isPaused == true){

			if (isOverPauseButton)
				g.drawImage(pause1Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(pause2Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);


			if (isOverRestartButton)
				g.drawImage(restart1Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(restart2Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);


			if (isOverQuitButton)
				g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
		
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

		Font font = new Font("SansSerif", Font.BOLD, 24);
		FontMetrics metrics = this.getFontMetrics(font);

		String msg1 = "GAME OVER";
		String msg2 = "THANKS FOR PLAYING!";

		int x1 = (pWidth - metrics.stringWidth(msg1)) / 2;
		int x2 = (pWidth - metrics.stringWidth(msg2)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg1, x1, y-150);
		g.drawString(msg2, x2, y-100);
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



	// implementation of methods in KeyListener interface

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
				leftPressed = true;
				showMovementKeys = true;
				break;

			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightPressed = true;
				showMovementKeys = true;
				break;

			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_X:
				upPressed = true;
				showJumpKeys = true;
				break;

			case KeyEvent.VK_Z:
				attackPressed = true;
				showAttackKeys = true;
				break;

			case KeyEvent.VK_R:
				restartGame();
				break;
		}

		if (leftPressed) {
            tileMap.moveLeft();
        }

        if (rightPressed) {
            tileMap.moveRight();
        }

        if (upPressed) {
            tileMap.jump();
            upPressed = false; // Assuming jump action should only happen once per press
        }

        if (attackPressed) {
            tileMap.attack();
            soundManager.playSound("pew", false);
            attackPressed = false; // Assuming attack should only happen once per press
        }

		if (!leftPressed && !rightPressed && !upPressed && !attackPressed) {
			tileMap.playIdle();
		}
	}
	


	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				leftPressed = false;
				showMovementKeys = false;
				break;
	
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				rightPressed = false;
				showMovementKeys = false;
				break;
	
			case KeyEvent.VK_SPACE:	
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_X:
				upPressed = false;
				showJumpKeys = false;
				break;
	
			case KeyEvent.VK_Z:
				attackPressed = false;
				showAttackKeys = false;
				break;
		}
	}

	

	public void keyTyped (KeyEvent e) {

	}


	public void mouseClicked(MouseEvent e) {

	}


	public void mouseEntered(MouseEvent e) {

	}


	public void mouseExited(MouseEvent e) {

	}


	public void mousePressed(MouseEvent e) {
		testMousePress(e.getX(), e.getY());
	}


	public void mouseReleased(MouseEvent e) {

	}


	public void mouseDragged(MouseEvent e) {

	}	


	public void mouseMoved(MouseEvent e) {
		testMouseMove(e.getX(), e.getY()); 
	}


	
	private void testMousePress(int x, int y) {

		if (isOverPauseButton) {
			isPaused = !isPaused;
		}
		else if (isOverRestartButton) {
			restartGame();
			isPaused = false;
		}
		else if (isOverQuitButton) {
			isRunning = false;
		}
  	}



	private void testMouseMove(int x, int y) { 
		if (isRunning) {
			isOverPauseButton = pauseButtonArea.contains(x,y) ? true : false;
			isOverRestartButton = restartButtonArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}
	}
}
