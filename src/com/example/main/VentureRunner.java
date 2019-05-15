package com.example.main;

import java.awt.Color;
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

import com.example.libs.ReferenceResource;
import com.example.utils.ConsoleLog;

/*
 * Game runner that contains a Frame that holds a game reference. Currently offers windowed and fullscreen
 */
public class VentureRunner
{
	public static int				WINDOWED_WIDTH	= 1600;	// currently only one windowed resolution
	public static int				WINDOWED_HEIGHT	= 900;	// TASK select best windowed resolution

	static int						WIDTH;					// current width
	static int						HEIGHT;					// current height

	private static GraphicsDevice	device;					// contains device configurations
	// private static DisplayMode origDisplayMode; // for use in window switching
	// (not sure if required) ^

	private static Frame			window;					// Window
	private static VenturePanel		game;					// Game

	private static int				windowedOffsetX;		// Offsets as a result of the windowed bar etc
	private static int				windowedOffsetY;

	private static boolean			full;					// State for full or windowed

	public VentureRunner()
	{
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// origDisplayMode = device.getDisplayMode();

		game = new VenturePanel();

		window = new Frame("Venture");
		window.setBackground(Color.BLACK);
		window.addWindowListener(new WindowAdapter() // terminate program upon "close"
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		window.add(game); // add game to the window
		window.setResizable(false); // fix size of window

		String cLocation = ReferenceResource.RESOURCE_LOC + "cursor.png";
		Image cImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(cLocation));
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cImage, new Point(0, 0), "Cursor");
		window.setCursor(cursor); // TODO not sure if I will use this method for painting cursor
		// may use a transparent cursor

		setFullscreen(); // initial setting TODO will add to menu to make it toggleable

		game.run();

		// window.setIconImage(Toolkit.getDefaultToolkit().getImage(Reference.RESOURCE_LOC
		// + "icon.png")); TODO set icon
	}

	public static void toggleScreenMode() // in the name
	{
		window.dispose(); // reset current window
		if (full) {
			setWindowed();
		} else {
			setFullscreen();
		}
		ReferenceConfig.updateOuter();
	}

	private static void setWindowed()
	{
		window.setUndecorated(false); // keep window bar
		device.setFullScreenWindow(null); // center window
		window.pack(); // correct window sizing (it's required)

		Insets insets = window.getInsets(); // get size of window bar etc
		windowedOffsetX = insets.left + insets.right;
		windowedOffsetY = insets.top + insets.bottom;

		window.setSize( // adjust size of window accordingly
				WINDOWED_WIDTH + windowedOffsetX, WINDOWED_HEIGHT + windowedOffsetY);
		window.setVisible(true);
		window.setLocationRelativeTo(null); // center window

		updateDimensions(true);
		full = false;
		ConsoleLog.write("Minimized Screen Initiated - " + WIDTH + " x " + HEIGHT);
	}

	private static void setFullscreen()
	{
		if (device.isFullScreenSupported()) { // check if full screen allowed
			window.setUndecorated(true); // remove window bar (we want full screen!)
			device.setFullScreenWindow(window);
			window.validate(); // somehow it's here... I believe it's only recommended

			updateDimensions(false); // TODO work with display modes?
			full = true;
			ConsoleLog.write("Full Screen Initiated - " + WIDTH + " x " + HEIGHT);
		} else { // if full screen isn't allowed :(
			ConsoleLog.write("Fullscreen not supported. Switching screen mode to windowed");
			if (!game.isRunning()) setWindowed();
		}
	}

	private static void updateDimensions(boolean windowOffset) // update current window size to new window
	{
		WIDTH = window.getWidth();
		HEIGHT = window.getHeight();
		if (windowOffset) {
			WIDTH -= windowedOffsetX;
			HEIGHT -= windowedOffsetY;
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
