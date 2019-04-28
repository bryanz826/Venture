package com.example.entities;

import java.awt.Graphics2D;

import com.example.entities.animations.SingleRender;
import com.example.entities.collisions.Collisions;
import com.example.main.ReferenceConfig;
import com.example.utils.gameloop_i.Playable;
import com.example.utils.resource.Resource;

public class EntityManager implements Playable
{
	private Player player;

	public EntityManager()
	{
		player = new Player(ReferenceConfig.getWidth() / 2, ReferenceConfig.getHeight() / 2,
				new SingleRender(0, new Resource("FILENAME"))); //TODO pic file of player
	}

	@Override
	public void processInput()
	{
		player.processInput();
	}

	@Override
	public void update()
	{
		Collisions.update(player);

		player.update();
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		player.render(g, interpolation);
	}

}
