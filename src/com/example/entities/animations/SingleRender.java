package com.example.entities.animations;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import com.example.utils.resource.Resource;

public class SingleRender extends Render
{
	private Resource	frame;
	private float		rotationCenterX;
	private float		rotationCenterY;

	public SingleRender(float degrees, Resource frame)
	{
		this(degrees, frame.getWidth() / 2, frame.getHeight() / 2, frame);
	}

	public SingleRender(float degrees, float rotationCenterX, float rotationCenterY, Resource frame)
	{
		super(degrees);
		this.rotationCenterX = rotationCenterX;
		this.rotationCenterY = rotationCenterY;
		this.frame = frame;
	}

	public void update()
	{
		if (getRadians() != getLastRadians()) { // don't rotate if don't need to
			rotate(frame);
		}

		setLastRadians(getRadians()); // update current and prev radians
	}

	public void rotate(Resource frame)
	{
		AffineTransform details = AffineTransform.getRotateInstance(getRadians(), rotationCenterX, rotationCenterY);
		frame.setImage(new AffineTransformOp(details, AffineTransformOp.TYPE_BILINEAR).filter(frame.getImage(), null));
	}

	public void setDegrees(float degrees)
	{
		float radians = (float) (degrees * Math.PI / 180);
		setRadians(radians);
	}

	public void render(Graphics2D g, float x, float y)
	{
		if (frame != null) frame.render(g, x, y);
	}

	public void render(Graphics2D g, float x, float y, float width, float height) // for resizing
	{
		if (frame != null) frame.render(g, x, y, width, height);
	}
}
