package sg;

import java.util.Random;

public class BallUtils
{
	private static Random rng = new Random();
	
	// Randomly selects a number between the two provided values.
	public static int randomlySelectIntermediateValue(int value1, int value2)
	{
		int largerValue;
		int smallerValue;
		int difference;
		int randValue;
		
		if( value1 >= value2 )
		{
			largerValue = value1;
			smallerValue = value2;
		}
		else
		{
			largerValue = value2;
			smallerValue = value1;
		}
		
		System.out.println( "largerValue: " + largerValue);
		System.out.println( "smallerValue: " + smallerValue);
		
		
		// Adding 1.  In rng, the upper limit is excluded, but we want the random number to be either the lower or higher of values.
		difference = largerValue-smallerValue+1;
		randValue = rng.nextInt(difference);
		
		System.out.println( "difference: " + difference + "\trandValue: " + randValue);
		
		int finalDestination = rng.nextInt(2);
		
		if( finalDestination == 1)
		{
			return smallerValue + randValue;
		}
		else
		{
			return largerValue-randValue;
		}
		
	}
}