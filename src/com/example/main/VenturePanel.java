package com.example.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.example.libs.Reference;
import com.example.state.StateManager;
import com.example.state.list.Intro;
import com.example.state.list.Menu;
import com.example.state.list.Play;
import com.example.utils.ConsoleLog;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;

/*
 * Is a Canvas and allows rendering (drawing) on it
 * Has a game loop that contains all directions for game updates each frame
 */
@SuppressWarnings("serial")
public class VenturePanel extends Canvas implements Runnable
{
	public static final int	TARGET_UPS	= 60;	// target updates per second
	public static final int	FRAME_CAP	= 60;	// FPS cap
	private int				UPS;
	private int				FPS;

	private Thread			thread;

	private boolean			running;			// is programming running

	public VenturePanel() //
	{
		FPS = FRAME_CAP;
		UPS = TARGET_UPS;

		MouseManager mm = new MouseManager();
		addMouseListener(mm);
		addMouseMotionListener(mm);
		addKeyListener(new KeyManager());
		setVisible(true);
		setFocusable(true); // allow interactions on this window
		requestFocus();
	}

	private void init() // initializes game (not in constructor bc we need a secondary init)
	{
		StateManager.addState(new Intro());
		StateManager.addState(new Menu());
		StateManager.addState(new Play());
	}

	private void processInput() // get all user interaction input
	{
		StateManager.processInput();
		KeyManager.update();
		MouseManager.update();
	}

	private void update() // update one game frame accordingly
	{
		StateManager.update();
	}

	private void render(float interpolation) // render one game frame accordingly
	{
		Graphics2D g = null;
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // triple buffering and page flipping
			return;
		}
		do { // repeat rendering if rendering contents were lost
			do { // ensure rendering is consistent
				try {
					g = (Graphics2D) bs.getDrawGraphics();
					StateManager.render(g, interpolation); // render whatever is in current state
				} finally {
					g.dispose(); // reset drawing graphics
				}
			} while (bs.contentsRestored());
			bs.show(); // show graphics
		} while (bs.contentsLost());
	}

	private void gameLoop()
	{
		final long OPTIMAL_TIME = 1000000000 / TARGET_UPS; // target time in nanoseconds btwn each update
		final double FRAME_CAPPER = (double) TARGET_UPS / FRAME_CAP; // used to determine if time for post

		/* (?) */
		long timer = System.currentTimeMillis(); // a seconds timer used to reset/update vars each second
		int frameCount = 0; // keep track of number of renders
		int updates = 0; // keep track of number of updates

		long previous = System.nanoTime(); // get initial system time (used for elapsed time)
		double dt = 0; // used to determine if it is time for updates (in units of OPTIMAL_TIME)
		double frameDuration = 0;

		while (running) {
			long current = System.nanoTime(); // get current system time (used for elapsed time)
			long elapsed = current - previous; // number of time between each frame
			previous = current;

			dt += elapsed / (double) OPTIMAL_TIME; // add the time that has passed
			frameDuration += elapsed / (double) OPTIMAL_TIME; // same purpose as above

			if (dt >= 1.0) { // update once the time (dt (for updates)) has reached OPTIMAL_TIME
				processInput();
				if (!Reference.PAUSE) update();
				updates++; // we've updated once, now let's add it to the tracker!
				dt--; // reset dt, but at same time adjust dt for real time if there are leftovers
			}

			if (frameDuration >= FRAME_CAPPER) { // render once the time (for renders) has been reached
				float interpolation = Math.min(1.0f, (float) dt);
				render(interpolation); // will interpolate when FPS > UPS
				frameCount++; // we've rendered once, now let's add it to the tracker!
				frameDuration = 0;
			}

			if (System.currentTimeMillis() - 1000 > timer) { // update specified variables at second mark
				timer += 1000;
				FPS = frameCount;
				UPS = updates;
				frameCount = 0;
				updates = 0;
				if (Reference.DEBUG) ConsoleLog.write("" + FPS);
			}

			// Below statements supposedly may cut down CPU usage
			// Thread.sleep(0, 1); // this one just sometimes lags depending on OS
			Thread.yield();
		}
		stop();
	}

	void start() // start the thread
	{
		if (running) return;
		thread = new Thread(this, "GameMain-Thread");
		thread.start();
		running = true;
	}

	public void stop() // stop the thread
	{
		if (!running) return;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("Terminating game...");
		running = false;
	}

	@Override
	public void run()
	{
		init();
		gameLoop();
	}

	int getFPS()
	{
		return FPS;
	}

	int getUPS()
	{
		return UPS;
	}

	boolean isRunning()
	{
		return running;
	}
}
