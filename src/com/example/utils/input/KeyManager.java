package com.example.utils.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.example.main.VentureRunner;

public class KeyManager extends KeyAdapter
{
	public static int		A				= 0;
	public static int		D				= 1;
	public static int		W				= 2;
	public static int		S				= 3;
	public static int		SPACE			= 4;
	public static int		ENTER			= 5;
	public static int		ESCAPE			= 6;
	public static int		E				= 7;

	public static final int	NUM_KEYS		= 8;

	public static boolean[]	keyState		= new boolean[NUM_KEYS];
	public static boolean[]	prevKeyState	= new boolean[NUM_KEYS];

	public static void keySet(int code, boolean b)
	{
		if (KeyEvent.VK_A == code) keyState[A] = b;
		else if (KeyEvent.VK_D == code) keyState[D] = b;
		else if (KeyEvent.VK_W == code) keyState[W] = b;
		else if (KeyEvent.VK_S == code) keyState[S] = b;
		else if (KeyEvent.VK_SPACE == code) keyState[SPACE] = b;
		else if (KeyEvent.VK_ENTER == code) keyState[ENTER] = b;
		else if (KeyEvent.VK_ESCAPE == code) keyState[ESCAPE] = b;
		else if (KeyEvent.VK_E == code) keyState[E] = b;
	}

	public static void update()
	{
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isDown(int i)
	{
		return keyState[i];
	}

	public static boolean isUp(int i) {
		return !keyState[i];
	}

	public static boolean wasPressed(int i)
	{
		return isDown(i) && !prevKeyState[i];
	}

	public static boolean wasReleased(int i)
	{
		return !isDown(i) && prevKeyState[i];
	}

	public static boolean anyKeyPress()
	{
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i] && !prevKeyState[i]) return true;
		}
		return false;
	}

	public static boolean anyKeyDown()
	{
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i]) return true;
		}
		return false;
	}

	// key events
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) System.exit(0);
		if (e.getKeyCode() == KeyEvent.VK_O) VentureRunner.toggleScreenMode();
		KeyManager.keySet(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e)
	{
		KeyManager.keySet(e.getKeyCode(), false);
	}
}
