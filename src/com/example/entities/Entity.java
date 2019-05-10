package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Loopable;

/**
 * An Entity that can be represented on an (x,y) coordinate space, specified in
 * floating-point precision.
 * 
 * @author poroia
 *
 */
public class Entity implements Loopable
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
	public Entity(Vector2D position, float width, float height, Render mainRender)
	{
		this.position = position;
		setWidth(width);
		setHeight(height);
		setMainRender(mainRender);
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
	 * Returns the main Render.
	 * 
	 * @return render
	 */
	public Render getMainRender()
	{
		return mainRender;
	}
}
