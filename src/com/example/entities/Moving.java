package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.libs.Reference;
import com.example.libs.ReferenceRender;
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
	 * The target velocity in floating-point precision. It is used to cap the
	 * velocity of this Player to prevent it from going too fast.
	 */
	private float		targetSpd;

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
	 * @param targetSpd
	 *            The target/capped speed of this Entity.
	 * @param thrust
	 *            The thrust capabilities.
	 * @param mainRender
	 *            The main image to be rendered.
	 */
	public Moving(Vector2D position, float width, float height, float targetSpd, float thrust, BoundsType boundsType,
			Render mainRender)
	{
		super(position, width, height, boundsType, mainRender);
		velocity = new Vector2D();
		acceleration = new Vector2D();
		setTargetSpd(targetSpd);
		setThrust(thrust);
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

		updateBounds();

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
		int x = ReferenceRender.getInterpolatedX(getPosition(), velocity, interpolation);
		int y = ReferenceRender.getInterpolatedY(getPosition(), velocity, interpolation);
		getMainRender().render(g, x, y);

		if (Reference.DEBUG) {
			


			g.setColor(new Color(255, 255, 255));
			// for (Circle circ : )

			switch (getBoundsType())
			{
				case RECT_BOUNDS:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawInterpolatedRect(g, getRectBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "rectBounds", getRectBounds(), velocity, interpolation);
					break;
				}
				case CIRC_BOUNDS:
				{
					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawInterpolatedCirc(g, getCircBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "circBounds", getCircBounds(), velocity, interpolation);
					break;
				}
				case COMPLEX_CIRC_BOUNDS:
				{
//					updateComplexCircBounds();
					break;
				}
				case RECT_CIRC_BOUNDS:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawInterpolatedRect(g, getRectBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "rectBounds", getRectBounds(), velocity, interpolation);
					
					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawInterpolatedCirc(g, getCircBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "circBounds", getCircBounds(), velocity, interpolation);
					break;
				}
				case RECT_COMPLEX_BOUNDS:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawInterpolatedRect(g, getRectBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "rectBounds", getRectBounds(), velocity, interpolation);
//					updateComplexCircBounds();
					break;
				}
				case CIRC_COMPLEX_BOUNDS:
				{
					g.setColor(new Color(192, 192, 192)); // cirBounds
					ReferenceRender.drawInterpolatedCirc(g, getCircBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "circBounds", getCircBounds(), velocity, interpolation);
//					updateComplexCircBounds();
					break;
				}
				case ALL_BOUNDS:
				{
					g.setColor(new Color(128, 128, 128)); // rectBounds
					ReferenceRender.drawInterpolatedRect(g, getRectBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "rectBounds", getRectBounds(), velocity, interpolation);

					g.setColor(new Color(192, 192, 192)); // circBounds
					ReferenceRender.drawInterpolatedCirc(g, getCircBounds(), velocity, interpolation);
					ReferenceRender.drawInterpolatedString(g, "circBounds", getCircBounds(), velocity, interpolation);
//					updateComplexCircBounds();
					break;
				}
				case NO_BOUNDS:
				{
					break;
				}
			}
		}
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
	 * Sets the velocity vector.
	 * 
	 * @param velocity
	 *            The velocity vector
	 */
	public void setVelocity(Vector2D velocity)
	{
		this.velocity = velocity;
	}

	/**
	 * Sets the target speed.
	 * 
	 * @param targetSpd
	 *            The target or capped speed.
	 */
	public void setTargetSpd(float targetSpd)
	{
		this.targetSpd = targetSpd;
	}

	/**
	 * Sets the thrust.
	 * 
	 * @param thrust
	 *            The thrust capabilities.
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
	 * Returns the target speed.
	 * 
	 * @return targetSpd
	 */
	public float getTargetSpd()
	{
		return targetSpd;
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
