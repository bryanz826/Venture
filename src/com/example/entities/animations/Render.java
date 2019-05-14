package com.example.entities.animations;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.example.utils.resource.Resource;

public class Render
{
	private float		radians;
	private float		lastRadians;

	private float		width;
	private float		height;
	private float		lastWidth;
	private float		lastHeight;

	private Resource	currentFrame;
	private Resource	scaledFrame;
	private Resource	rotatedFrame;

	public Render(float radians)
	{
		setRadians(radians);
		setLastRadians(radians);
		setCurrentFrame(new Resource("flying-bonger.png"));
		scaledFrame = new Resource(this.currentFrame);
		rotatedFrame = new Resource(this.scaledFrame);
		updateRotation();
	}

	public Render(float radians, Resource currentFrame)
	{
		setRadians(radians);
		setLastRadians(radians);
		setCurrentFrame(currentFrame);
		scaledFrame = new Resource(this.currentFrame);
		rotatedFrame = new Resource(this.scaledFrame);
		updateRotation();
	}

	public Render(float radians, float width, float height, Resource currentFrame)
	{
		setRadians(radians);
		setLastRadians(radians);
		setCurrentFrame(currentFrame);
		setWidth(width);
		setLastWidth(width);
		setHeight(height);
		setLastHeight(height);
		scaledFrame = new Resource(this.currentFrame);
		rotatedFrame = new Resource(this.scaledFrame);
		updateScale();
		updateRotation();
	}

	public void update()
	{
		if (width != 0 && height != 0) {
			if (getWidth() != getLastWidth() && getHeight() != getLastHeight()) {
				updateScale();
			}
			setLastWidth(getWidth());
			setLastHeight(getHeight());
		}
		
		if (getRadians() != getLastRadians()) { // don't rotate if don't need to
			updateRotation();
		}
		setLastRadians(getRadians()); // update current and prev radians
	}

	public void render(Graphics2D g, float x, float y)
	{
		if (getRotatedFrame() != null) getRotatedFrame().render(g, x, y);
	}

	public void updateScale()
	{
		BufferedImage resizedImage = new BufferedImage(Math.round(width), Math.round(height),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(currentFrame.getImage(), 0, 0, Math.round(width), Math.round(height), null);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.dispose();

		scaledFrame.setImage(resizedImage);
	}

	public void updateRotation()
	{
		AffineTransform details = AffineTransform.getRotateInstance(getRadians(), scaledFrame.getWidth() / 2,
				scaledFrame.getHeight() / 2);
		rotatedFrame.setImage(
				new AffineTransformOp(details, AffineTransformOp.TYPE_BICUBIC).filter(scaledFrame.getImage(), null));
	}

	public void setRadians(float radians)
	{
		this.radians = radians;
	}

	void setLastRadians(float lastRadians)
	{
		this.lastRadians = lastRadians;
	}

	public void setSize(float size)
	{
		setWidth(size);
		setHeight(size);
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	void setLastWidth(float lastWidth)
	{
		this.lastWidth = lastWidth;
	}

	void setLastHeight(float lastHeight)
	{
		this.lastHeight = lastHeight;
	}

	void setCurrentFrame(Resource currentFrame)
	{
		this.currentFrame = currentFrame;
	}

	public float getRadians()
	{
		return radians;
	}

	float getLastRadians()
	{
		return lastRadians;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	float getLastWidth()
	{
		return lastWidth;
	}

	float getLastHeight()
	{
		return lastHeight;
	}

	public Resource getRotatedFrame()
	{
		return rotatedFrame;
	}
}
