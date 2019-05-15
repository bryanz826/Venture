package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.entities.collisions.Bounds;
import com.example.libs.Reference;
import com.example.libs.ReferenceRender;
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
	/**
	 * All bounds updating types. Used to determine how bounds should be updated.
	 */
	public enum BoundsCombination
	{
		NONE,
		RECT,
		CIRC,
		COMCIRC,
		RECT_CIRC,
		RECT_COMCIRC,
		CIRC_COMCIRC,
		ALL
	}

	//
	// FIELDS
	//

	/**
	 * The width of this Entity.
	 */
	private float				width;

	/**
	 * The height of this Entity.
	 */
	private float				height;

	/**
	 * A vector representing the this Entity's location in the coordinate space.
	 */
	private Vector2D			position;

	/**
	 * The bounds of the Entity represented by a rectangle.
	 */
	private Bounds				rect;

	/**
	 * The bounds of the Entity represented by a circle.
	 */
	private Bounds				circ;

	/**
	 * The bounds of the Entity represented by many circles and must be initialized
	 * if it is used. If not initialized, then uses circBounds.
	 */
	private Bounds[]			comCirc;

	/**
	 * Refers to bound updating type. See class enum for more information.
	 */
	private BoundsCombination	boundsCombination;

	/**
	 * The main image rendered that represents the main body. Calculations of
	 * rotation are based off of this.
	 */
	private Render				mainRender;

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
	public Entity(Vector2D position, float width, float height, BoundsCombination boundsCombination, Render mainRender)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		setBoundsCombination(boundsCombination);
		setMainRender(mainRender);

		this.rect = new Bounds(position.getX(), position.getY(), width, height);
		this.circ = new Bounds(position.getX(), position.getY(), width);
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

		if (Reference.DEBUG) {
			switch (getBoundsUpdateType())
			{
				case RECT:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawRect(g, getRect());
					ReferenceRender.drawString(g, "rectBounds", getRect());
					break;
				}
				case CIRC:
				{
					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawCirc(g, getCirc());
					ReferenceRender.drawString(g, "circBounds", getCirc());
					break;
				}
				case COMCIRC:
				{
					g.setColor(new Color(255, 255, 255));
					// updateComplexCircBounds();
					break;
				}
				case RECT_CIRC:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawRect(g, getRect());
					ReferenceRender.drawString(g, "rectBounds", getRect());

					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawCirc(g, getCirc());
					ReferenceRender.drawString(g, "circBounds", getCirc());
					break;
				}
				case RECT_COMCIRC:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawRect(g, getRect());
					ReferenceRender.drawString(g, "rectBounds", getRect());

					g.setColor(new Color(255, 255, 255));
					// updateComplexCircBounds();
					break;
				}
				case CIRC_COMCIRC:
				{
					g.setColor(new Color(192, 192, 192)); // cirBounds
					ReferenceRender.drawCirc(g, getCirc());
					ReferenceRender.drawString(g, "circBounds", getCirc());

					g.setColor(new Color(255, 255, 255));
					// updateComplexCircBounds();
					break;
				}
				case ALL:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawRect(g, getRect());
					ReferenceRender.drawString(g, "rectBounds", getRect());

					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawCirc(g, getCirc());
					ReferenceRender.drawString(g, "circBounds", getCirc());

					g.setColor(new Color(255, 255, 255));
					// updateComplexCircBounds();
					break;
				}
				case NONE:
				{
					break;
				}
			}
		}
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
	 * Updates all the bounds depending on the BoundsType.
	 */
	public void updateBounds()
	{
		switch (boundsCombination)
		{
			case RECT:
			{
				updateRect();
				break;
			}
			case CIRC:
			{
				updateCirc();
				break;
			}
			case COMCIRC:
			{
				updateComCirc();
				break;
			}
			case RECT_CIRC:
			{
				updateRect();
				updateCirc();
				break;
			}
			case RECT_COMCIRC:
			{
				updateRect();
				updateComCirc();
				break;
			}
			case CIRC_COMCIRC:
			{
				updateCirc();
				updateComCirc();
				break;
			}
			case ALL:
			{
				updateRect();
				updateCirc();
				updateComCirc();
				break;
			}
			case NONE:
			{
				break;
			}
		}
	}

	/**
	 * Updates the rect.
	 */
	public void updateRect()
	{
		rect.setRect(position.getX(), position.getY(), width, height);
	}

	/**
	 * Updates the circ.
	 */
	public void updateCirc()
	{
		circ.setCirc(position.getX(), position.getY(), width);
	}

	/**
	 * Updates the comCirc.
	 */
	public void updateComCirc()
	{
		// for (Circle circ : complexCircBounds) {
		//
		// }
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
	 * Sets the boundsCombination.
	 * 
	 * @param boundsCombination
	 *            The type of bounds combination used.
	 */
	public void setBoundsCombination(BoundsCombination boundsCombination)
	{
		this.boundsCombination = boundsCombination;
	}

	/**
	 * Sets the main Render.
	 * 
	 * @param mainRender
	 *            The main image associated with the body of the Entity.
	 */
	public void setMainRender(Render mainRender)
	{
		if (mainRender != null) this.mainRender = mainRender;
		else this.mainRender = new Render(0, width, height);
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
	 * Returns the Bounds represented by a rectangle.
	 * 
	 * @return rect
	 */
	public Bounds getRect()
	{
		return rect;
	}

	/**
	 * Returns the Bounds represented by a circle.
	 * 
	 * @return circ
	 */
	public Bounds getCirc()
	{
		return circ;
	}

	/**
	 * Returns the Bounds represented by many circles specified.
	 * 
	 * @return comCirc
	 */
	public Bounds[] getComCirc()
	{
		return comCirc;
	}

	/**
	 * Returns the boundsBoundsCombination
	 * 
	 * @return boundsBoundsCombinations
	 */
	public BoundsCombination getBoundsUpdateType()
	{
		return boundsCombination;
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
