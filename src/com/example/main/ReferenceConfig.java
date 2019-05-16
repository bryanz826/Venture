package com.example.main;

import java.awt.GraphicsDevice;

import com.example.entities.collisions.Bounds;
import com.example.libs.Vector2D;

public class ReferenceConfig
{
	public static final int	FRAME_CAP	= VenturePanel.FRAME_CAP;
	public static final int	TARGET_UPS	= VenturePanel.TARGET_UPS;

	public static Bounds	outer;

	public static void initOuter()
	{
		int space = 120;

		int x = -space;
		int y = -space;
		int width = getWidth() + 2 * space;
		int height = getHeight() + 2 * space;
		outer = new Bounds(new Vector2D(x, y), width, height);
	}

	public static int getWidth()
	{
		return VentureRunner.WIDTH;
	}

	public static int getHeight()
	{
		return VentureRunner.HEIGHT;
	}

	public static void updateOuter()
	{
		int space = 120;
		
		int width = getWidth() + 2 * space;
		int height = getHeight() + 2 * space;
		outer.setWidth(width);
		outer.setHeight(height);
	}

	public static Bounds getOuter()
	{
		return outer;
	}

	public static int getFPS()
	{
		return VentureRunner.getGame().getFPS();
	}

	public static int getUPS()
	{
		return VentureRunner.getGame().getUPS();
	}

	public static GraphicsDevice getDevice()
	{
		return VentureRunner.getDevice();
	}

	public static boolean isFull()
	{
		return VentureRunner.isFull();
	}
}
