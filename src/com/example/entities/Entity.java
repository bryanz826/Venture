package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.entities.animations.SingleRender;
import com.example.utils.gameloop_i.Loopable;

/*
 * Models real physics
 */
public abstract class Entity implements Loopable
{
	private float	x, y;
	private float	width, height;
	private float	dx, dy;			// d*: current speed;
	private float	d2x, d2y;		// d2*: current accel;
	private Render	render;

	public Entity(float x, float y, float width, float height, Render render)
	{
		setPosition(x, y);
		setDimensions(width, height);
		this.render = render;
	}

	@Override
	public void update()
	{
		setDx(getDx() + getD2x());
		setDy(getDy() + getD2y());
		setX(getX() + getDx());
		setY(getY() + getDy());
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		// g.drawImage(Math.round(x + interpolation * dx), Math.round(y + interpolation
		// * dy), Math.round(width), Math.round(height), null);
		render.render(g, Math.round(x + interpolation * dx), Math.round(y + interpolation * dy), Math.round(width),
				Math.round(height));
	}

	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void setDimensions(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public void setDx(float dx)
	{
		this.dx = dx;
	}

	public void setDy(float dy)
	{
		this.dy = dy;
	}

	public void setD2x(float d2x)
	{
		this.d2x = d2x;
	}

	public void setD2y(float d2y)
	{
		this.d2y = d2y;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public float getDx()
	{
		return dx;
	}

	public float getDy()
	{
		return dy;
	}

	public float getD2x()
	{
		return d2x;
	}

	public float getD2y()
	{
		return d2y;
	}

	// @Override
	// public String toString()
	// {
	// return "Entity [x=" + x + ", y" + y + ", width=" + width + ", height=" +
	// height + ", dx=" + dx + ", dy=" + dy
	// + ", terminalDx=" + maxDx + ", terminalDy=" + maxDy + ", d2x=" + d2x + ",
	// d2y=" + d2y;
	// // + ", boundsCount=" + getBounds().size() + "]";
	// }
}
