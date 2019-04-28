package com.example.entities.animations;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import com.example.utils.resource.Resource;

public abstract class AnimatedRender extends Render
{
	private Resource	currentFrame;		// current frame reference of animation
	private Resource[]	frames;				// array of frames

	private int			index;				// index of current frame of Resource[] frames
	private int			speed;				// speed of animation (0 is fastest w/ 60 frames per second)
	private int			tick;				// keeps track of updates
	
	private float		rotationCenterX;
	private float		rotationCenterY;

	private boolean		oneCycleComplete;	// true when one cycle of frames is complete

	public AnimatedRender(int speed, Resource... frames)
	{
		this(0, speed, frames);
	}

	public AnimatedRender(float degrees, int speed, Resource... frames)
	{
		this(degrees, frames[0].getWidth() / 2, frames[0].getHeight() / 2, speed, frames);
	}

	public AnimatedRender(float degrees, float rotationCenterX, float rotationCenterY, int speed, Resource... frames)
	{
		super(degrees);
		this.speed = speed;
		this.frames = frames;
		this.rotationCenterX = rotationCenterX;
		this.rotationCenterY = rotationCenterY;
		oneCycleComplete = false;
	}

	private void nextFrame() // get frame and increment index to prepare next frame
	{
		currentFrame = frames[index];
		if (++index >= frames.length) {
			oneCycleComplete = true;
			index = 0;
		}
	}

	public void update()
	{
		tick++;
		if (speed == 0 && getRadians() != getLastRadians()) { // rotate every update and sidestep divide by zero
			rotate(currentFrame);
		} else if (tick % speed == 1 && getRadians() != getLastRadians()) { // and don't rotate if don't need to
			rotate(currentFrame);
		}
		
		if (tick > speed) {
			nextFrame();
			tick = 0;
		}
		
		setLastRadians(getRadians()); // update current and prev radians
	}

	public void rotate(Resource frame)
	{
		AffineTransform details = AffineTransform.getRotateInstance(getRadians(), rotationCenterX, rotationCenterY);
		frame.setImage(new AffineTransformOp(details, AffineTransformOp.TYPE_BILINEAR).filter(frame.getImage(), null));
	}

	public void rotateAll()
	{
		for (Resource frame : frames) {
			rotate(frame);
		}
	}

	public void setDegrees(float degrees)
	{
		float radians = (float) (degrees * Math.PI / 180);
		setRadians(radians);
	}

	public Resource getCurrentFrame()
	{
		return currentFrame;
	}

	public boolean isOneCycleComplete()
	{
		return oneCycleComplete;
	}
}
