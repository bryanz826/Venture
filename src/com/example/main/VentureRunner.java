package com.example.main;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.example.utils.ConsoleLog;

/**
 * Game runner that contains a JFrame instance.
 * 
 * @author bryan
 *
 */
public class VentureRunner
{
	public static int				WINDOWED_WIDTH	= 800;
	public static int				WINDOWED_HEIGHT	= 450;

	static int						WIDTH;
	static int						HEIGHT;

	private static GraphicsDevice	device;
//	private static DisplayMode		origDisplayMode;		// for use in window switching

	private static Frame			window;
	private static VenturePanel		game;

	private static int				windowOffsetX;
	private static int				windowOffsetY;
	private static boolean			full;

	public VentureRunner()
	{
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		origDisplayMode = device.getDisplayMode();

		game = new VenturePanel();

		window = new Frame("Venture");
		window.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		window.add(game);
		window.setResizable(false);
		
		Image cImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/cursor.png"));
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cImage, new Point(0, 0), "Cursor");
		window.setCursor(cursor);
		
		setFullscreen();

		game.run();

		// window.setIconImage(Toolkit.getDefaultToolkit().getImage(Reference.RESOURCE_LOC
		// + "icon.png"));
	}

	public static void toggleScreenMode()
	{
		window.dispose();
		if (full) {
			setWindowed();
		} else {
			setFullscreen();
		}
	}

	private static void setWindowed()
	{
		window.setUndecorated(false);
		device.setFullScreenWindow(null);
		window.pack();

		Insets insets = window.getInsets();
		windowOffsetX = insets.left + insets.right;
		windowOffsetY = insets.top + insets.bottom;
		window.setSize(WINDOWED_WIDTH + windowOffsetX, WINDOWED_HEIGHT + windowOffsetY);
		window.setVisible(true);
		window.setLocationRelativeTo(null);

		updateDimensions(true);
		full = false;
		ConsoleLog.write("Minimized Screen Initiated - " + WIDTH + " x " + HEIGHT);
	}

	private static void setFullscreen()
	{
		if (device.isFullScreenSupported()) {
			window.setUndecorated(true);
			device.setFullScreenWindow(window);
			window.validate();

			updateDimensions(false); // TODO won't work with dif display modes
			full = true;
			ConsoleLog.write("Full Screen Initiated - " + WIDTH + " x " + HEIGHT);
		} else {
			ConsoleLog.write("Fullscreen not supported. Switching screen mode to windowed");
			if (!game.isRunning()) setWindowed();
		}
	}

	private static void updateDimensions(boolean windowOffset)
	{
		WIDTH = window.getWidth();
		HEIGHT = window.getHeight();
		if (windowOffset) {
			WIDTH -= windowOffsetX;
			HEIGHT -= windowOffsetY;
		}
	}

	public static void main(String args[])
	{
		// new Splash();

		ConsoleLog.write("Launching Venture...");
		new VentureRunner();
	}

	static boolean isFull()
	{
		return full;
	}

	static GraphicsDevice getDevice()
	{
		return device;
	}

	static VenturePanel getGame()
	{
		return game;
	}
}
