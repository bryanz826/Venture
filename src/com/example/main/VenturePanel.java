package com.example.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.example.state.StateManager;
import com.example.state.list.Intro;
import com.example.state.list.Menu;
import com.example.utils.KeyManager;
import com.example.utils.MouseManager;

@SuppressWarnings("serial")
public class VenturePanel extends Canvas implements Runnable
{
	static final int	FRAME_CAP	= 60;
	static final int	TARGET_UPS	= 60;
	private int			FPS;
	private int			UPS;

	private Thread		thread;

	private boolean		running;

	public VenturePanel()
	{
		MouseManager mm = new MouseManager();
		addMouseListener(mm);
		addMouseMotionListener(mm);
		addKeyListener(new KeyManager());
		setVisible(true);
		setFocusable(true);
		requestFocus();
	}

	private void init()
	{
		StateManager.addState(new Intro());
		StateManager.addState(new Menu());

		FPS = FRAME_CAP;
		UPS = TARGET_UPS;
		running = true;
	}

	private void processInput()
	{
		StateManager.processInput();
		KeyManager.update();
		MouseManager.update();
	}

	private void update()
	{
		StateManager.update();
	}

	private void render()
	{
		Graphics2D g = null;
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		do {
			do {
				try {
					g = (Graphics2D) bs.getDrawGraphics();
					StateManager.render(g);
				} finally {
					g.dispose();
				}
				bs.show();
			} while (bs.contentsRestored());
		} while (bs.contentsLost());
	}

	private void gameLoop()
	{
		final long OPTIMAL_TIME = 1000000000 / TARGET_UPS;
		final double FRAME_CAPPER = (double) TARGET_UPS / FRAME_CAP;

		long timer = System.currentTimeMillis();
		int frameCount = 0;
		int updates = 0;

		long previous = System.nanoTime();
		double dt = 0;
		double frameDuration = 0;

		while (running) {
			long current = System.nanoTime();
			long elapsed = current - previous;
			previous = current;

			dt += elapsed / (double) OPTIMAL_TIME;
			frameDuration += elapsed / (double) OPTIMAL_TIME;

			if (dt >= 1.0) {
				processInput();
				update();
				updates++;
				dt--;
			}

			try {
				Thread.sleep(0, 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (frameDuration >= FRAME_CAPPER) {
				render();
				frameCount++;
				frameDuration = 0;
			}

			if (System.currentTimeMillis() - 1000 > timer) {
				timer += 1000;
				FPS = frameCount;
				UPS = updates;
				frameCount = 0;
				updates = 0;
			}
		}
		stop();
	}

	void start()
	{
		if (running) return;
		thread = new Thread(this, "GameMain-Thread");
		thread.start();
		running = true;
	}

	public void stop()
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
