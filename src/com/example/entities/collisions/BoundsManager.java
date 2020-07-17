package com.example.entities.collisions;

import com.example.libs.Vector2D;

public class BoundsManager
{
	public enum BoundsType
	{
		CIRC,
		RECT,
		COMPLEX
	}

	private BoundsType	type;

	private Bounds[]	bounds;

	public BoundsManager(BoundsType type)
	{
		this.type = type;
		bounds = new Bounds[1];
		bounds[0] = new Bounds(new Vector2D(0, 0), 0, 0);
	}

	//
	// GAMELOOP METHODS
	//

	/**
	 * Updates the position of the first bounds.
	 * 
	 * @param position
	 * @param width
	 * @param height
	 */
	public void update(Vector2D position, float width, float height)
	{
		getFirst().setBounds(position, width, height);
	}

	public void updateComplex(Bounds... bounds)
	{
		this.bounds = bounds;
	}

	//
	// GENERAL METHODS
	//

	/**
	 * Determines if two BoundsManagers intersect and returns the index of the
	 * corresponding intersect or -1 if none.
	 * 
	 * TODO: Multiple intersections. ie. return an array of all indexes collided.
	 * 
	 * @param other
	 *            The other bounds
	 * @return A number corresponding to the index
	 */
	public int intersects(BoundsManager other)
	{
		Bounds thisFirst = this.getBounds()[0];
		Bounds otherFirst = other.getBounds()[0];

		switch (this.getType())
		{
			case CIRC:
			{
				switch (other.getType())
				{
					case CIRC:
						if (circCirc(thisFirst, otherFirst)) return 0;
						return -1;

					case RECT:
						if (circRect(thisFirst, otherFirst)) return 0;
						return -1;

					case COMPLEX:
						for (int i = 0; i < other.getBounds().length; i++)
							if (circCirc(thisFirst, other.getBounds()[i])) return 0;
						return -1;

					default:
						return -1;
				}
			}

			case RECT:
			{
				switch (other.getType())
				{
					case CIRC:
						if (circRect(otherFirst, thisFirst)) return 0;
						return -1;

					case RECT:
						if (rectRect(thisFirst, otherFirst)) return 0;
						return -1;

					case COMPLEX:
						for (int i = 0; i < other.getBounds().length; i++)
							if (circRect(other.getBounds()[i], thisFirst)) return 0;
						return -1;

					default:
						return -1;
				}
			}

			case COMPLEX:
			{
				switch (other.getType())
				{
					case CIRC:
						for (int i = 0; i < this.getBounds().length; i++)
							if (circCirc(this.getBounds()[i], otherFirst)) return i;
						return -1;

					case RECT:
						for (int i = 0; i < this.getBounds().length; i++)
							if (circRect(this.getBounds()[i], otherFirst)) return i;
						return -1;

					case COMPLEX:
						for (int i = 0; i < this.getBounds().length; i++)
							for (int j = 1; j < other.getBounds().length; j++)
								if (circCirc(this.getBounds()[i], other.getBounds()[j])) return i;
						return -1;

					default:
						return -1;
				}
			}

			default:
				return -1;
		}
	}

	//
	// HELPER METHODS
	//

	/**
	 * Determine if an intersection occurs between two circs.
	 * 
	 * @param other
	 *            The other Bounds (assumed to be a circ)
	 * @return if intersects
	 */
	private boolean circCirc(Bounds circ1, Bounds circ2)
	{
		float distX = (circ1.getX() + circ1.getRadius()) - (circ2.getX() + circ2.getRadius());
		float distY = (circ1.getY() + circ1.getRadius()) - (circ2.getY() + circ2.getRadius());
		float distRad = circ1.getRadius() + circ2.getRadius();
		return distX * distX + distY * distY <= distRad * distRad;
	}

	/**
	 * Determine if an intersection occurs between two rects.
	 * 
	 * @param other
	 *            The other Bounds (assumed to be a rect)
	 * @return if intersects
	 */
	private boolean rectRect(Bounds rect1, Bounds rect2)
	{
		return rect1.getX() < rect2.getX() + rect2.getWidth() && rect1.getX() + rect1.getWidth() > rect2.getX()
				&& rect1.getY() < rect2.getY() + rect2.getHeight() && rect1.getY() + rect1.getHeight() > rect2.getY();
	}

	/**
	 * Determine if an intersection occurs between this circ and a rect.
	 * 
	 * @param other
	 *            The other rect (assumed)
	 * @return if intersects
	 */
	private boolean circRect(Bounds circ, Bounds rect)
	{
		float closestX = clip(circ.getCenter().getX(), rect.getX(), rect.getX() + rect.getWidth());
		float closestY = clip(circ.getCenter().getY(), rect.getY(), rect.getY() + rect.getHeight());

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

	//
	// SETTER AND GETTER METHODS
	//

	public BoundsType getType()
	{
		return type;
	}

	public Bounds[] getBounds()
	{
		return bounds;
	}

	public Bounds getFirst()
	{
		return bounds[0];
	}

	//
	// INHERENT METHODS
	//

	@Override
	public String toString()
	{
		String result = "";
		for (Bounds b : bounds) {
			result += String.format("%s: %s\n", type, b);
		}
		return result;
	}
}
