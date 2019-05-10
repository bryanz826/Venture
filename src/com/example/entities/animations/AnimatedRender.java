package com.example.entities.animations;

import com.example.utils.resource.Resource;

public class AnimatedRender extends Render
{
	private Resource[]	frames;				// array of frames

	private int			index;				// index of current frame of Resource[] frames
	private int			speed;				// speed of animation (0 is fastest w/ 60 frames per second)
	private int			tick;				// keeps track of updates

	private boolean		oneCycleComplete;	// true when one cycle of frames is complete

	public AnimatedRender(int speed, Resource... frames)
	{
		this(0, speed, frames);
	}

	public AnimatedRender(float radians, int speed, Resource... frames)
	{
		super(radians);
		this.speed = speed;
		this.frames = frames;
		oneCycleComplete = false;
	}

	private void nextFrame() // get frame and increment index to prepare next frame
	{
		setCurrentFrame(frames[index]);
		if (++index >= frames.length) {
			oneCycleComplete = true;
			index = 0;
		}
	}

	@Override
	public void update()
	{
		tick++;
		if (speed == 0 && getRadians() != getLastRadians()) { // rotate every update and sidestep divide by zero
			updateRotation(getCurrentFrame());
		} else if (tick % speed == 1 && getRadians() != getLastRadians()) { // and don't rotate if don't need to
			updateRotation(getCurrentFrame());
		}
		
		if (tick > speed) {
			nextFrame();
			tick = 0;
		}
		
		setLastRadians(getRadians()); // update current and prev radians
	}

	public boolean isOneCycleComplete()
	{
		return oneCycleComplete;
	}
}
