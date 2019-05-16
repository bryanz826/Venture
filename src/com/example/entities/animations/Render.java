package com.example.entities.animations;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.example.utils.resource.Resource;

public class Render
{
	private float		lastRotation;
	private float		lastWidth;
	private float		lastHeight;

	private Resource	currentFrame;
	private Resource	scaledFrame;
	private Resource	rotatedFrame;

	public Render()//, float radians)
	{
		this(new Resource("flying-bonger.png"));
	}

	public Render(Resource currentFrame)
	{
		setCurrentFrame(currentFrame);
		scaledFrame = new Resource(this.currentFrame);
		rotatedFrame = new Resource(this.scaledFrame);
	}

	public void update(float width, float height, float rotation)
	{
		if (width != getLastWidth() && height != getLastHeight()) {
			updateScale(width, height);
			updateRotation(rotation);
		}
		setLastWidth(width);
		setLastHeight(height);

		if (rotation != getLastRotation()) { // don't rotate if don't need to
			updateRotation(rotation);
		}
		setLastRotation(rotation); // update current and prev rotation
	}

	public void render(Graphics2D g, float x, float y)
	{
		if (getRotatedFrame() != null) getRotatedFrame().render(g, x, y);
	}

	void updateScale(float width, float height)
	{
		BufferedImage resizedImage = new BufferedImage(Math.round(width), Math.round(height),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(currentFrame.getImage(), 0, 0, Math.round(width), Math.round(height), null);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.dispose();
		
		scaledFrame.setImage(resizedImage);
	}

	void updateRotation(float rotation)
	{
		AffineTransform details = AffineTransform.getRotateInstance(rotation, scaledFrame.getWidth() / 2,
				scaledFrame.getHeight() / 2);
		rotatedFrame.setImage(
				new AffineTransformOp(details, AffineTransformOp.TYPE_BICUBIC).filter(scaledFrame.getImage(), null));
	}

	void setLastRotation(float lastRotation)
	{
		this.lastRotation = lastRotation;
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

	float getLastRotation()
	{
		return lastRotation;
	}

	public float getLastWidth()
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
