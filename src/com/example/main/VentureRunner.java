package com.example.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import com.example.entities.collisions.BoundsManager;
import com.example.entities.collisions.BoundsManager.BoundsType;
import com.example.refs.ReferenceResource;
import com.example.refs.Vector2D;
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

	public static final int				SCREEN_WIDTH = 
							Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public static final int				SCREEN_HEIGHT = 
							Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Current width of Frame
	 */
	public static int				WIDTH;						// current width

	/**
	 * Current height of Frame
	 */
	public static int				HEIGHT;						// current height

	/**
	 * The outer bounds where objects may spawn.
	 */
	public static BoundsManager		outer;

	private static GraphicsDevice	device;						// contains device configurations
	private static DisplayMode		origDisplayMode;			// for use in window switching

	private static Frame			window;						// Window
	private static VenturePanel		game;						// Game

	private static int				windowedOffsetX;			// Offsets as a result of the windowed bar etc
	private static int				windowedOffsetY;

	private static boolean			full;						// State for full or windowed

	public VentureRunner()
	{
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

		String cLocation = ReferenceResource.IMAGE_LOC + "cursor.png";
		Image cImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(cLocation));
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cImage, new Point(0, 0), "Cursor");
		window.setCursor(cursor); // TODO not sure if I will use this method for painting cursor
		// may use a transparent cursor

		outer = new BoundsManager(BoundsType.RECT);
		setFullscreen(); // initial setting TODO will add to menu to make it toggleable

		game.start();

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
		// resizeAll(Entity.class, width, new String[] { "width" });
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
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		ConsoleLog.write("Screen size - " + (int) d.getWidth() + " x " + (int) d.getHeight());
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
			
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			ConsoleLog.write("Screen size - " + (int) d.getWidth() + " x " + (int) d.getHeight());
		} else { // if full screen isn't allowed :(
			ConsoleLog.write("Fullscreen not supported. Switching screen mode to windowed");
			if (!game.isRunning()) setWindowed();
		}
	}

	private static void updateDimensions(boolean windowOffset) // update current window size to new window
	{
		WIDTH = device.getDisplayMode().getWidth();
		HEIGHT = device.getDisplayMode().getHeight();
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

		outer.update(new Vector2D(x, y), width, height);
	}

	public static void main(String args[])
	{
		ConsoleLog.write("Launching Splash...");
		new Splash();

		ConsoleLog.write("Launching Venture...");
		new VentureRunner();
	}

	public static boolean isFull()
	{
		return full;
	}
	//
	// private static void resizeAll(Class<?> clazz, float newSize, String[]
	// wantedFields)
	// {
	// List<Field> fields = new ArrayList<Field>();
	// for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
	// fields.addAll(Arrays.asList(c.getDeclaredFields()));
	// }
	//
	// for (int i = 0; i < wantedFields.length; i++)
	// for (int j = 0; j < fields.size(); j++) {
	// if (wantedFields[i].equals(fields.get(j).getName())) {
	// String name = wantedFields[i].substring(0, 1).toUpperCase() +
	// wantedFields[i].substring(1);
	//
	// Method method = null;
	// try {
	// method = clazz.getMethod("set" + name, float.class);
	// } catch (NoSuchMethodException | SecurityException e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// method.invoke(clazz.newInstance(), 900);
	// } catch (IllegalAccessException | IllegalArgumentException |
	// InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	//
	// private static float getResize(float size, float newSize)
	// {
	// return size * newSize / ReferenceConfig.getWidth();
	// }
}
