package com.example.libs;

import java.awt.Graphics2D;

import com.example.entities.collisions.Bounds;

public class ReferenceRender
{
	public static int getInterpolatedX(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getX() + interpolation * velocity.getX());
	}

	public static int getInterpolatedX(Bounds bounds, Vector2D velocity, float interpolation)
	{
		return Math.round(bounds.getX() + interpolation * velocity.getX());
	}

	public static int getInterpolatedY(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getY() + interpolation * velocity.getY());
	}

	public static int getInterpolatedY(Bounds bounds, Vector2D velocity, float interpolation)
	{
		return Math.round(bounds.getY() + interpolation * velocity.getY());
	}

	public static void drawRect(Graphics2D g, float width, float height, Vector2D position)
	{
		g.drawRect(Math.round(position.getX()), Math.round(position.getY()), Math.round(width), Math.round(height));
	}

	public static void drawRect(Graphics2D g, Bounds rect)
	{
		g.drawRect(Math.round(rect.getX()), Math.round(rect.getY()), Math.round(rect.getWidth()),
				Math.round(rect.getHeight()));
	}

	public static void drawInterpolatedRect(Graphics2D g, float width, float height, Vector2D position,
			Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawRect(x, y, Math.round(width), Math.round(height));
	}

	public static void drawInterpolatedRect(Graphics2D g, Bounds rect, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(rect, velocity, interpolation);
		int y = getInterpolatedY(rect, velocity, interpolation);
		int width = Math.round(rect.getWidth());
		int height = Math.round(rect.getHeight());
		g.drawRect(x, y, width, height);
	}

	public static void drawCirc(Graphics2D g, float size, Vector2D position)
	{
		g.drawOval(Math.round(position.getX()), Math.round(position.getY()), Math.round(size), Math.round(size));
	}

	public static void drawCirc(Graphics2D g, Bounds circ)
	{
		g.drawOval(Math.round(circ.getX()), Math.round(circ.getY()), Math.round(circ.getWidth()),
				Math.round(circ.getWidth()));
	}

	public static void drawInterpolatedCirc(Graphics2D g, float size, Vector2D position, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawOval(x, y, Math.round(size), Math.round(size));
	}

	public static void drawInterpolatedCirc(Graphics2D g, Bounds circ, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(circ, velocity, interpolation);
		int y = getInterpolatedY(circ, velocity, interpolation);
		int size = Math.round(circ.getWidth());
		g.drawOval(x, y, size, size);
	}

	public static void drawString(Graphics2D g, String str, Vector2D position)
	{
		g.drawString(str, position.getX(), position.getY());
	}

	public static void drawString(Graphics2D g, String str, Bounds bounds)
	{
		g.drawString(str, bounds.getX(), bounds.getY());
	}

	public static void drawInterpolatedString(Graphics2D g, String str, Vector2D position, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawString(str, x, y);
	}

	public static void drawInterpolatedString(Graphics2D g, String str, Bounds bounds, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(bounds, velocity, interpolation);
		int y = getInterpolatedY(bounds, velocity, interpolation);
		if (bounds.checkType(bounds, Bounds.Type.RECT)) g.drawString(str, x, y);
		else if (bounds.checkType(bounds, Bounds.Type.CIRC)) g.drawString(str, x, y + bounds.getHeight());
	}
}
