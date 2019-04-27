package com.example.entities;

import java.awt.Graphics2D;

import com.example.main.LowReference;
import com.example.utils.Playable;

public class EntityManager implements Playable
{
	private Player player;

	public EntityManager()
	{
		player = new Player(LowReference.getWidth() / 2, LowReference.getHeight() / 2);
	}

	@Override
	public void processInput()
	{
		player.processInput();
	}

	@Override
	public void update()
	{
		player.update();
	}

	@Override
	public void render(Graphics2D g)
	{
		player.render(g);
	}

}
