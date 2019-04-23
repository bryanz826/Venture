package com.example.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter
{
	private static int			x, y;
	private static int			prevX, prevY;
	private static boolean		moving;

	private static final int	NUM_BUTTONS		= 7;

	private static boolean[]	buttonState		= new boolean[NUM_BUTTONS];
	private static boolean[]	prevButtonState	= new boolean[NUM_BUTTONS];

	public static void update()
	{
		for (int i = 0; i < NUM_BUTTONS; i++) {
			prevButtonState[i] = buttonState[i];
		}
		if (x == prevX && y == prevY) moving = false;
		prevX = x;
		prevY = y;
	}

	public static boolean isDown(int i)
	{
		return buttonState[i];
	}

	public static boolean wasPressed(int i)
	{
		return isDown(i) && !prevButtonState[i];
	}

	public static boolean wasReleased(int i)
	{
		return !isDown(i) && prevButtonState[i];
	}

	public static boolean anyButtonPress()
	{
		for (int i = 0; i < NUM_BUTTONS; i++) {
			if (buttonState[i] && !prevButtonState[i]) return true;
		}
		return false;
	}

	public static boolean anyButtonDown()
	{
		for (int i = 0; i < NUM_BUTTONS; i++) {
			if (isDown(i)) return true;
		}
		return false;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		buttonState[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttonState[e.getButton()] = false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
		moving = true;
	}

	public static int getX()
	{
		return x;
	}

	public static int getY()
	{
		return y;
	}

	public static boolean isMoving()
	{
		return moving;
	}
}
