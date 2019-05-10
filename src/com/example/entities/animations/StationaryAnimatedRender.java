package com.example.entities.animations;

import java.awt.Graphics2D;

import com.example.utils.resource.Resource;

public class StationaryAnimatedRender extends AnimatedRender
{
	private float	x;
	private float	y;

	public StationaryAnimatedRender(float x, float y, int speed, Resource... frames)
	{
		this(x, y, 0, speed, frames);
	}

	public StationaryAnimatedRender(float x, float y, float radians, int speed,
			Resource... frames)
	{
		super(radians, speed, frames);
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(Graphics2D g, float x, float y) // since I'm bad at coding, ignore parameters
	{
		if (getRotatedFrame() != null) getRotatedFrame().render(g, this.x, this.y);
	}
}
