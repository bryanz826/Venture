package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.collisions.Collisions;
import com.example.utils.gameloop_instructions.Interactive;

public class EntityManager implements Interactive
{
	public EntityManager()
	{
		Player.I();
	}

	@Override
	public void processInput()
	{
		Player.I().processInput();
	}

	@Override
	public void update()
	{
		Collisions.update();

		Player.I().update();
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		Player.I().render(g, interpolation);
	}

}
