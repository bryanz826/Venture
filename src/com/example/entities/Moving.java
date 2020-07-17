package com.example.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.BoundsManager;
import com.example.entities.collisions.BoundsManager.BoundsType;
import com.example.entities.types.ID;
import com.example.refs.Reference;
import com.example.refs.ReferenceRender;
import com.example.refs.Vector2D;

/**
 * A Moving entity that has models real world physics using velocity and
 * acceleration, resulting in smooth motion.
 * 
 * @author poroia
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
	private Vector2D		velocity;

	/**
	 * A vector representing this Entity's acceleration.
	 */
	private Vector2D		acceleration;

	/**
	 * The target velocity in floating-point precision. It is used to cap the
	 * velocity of this Moving entity to prevent it from going too fast.
	 */
	private float			targetSpd;

	/**
	 * The self acceleration capabilities upon movement in floating-point precision.
	 */
	private float			thrust;

	/**
	 * The mass of this Moving entity. Basically the area approximation.
	 */
	private float			mass;

	/**
	 * A BoundsManager representing this object's bounds.
	 */
	private BoundsManager	bm;

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
	public Moving(Vector2D position, float width, float height, float targetSpd, float thrust, BoundsType type,
			Render mainRender, ID id)
	{
		super(position, width, height, mainRender, id);
		velocity = new Vector2D();
		acceleration = new Vector2D();
		setTargetSpd(targetSpd);
		setThrust(thrust);
		this.bm = new BoundsManager(type);
		
		if (bm.getBounds().length == 1) {
			bm.update(position, width, height);
			setMass(bm.getBounds());
		}
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
		setVelocity(Vector2D.add(getVelocity(), getAcceleration()));

		calcVelocity();
		setPosition(Vector2D.add(getPosition(), getVelocity()));

		bm.update(getPosition(), getWidth(), getHeight());
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

		if (Reference.DEBUG) renderBounds(g, interpolation);
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
		setAcceleration(Vector2D.getScaled(getAcceleration(), getThrust()));
	}

	/**
	 * Calculates the velocity of this Moving entity.
	 */
	protected void calcVelocity()
	{}

	/**
	 * FOR DEBUG PURPOSES ONLY.
	 * 
	 * @param g
	 *            Graphics2D
	 * @param interpolation
	 *            Interpolation for smoother rendering.
	 */
	public void renderBounds(Graphics2D g, float interpolation)
	{
		switch (bm.getType())
		{
			case CIRC:
			{
				g.setColor(new Color(192, 192, 192)); // circBounds
				ReferenceRender.drawInterpolatedCirc(g, bm.getFirst(), velocity, interpolation);
				ReferenceRender.drawInterpolatedString(g, "circBounds", bm, velocity, interpolation);
				break;
			}

			case RECT:
			{
				g.setColor(new Color(128, 128, 128)); // rectBounds
				ReferenceRender.drawInterpolatedRect(g, bm.getFirst(), velocity, interpolation);
				ReferenceRender.drawInterpolatedString(g, "rectBounds", bm, velocity, interpolation);
				break;
			}

			case COMPLEX:
			{
				g.setColor(new Color(255, 255, 255));
				g.setStroke(new BasicStroke(2));
				for (int i = 0; i < bm.getBounds().length; i++)
					ReferenceRender.drawInterpolatedCirc(g, bm.getBounds()[i], velocity, interpolation);
				break;
			}

			default:
				break;
		}
	}

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
	 * Sets the acceleration vector.
	 * 
	 * @param acceleration
	 *            The acceleration vector
	 */
	public void setAcceleration(Vector2D acceleration)
	{
		this.acceleration = acceleration;
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
	 * Takes all the bounds and gets the total area of them and sets them to this
	 * mass.
	 * 
	 * @param bounds
	 *            The bounds for calculations
	 */
	public void setMass(Bounds[] bounds)
	{
		float mass = 0;
		for (Bounds b : bounds) {
			mass += b.getOvalArea();
		}
		this.mass = mass;
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

	/**
	 * Returns the mass.
	 * 
	 * @return mass
	 */
	public float getMass()
	{
		return mass;
	}

	/**
	 * Returns the BoundsManager containing the bounds.
	 * 
	 * @return bm
	 */
	public BoundsManager getBm()
	{
		return bm;
	}
}
