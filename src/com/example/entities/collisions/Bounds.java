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
	 * The different types of Bounds
	 */
	public static enum Type
	{
		CIRC,
		RECT
	}

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
	 * The bounds type specified in this class's enum Type.
	 */
	private Type		type;

	/**
	 * Constructs a Bound with a specified type.
	 * 
	 * @param type
	 *            The bounds type
	 */
	public Bounds(Type type)
	{
		this.type = type;
		position = new Vector2D(0, 0);
	}

	/**
	 * Constructs a CIRC type Bounds.
	 * 
	 * @param x
	 *            The x-coordinates
	 * @param y
	 *            The y-coordinates
	 * @param size
	 *            The size
	 */
	public Bounds(Vector2D position, float size)
	{
		setCirc(position, size);
		this.type = Type.CIRC;
	}

	/**
	 * Constructs a RECT type Bounds.
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
	public Bounds(Vector2D position, float width, float height)
	{
		setRect(position, width, height);
		this.type = Type.RECT;
	}

	/**
	 * Determine if an intersection occurs between two bounds. Able to account for
	 * different bound types.
	 * 
	 * @param other
	 *            The other bounds used in the calculation.
	 * @return if intersects
	 */
	public boolean intersects(Bounds other)
	{
		if (checkType(this, Type.CIRC)) {

			if (checkType(other, Type.CIRC)) {
				return circIntersectsCirc(other);
			}

			else if (checkType(other, Type.RECT)) {
				return circIntersectsRect(other);
			}
		}

		else if (checkType(this, Type.RECT)) {

			if (checkType(other, Type.RECT)) {
				return rectIntersectsRect(other);
			}

			else if (checkType(other, Type.CIRC)) {
				return rectIntersectsCirc(other);
			}
		}

		return false;
	}

	/**
	 * Determine if an intersection occurs between two circs.
	 * 
	 * @param other
	 *            The other Bounds (assumed to be a circ)
	 * @return if intersects
	 */
	private boolean circIntersectsCirc(Bounds circ)
	{
		float distX = (this.getX() + this.getRadius()) - (circ.getX() + circ.getRadius());
		float distY = (this.getY() + this.getRadius()) - (circ.getY() + circ.getRadius());
		float distRad = this.getRadius() + circ.getRadius();
		return distX * distX + distY * distY <= distRad * distRad;
	}

	/**
	 * Determine if an intersection occurs between two rects.
	 * 
	 * @param other
	 *            The other Bounds (assumed to be a rect)
	 * @return if intersects
	 */
	private boolean rectIntersectsRect(Bounds rect)
	{
		return this.getX() < rect.getX() + rect.getWidth() && this.getX() + this.getWidth() > rect.getX()
				&& this.getY() < rect.getY() + rect.getHeight() && this.getY() + this.getHeight() > rect.getY();
	}

	/**
	 * Determine if an intersection occurs between this circ and a rect.
	 * 
	 * @param other
	 *            The other rect (assumed)
	 * @return if intersects
	 */
	private boolean circIntersectsRect(Bounds rect)
	{
		float closestX = clip(this.getCenter().getX(), rect.getX(), rect.getX() + rect.getWidth());
		float closestY = clip(this.getCenter().getY(), rect.getY(), rect.getY() + rect.getHeight());

		float distX = this.getCenter().getX() - closestX;
		float distY = this.getCenter().getY() - closestY;
		float distRad = this.getRadius();
		return distX * distX + distY * distY <= distRad * distRad;
	}

	/**
	 * Determine if an intersection occurs between this rect and a circ.
	 * 
	 * @param other
	 *            The other circ (assumed)
	 * @return if intersects
	 */
	private boolean rectIntersectsCirc(Bounds circ)
	{
		float closestX = clip(circ.getCenter().getX(), this.getX(), this.getX() + this.getWidth());
		float closestY = clip(circ.getCenter().getY(), this.getY(), this.getY() + this.getHeight());

		float distX = circ.getCenter().getX() - closestX;
		float distY = circ.getCenter().getY() - closestY;
		float distRad = circ.getRadius();
		return distX * distX + distY * distY <= distRad * distRad;
	}

	/**
	 * Used to force a value within a certain range by clipping it when it is too
	 * high or low.
	 * 
	 * @param value
	 *            The input value
	 * @param min
	 *            The minimum wanted
	 * @param max
	 *            The maximum wanted
	 * @return clippedValue
	 */
	private float clip(float value, float min, float max)
	{
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	/**
	 * Checks if the bounds is a certain specified type.
	 * 
	 * @param bounds
	 *            The bounds to be checked
	 * @param type
	 *            The type for comparison
	 * @return if same type
	 */
	public static boolean checkType(Bounds bounds, Type type)
	{
		return bounds.getType() == type;
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Set Bounds for CIRC.
	 *
	 * @param x
	 *            The x-coordinates
	 * @param y
	 *            The y-coordinates
	 * @param size
	 *            The size
	 */
	public void setCirc(Vector2D position, float size)
	{
		setPosition(position);
		setSize(size);
	}

	/**
	 * Set Bounds for RECT.
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
	public void setRect(Vector2D position, float width, float height)
	{
		setPosition(position);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Sets the 2D coordinate vector.
	 * 
	 * @param x
	 *            The x-coordinates
	 */
	public void setPosition(Vector2D position)
	{
		this.position = position;
	}

	/**
	 * Sets the 2D coordinate vector with x and y coordinates.
	 * 
	 * @param x
	 *            The x-coordinates
	 */
	public void setPosition(float x, float y)
	{
		position.setX(x);
		position.setY(y);
	}

	/**
	 * Sets the size of this Bounds.
	 * 
	 * @param size
	 *            The size
	 */
	public void setSize(float size)
	{
		setWidth(size);
		setHeight(size);
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            The width
	 */
	public void setWidth(float width)
	{
		this.width = width;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            The height
	 */
	public void setHeight(float height)
	{
		this.height = height;
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

	/**
	 * Returns the bounds type.
	 * 
	 * @return type
	 */
	public Type getType()
	{
		return type;
	}

	//
	// INHERENT METHODS
	//

	@Override
	public String toString()
	{
		if (checkType(this, Bounds.Type.RECT)) {
			return "RECT: " + position + ", width=" + width + " height=" + height;
		} else if (checkType(this, Bounds.Type.CIRC)) {
			return "CIRC: " + position + ", size=" + width;
		}
		return "Oops, what Bounds are you using?";
	}
}
