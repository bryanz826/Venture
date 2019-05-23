package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.entities.types.ID;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Loopable;

/**
 * An Entity that can be represented on an (x,y) coordinate space, specified in
 * floating-point precision.
 * 
 * @author poroia
 */
public abstract class Entity implements Loopable
{
	//
	// FIELDS
	//

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
	 * The rotation of this Entity specified in radians.
	 */
	private float		rotation;

	/**
	 * The main image rendered that represents the main body. Calculations of
	 * rotation are based off of this.
	 */
	private Render		mainRender;

	/**
	 * The identification used to check this type of Entity.
	 */
	private ID			id;

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
	public Entity(Vector2D position, float width, float height, Render mainRender, ID id)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		setMainRender(mainRender);
		this.id = id;
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
		mainRender.update(width, height, rotation);
	}

	/**
	 * Renders the mainRender's image.
	 */
	@Override
	public void render(Graphics2D g, float interpolation)
	{
		mainRender.render(g, Math.round(position.getX()), Math.round(position.getY()));
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
	}

	/**
	 * Calculates and returns a vector representing the top-center of this Entity.
	 * 
	 * @return front
	 */
	public Vector2D getFront()
	{
		Vector2D center = getCenter();
		float x = getWidth() / 2 * (float) Math.cos(rotation) + center.getX();
		float y = getHeight() / 2 * (float) Math.sin(rotation) + center.getY();
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
	}

	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            The position of the Entity
	 */
	public void setPosition(Vector2D position)
	{
		this.position = position;
	}

	/**
	 * Sets the rotation specified in radians.
	 * 
	 * @param rotation
	 *            The rotation of this entity in radians.
	 */
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
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
		else this.mainRender = new Render();
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
	 * Returns the rotation specified in radians.
	 * 
	 * @return rotation in radians
	 */
	public float getRotation()
	{
		return rotation;
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
	 * Returns the main Render.
	 * 
	 * @return render
	 */
	public Render getMainRender()
	{
		return mainRender;
	}
	
	/**
	 * Returns the ID
	 * @return ID
	 */
	public ID getId()
	{
		return id;
	}

	//
	// INNATE METHODS
	//

	@Override
	public String toString()
	{
		return String.format("%s: pos=%s, width=%s, height=%s, rotation=%s", getClass().getSimpleName(), position,
				width, height, Math.round(Math.toDegrees(rotation)));
	}
}
