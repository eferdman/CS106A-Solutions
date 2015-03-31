import acm.graphics.*;
import acm.program.*;
import acm.util.*;
 
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
 
public class Breakout extends GraphicsProgram {
 
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
 
	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;
 
	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
 
	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
 
	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 1;
 
	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 1;
 
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;
 
	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
 
	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
 
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 8;
	
	/** Diameter of the ball in pixels */
	private static final int BALL_DIAM = BALL_RADIUS * 2;
 
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
 
	/** Number of turns */
	private static final int NTURNS = 3;
 
	
	public void init() {
		addMouseListeners();
	}
 
	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		waitForUser();
		
		
		while (true) {
			playBreakout();
		}
	}		
	
	/*Prompt user to click to start the game*/
	private void waitForUser() {
		
		GLabel clickToPlay = new GLabel("Click to Play");
		double clickToPlayX = (getWidth() - clickToPlay.getWidth()) / 2;
		double clickToPlayY = (getHeight() + clickToPlay.getAscent()) / 2;	
		add(clickToPlay, clickToPlayX, clickToPlayY);
		
		waitForClick();
		
		remove(clickToPlay);
	}
	
	private void playBreakout() {
		/*setup bricks and paddle*/
		setup();
		
		for (int i=0;i<NTURNS;i++) {
			
			play();
			
			/*initiate next turn*/
			if (ball.getY() + BALL_DIAM > getHeight()) {
				remove(ball);
				livesLeft--;
				
				if (livesLeft != 0) {
					/*wait for click before serving next ball*/
					GLabel tryAgain = new GLabel("Click to Try Again");
					double tryAgainX = (getWidth() - tryAgain.getWidth()) / 2;
					double tryAgainY = (getHeight() + tryAgain.getAscent()) / 2;	
					add(tryAgain, tryAgainX, tryAgainY);
					
					waitForClick();
					
					remove(tryAgain);
				}
				
			}
			
			if (brickCounter == 0) break;
		}
	
		if (livesLeft == 0) {
			removeAll();
			GLabel lose = new GLabel("Game Over. Click to Play Again");
			double loseCenterX = (getWidth() -lose.getWidth())/2;
			double loseCenterY = (getHeight()+lose.getAscent())/2;	
			add(lose, loseCenterX, loseCenterY);
			
			waitForClick();
			removeAll();
		}
	
		else {
			removeAll();
			GLabel win = new GLabel("You Win! Click to Play Again");
			double centerX = (getWidth()-win.getWidth())/2;
			double centerY = (getHeight()+win.getAscent())/2;
			add(win, centerX, centerY);
			
			waitForClick();
			removeAll();
		}
		
	}
	
	private void setup() {
		/*Set up rainbow bricks*/
		for (int i=0;i<NBRICK_ROWS;i++) {
			for (int j=0;j<NBRICKS_PER_ROW;j++) {
				double bricksAndSpaces = BRICK_WIDTH*NBRICKS_PER_ROW + (NBRICKS_PER_ROW -1)*BRICK_SEP;
				double x = (getWidth()-bricksAndSpaces)/2 + j * (BRICK_WIDTH + BRICK_SEP);
				int y = BRICK_Y_OFFSET + i*(BRICK_HEIGHT + BRICK_SEP);
				brick = new GRect(x,y,BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);
				if (i<=1) {
					brick.setColor(Color.RED);
					brick.setFilled(true);
				} else if (i<=3) {
					brick.setColor(Color.ORANGE);
					brick.setFilled(true);
				} else if (i<=5) {
					brick.setColor(Color.YELLOW);
					brick.setFilled(true);
				} else if (i<=7) {
					brick.setColor(Color.GREEN);
					brick.setFilled(true);
				} else {
					brick.setColor(Color.CYAN);
					brick.setFilled(true);
				}
			}
		}
		/*Set up the paddle*/
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddleY = getHeight()-PADDLE_Y_OFFSET;
		add(paddle,(getWidth()-PADDLE_WIDTH)/2,paddleY);
		
		/*Set up # of lives and brick counter*/
		livesLeft = 3;
		brickCounter = NBRICKS_PER_ROW*NBRICK_ROWS;
		
	}
	
	/*move center of paddle using mouse*/
	public void mouseMoved(MouseEvent e) {
		
		/*right-hand limit of the mouse*/
		double rightMouseLimit = WIDTH - PADDLE_WIDTH/2;
		
		/*distance mouse moves over the right-hand limit*/
		double rightMouseOver = e.getX()-rightMouseLimit;
		
		if (e.getX() <= rightMouseLimit && e.getX() >= PADDLE_WIDTH/2) {
			paddle.setLocation(e.getX()-PADDLE_WIDTH/2,paddleY);
	   } else if (e.getX() > rightMouseLimit) {
		   	paddle.setLocation(e.getX()-PADDLE_WIDTH/2-rightMouseOver,paddleY);
	   } else {
		   	paddle.setLocation(0,paddleY);
	   }
	}	
	
 
	private void play() {
		/*Set up the ball*/
		ball = new GOval(getWidth()/2 - BALL_RADIUS, getHeight()/2 - BALL_RADIUS, BALL_DIAM, BALL_DIAM);
		ball.setFilled(true);
		add(ball);
		
		/* Set up x and y velocities of the ball:
		 * downward velocity to be constant at 3;
		 * vx to be a random double between 1.0 and 3.0,
		 * and negative half the time.
		 */
		vy = 3;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		while (ball.getY() + BALL_DIAM < getHeight() && brickCounter != 0) {
				moveBall();
				checkForCollision();
				pause(30);
		}
	}
	
	private void moveBall() {
		ball.move(vx, vy);
		if (ball.getX()+ BALL_DIAM >= getWidth()) vx = -vx; //bounce off right wall
		if (ball.getX() <= 0) vx = -vx; 					//bounce off left wall
		if (ball.getY() <= 0) vy = -vy; 					//bounce off ceiling
	}
	
	/*Sets paddle to bounce off the paddle and bricks and remove a brick upon collision*/
	private void checkForCollision() {
		collider = getCollidingObject();
		if (collider != null) {
			if (collider == paddle) {
				vy = -vy;
				if (ball.getX()  < collider.getX()) { // if the ball hits the left edge of paddle
					if (vx >0) vx = -vx;  // and if it's moving to the right, change its x direction to move left
				}
				if (ball.getX() + BALL_DIAM > collider.getX() + PADDLE_WIDTH) { //if the ball hits the right edge of the paddle
					if (vx < 0) vx = -vx; // and if it's moving to the left, change its x direction to move right
				}
			}
			else {
				remove(collider);
				vy = -vy;
				brickCounter--;
			}
		}	
	}
	
	/*Returns the object that the ball collides with: paddle or brick*/
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null) 
			return getElementAt(ball.getX(), ball.getY());
		else if (getElementAt(ball.getX()+BALL_DIAM, ball.getY())  != null ) 
			return getElementAt(ball.getX()+BALL_DIAM, ball.getY());
		else if (getElementAt(ball.getX(), ball.getY()+BALL_DIAM) != null) 
			return getElementAt(ball.getX(), ball.getY()+BALL_DIAM);
		else if (getElementAt(ball.getX()+BALL_DIAM, ball.getY()+BALL_DIAM) != null) 
			return getElementAt(ball.getX()+BALL_DIAM, ball.getY()+BALL_DIAM);
		else return null;
	}
	
	
	/*private instance variables*/
	private GRect brick;
	private GRect paddle;
	private int paddleY;
	private GOval ball;
	private double vx, vy;                                          //horizontal and vertical velocities
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GObject collider;                                       //object the ball collides with: paddle or brick
	private int brickCounter = NBRICKS_PER_ROW*NBRICK_ROWS;         //counts remaining bricks, player wins at 0
	private int livesLeft = 3;				                              //counts down from 3 to 0, player loses at 0
 
}
