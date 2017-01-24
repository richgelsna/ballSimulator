package sg;

import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class Game extends JPanel
{
	
	final int globalBallDiameter = 30;
	double acceptableBallAreaConsumption = 0.1;
	
	public static void main( String[] args ) throws InterruptedException
	{
		JFrame frame = new JFrame( "<(~_~<) |Trippin' Balls| (>'_')>" );
		Game game = new Game();
		
		frame.add(game);
		frame.setSize(300, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.addBall(game.spawnBall(Color.WHITE));
		game.addBall(game.spawnBall(Color.BLACK));
		while(true)
		{
			game.rewriteBackgroundColor(frame);
			game.moveBalls(game.getSize());
			game.repaint();
			Thread.sleep(10);
		}
		
	}
	
	@Override 
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
		
		detectBallCollision();
		for(int i = 0; i < ballList.size(); i++)
		{
			Ball theBall = ballList.get(i);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(theBall.getXPosition(), theBall.getYPosition(), theBall.getWidth(), theBall.getHeight());
			g2d.setColor(theBall.getColor());
			g2d.fillOval(theBall.getXPosition()+2, theBall.getYPosition()+2, theBall.getWidth()-4, theBall.getHeight()-4 );
		}

		
	}//End function Paint
	
	public void rewriteBackgroundColor(JFrame frame)
	{
		int numberOfBalls = ballList.size();
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		int r;
		int g;
		int b;
		
		for(int i = 0; i < ballList.size(); i++)
		{
			Color currentBallColor = ballList.get(i).getColor();
			totalR = totalR + currentBallColor.getRed();
			totalG = totalG + currentBallColor.getGreen();
			totalB = totalB + currentBallColor.getBlue();
		}
		
		r = totalR/numberOfBalls;
		g = totalG/numberOfBalls;
		b = totalB/numberOfBalls;
		
		setBackground(new Color(r, g, b) );
	}// End rewriteBackground
	
	
	
}