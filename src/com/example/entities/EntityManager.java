package com.example.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.entities.collisions.Collisions;
import com.example.entities.types.meteor.MeteorManager;
import com.example.entities.types.player.PlayerManager;
import com.example.libs.ReferenceConfig;
import com.example.utils.gameloop_instructions.Playable;

public class EntityManager implements Playable
{
	private List<Moving>	movables;
	
	private MeteorManager	mm;

	public EntityManager()
	{
		movables = new ArrayList<Moving>();
		movables.add(0, PlayerManager.I().getPlayer());

		mm = new MeteorManager(0.42f, 0.84f, 0.5f, 2, 2, 4);
	}


	public void processInput()
	{
		PlayerManager.I().processInput();
	}

	@Override
	public void update()
	{
		/*
		 * Helpers
		 */
		mm.update(movables);

		/*
		 * Entities
		 */
		updateMovables();

		/*
		 * Collisions
		 */
		Collisions.update(movables);
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		mm.render(g, interpolation);
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

			if (moving.getBm().intersects(ReferenceConfig.getOuter()) == -1) {
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
