package sg;

import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;


public class BallCollection
{
	LinkedList<Ball> ballList;
	int uniformBallDiameter;
	double acceptableBallAreaConsumption;
	Dimension d;
	
	
	public BallCollection(int uniformBallDiameter, double acceptableBallAreaConsumption, Dimension d)
	{
		ballList = new LinkedList<Ball>();
		this.uniformBallDiameter = uniformBallDiameter;
		this.acceptableBallAreaConsumption = acceptableBallAreaConsumption;
		this.d = d;
	}
	
	public void setDimension(Dimension d)
	{
		this.d = d;
	}
	
	public Dimension getDimension()
	{
		return d;
	}
	
	public int getBallPopulation()
	{
		return ballList.size();
	}
	
	public void detectBallCollision()
	{
			
		for(int i = 0; i< ballList.size(); i++)
		{
			for(int n = (i + 1); n < ballList.size(); n++)
			{
				Ball ball1 = ballList.get(i);
				Ball ball2 = ballList.get(n);
				
				if( ball1.isIntersecting(ball2) )
				{
					ball1.deflect();
					ball2.deflect();
					Ball childBall = spawnBallFromParents(ball1, ball2);
					if(childBall != null)
					{
						this.addBall(childBall);
					}
				}// End if
			}// End for
		}// End for
		
	}// End method detectBallCollision
	
	public void moveBalls()
	{
		for(int i = 0; i < ballList.size(); i++)
		{
			ballList.get(i).moveBall(d);
			
		}
		
		detectBallCollision();
	}// End moveBalls
	
	private void addBall(Ball ball)
	{
		ballList.add(ball);
	}
	
	public LinkedList<Ball> getRenderList()
	{
		LinkedList<Ball> deepCopy = new LinkedList<Ball>();
		for(int i = 0; i < ballList.size(); i++ )
		{
			deepCopy.add( new Ball(ballList.get(i)) );
		}
		return deepCopy;
	}
	
	
	public Ball spawnBall(Color color)
	{
		boolean successfulReproduction = false;
		int panelWidth = (int) d.getWidth();
		int panelHeight = (int) d.getHeight();
		int maxAttempts = 10;
		System.out.println("Spawning ball.");
		for( int i =0; i<maxAttempts; i++)
		{
			boolean kosherSpawnSpot = true;
			
			// Subracting the uniformBallDiameter constant, so that the ball doesn't try to partially spawn off screen.
			int initialX = BallUtils.randomlySelectIntermediateValue(1, panelWidth-uniformBallDiameter);
			int initialY = BallUtils.randomlySelectIntermediateValue(1, panelWidth-uniformBallDiameter);
		
			Ball newBall = new Ball(initialX, initialY, uniformBallDiameter);
			
			
			
			int n = 0;
			while( n < ballList.size() && kosherSpawnSpot)
			{
				Ball ballCheck = ballList.get(n);
				
				kosherSpawnSpot = !(ballCheck.isIntersecting(newBall));
				n++;
			}//End while loop
			
			if( kosherSpawnSpot )
			{				
				newBall.setColor(color);
				addBall(newBall);
				return newBall;
			}// End if
			else
			{
				//System.out.println( "Not a good spawn spot for this attempt.\n");
			}// End else
			
		}//End outer for loop
		
		System.out.println( "No suitable ball spawn location found with " + maxAttempts + " random attempts." );
		return null;
	}//End spawnBall
	
	public Ball spawnBallFromParents(Ball parent1, Ball parent2)
	{
		if(isStableSpawn())
		{
			Color parent1Color = parent1.getColor();
			Color parent2Color = parent2.getColor();
			Color childColor;
			
			int r = BallUtils.randomlySelectIntermediateValue(parent1Color.getRed(), parent2Color.getRed());
			int g = BallUtils.randomlySelectIntermediateValue(parent1Color.getGreen(), parent2Color.getGreen());
			int b = BallUtils.randomlySelectIntermediateValue(parent1Color.getBlue(), parent2Color.getBlue());
			
			/*
			System.out.println( "Creating child ball.");
			System.out.println( "Parent1 red: " + parent1Color.getRed() + "\tParent2 red: " + parent2Color.getRed() + "\tChild red: " + r );
			System.out.println( "Parent1 green: " + parent1Color.getGreen() + "\tParent2 green: " + parent2Color.getGreen() + "\tChild green: " + g );
			System.out.println( "Parent1 blue: " + parent1Color.getBlue() + "\tParent2 blue: " + parent2Color.getBlue() + "\tChild blue: " + b );
			*/
			
			childColor = new Color(r, g, b);
			
			return spawnBall(childColor);
		}
		else
		{
			return null;
		}
	}// End spawnBallFromParents
	
	public boolean isStableSpawn()
	{
		int panelWidth = (int) d.getWidth();
		int panelHeight = (int) d.getHeight();
		
		double totalPanelArea = panelWidth * panelHeight;
		double ballComfortSpace = uniformBallDiameter*uniformBallDiameter;
		double totalBallAreaConsumption = ballList.size() * ballComfortSpace;
		
		
		double ballConsumptionRatio = totalBallAreaConsumption/totalPanelArea;
		if(ballConsumptionRatio < acceptableBallAreaConsumption)
		{
			System.out.println( "ballConsumptionRatio is: " + ballConsumptionRatio);
		}
		return (ballConsumptionRatio < acceptableBallAreaConsumption);
		
	}// End isStableSpawn
	
}// end class BallCollection