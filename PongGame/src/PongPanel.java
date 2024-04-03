import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongPanel extends JPanel implements ActionListener{
	static final int WIDTH = 900;
	static final int HEIGHT = 500;
	static final int UNIT = 25;
	static final int BAR_WIDTH = 30;
	static final int BAR_HEIGHT = 140;
	static final int GAME_UNIT = (WIDTH+HEIGHT)/UNIT;
	static final int  X_RED_BAR = WIDTH-BAR_WIDTH;
	static final int X_GREEN_BAR = 0;
	int y_Red_Bar = 160;
	int y_Green_Bar = 160;
	int delay = 50;
	int xBall = (WIDTH /2);
	int yBall = (HEIGHT/2); 
	int xVelocity =6;
	int yVelocity =6;
	boolean running = false;
	char redDirection = 'u';
	char greenDirection = 'w';
	Timer timer;
	JButton button;
	boolean checkRedScore = true;
	boolean checkGreenScore = true;	
	int countRedScore = 0;
	int countGreenSocre = 0;
	
	
	public PongPanel() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.black);
		this.setOpaque(true);
		startGame();
		this.setFocusable(true);
	}
	
	public void startGame() {
		running = true;
		this.addKeyListener(new KeyControlRed());
		//this.addKeyListener(new KeyControlGreen());
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawObjects(g);
	}
	
	private void drawObjects(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
//		for(int i=0; i<GAME_UNIT; i++) {
//		g2D.setColor(Color.white);
//		g2D.drawLine(WIDTH,i*UNIT, 0, i*UNIT);}
		
		// draw the line in the middle from top to bottom of the Panel
		g2D.setColor(Color.white);
		g2D.setStroke(new BasicStroke(1));
		g2D.drawLine((WIDTH/2), 0, (WIDTH/2), HEIGHT);
		
		// draw the bar on the right middle of the Panel
		g2D.setColor(Color.red);
		g2D.fillRect(X_RED_BAR , y_Red_Bar ,BAR_WIDTH, BAR_HEIGHT);
		
		// draw the bar on the left middle of the Panel
		g2D.setColor(Color.blue);
		g2D.fillRect(X_GREEN_BAR, y_Green_Bar,BAR_WIDTH, BAR_HEIGHT);
		
		// draw the ball 
		g2D.setColor(Color.white);
		g2D.fillOval(xBall, yBall, UNIT, UNIT);

		// draw red score
		if (!checkRedScore()){
			String score = "" + countRedScore;
			FontMetrics metrics = getFontMetrics(new Font("MV Boli", Font.PLAIN, 10));
			g.setColor(Color.white);
			g.setFont((new Font("MV Boli", Font.PLAIN, 30)));
			g.drawString(score, ((WIDTH-metrics.stringWidth(score))/2)+20, 50);
			g2D.setColor(Color.white);
			g2D.fillOval(xBall, yBall, UNIT, UNIT);
	
			
		}

		// draw green score
		if (!checkGreenScore()){
			String score = "" + countGreenSocre;
			FontMetrics metrics = getFontMetrics(new Font("MV Boli", Font.PLAIN, 10));
			g.setColor(Color.white);
			g.setFont(new Font("MV Boli", Font.PLAIN, 30));
			g.drawString(score, ((WIDTH-metrics.stringWidth(score))/2)-40, 50);
		}
	}
	
	
	private void ballMove() {
		
		// Change direction when touching Red Bar
		
		if (xBall ==(X_RED_BAR-BAR_WIDTH) && (yBall>=(y_Red_Bar -10)&& yBall<=(BAR_HEIGHT+y_Red_Bar)) )  {
			xVelocity *= -1;
			delay = 15;
			timer.stop();
			timer.setDelay(delay);
			timer.start();
		}
		
		// Change direction when touching Green Bar
		
		if (xBall == (X_GREEN_BAR+BAR_WIDTH) && (yBall >= (y_Green_Bar-10) && yBall <=(BAR_HEIGHT+y_Green_Bar)) ) {
			xVelocity *= -1;
			delay = 15;
			timer.stop();
			timer.setDelay(delay);
			timer.start();  // restart with new delay
			
		}
			
		
		// Change direction when touching the top or bottom
		if (yBall>=(HEIGHT-UNIT) || yBall <5) {
			yVelocity *= -1;
		}
		
		// speed of the ball 
		xBall = xBall + xVelocity;
		yBall = yBall + yVelocity;	
	}

	private void redBarMove() {
		switch(redDirection){
			case 'u':if (y_Red_Bar >0) {y_Red_Bar=y_Red_Bar-15;}
				break;
			case 'd' : if (y_Red_Bar <(HEIGHT -BAR_HEIGHT)){y_Red_Bar=y_Red_Bar+15;}
				break;
		}
		
	}

	private void greenBarMove() {
		switch(greenDirection) {
		  	case 'w':if (y_Green_Bar>0) {y_Green_Bar = y_Green_Bar-15;}
		  		break;
		  	case 's':if (y_Green_Bar <(HEIGHT - BAR_HEIGHT)) {y_Green_Bar = y_Green_Bar+15;}
		  		break;
		}
		
	}


	private boolean checkRedScore(){
		
		if (xBall<=0){
			countRedScore ++;
			checkRedScore = false;
			
		}
		return checkRedScore;
		
	}

	private boolean checkGreenScore(){
		if (xBall >= (WIDTH-UNIT)){
			countGreenSocre++;
			checkGreenScore = false;
		}
		return checkGreenScore;
	}

	private void checkBall(){
		if (xBall<=0){
			 xBall = (WIDTH /2);
			 yBall = (HEIGHT/2);
			 delay = 50;
			timer.stop();
			timer.setDelay(delay);
			timer.start();
			
		}
		if (xBall >= (WIDTH-UNIT)){
			 xBall = (WIDTH /2);
			 yBall = (HEIGHT/2);
			 delay = 50;
			timer.stop();
			timer.setDelay(delay);
			timer.start();
			
		}
	}


	private class KeyControlRed extends KeyAdapter {
		@Override 
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() ==KeyEvent.VK_UP){
				redDirection = 'u';
				
			}else if (e.getKeyCode() ==KeyEvent.VK_DOWN){
				redDirection = 'd';	
			}   
			
			if (e.getKeyCode() ==KeyEvent.VK_W){
				greenDirection = 'w';
					
			}else if(e.getKeyCode() ==KeyEvent.VK_S){
				greenDirection = 's';		
			}  


		
		 redBarMove();	
		 greenBarMove();
		 repaint();
	}
}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ballMove();
		//speedUp();
		checkRedScore();
		checkGreenScore();
		checkBall();
		repaint();
	
	}}