package com.example.refs;

import com.example.entities.collisions.BoundsManager;
import com.example.main.VenturePanel;
import com.example.main.VentureRunner;

public class ReferenceConfig
{
	public static final int	FRAME_CAP	= VenturePanel.FRAME_CAP;
	public static final int	TARGET_UPS	= VenturePanel.TARGET_UPS;

	public static int getWidth()
	{
		return VentureRunner.WIDTH;
	}

	public static int getHeight()
	{
		return VentureRunner.HEIGHT;
	}
	
	public static float getScreenRatioX()
	{
		return VentureRunner.SCREEN_WIDTH / (float) VentureRunner.WIDTH;
	}
	
	public static float getScreenRatioY()
	{
		return VentureRunner.SCREEN_HEIGHT / (float) VentureRunner.HEIGHT;
	}

	public static BoundsManager getOuter()
	{
		return VentureRunner.outer;
	}

	public static boolean isFull()
	{
		return VentureRunner.isFull();
	}
}
