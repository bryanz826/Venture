package com.example.entities.animations;

import java.awt.Graphics2D;

public abstract class Render
{
	private float	lastRadians;
	private float	radians;

	public Render(float degrees)
	{
		this.radians = (float) (degrees * Math.PI / 180);
	}

	public abstract void update();

	public abstract void render(Graphics2D g, float x, float y);

	public abstract void render(Graphics2D g, float x, float y, float width, float height);

	public void setLastRadians(float lastRadians)
	{
		this.lastRadians = lastRadians;
	}

	public void setRadians(float radians)
	{
		this.radians = radians;
	}

	public float getLastRadians()
	{
		return lastRadians;
	}

	public float getRadians()
	{
		return radians;
	}
}
