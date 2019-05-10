package com.example.entities.animations;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import com.example.utils.resource.Resource;

public class Render
{
	private float		lastRadians;
	private float		radians;

	private Resource	currentFrame;
	private Resource	rotatedFrame;

	public Render(float radians)
	{
		this.radians = radians;
		currentFrame = new Resource("flying-bonger.png");
		rotatedFrame = new Resource(currentFrame);
	}

	public Render(float radians, Resource currentFrame)
	{
		this.radians = radians;
		this.currentFrame = currentFrame;
		this.rotatedFrame = new Resource(currentFrame);
		updateRotation(currentFrame);
	}

	public void update()
	{
		if (getRadians() != getLastRadians()) { // don't rotate if don't need to
			updateRotation(currentFrame);
		}

		setLastRadians(getRadians()); // update current and prev radians
	}

	public void render(Graphics2D g, float x, float y)
	{
		if (getRotatedFrame() != null) getRotatedFrame().render(g, x, y);
	}

	public void updateRotation(Resource currentFrame)
	{
		AffineTransform details = AffineTransform.getRotateInstance(getRadians(), currentFrame.getWidth() / 2,
				currentFrame.getHeight() / 2);
		rotatedFrame.setImage(
				new AffineTransformOp(details, AffineTransformOp.TYPE_BICUBIC).filter(currentFrame.getImage(), null));
	}

	void setLastRadians(float lastRadians)
	{
		this.lastRadians = lastRadians;
	}

	public void setRadians(float radians)
	{
		this.radians = radians;
	}

	void setCurrentFrame(Resource currentFrame)
	{
		this.currentFrame = currentFrame;
	}

	public float getLastRadians()
	{
		return lastRadians;
	}

	public float getRadians()
	{
		return radians;
	}

	public Resource getCurrentFrame()
	{
		return currentFrame;
	}

	public Resource getRotatedFrame()
	{
		return rotatedFrame;
	}
}
