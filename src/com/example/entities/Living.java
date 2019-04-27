package com.example.entities;

/*
 * Follows pseudo-physics involving friction
 */
public class Living extends Entity
{
	private float	targetSpd;	// target: pseudo-terminal velocity
	private float	thrust;		// thrust: accel capabilities

	public Living(float x, float y, float width, float height, float targetSpd, float thrust)
	{
		super(x, y, width, height);
		setTargetSpd(targetSpd);
		setThrust(thrust);
	}

	public void setTargetSpd(float targetSpd)
	{
		this.targetSpd = targetSpd;
	}

	public void setThrust(float thrust)
	{
		this.thrust = thrust;
	}

	public float getTargetSpd()
	{
		return targetSpd;
	}

	public float getThrust()
	{
		return thrust;
	}
}
