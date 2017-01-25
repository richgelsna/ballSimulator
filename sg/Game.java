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
	final double acceptableBallAreaConsumption = 0.12345;
	final int tickTimeLength = 10;
	
	BallCollection ballCollection;
	
	public void initializeSim()
	{
		ballCollection = new BallCollection(globalBallDiameter, acceptableBallAreaConsumption, getSize());
		System.out.println("Initialized ballCollection.  Adding ball1");
		ballCollection.spawnBall(Color.WHITE);
		System.out.println("Ball Population: " + ballCollection.getBallPopulation() );
		System.out.println("Initialized ballCollection.  Adding ball2");
		ballCollection.spawnBall(Color.BLACK);
		System.out.println("Ball Population: " + ballCollection.getBallPopulation() );
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		
		
		JFrame frame = new JFrame( "<(~_~<) |Trippin' Balls| (>'_')>" );
		Game game = new Game();
		
		frame.add(game);
		frame.setSize(300, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.initializeSim();
		while(true)
		{
			game.tick();
			
		}
		
	}
	
	public void tick() throws InterruptedException
	{
		rewriteBackgroundColor();
		ballCollection.setDimension(getSize());
		ballCollection.moveBalls();
		repaint();
		Thread.sleep(tickTimeLength);
	}
	
	@Override 
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		LinkedList<Ball> ballList = ballCollection.getRenderList();
		
		for(int i = 0; i < ballList.size(); i++)
		{
			Ball theBall = ballList.get(i);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(theBall.getXPosition(), theBall.getYPosition(), theBall.getWidth(), theBall.getHeight());
			g2d.setColor(theBall.getColor());
			g2d.fillOval(theBall.getXPosition()+2, theBall.getYPosition()+2, theBall.getWidth()-4, theBall.getHeight()-4 );
		}

		
	}//End function Paint
	
	public void rewriteBackgroundColor()
	{
		LinkedList<Ball> ballList = ballCollection.getRenderList();
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