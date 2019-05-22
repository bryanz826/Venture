package com.example.libs;

import java.awt.Graphics2D;

import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.BoundsManager;
import com.example.entities.collisions.BoundsManager.BoundsType;

public class ReferenceRender
{
	//
	// GENERAL METHODS
	//

	public static int getInterpolatedX(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getX() + interpolation * velocity.getX());
	}

	public static int getInterpolatedY(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getY() + interpolation * velocity.getY());
	}

	//
	// DEBUG METHODS
	//

	public static int getInterpolatedX(Bounds bounds, Vector2D velocity, float interpolation)
	{
		return Math.round(bounds.getX() + interpolation * velocity.getX());
	}

	public static int getInterpolatedY(Bounds bounds, Vector2D velocity, float interpolation)
	{
		return Math.round(bounds.getY() + interpolation * velocity.getY());
	}

	/*
	 * RECT
	 */
	public static void drawRect(Graphics2D g, Bounds rect)
	{
		g.drawRect(Math.round(rect.getX()), Math.round(rect.getY()), Math.round(rect.getWidth()),
				Math.round(rect.getHeight()));
	}

	public static void drawRectFromCenter(Graphics2D g, Bounds rect)
	{
		g.drawOval(Math.round(rect.getX() - rect.getWidth() / 2), Math.round(rect.getY() - rect.getHeight() / 2),
				Math.round(rect.getWidth()), Math.round(rect.getHeight()));
	}

	public static void drawInterpolatedRect(Graphics2D g, Bounds rect, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(rect, velocity, interpolation);
		int y = getInterpolatedY(rect, velocity, interpolation);
		int width = Math.round(rect.getWidth());
		int height = Math.round(rect.getHeight());
		g.drawRect(x, y, width, height);
	}

	public static void drawInterpolatedRectFromCenter(Graphics2D g, Bounds rect, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(rect, velocity, interpolation) - Math.round(rect.getWidth() / 2);
		int y = getInterpolatedY(rect, velocity, interpolation) - Math.round(rect.getHeight() / 2);
		int width = Math.round(rect.getWidth());
		int height = Math.round(rect.getHeight());
		g.drawRect(x, y, width, height);
	}

	/*
	 * CIRC
	 */
	public static void drawCirc(Graphics2D g, Bounds circ)
	{
		g.drawOval(Math.round(circ.getX()), Math.round(circ.getY()), Math.round(circ.getWidth()),
				Math.round(circ.getWidth()));
	}

	public static void drawCircFromCenter(Graphics2D g, Bounds circ)
	{
		g.drawOval(Math.round(circ.getX() - circ.getRadius()), Math.round(circ.getY() - circ.getRadius()),
				Math.round(circ.getWidth()), Math.round(circ.getWidth()));
	}

	public static void drawInterpolatedCirc(Graphics2D g, Bounds circ, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(circ, velocity, interpolation);
		int y = getInterpolatedY(circ, velocity, interpolation);
		int size = Math.round(circ.getWidth());
		g.drawOval(x, y, size, size);
	}

	public static void drawInterpolatedCircFromCenter(Graphics2D g, Bounds circ, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(circ, velocity, interpolation) - Math.round(circ.getRadius());
		int y = getInterpolatedY(circ, velocity, interpolation) - Math.round(circ.getRadius());
		int size = Math.round(circ.getWidth());
		g.drawOval(x, y, size, size);
	}

	/*
	 * STRING
	 */
	public static void drawString(Graphics2D g, String str, Bounds bounds)
	{
		g.drawString(str, bounds.getX(), bounds.getY());
	}

	public static void drawInterpolatedString(Graphics2D g, String str, BoundsManager bm, Vector2D velocity,
			float interpolation)
	{
		Bounds bounds = bm.getFirst();
		int x = getInterpolatedX(bounds, velocity, interpolation);
		int y = getInterpolatedY(bounds, velocity, interpolation);
		
		if (bm.getType() == BoundsType.RECT) g.drawString(str, x, y);
		else if (bm.getType() == BoundsType.CIRC) g.drawString(str, x, y + bounds.getHeight());
	}
}
