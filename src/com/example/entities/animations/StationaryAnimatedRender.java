package com.example.entities.animations;

import java.awt.Graphics2D;

import com.example.refs.Vector2D;
import com.example.utils.resource.Resource;

/**
 * Creates an animated render that extracts frames from a spritesheet to create
 * animations on the specified coordinates.
 * 
 * @author poroia
 */
public class StationaryAnimatedRender extends AnimatedRender
{
	private Vector2D position;

	public StationaryAnimatedRender(Vector2D position, int speed,
			Resource... frames)
	{
		super(speed, frames);
		this.position = position;
	}

	@Override
	public void render(Graphics2D g, float x, float y) // since I'm bad at coding, ignore parameters
	{
		if (getRotatedFrame() != null) getRotatedFrame().render(g, position.getX(), position.getY());
	}
}
