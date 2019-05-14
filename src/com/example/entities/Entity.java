package com.example.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.entities.animations.Render;
import com.example.entities.collisions.Circle;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Loopable;

/**
 * An Entity that can be represented on an (x,y) coordinate space, specified in
 * floating-point precision.
 * 
 * @author poroia
 */
public class Entity implements Loopable
{
	//
	// FIELDS
	//

	/**
	 * All bounds updating types. Used to determine how bounds should be updated.
	 */
	public enum BoundsType
	{
		NO_BOUNDS,
		RECT_BOUNDS,
		CIRC_BOUNDS,
		COMPLEX_CIRC_BOUNDS,
		RECT_CIRC_BOUNDS,
		RECT_COMPLEX_BOUNDS,
		CIRC_COMPLEX_BOUNDS,
		ALL_BOUNDS
	}

	/**
	 * The width of this Entity.
	 */
	private float		width;

	/**
	 * The height of this Entity.
	 */
	private float		height;

	/**
	 * A vector representing the this Entity's location in the coordinate space.
	 */
	private Vector2D	position;

	/**
	 * The bounds of the Entity represented by a rectangle.
	 */
	private Rectangle	rectBounds;

	/**
	 * The bounds of the Entity represented by a circle.
	 */
	private Circle		circBounds;

	/**
	 * The bounds of the Entity represented by many circles and must be initialized
	 * if it is used. If not initialized, then uses circBounds.
	 */
	private Circle[]	complexCircBounds;

	/**
	 * Refers to bound updating type. See final fields for more information.
	 */
	private BoundsType	boundsType;

	/**
	 * The main image rendered that represents the main body. Calculations of
	 * rotation are based off of this.
	 */
	private Render		mainRender;

	//
	// CONSTRUCTORS
	//

	/**
	 * Constructs an Entity object that cannot move.
	 * 
	 * @param position
	 *            The vector position of this Entity.
	 * @param width
	 *            The width of this Entity.
	 * @param height
	 *            The height of this Entity.
	 * @param mainRender
	 *            The main image to be rendered.
	 */
	public Entity(Vector2D position, float width, float height, BoundsType boundsType, Render mainRender)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		setBoundsType(boundsType);
		setMainRender(mainRender);
		updateBounds();
	}

	//
	// GAMELOOP METHODS
	//

	/**
	 * Updates the mainRender's rotation.
	 */
	@Override
	public void update()
	{
		getMainRender().update();
	}

	/**
	 * Renders the mainRender's image.
	 */
	@Override
	public void render(Graphics2D g, float interpolation)
	{
		getMainRender().render(g, Math.round(position.getX()), Math.round(position.getY()));
	}

	//
	// GENERAL METHODS
	//

	/**
	 * Repositions the Entity based off of an offset.
	 * 
	 * @param offset
	 *            The amount to be subtracted.
	 */
	public void reposition(float offset)
	{
		getPosition().reposition(offset);
		updateBounds();
	}

	/**
	 * Calculates and returns a vector representing the top-center of this Entity.
	 * 
	 * @return front
	 */
	public Vector2D getFront()
	{
		Vector2D center = getCenter();
		float x = getHeight() / 2 * (float) Math.cos(mainRender.getRadians()) + center.getX();
		float y = getHeight() / 2 * (float) Math.sin(mainRender.getRadians()) + center.getY();
		return new Vector2D(x, y);
	}

	/**
	 * Calculates and returns a vector representing the absolute center of Entity.
	 * 
	 * @return center
	 */
	public Vector2D getCenter()
	{
		float x = position.getX() + width / 2;
		float y = position.getY() + height / 2;
		return new Vector2D(x, y);
	}

	/**
	 * Updates all the bounds.
	 */
	public void updateBounds()
	{
		switch (boundsType)
		{
			case RECT_BOUNDS:
			{
				updateRectBounds();
				break;
			}
			case CIRC_BOUNDS:
			{
				updateCircBounds();
				break;
			}
			case COMPLEX_CIRC_BOUNDS:
			{
				updateComplexCircBounds();
				break;
			}
			case RECT_CIRC_BOUNDS:
			{
				updateRectBounds();
				updateCircBounds();
				break;
			}
			case RECT_COMPLEX_BOUNDS:
			{
				updateRectBounds();
				updateComplexCircBounds();
				break;
			}
			case CIRC_COMPLEX_BOUNDS:
			{
				updateCircBounds();
				updateComplexCircBounds();
				break;
			}
			case ALL_BOUNDS:
			{
				updateRectBounds();
				updateCircBounds();
				updateComplexCircBounds();
				break;
			}
			case NO_BOUNDS:
			{
				break;
			}
		}
	}

	/**
	 * Updates the rectBounds.
	 */
	public void updateRectBounds()
	{
		rectBounds = new Rectangle(Math.round(position.getX()), Math.round(position.getY()), Math.round(width),
				Math.round(height));
	}

	/**
	 * Updates the circBounds.
	 */
	public void updateCircBounds()
	{
		circBounds = new Circle(position.getX(), position.getY(), width);
	}

	/**
	 * Updates the complexCircBounds.
	 */
	public void updateComplexCircBounds()
	{
//		for (Circle circ : complexCircBounds) {
//			
//		}
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            The width of the Entity.
	 */
	public void setWidth(float width)
	{
		this.width = width;
		updateBounds();
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *            The height of the Entity.
	 */
	public void setHeight(float height)
	{
		this.height = height;
		updateBounds(); // used for entity where no constant bounds check
	}

	/**
	 * Sets the boundsType.
	 * 
	 * @param boundsType
	 *            The type of bounds used.
	 */
	public void setBoundsType(BoundsType boundsType)
	{
		this.boundsType = boundsType;
	}

	/**
	 * Sets the main Render.
	 * 
	 * @param mainRender
	 *            The main image associated with the body of the Entity.
	 */
	public void setMainRender(Render mainRender)
	{
		this.mainRender = mainRender;
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
	 * Returns the position vector.
	 * 
	 * @return position
	 */
	public Vector2D getPosition()
	{
		return position;
	}

	/**
	 * Returns the bounds represented by a rectangle.
	 * 
	 * @return rectBounds
	 */
	public Rectangle getRectBounds()
	{
		return rectBounds;
	}

	/**
	 * Returns the bounds represented by a circle.
	 * 
	 * @return circBounds
	 */
	public Circle getCircBounds()
	{
		return circBounds;
	}

	/**
	 * Returns the bounds represented by many circles specified.
	 * 
	 * @return complexCircBounds
	 */
	public Circle[] getComplexCircBounds()
	{
		return complexCircBounds;
	}

	/**
	 * Returns the boundsType
	 * 
	 * @return boundsType
	 */
	public BoundsType getBoundsType()
	{
		return boundsType;
	}

	/**
	 * Returns the main Render.
	 * 
	 * @return render
	 */
	public Render getMainRender()
	{
		return mainRender;
	}
}
