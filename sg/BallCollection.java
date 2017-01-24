package sg;

import java.util.LinkedList;

LinkedList<Ball> ballList;

public class BallCollection
{
	
	int uniformBallDiameter;
	double acceptableBallAreaConsumption;
	
	public BallCollection()
	{
		ballList = new LinkedList<Ball>();
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
	
	public void moveBalls(Dimension d)
	{
		for(int i = 0; i < ballList.size(); i++)
		{
			ballList.get(i).moveBall(d);
			
		}
	}// End moveBalls
	
	private void addBall(Ball ball)
	{
		ballList.add(ball);
	}
	
	private LinkedList<Ball> getRenderList()
	{
		LinkedList<Ball> deepCopy = new LinkedList<Ball>;
		for(int i = 0; i < ballList.size(); i++ )
		{
			deepCopy.add( new Ball(ballList.get(i)) );
		}
		
		return deepCopy;
	}
	
	
	public Ball spawnBall(Color color)
	{
		Dimension d = getSize();
		boolean successfulReproduction = false;
		int panelWidth = (int) d.getWidth();
		int panelHeight = (int) d.getHeight();
		int maxAttempts = 10;
		System.out.println( "Attempting to spawn a new ball.");
		
		for( int i =0; i<maxAttempts; i++)
		{
			boolean kosherSpawnSpot = true;
			System.out.println("Attempt #" + i + ": ");
			
			// Subracting the globalBallDiameter constant, so that the ball doesn't try to partially spawn off screen.
			int initialX = BallUtils.randomlySelectIntermediateValue(1, panelWidth-globalBallDiameter);
			int initialY = BallUtils.randomlySelectIntermediateValue(1, panelWidth-globalBallDiameter);
		
			Ball newBall = new Ball(initialX, initialY, globalBallDiameter);
			
			
			
			int n = 0;
			while( n < ballList.size() && kosherSpawnSpot)
			{
				Ball ballCheck = ballList.get(n);
				
				kosherSpawnSpot = !(ballCheck.isIntersecting(newBall));
				n++;
			}//End while loop
			
			if( kosherSpawnSpot )
			{
				System.out.println("Spawning ball at coordinates (" + initialX + ", " + initialY + ").");
				
				newBall.setColor(color);
				return newBall;
			}// End if
			else
			{
				System.out.println( "Not a good spawn spot for this attempt.\n");
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
			
			System.out.println( "Creating child ball.");
			System.out.println( "Parent1 red: " + parent1Color.getRed() + "\tParent2 red: " + parent2Color.getRed() + "\tChild red: " + r );
			System.out.println( "Parent1 green: " + parent1Color.getGreen() + "\tParent2 green: " + parent2Color.getGreen() + "\tChild green: " + g );
			System.out.println( "Parent1 blue: " + parent1Color.getBlue() + "\tParent2 blue: " + parent2Color.getBlue() + "\tChild blue: " + b );
			
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
		Dimension d = getSize();
		int panelWidth = (int) d.getWidth();
		int panelHeight = (int) d.getHeight();
		
		double totalPanelArea = panelWidth * panelHeight;
		double ballComfortSpace = globalBallDiameter*globalBallDiameter;
		double totalBallAreaConsumption = ballList.size() * ballComfortSpace;
		
		
		double ballConsumptionRatio = totalBallAreaConsumption/totalPanelArea;
		if(ballConsumptionRatio < acceptableBallAreaConsumption)
		{
			System.out.println( "ballConsumptionRatio is: " + ballConsumptionRatio);
		}
		return (ballConsumptionRatio < acceptableBallAreaConsumption);
		
	}// End isStableSpawn
	
}// end class BallCollection