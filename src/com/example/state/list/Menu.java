package com.example.state.list;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.main.ReferenceConfig;
import com.example.state.State;
import com.example.state.StateManager;
import com.example.utils.ConsoleLog;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;

public class Menu implements State
{
	public enum MenuButton
	{
		START,
		SETTINGS,
		QUIT
	}

	private int			selection;
	private String[]	options;

	public Menu()
	{
		int optionLen = MenuButton.values().length;
		options = new String[optionLen];
		for (int i = 0; i < optionLen; i++) {
			options[i] = MenuButton.values()[i].toString();
		}
	}

	private void selectOption()
	{
		// switch (selection)
	}

	@Override
	public void processInput()
	{
		// if (KeyManager.wasPressed(KeyManager.UP)) {
		// selection--;
		// if (selection < 0) selection = options.length - 1;
		// }
		// if (KeyManager.wasPressed(KeyManager.DOWN)) {
		// selection++;
		// if (selection >= options.length) selection = 0;
		// }
		//
		// if (KeyManager.wasPressed(KeyManager.ENTER)) {
		// selectOption(sm);
		// }
		if (KeyManager.anyKeyPress()|| MouseManager.anyButtonPress()) {
			StateManager.setState("PLAY");
		}
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ReferenceConfig.getWidth(), ReferenceConfig.getHeight());

		g.setColor(Color.RED);
		g.fillRect(0, 0, 50, 50);

		g.fillRect(0, ReferenceConfig.getHeight() - 50, 50, 50);
		g.setColor(Color.CYAN);

		g.drawString("X: " + MouseManager.getX() + ", Y: " + MouseManager.getY(), 100, 100);
	}

	@Override
	public String getName()
	{
		return "MENU";
	}

}
