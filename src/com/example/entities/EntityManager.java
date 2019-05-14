package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.collisions.Collisions;
import com.example.utils.gameloop_instructions.Interactive;

public class EntityManager implements Interactive
{
	private MeteorManager mm;

	public EntityManager()
	{
		Player.I();
		mm = new MeteorManager(0.42f, 0.84f, 0.5f, 2, 2, 2);
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
		
		mm.update();
		Player.I().update();
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		mm.render(g, interpolation);
		Player.I().render(g, interpolation);
	}
}
