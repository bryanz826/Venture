package com.example.libs;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.entities.collisions.Circle;

public class ReferenceRender
{
	public static int getInterpolatedX(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getX() + interpolation * velocity.getX());
	}
	
	public static int getInterpolatedX(Rectangle rect, Vector2D velocity, float interpolation)
	{
		return Math.round((float) rect.getX() + interpolation * velocity.getX());
	}
	
	public static int getInterpolatedX(Circle circ, Vector2D velocity, float interpolation)
	{
		return Math.round(circ.getX() + interpolation * velocity.getX());
	}

	public static int getInterpolatedY(Vector2D position, Vector2D velocity, float interpolation)
	{
		return Math.round(position.getY() + interpolation * velocity.getY());
	}
	
	public static int getInterpolatedY(Rectangle rect, Vector2D velocity, float interpolation)
	{
		return Math.round((float) rect.getY() + interpolation * velocity.getY());
	}
	
	public static int getInterpolatedY(Circle circ, Vector2D velocity, float interpolation)
	{
		return Math.round(circ.getY() + interpolation * velocity.getY());
	}

	public static void drawRect(Graphics2D g, float width, float height, Vector2D position)
	{
		g.drawRect(Math.round(position.getX()), Math.round(position.getY()), Math.round(width), Math.round(height));
	}

	public static void drawRect(Graphics2D g, Rectangle rect)
	{
		g.drawRect(Math.round((float) rect.getX()), Math.round((float) rect.getY()),
				Math.round((float) rect.getWidth()), Math.round((float) rect.getHeight()));
	}

	public static void drawInterpolatedRect(Graphics2D g, float width, float height, Vector2D position,
			Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawRect(x, y, Math.round(width), Math.round(height));
	}

	public static void drawInterpolatedRect(Graphics2D g, Rectangle rect, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(rect, velocity, interpolation);
		int y = getInterpolatedY(rect, velocity, interpolation);
		int width = Math.round((float) rect.getWidth());
		int height = Math.round((float) rect.getHeight());
		g.drawRect(x, y, width, height);
	}

	public static void drawCirc(Graphics2D g, float size, Vector2D position)
	{
		g.drawOval(Math.round(position.getX()), Math.round(position.getY()), Math.round(size), Math.round(size));
	}

	public static void drawCirc(Graphics2D g, Circle circ)
	{
		g.drawOval(Math.round(circ.getX()), Math.round(circ.getY()),
				Math.round(2 * circ.getRadius()), Math.round(2 * circ.getRadius()));
	}

	public static void drawInterpolatedCirc(Graphics2D g, float size, Vector2D position,
			Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawOval(x, y, Math.round(size), Math.round(size));
	}

	public static void drawInterpolatedCirc(Graphics2D g, Circle circ, Vector2D velocity, float interpolation)
	{
		int x = getInterpolatedX(circ, velocity, interpolation);
		int y = getInterpolatedY(circ, velocity, interpolation);
		int size = Math.round(2 * circ.getRadius());
		g.drawOval(x, y, size, size);
	}
	
	public static void drawString(Graphics2D g, String str, Vector2D position)
	{
		g.drawString(str, position.getX(), position.getY());
	}
	
	public static void drawString(Graphics2D g, String str, Rectangle rect)
	{
		g.drawString(str, (float) rect.getX(), (float) rect.getY());
	}

	public static void drawInterpolatedString(Graphics2D g, String str, Vector2D position, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(position, velocity, interpolation);
		int y = getInterpolatedY(position, velocity, interpolation);
		g.drawString(str, x, y);
	}

	public static void drawInterpolatedString(Graphics2D g, String str, Rectangle rect, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(rect, velocity, interpolation);
		int y = getInterpolatedY(rect, velocity, interpolation);
		g.drawString(str, x, y);
	}

	public static void drawInterpolatedString(Graphics2D g, String str, Circle circ, Vector2D velocity,
			float interpolation)
	{
		int x = getInterpolatedX(circ, velocity, interpolation);
		int y = getInterpolatedY(circ, velocity, interpolation);
		g.drawString(str, x, y + 2 * circ.getRadius());
	}
}
