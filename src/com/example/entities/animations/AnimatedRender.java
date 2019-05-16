package com.example.entities.animations;

import com.example.utils.resource.Resource;

/**
 * Creates an animated render that extracts frames from a spritesheet to create
 * animations, and follows the movement of the object it is on.
 * 
 * @author poroia
 */
public class AnimatedRender extends Render
{
	/**
	 * Array of Resource frames holding individual spritesheet images.
	 */
	private Resource[]	frames;

	private int			index;				// index of current frame of Resource[] frames
	private int			speed;				// speed of animation (0 is fastest w/ 60 frames per second)
	private int			tick;				// keeps track of updates

	private boolean		oneCycleComplete;	// true when one cycle of frames is complete

	public AnimatedRender(int speed, Resource... frames)
	{
		super();
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
	public void update(float width, float height, float rotation)
	{
		tick++;

		if (speed == 0 && width != getLastWidth() && height != getLastHeight()) {
			updateScale(width, height);
		} else if (tick % speed == 1 && width != getLastWidth() && height != getLastHeight()) {
			updateScale(width, height);
		}
		setLastWidth(width);
		setLastHeight(height);

		if (speed == 0 && rotation != getLastRotation()) { // rotate every update and sidestep divide by zero
			updateRotation(rotation);
		} else if (tick % speed == 1 && rotation != getLastRotation()) { // and don't rotate if don't need to
			updateRotation(rotation);
		}
		setLastRotation(rotation); // update current and prev radians

		if (tick > speed) {
			nextFrame();
			tick = 0;
		}

	}

	public boolean isOneCycleComplete()
	{
		return oneCycleComplete;
	}
}
