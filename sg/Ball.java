package sg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Color;
import java.lang.Math;

public class Ball
{
	//TODO: Do color stuff.
	
	private Color ballColor = Color.BLACK;
	private int xPosition;
	private int yPosition;
	
	private int diameter;
	
	private boolean rightwardTrajectory = true;
	private boolean downwardTrajectory = true;
	
	boolean debug = false;
	
	public Ball( int initialXPos, int initialYPos, int diameter)
	{
		xPosition = initialXPos;
		yPosition = initialYPos;
		this.diameter = diameter;
	}
	
	public Ball( Ball ball)
	{
		xPosition = ball.getXPosition();
		yPosition = ball.getYPosition();
		diameter = ball.getDiameter();
		ballColor = ball.getColor();
		rightwardTrajectory = ball.getRightwardTrajectory();
		downwardTrajectory = ball.getDownwardTrajectory();
	}
	
	public void setColor( Color newColor )
	{
		ballColor = newColor;
	}
	
	public Color getColor()
	{
		return ballColor;
	}
	
	public void setXPosition( int newXPosition )
	{
		xPosition = newXPosition;
	}
	
	public int getXPosition()
	{
		return xPosition;
	}
	
	public void setYPosition( int newYPosition )
	{
		yPosition = newYPosition;
	}
	
	public int getYPosition()
	{
		return yPosition;
	}
	
	public void setDiameter( int d )
	{
		diameter = d;
	}
	
	public int getDiameter()
	{
		return diameter;
	}
	
	public int getWidth()
	{
		return diameter;
	}
	
	public int getHeight()
	{
		return diameter;
	}
	
	public boolean debugMode(boolean debug)
	{
		this.debug = debug;
		return this.debug;
	}
	
	public boolean debugMode()
	{
		return debug;
	}
	
	public void moveBall(Dimension d)
	{
		if(rightwardTrajectory)
		{
			xPosition = xPosition + 1;
		}
		else
		{
			xPosition = xPosition-1;
		}
		
		if(downwardTrajectory)
		{
			yPosition = yPosition + 1;
		}
		else
		{
			yPosition = yPosition - 1;
		}
		
		detectBorderCollission(d);
		
	}
	
	public void deflect()
	{
		reflectX();
		reflectY();
		
		if(debug)
		{
			System.out.println("Made contact with another ball.");
			System.out.println("");
		}
	}
	
	private void reflectX()
	{
		rightwardTrajectory = !rightwardTrajectory;
		
		if(debug)
		{
			System.out.println( "Rightward Trajectory is now set to : " + rightwardTrajectory );
		}
		
	}
	
	private void reflectY()
	{
		downwardTrajectory = !downwardTrajectory;
		
		if(debug)
		{
			System.out.println( "Downward Trajectory is now set to : " + downwardTrajectory );
		}
	}
	
	public boolean getRightwardTrajectory()
	{
		return rightwardTrajectory;
	}
	
	public boolean getDownwardTrajectory()
	{
		return downwardTrajectory;
	}
	
	
	public void detectBorderCollission(Dimension d)
	{
		boolean borderCollision = false;
		int minX=0;
		int minY=0;
		
		int maxX = (int) d.getWidth();
		int maxY = (int) d.getHeight();

		int rightMostPositionOnBall = xPosition + diameter;
		int downwardMostPositionOnBall = yPosition + diameter;
		int leftMostPositionOnBall = xPosition;
		int upwardMostPositionOnBall = yPosition;
		
		if(rightMostPositionOnBall >= maxX || leftMostPositionOnBall <= minX)
		{
			reflectX();
			borderCollision = true;
		}
		
		if( downwardMostPositionOnBall >= maxY || upwardMostPositionOnBall <= minY )
		{
			reflectY();
			borderCollision = true;
		}
		
		if(debug)
		{
			if(borderCollision)
			{
				System.out.println( "Border collission detected." );
			}
			
			System.out.println( "leftMostPositionOnBall " + leftMostPositionOnBall );
			System.out.println( "rightMostPositionOnBall: " + rightMostPositionOnBall);
			System.out.println( "upwardMostPositionOnBall: " + upwardMostPositionOnBall);
			System.out.println("downwardMostPositionOnBall: " + downwardMostPositionOnBall);
			System.out.println( "maxX: " + maxX);
			System.out.println("maxY: " + maxY);
			System.out.println("");
		}//End if statement for debug
	}
	
	public double getRadius()
	{
		return ( ((double) diameter)/2 );
	}
	
	public PixelCoordinates getCenter()
	{
		double xCenterCoordinate = (double) xPosition + getRadius();
		double yCenterCoordinate = (double) yPosition + getRadius();
		
		return (new PixelCoordinates(xCenterCoordinate, yCenterCoordinate));
	}
	
	public boolean isIntersecting(Ball foreignBall)
	{
		PixelCoordinates localCenterPoint = getCenter();
		PixelCoordinates foreignCenterPoint = foreignBall.getCenter();
		
		double deltaX = foreignCenterPoint.getXPosition() - localCenterPoint.getXPosition();
		double deltaY = foreignCenterPoint.getYPosition() - localCenterPoint.getYPosition();
		
		// Pythagorean theorem
		double straightDistance = Math.sqrt( (deltaX * deltaX) + (deltaY*deltaY) );
		
		// If the straightDistance (AKA distance between center points) is less than the sum of the radii, then the circles are colliding.
		return ( straightDistance <= (this.getRadius() + foreignBall.getRadius()) );
		
	}
}
