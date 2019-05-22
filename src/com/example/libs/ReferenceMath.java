package com.example.libs;

import java.util.concurrent.ThreadLocalRandom;

import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.BoundsManager;

public class ReferenceMath
{
	/**
	 * Calculates and returns the first argument mod second argument, not remainder.
	 * 
	 * @param a
	 *            The first argument
	 * @param b
	 *            The second argument
	 * @return a modulus b
	 */
	public static float modulus(float a, float b)
	{
		return (a % b + b) % b;
	}

	/**
	 * Converts ticks to seconds.
	 * 
	 * @param tick
	 *            The input tick to be converted to seconds
	 * @return seconds
	 */
	public static float toSeconds(int tick)
	{
		return (float) tick / ReferenceConfig.TARGET_UPS;
	}

	/**
	 * Converts seconds to ticks.
	 * 
	 * @param seconds
	 *            The input seconds to be converted to ticks
	 * @return ticks
	 */
	public static int toTicks(float seconds)
	{
		return Math.round(seconds * ReferenceConfig.TARGET_UPS);
	}

	/**
	 * Generates a random int between origin and bound. Origin cannot be greater
	 * than bound.
	 * 
	 * @param origin
	 *            The minimum value
	 * @param bound
	 *            The maximum value
	 * @return randInt
	 */
	public static int getRandomInt(int origin, int bound)
	{
		return ThreadLocalRandom.current().nextInt(origin, bound);
	}

	/**
	 * Generates a random float between a and b.
	 * 
	 * @param a
	 *            The first value
	 * @param b
	 *            The second value
	 * @return randFloat
	 */
	public static float getRandomFloat(float a, float b)
	{
		float rand;
		if (b >= a) rand = (ThreadLocalRandom.current().nextFloat() * (b - a)) + a;
		else rand = (ThreadLocalRandom.current().nextFloat() * (a - b)) + b;
		return rand;
	}

	/**
	 * Generates a random float from a normal distribution with a specified mean and
	 * standard deviation. Cannot generate numbers more than four standard
	 * deviations from the mean.
	 * 
	 * @param mean
	 * @param stdDev
	 * @return randFloat
	 */
	public static float getRandomFloatNormal(float mean, float stdDev)
	{
		return (float) ThreadLocalRandom.current().nextGaussian() * stdDev + mean;
	}

	/**
	 * Returns a random point within a rectangle (either targetArea or failArea).
	 * 
	 * @param rect
	 *            The point will be found in this rectangle
	 * @return randPoint
	 */
	public static Vector2D getRandInPoint(Bounds rect)
	{
		float x = getRandomFloat(rect.getX(), rect.getX() + rect.getWidth());
		float y = getRandomFloat(rect.getY(), rect.getY() + rect.getHeight());
		return new Vector2D(x, y);
	}

	/**
	 * Returns a random point on the perimeter of a rectangle off the map. Chooses
	 * with side of the rectangle and then chooses a point on that line.
	 * 
	 * @return randPoint
	 */
	public static Vector2D getRandPerimeterPoint(BoundsManager bm)
	{
		Bounds rect = bm.getFirst();
		Vector2D randPoint = new Vector2D();

		Vector2D topLeft = new Vector2D(rect.getX(), rect.getY());
		Vector2D topRight = new Vector2D(rect.getX() + rect.getWidth(), rect.getY());
		Vector2D bottomLeft = new Vector2D(rect.getX(), rect.getY() + rect.getHeight());
		Vector2D bottomRight = new Vector2D(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());

		int r = ThreadLocalRandom.current().nextInt(4);
		switch (r)
		{
			case 0:
				randPoint = Vector2D.getRandPoint(topLeft, topRight); // top
				break;
			case 1:
				randPoint = Vector2D.getRandPoint(topLeft, bottomLeft); // left
				break;
			case 2:
				randPoint = Vector2D.getRandPoint(topRight, bottomRight); // right
				break;
			case 3:
				randPoint = Vector2D.getRandPoint(bottomLeft, bottomRight); // bottom
				break;
		}

		return randPoint;
	}
}
