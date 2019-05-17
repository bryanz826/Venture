package com.example.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.example.entities.collisions.Bounds;
import com.example.libs.ReferenceResource;
import com.example.utils.ConsoleLog;

/**
 * Game runner that contains a Frame that holds a game reference. Currently
 * offers windowed and fullscreen
 * 
 * @author poroia
 */
public class VentureRunner
{
	/**
	 * The recommended playing width. Game was also made in this resolution and
	 * calculations based off it.
	 */
	public static final int			RECOMMENDED_WIDTH	= 1920;
	/**
	 * The recommended playing height. Game was also made in this resolution and
	 * calculations based off it.
	 */
	public static final int			RECOMMENDED_HEIGHT	= 1080;

	/**
	 * Current width of Frame
	 */
	public static int				WIDTH;						// current width

	/**
	 * Current height of Frame
	 */
	public static int				HEIGHT;						// current height

	/**
	 * 
	 */
	public static Bounds			outer;

	private static GraphicsDevice	device;						// contains device configurations
	private static DisplayMode		origDisplayMode;			// for use in window switching

	private static Frame			window;						// Window
	private static VenturePanel		game;						// Game

	private static int				windowedOffsetX;			// Offsets as a result of the windowed bar etc
	private static int				windowedOffsetY;

	private static boolean			full;						// State for full or windowed

	public VentureRunner()
	{
		outer = new Bounds(Bounds.Type.RECT);

		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		origDisplayMode = device.getDisplayMode();

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
	}

	private static void setWindowed()
	{
		window.setUndecorated(false); // keep window bar
		if (device.isDisplayChangeSupported()) device.setDisplayMode(origDisplayMode);
		device.setFullScreenWindow(null); // center window
		window.pack(); // correct window sizing (it's required)

		Insets insets = window.getInsets(); // get size of window bar etc
		windowedOffsetX = insets.left + insets.right;
		windowedOffsetY = insets.top + insets.bottom;

		window.setSize( // adjust size of window accordingly
				RECOMMENDED_WIDTH * 5 / 6 + windowedOffsetX, RECOMMENDED_HEIGHT * 5 / 6 + windowedOffsetY);
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
			window.validate(); // recommended to do so

			displayChange: {
				if (device.isDisplayChangeSupported()) {

					DisplayMode[] displayModes = device.getDisplayModes();

					/*
					 * Set recommended display mode if possible
					 */
					for (int i = displayModes.length - 1; i >= 0; i--) {
						DisplayMode dm = displayModes[i];
						if (dm.getWidth() == RECOMMENDED_WIDTH && dm.getHeight() == RECOMMENDED_HEIGHT) {
							device.setDisplayMode(dm);
							break displayChange;
						}
					}

					/*
					 * Otherwise find closest display mode with same resolution within a range. Also
					 * keep up with closest display mode in general if somehow there is no 16:9
					 * resolution
					 */
					int sameResDist = Math.abs(RECOMMENDED_WIDTH - displayModes[displayModes.length - 1].getWidth());
					int sameResDistI = displayModes.length - 1;

					int generalDist = Math.abs(RECOMMENDED_WIDTH - displayModes[displayModes.length - 1].getWidth());
					int generalDistI = displayModes.length - 1;

					for (int i = displayModes.length - 1; i >= 0; i--) {
						int tempDist = Math.abs(RECOMMENDED_WIDTH - displayModes[i].getWidth());

						if (tempDist < sameResDist) {
							if ((float) displayModes[i].getWidth() / displayModes[i].getHeight() == 16f / 9) {
								sameResDist = tempDist;
								sameResDistI = i;
							}
						}
						if (tempDist < generalDist) {
							generalDist = tempDist;
							generalDistI = i;
						}
					}

					/*
					 * Set display mode if same-res display mode greater than 1080
					 */
					if (displayModes[sameResDistI].getWidth() > 1080) {
						device.setDisplayMode(displayModes[sameResDistI]);
						break displayChange;
					}

					/*
					 * Otherwise draw black bars :( TODO draw black bars for non same-res display
					 */
					device.setDisplayMode(displayModes[generalDistI]);
				}
			}

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
		updateOuter();
		if (windowOffset) {
			WIDTH -= windowedOffsetX;
			HEIGHT -= windowedOffsetY;
		}
	}

	private static void updateOuter()
	{
		int space = 120;

		int x = -space;
		int y = -space;
		int width = WIDTH + 2 * space;
		int height = HEIGHT + 2 * space;

		outer.setPosition(x, y);
		outer.setWidth(width);
		outer.setHeight(height);
	}

	public static void main(String args[])
	{
		// new Splash();

		ConsoleLog.write("Launching Venture...");
		new VentureRunner();
	}

	public static boolean isFull()
	{
		return full;
	}
}
