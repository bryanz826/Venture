package com.example.libs;

/**
 * A two-dimensional vector library with many utility methods useful for
 * calculations.
 * 
 * @author poroia
 */
public class Vector2D
{
	//
	// FIELDS
	//

	/**
	 * The first dimensional x-component of this vector.
	 */
	private float	x;

	/**
	 * The second dimensional y-component of this vector.
	 */
	private float	y;

	//
	// CONSTRUCTORS
	//

	/**
	 * Constructs a 2D vector with values of zero.
	 */
	public Vector2D()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructs a 2D vector that aligns with input radians.
	 * 
	 * @param rad
	 *            The radians from a Math.atan2() output.
	 */
	public Vector2D(float rad)
	{
		x = (float) Math.cos(rad);
		y = (float) Math.sin(rad);
		// normalize();
	}

	/**
	 * Constructs a 2D vector with the specified x and y values.
	 * 
	 * @param x
	 *            The first dimensional x-component.
	 * @param y
	 *            The second dimensional y-component.
	 */
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	//
	// GENERAL METHODS
	//

	/**
	 * Adds the other vector to this vector.
	 * 
	 * @param other
	 *            The other vector used in the calculation.
	 */
	public void add(Vector2D other)
	{
		x += other.x;
		y += other.y;
	}

	/**
	 * Adds a multitude of other vectors to this vector.
	 * 
	 * @param others
	 */
	public void add(Vector2D... others)
	{
		for (Vector2D other : others) {
			this.add(other);
		}
	}

	/**
	 * Normalizes this vector.
	 */
	public void normalize()
	{
		float length = getExactLength();
		quickNormalize(length);
	}

	/**
	 * Normalizes this vector based on a length specification. Can be used to
	 * prevent recalculation of the costly length method.
	 * 
	 * @param length
	 *            The length or magnitude of this vector.
	 */
	public void quickNormalize(float length)
	{
		if (length != 0f) {
			x /= length;
			y /= length;
		}
	}

	/**
	 * Multiply this vector by a scalar value.
	 * 
	 * @param scalar
	 */
	public void scale(float scalar)
	{
		x *= scalar;
		y *= scalar;
	}

	/**
	 * Calculates and returns the exact distance between two vectors.
	 * 
	 * @param v1
	 *            The first vector used in the calculation.
	 * @param v2
	 *            The second vector used in the calculation.
	 * @return exactDist
	 */
	public static float getExactDist(Vector2D v1, Vector2D v2)
	{
		float x = v2.x - v1.x;
		float y = v2.y - v2.y;
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates and returns the dot product between two vectors.
	 * 
	 * @param v1
	 *            The first vector used in the calculation.
	 * @param v2
	 *            The second vector used in the calculation.
	 * @return dotProduct
	 */
	public static float getDot(Vector2D v1, Vector2D v2)
	{
		return v1.x * v2.x + v1.y * v2.y;
	}

	/**
	 * Returns a random point between two Vector2Ds.
	 * 
	 * @param v1
	 *            The first vector
	 * @param v2
	 *            The second vector
	 * @return randPoint
	 */
	public static Vector2D getRandPoint(Vector2D v1, Vector2D v2)
	{
		float x = ReferenceMath.getRandomFloat(v1.getX(), v2.getX());
		float y = ReferenceMath.getRandomFloat(v1.getY(), v2.getY());
		return new Vector2D(x, y);
	}

	/**
	 * Calculates and returns the angle between this vector and another with this
	 * vectora as the "center".
	 * 
	 * @param other
	 *            The other vector.
	 * @return angle
	 */
	public float getRadians(Vector2D other)
	{
		float y = other.getY() - getY();
		float x = other.getX() - getX();
		return (float) Math.atan2(y, x);
	}

	/**
	 * Calculates and returns the exact length or magnitude of this vector.
	 * 
	 * @return exactLength
	 */
	public float getExactLength()
	{
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Randomizes both the x and y values between 0 and 1. Cannot return a zero
	 * vector.
	 */
	public void randomizeComponents()
	{
		x = (float) Math.random();
		y = (float) Math.random();
		if (x == 0 && y == 0) randomizeComponents();
		else normalize();
	}

	/**
	 * Rounds both the x and y values to zero within a 0.05 threshold.
	 */
	public void validateZero()
	{
		validateZeroX();
		validateZeroY();
	}

	/**
	 * Rounds both the x and y values to zero within a specified threshold.
	 */
	public void validateZero(float threshold)
	{
		validateZeroX(threshold);
		validateZeroY(threshold);
	}

	/**
	 * Rounds the x value to zero within a 0.05 threshold.
	 */
	public void validateZeroX()
	{
		if (x > -0.05f && x < 0.05f) x = 0;
	}

	/**
	 * Rounds the x value to zero within a specified threshold.
	 */
	public void validateZeroX(float threshold)
	{
		if (x > -threshold && x < threshold) x = 0;
	}

	/**
	 * Rounds the y value to zero within a 0.05 threshold.
	 */
	public void validateZeroY()
	{
		if (y > -0.05f && y < 0.05f) y = 0;
	}

	/**
	 * Rounds the y value to zero within a specified threshold.
	 */
	public void validateZeroY(float threshold)
	{
		if (y > -threshold && y < threshold) y = 0;
	}

	/**
	 * Negates the x and y values.
	 */
	public void negate()
	{
		negateX();
		negateY();
	}

	/**
	 * Negates the x value.
	 */
	public void negateX()
	{
		x = -x;
	}

	/**
	 * Negates the y value.
	 */
	public void negateY()
	{
		y = -y;
	}
	
	/**
	 * Repositions entire vector based on offset. Used mainly for position vectors.
	 */
	public void reposition(float offset)
	{
		x -= offset;
		y -= offset;
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Sets both the x and y values of this vector.
	 * 
	 * @param a
	 *            The value to set both x and y.
	 */
	public void setAll(float a)
	{
		setX(a);
		setY(a);
	}

	/**
	 * Sets the x value.
	 * 
	 * @param x
	 *            The x value.
	 */
	public void setX(float x)
	{
		this.x = x;
	}

	/**
	 * Sets the y value.
	 * 
	 * @param y
	 *            The y value.
	 */
	public void setY(float y)
	{
		this.y = y;
	}

	/**
	 * Returns the x value.
	 * 
	 * @return x
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * Returns the y value.
	 * 
	 * @return
	 */
	public float getY()
	{
		return y;
	}

	//
	// INNATE METHODS
	//

	@Override
	public String toString()
	{
		return String.format("(%f, %f)", x, y);
	}
}
