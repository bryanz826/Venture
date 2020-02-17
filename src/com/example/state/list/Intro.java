package com.example.state.list;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.refs.ReferenceConfig;
import com.example.state.State;
import com.example.state.StateManager;
import com.example.utils.ConsoleLog;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;
import com.example.utils.timing.TimeDuration;
import com.example.utils.timing.TimingCurve;

public class Intro implements State
{
	private TimingCurve	fadeIn;
	private TimeDuration wait;
	private TimingCurve	fadeOut;

	private int			alpha;

	public Intro()
	{
		alpha = 255;
		fadeIn = new TimingCurve(TimingCurve.Preset.EASE_IN, 1.5f);
		wait = new TimeDuration(0.5f);
		fadeOut = new TimingCurve(TimingCurve.Preset.EASE_IN_OUT, 1);
	}

	@Override
	public void processInput()
	{
		if (KeyManager.anyKeyDown()|| MouseManager.anyButtonDown()) {
			ConsoleLog.write(getName() + " state skipped.");
			StateManager.setState("MENU");
		}
	}

	@Override
	public void update()
	{
		if (!fadeIn.isComplete()) fadeIn.update();
		else if (!wait.isComplete()) wait.update();
		else if (!fadeOut.isComplete()) fadeOut.update();
		else StateManager.setState("MENU");
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, ReferenceConfig.getWidth(), ReferenceConfig.getHeight());

		if (!fadeIn.isComplete()) alpha = 255 - Math.round(255 * fadeIn.getProgression());
		else if (!fadeOut.isComplete()) alpha = Math.round(255 * fadeOut.getProgression());
		
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, ReferenceConfig.getWidth(), ReferenceConfig.getHeight());
	}

	@Override
	public String getName()
	{
		return "INTRO";
	}

}
