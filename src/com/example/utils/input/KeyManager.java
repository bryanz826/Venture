package com.example.utils.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.example.main.VentureRunner;
import com.example.refs.Reference;

public class KeyManager extends KeyAdapter
{
	public static final int	NUM_KEYS		= 256;

	public static boolean[]	keyState		= new boolean[NUM_KEYS];
	public static boolean[]	prevKeyState	= new boolean[NUM_KEYS];

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

	public static boolean isUp(int i)
	{
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
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) System.exit(0);
		if (e.getKeyCode() == KeyEvent.VK_O) VentureRunner.toggleScreenMode();
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (Reference.PAUSE) Reference.PAUSE = false;
			else Reference.PAUSE = true;
		}
		
		keyState[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keyState[e.getKeyCode()] = false;
	}
}
