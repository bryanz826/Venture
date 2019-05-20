package com.example.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.Collisions;
import com.example.libs.ReferenceConfig;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Playable;

public class EntityManager implements Playable
{
	private List<Moving>	movables;

	private MeteorLauncher	ml;
	private Moving			dummy;

	public EntityManager()
	{
		movables = new ArrayList<Moving>();
		movables.add(0, Player.I());
		
		ml = new MeteorLauncher(0.42f, 0.84f, 0.5f, 2, 2, 4);
		// mm = new MeteorManager(0.10f, 0.10f, 1.0f, 5, 1, 420);

		dummy = new Moving(new Vector2D(420, 420), 600, 200, 0, 0, null, ID.DUMMY);
	}

	@Override
	public void processInput()
	{
		Player.I().processInput();
	}

	@Override
	public void update()
	{
		/*
		 * Collisions
		 */
		Collisions.update(movables);

		/*
		 * Helpers
		 */
		ml.update(movables);

		/*
		 * Entities
		 */
		updateMovables();

		dummy.update();
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

		renderMovables(g, interpolation);
	}

	//
	// HELPER METHODS
	//
	private void updateMovables()
	{
		for (Iterator<Moving> iter = movables.iterator(); iter.hasNext();) {
			Moving moving = iter.next();
			moving.update();

			// if (a.isLanded()) {
			// a.actionOnLanding();
			// iter.remove();
			// }

			if (!moving.getCirc().intersects(ReferenceConfig.getOuter())) {
				iter.remove(); // remove off-screen meteors
			}
		}
	}

	private void renderMovables(Graphics2D g, float interpolation)
	{
		for (Moving moving : movables)
			moving.render(g, interpolation);
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Returns the entities list.
	 * 
	 * @return entities
	 */
	public List<Moving> getMovables()
	{
		return movables;
	}
}
