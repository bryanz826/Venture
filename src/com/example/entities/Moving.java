package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.libs.Vector2D;

/**
 * A Moving entity that has models real world physics using velocity and
 * acceleration, resulting in smooth motion.
 * 
 * @author bryan
 *
 */
public class Moving extends Entity
{
	//
	// FIELDS
	//

	/**
	 * A vector representing this Entity's velocity.
	 */
	private Vector2D	velocity;

	/**
	 * A vector representing this Entity's acceleration.
	 */
	private Vector2D	acceleration;

	/**
	 * The self acceleration capabilities upon movement in floating-point precision.
	 */
	private float		thrust;

	//
	// CONSTRUCTORS
	//

	/**
	 * Constructs an Moving object that can move.
	 * 
	 * @param position
	 *            The vector position of this Entity.
	 * @param width
	 *            The width of this Entity.
	 * @param height
	 *            The height of this Entity.
	 * @param thrust
	 *            The thrust capabilities.
	 * @param mainRender
	 *            The main image to be rendered.
	 */
	public Moving(Vector2D position, float width, float height, float thrust, Render mainRender)
	{
		super(position, width, height, mainRender);
		velocity = new Vector2D();
		acceleration = new Vector2D();
		setThrust(1 / (float) Math.E);
	}

	//
	// GAMELOOP METHODS
	//

	/**
	 * Updates the motion of Moving and the mainRender's rotation
	 */
	@Override
	public void update()
	{
		super.update();

		calcAcceleration();
		getVelocity().add(getAcceleration());

		calcVelocity();
		getPosition().add(getVelocity());
	}

	/**
	 * Renders the mainRender's image and interpolates.
	 */
	@Override
	public void render(Graphics2D g, float interpolation)
	{
		float x = getPosition().getX() + interpolation * getVelocity().getX();
		float y = getPosition().getY() + interpolation * getVelocity().getY();
		getMainRender().render(g, x, y);
	}

	//
	// HELPER METHODS
	//

	/**
	 * Calculates the acceleration of this Moving entity.
	 */
	protected void calcAcceleration()
	{
		float aLength = getAcceleration().getExactLength();
		if (aLength > 1) { // limit to circular movement
			getAcceleration().quickNormalize(aLength);
		}
		getAcceleration().scale(getThrust());
	}

	/**
	 * Calculates the velocity of this Moving entity.
	 */
	protected void calcVelocity()
	{}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Sets the thrust.
	 * 
	 * @param thrust
	 * The thrust capabilities.
	 */
	public void setThrust(float thrust)
	{
		this.thrust = thrust;
	}

	/**
	 * Returns the velocity vector.
	 * 
	 * @return velocity
	 */
	public Vector2D getVelocity()
	{
		return velocity;
	}

	/**
	 * Returns the acceleration vector.
	 * 
	 * @return acceleration
	 */
	public Vector2D getAcceleration()
	{
		return acceleration;
	}

	/**
	 * Returns the thrust capabilities.
	 * 
	 * @return thrust
	 */
	public float getThrust()
	{
		return thrust;
	}
}
