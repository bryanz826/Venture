package com.example.entities.collisions;

import com.example.libs.Vector2D;

/**
 * The bounds used for collision detection.
 * 
 * @author poroia
 */
public class Bounds
{
	/**
	 * The 2D coordinates of this Bounds represented by a vector.
	 */
	private Vector2D	position;

	/**
	 * The width of this Bounds.
	 */
	private float		width;

	/**
	 * The height of this Bounds.
	 */
	private float		height;

	/**
	 * Constructs a Bounds.
	 * 
	 * @param position
	 *            The 2D coordinates
	 * @param width
	 *            The width
	 * @param height
	 *            The height
	 */
	public Bounds(Vector2D position, float width, float height)
	{
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	//
	// GENERAL METHODS
	//

	/**
	 * Returns the y-coordinate for the center.
	 * 
	 * @return centerY
	 */
	public Vector2D getCenter()
	{
		float x = position.getX() + width / 2;
		float y = position.getY() + height / 2;
		return new Vector2D(x, y);
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Set Bounds.
	 * 
	 * @param x
	 *            The x-coordinates
	 * @param y
	 *            The y-coordinates
	 * @param width
	 *            The width
	 * @param height
	 *            The height
	 */
	public void setBounds(Vector2D position, float width, float height)
	{
		this.position = position;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the 2D coordinates in a Vector2D.
	 * 
	 * @return position
	 */
	public Vector2D getPosition()
	{
		return position;
	}

	/**
	 * Returns the x-coordinate.
	 * 
	 * @return x
	 */
	public float getX()
	{
		return position.getX();
	}

	/**
	 * Returns the y-coordinate.
	 * 
	 * @return y
	 */
	public float getY()
	{
		return position.getY();
	}

	/**
	 * Returns the width.
	 * 
	 * @return width
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * Returns the height.
	 * 
	 * @return height
	 */
	public float getHeight()
	{
		return height;
	}

	/**
	 * Returns the radius, assuming width is equal to height and the type is a
	 * circle.
	 * 
	 * @return
	 */
	public float getRadius()
	{
		return width / 2;
	}

	//
	// INHERENT METHODS
	//

	@Override
	public String toString()
	{
		return String.format("pos=%s, width=%s, height=%s", position, width, height);
	}
}
