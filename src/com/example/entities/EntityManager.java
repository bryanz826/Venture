package com.example.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.example.entities.Entity.BoundsCombination;
import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.Collisions;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Interactive;

public class EntityManager implements Interactive
{
	private MeteorManager	mm;
	private Entity			dummy;

	public EntityManager()
	{
		Player.I();
		mm = new MeteorManager(0.42f, 0.84f, 0.5f, 2, 2, 0);
		// mm = new MeteorManager(0.10f, 0.10f, 1.0f, 5, 1, 420);

		dummy = new Entity(new Vector2D(420, 420), 600, 200, BoundsCombination.RECT_CIRC, null);
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

		dummy.update();

		mm.update();
		Player.I().update();
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		boolean collide = false;
		for (Bounds b : Player.I().getComplex())
			if (b.intersects(dummy.getRect())) {
				collide = true;
				break;
			}
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString("COLLISION: " + collide, 500, 300);
		g.setFont(new Font("Arial", Font.PLAIN, 12));

		dummy.render(g, interpolation);

		mm.render(g, interpolation);
		Player.I().render(g, interpolation);
	}
}
