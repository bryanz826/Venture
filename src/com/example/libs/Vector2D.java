package com.example.libs;

public class Vector2D
{
	private float	x;
	private float	y;

	public Vector2D()
	{
		this.x = 0;
		this.y = 0;
	}

	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public static float exactDist(Vector2D v1, Vector2D v2)
	{
		float x = v2.x - v1.x;
		float y = v2.y - v2.y;
		return (float) Math.sqrt(x * x + y * y);
	}

	public static float getDot(Vector2D v1, Vector2D v2)
	{
		return v1.x * v2.x + v1.y * v2.y;
	}

	public void add(Vector2D other)
	{
		x += other.x;
		y += other.y;
	}
	
	public void add(Vector2D[] others)
	{
		for (Vector2D other : others) {
			this.add(other);
		}
	}

	public void normalize()
	{
		float length = getExactLength();
		quickNormalize(length);
	}

	public void quickNormalize(float length)
	{
		if (length != 0f) {
			x /= length;
			y /= length;
		}
	}

	public void scale(float scalar)
	{
		x *= scalar;
		y *= scalar;
	}

	public float getAngle(Vector2D other)
	{
		return 0;
	}

	public float getExactLength()
	{
		return (float) Math.sqrt(x * x + y * y);
	}
	
	//
	// UTILITY SETTERS
	//

	public void validateZero()
	{
		validateZeroX();
		validateZeroY();
	}

	public void validateZero(float threshold)
	{
		validateZeroX(threshold);
		validateZeroY(threshold);
	}
	
	public void validateZeroX()
	{
		if (x > -0.05f && x < 0.05f) x = 0;
	}
	
	public void validateZeroX(float threshold)
	{
		if (x > -threshold && x < threshold) x = 0;
	}
	
	public void validateZeroY()
	{
		if (y > -0.05f && y < 0.05f) y = 0;
	}
	
	public void validateZeroY(float threshold)
	{
		if (y > -threshold && y < threshold) y = 0;
	}

	public void negate()
	{
		negateX();
		negateY();
	}
	
	public void negateX()
	{
		x = -x;
	}
	
	public void negateY()
	{
		y = -y;
	}

	//
	//
	//
	
	public void setAll(float a)
	{
		setX(a);
		setY(a);
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
}
