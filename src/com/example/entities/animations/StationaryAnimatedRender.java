package com.example.entities.animations;

import java.awt.Graphics2D;

import com.example.utils.resource.Resource;

public class StationaryAnimatedRender extends AnimatedRender
{
	private float	x;
	private float	y;
	private float	width;
	private float	height;

	public StationaryAnimatedRender(float x, float y, float width, float height, int speed, Resource... frames)
	{
		this(x, y, width, height, 0, speed, frames);
	}

	public StationaryAnimatedRender(float x, float y, float width, float height, float degrees, int speed,
			Resource... frames)
	{
		super(degrees, speed, frames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public StationaryAnimatedRender(float x, float y, float width, float height, float rotationCenterX,
			float rotationCenterY, float degrees, int speed, Resource... frames)
	{
		super(degrees, rotationCenterX, rotationCenterY, speed, frames);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics2D g, float x, float y) // since I'm bad at coding, ignore parameters
	{
		if (getCurrentFrame() != null) getCurrentFrame().render(g, this.x, this.y);
	}

	@Override
	public void render(Graphics2D g, float x, float y, float width, float height)
	{
		if (getCurrentFrame() != null) getCurrentFrame().render(g, this.x, this.y, this.width, this.height);
	}
}
