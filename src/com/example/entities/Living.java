package com.example.entities;

import com.example.entities.animations.Render;

/*
 * Follows pseudo-physics involving friction
 */
public class Living extends Entity
{
	// private float initialHp;
	// private float hp;
	private float	targetSpd;	// target: pseudo-terminal velocity
	private float	thrust;		// thrust: accel capabilities

	public Living(float x, float y, float width, float height, float targetSpd, float thrust, Render render)
	{
		super(x, y, width, height, render);
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
