package com.example.utils.timing;

import com.example.main.ReferenceConfig;

public class TimeDuration
{
	private float	time;
	private float	timeIncrement;

	public TimeDuration(float seconds)
	{
		time = 0;
		timeIncrement = 1f / (seconds * ReferenceConfig.TARGET_UPS);
	}

	public void update()
	{
		time += timeIncrement;
	}

	public boolean isComplete()
	{
		return time > 1;
	}
	
	public float getTime() {
		return time;
	}
	
	public float getTimeIncrement() {
		return timeIncrement;
	}
}
