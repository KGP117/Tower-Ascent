import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for certain AWT classes
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.image.BufferStrategy;	// need this to implement page flipping


public class GameWindow extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
  	private static final int NUM_BUFFERS = 2;	// used for page flipping

	private int pWidth;				// width of the screen
	private int pHeight;     		// height of screen

	private int buttonWidth = 180;		// width of a button
	private int buttonHeight = 50;		// height of a button

	private Thread gameThread = null;            	// the thread that controls the game
	private volatile boolean isRunning = false;    	// used to stop the game thread

	private BufferedImage image;			// drawing area for each frame

	private Image pause1Image;			// first image for resume button
	private Image pause2Image;			// second image for resume button

	private Image restart1Image;		// first image for restart button
	private Image restart2Image;		// second image for restart button

	private Image quit1Image;			// first image for quit button
	private Image quit2Image;			// second image for quit button

	private Image fullHeart;
	private Image emptyHeart;

	private volatile boolean isOverPauseButton = false;
	private Rectangle pauseButtonArea;		// used by the pause 'button'
	private volatile boolean isPaused = false;
	
	private volatile boolean isOverRestartButton = false;
	private Rectangle restartButtonArea;		// used by the restart 'button'

	private volatile boolean isOverQuitButton = false;
	private Rectangle quitButtonArea;		// used by the quit button

	private boolean finishedOff = false;		// used when the game terminates
   
	private GraphicsDevice device;			// used for full-screen exclusive mode 
	private Graphics gScr;
	private BufferStrategy bufferStrategy;

	boolean rightPressed = false;
	boolean leftPressed = false;
	boolean spacePressed = false;

	SoundManager soundManager;
	

	
	public GameWindow() {
 
		super("Tower Ascent");

		initFullScreen();

		pause1Image = ImageManager.loadImage("images\\Pause1.png");
		pause2Image = ImageManager.loadImage("images\\Pause2.png");

		quit1Image = ImageManager.loadImage("images\\Quit1.png");
		quit2Image = ImageManager.loadImage("images//Quit2.png");

		fullHeart = ImageManager.loadImage("images\\full_heart.png");
		emptyHeart = ImageManager.loadImage("images\\empty_heart.png");


		setButtonAreas();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		soundManager = SoundManager.getInstance();
		image = new BufferedImage (pWidth, pHeight, BufferedImage.TYPE_INT_RGB);

		startGame();
	}


	// Implementation of Runnable interface

	public void run () {
		
		try {
			isRunning = true;
			while (isRunning) {
				if (isPaused == false) {
					gameUpdate();
				}
				screenUpdate();
				Thread.sleep (50);
			}
		}
		catch(InterruptedException e) {}

		finishOff();
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


	// Updates the game

	public void gameUpdate () {
		
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
      
			// Sync the display on some systems
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

		

		drawButtons(imageContext);			// draw the buttons
		drawLives(imageContext);			// draw the player lives
		drawScore(imageContext);			// draw the current score

		Graphics2D g2 = (Graphics2D) gScr;
		g2.drawImage(image, 0, 0, pWidth, pHeight, null);

		imageContext.dispose();
		g2.dispose();
	}


	// Standard procedure to get into FSEM

	private void initFullScreen() {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();

		setUndecorated(true);	// no menu bar, borders, etc.
		setIgnoreRepaint(true);	// turn off all paint events since doing active rendering
		setResizable(false);	// screen cannot be resized
		
		if (!device.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			System.exit(0);
		}

		device.setFullScreenWindow(this); // switch on full-screen exclusive mode

		// we can now adjust the display modes, if we wish

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
	}


	// Specify screen areas for the buttons and create bounding rectangles

	private void setButtonAreas() {
		
		//  leftOffset is the distance of a button from the left side of the window.
		//  topOffset is the distance of a button from the top of the window.
		//  Buttons are placed in the middle of the window when the game is paused.

		int leftOffset = (pWidth / 2) - (buttonWidth / 2) - 200;
		int topOffset = (pHeight / 2) - (buttonHeight / 2);
		pauseButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);

		leftOffset = leftOffset + 200;
		restartButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);

		leftOffset = leftOffset + 200;
		quitButtonArea = new Rectangle(leftOffset, topOffset, buttonWidth, buttonHeight);
	}


	private void drawLives (Graphics g) {

		Font newFont;

		newFont = new Font ("TimesRoman", Font.BOLD, 25);
		g.setFont(newFont);		// set this as font for text
		
		g.setColor(Color.WHITE);
		
		g.drawString("LIVES", 108, 40);
		
		g.drawString("SCORE", 1000, 40);

		g.drawImage(fullHeart, 50, 50, 50, 50, null);
		g.drawImage(fullHeart, 120, 50, 50, 50, null);
		g.drawImage(fullHeart, 190, 50, 50, 50, null);
	}


	private void drawScore (Graphics g) {

		Font newFont;
	
		newFont = new Font ("TimesRoman", Font.BOLD, 25);
		g.setFont(newFont);		// set this as font for text
		
		g.setColor(Color.WHITE);
		
		g.drawString("SCORE", 1000, 40);
	}


	private void drawButtons (Graphics g) {

		// buttons appear when the game is paused

		if (isPaused == true){

			// draw the pause button (an actual image that changes when the mouse moves over it)

			if (isOverPauseButton)
				g.drawImage(pause1Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(pause2Image, pauseButtonArea.x, pauseButtonArea.y, buttonWidth, buttonHeight, null);


			// draw the restart button (an actual image that changes when the mouse moves over it)

			if (isOverRestartButton)
				g.drawImage(restart1Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(restart2Image, restartButtonArea.x, restartButtonArea.y, buttonWidth, buttonHeight, null);


			// draw the quit button (an actual image that changes when the mouse moves over it)

			if (isOverQuitButton)
				g.drawImage(quit1Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
			else
				g.drawImage(quit2Image, quitButtonArea.x, quitButtonArea.y, buttonWidth, buttonHeight, null);
		
		}
	
	}


	private void startGame() { 
		if (gameThread == null) {
			soundManager.playSound ("background", true);

			gameThread = new Thread(this);
			gameThread.start();	 		

		}
	}


	// displays a message to the screen when the user stops the game

	private void gameOverMessage(Graphics g) {
		
		Font font = new Font("SansSerif", Font.BOLD, 24);
		FontMetrics metrics = this.getFontMetrics(font);

		String msg = "Game Over. Thanks for playing!";

		int x = (pWidth - metrics.stringWidth(msg)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString(msg, x, y);

	}


	// implementation of methods in KeyListener interface

	public void keyPressed (KeyEvent e) {

		if (isPaused)
			return;

		int keyCode = e.getKeyCode();

		if ((keyCode == KeyEvent.VK_Q) || (keyCode == KeyEvent.VK_END)) {
           	isRunning = false;		// user can quit anytime by pressing (Q, END)
			return;				 			
        }
		else
		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_P)) {
			isPaused = true;		// user can pause anytime by pressing (ESC, P)
		 	return;						
	 	}
		else
 		if (keyCode == KeyEvent.VK_LEFT) {
			leftPressed = true;
			
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			spacePressed = true;
			
			soundManager.playSound ("jump", false);
		}
/* 		if (spacePressed && leftPressed) {
			tileMap.jumpLeft();
			soundManager.playSound ("jump", false);
		}
		if (spacePressed && rightPressed) {
			tileMap.jumpRight();
			soundManager.playSound ("jump", false);
		} */

	}


	public void keyReleased (KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			leftPressed = false;
		} 
		else if (keyCode == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		} 
		else if (keyCode == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}

	}


	public void keyTyped (KeyEvent e) {

	}


	// implement methods of MouseListener interface

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


	// implement methods of MouseMotionListener interface

	public void mouseDragged(MouseEvent e) {

	}	


	public void mouseMoved(MouseEvent e) {
		testMouseMove(e.getX(), e.getY()); 
	}


	/* This method handles mouse clicks on one of the buttons
	   (Pause, Stop, Start Anim, Pause Anim, and Quit).
	*/

	private void testMousePress(int x, int y) {

		if (isOverRestartButton) {			// mouse click on Stop button
			isPaused = false;
		}
		else if (isOverPauseButton) {		// mouse click on Pause button
			isPaused = !isPaused;     	// toggle pausing
		}
		else if (isOverQuitButton) {		// mouse click on Quit button
			isRunning = false;		// set running to false to terminate
		}
  	}


	/* This method checks to see if the mouse is currently moving over one of
	   the buttons (Pause, Stop, Show Anim, Pause Anim, and Quit). It sets a
	   boolean value which will cause the button to be displayed accordingly.
	*/

	private void testMouseMove(int x, int y) { 
		if (isRunning) {
			isOverPauseButton = pauseButtonArea.contains(x,y) ? true : false;
			isOverRestartButton = restartButtonArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}
	}

}