package com.example.libs;

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

	public static BoundsManager getOuter()
	{
		return VentureRunner.outer;
	}

	public static boolean isFull()
	{
		return VentureRunner.isFull();
	}
}
