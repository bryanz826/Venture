package com.example.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.example.entities.collisions.Collisions;
import com.example.entities.types.meteor.MeteorManager;
import com.example.entities.types.player.Player;
import com.example.refs.ReferenceConfig;
import com.example.utils.gameloop_instructions.Playable;

/**
 * Manages all the entities, updating input
 */
public class EntityManager implements Playable
{
	private List<Moving>	movables;
	
	private MeteorManager	mm;

	public EntityManager()
	{
		movables = new ArrayList<Moving>();
		movables.add(0, Player.I().getMechanics());

		mm = new MeteorManager(0.42f, 0.84f, 0.5f, 2, 2, 4);
	}

	public void processInput()
	{
		Player.I().processInput();
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
		Player.I().update();

		/*
		 * Collisions
		 */
		Collisions.update(movables);
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		/*
		 * Helpers
		 */
		mm.render(g, interpolation);
		
		/*
		 * Entities
		 */
		renderMovables(g, interpolation);

		/*
		 * Collisions
		 */
		Player.I().render(g, interpolation);
	}

	//
	// HELPER METHODS
	//
	private void updateMovables()
	{
//		for (Iterator<Moving> iter = movables.iterator(); iter.hasNext();) {
//			Moving moving = iter.next();
//			moving.update();
//
//			// if (a.isLanded()) {
//			// a.actionOnLanding();
//			// iter.remove();
//			// }
//
//			if (moving.getBm().intersects(ReferenceConfig.getOuter()) == -1) {
//				iter.remove(); // remove off-screen meteors
//			}
//		}
		int i = 1; // ignore Player
		while (i < movables.size())
		{
			Moving moving = movables.get(i);
			moving.update();

			if (moving.getBm().intersects(ReferenceConfig.getOuter()) == -1) {
				movables.remove(i); // remove off-screen meteors
			} else {
				i++;
			}
		}
	}

	private void renderMovables(Graphics2D g, float interpolation)
	{
		for (int i = 1; i < movables.size(); i++)
			movables.get(i).render(g, interpolation);
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
