package com.example.entities.collisions;

/**
 * Utilizes circles for collision detection.
 * 
 * @author poroia
 */
public class Circle
{
	private float	x;
	private float	y;
	private float	radius;

	public Circle(float x, float y, float size)
	{
		this.x = x;
		this.y = y;
		this.radius = size / 2;
	}

	public boolean intersects(Circle other)
	{
		float distX = getX() + getRadius() - other.getX() - other.getRadius();
		float distY = getY() + getRadius() - other.getY() - other.getRadius();
		float distRad = getRadius() + other.getRadius();
		return distX * distX + distY * distY <= distRad * distRad;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getRadius()
	{
		return radius;
	}
}
