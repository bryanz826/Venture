package com.example.entities.animations;

import java.awt.Graphics2D;

import com.example.utils.resource.Resource;

public class MovingAnimatedRender extends AnimatedRender
{
	public MovingAnimatedRender(int speed, Resource... frames)
	{
		super(speed, frames);
	}

	public MovingAnimatedRender(float degrees, int speed, Resource... frames)
	{
		super(degrees, speed, frames);
	}

	public MovingAnimatedRender(float degrees, float rotationCenterX, float rotationCenterY, int speed, Resource... frames)
	{
		super(degrees, rotationCenterX, rotationCenterY, speed, frames);
	}

	public void render(Graphics2D g, float x, float y)
	{
		if (getCurrentFrame() != null) getCurrentFrame().render(g, x, y);
	}

	public void render(Graphics2D g, float x, float y, float width, float height) // for resizing
	{
		if (getCurrentFrame() != null) getCurrentFrame().render(g, x, y, width, height);
	}
}
