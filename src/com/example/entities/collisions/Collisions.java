package com.example.entities.collisions;

import java.util.List;

import com.example.entities.Moving;
import com.example.entities.types.player.PlayerManager;
import com.example.libs.ReferenceConfig;
import com.example.libs.Vector2D;

// ur mum gei
public class Collisions
{
	private static void handleCollisionWall()
	{
		int space = 0;
		if (PlayerManager.I().getPlayer().getPosition().getX() < space) {
			PlayerManager.I().getPlayer().getPosition().setX(space);
		} else if (PlayerManager.I().getPlayer().getPosition().getX() + PlayerManager.I().getPlayer().getWidth() > ReferenceConfig.getWidth() - space) {
			PlayerManager.I().getPlayer().getPosition().setX(ReferenceConfig.getWidth() - PlayerManager.I().getPlayer().getWidth() - space);
		}

		if (PlayerManager.I().getPlayer().getPosition().getY() < space) {
			PlayerManager.I().getPlayer().getPosition().setY(space);
		} else if (PlayerManager.I().getPlayer().getPosition().getY() + PlayerManager.I().getPlayer().getHeight() > ReferenceConfig.getHeight() - space) {
			PlayerManager.I().getPlayer().getPosition().setY(ReferenceConfig.getHeight() - PlayerManager.I().getPlayer().getHeight() - space);
		}
	}

	private static void handleCollisionMovables(List<Moving> movables)
	{
		for (int i = 0; i < movables.size(); i++)
			for (int j = i + 1; j < movables.size(); j++) {
				Moving m1 = movables.get(i);
				Moving m2 = movables.get(j);
				applyElastic(m1, m2, 0.7f);
			}
	}

	public static void update(List<Moving> movables)
	{
		handleCollisionWall();
		handleCollisionMovables(movables);
	}

	public static void applyElastic(Moving m1, Moving m2, float restitution)
	{
		int intersect = m1.getBm().intersects(m2.getBm());
		if (intersect != -1) {

			/*
			 * Determine index of Bounds intersected
			 */
			int m1i = 0, m2i = 0;
			if (m1.getBm().getBounds().length > 0) m1i = m1.getBm().intersects(m2.getBm());
			if (m2.getBm().getBounds().length > 0) m2i = m2.getBm().intersects(m1.getBm());

			Vector2D collision = Vector2D.add(m1.getBm().getBounds()[m1i].getCenter(),
					m2.getBm().getBounds()[m2i].getCenter().getNegate());

			/*
			 * Separate Moving entities
			 */
			float dist = collision.getExactLength();
			float r1 = m1.getBm().getBounds()[m1i].getRadius();
			float r2 = m2.getBm().getBounds()[m2i].getRadius();
			if (dist > r1 + r2) return;

			float mtdCalc = ((r1 + r2) - dist) / dist;
			Vector2D mtd = Vector2D.getScaled(collision, mtdCalc); // min translation dist
			m1.setPosition(Vector2D.add(m1.getPosition(), Vector2D.getScaled(mtd, r1 / (r1 + r2))));
			m2.setPosition(Vector2D.add(m2.getPosition(), Vector2D.getScaled(mtd, r2 / (r1 + r2)).getNegate()));

			/*
			 * Calculate resulting velocities
			 */
			collision.quickNormalize(dist);
			float u1 = Vector2D.getDot(m1.getVelocity(), collision);
			float u2 = Vector2D.getDot(m2.getVelocity(), collision);

			// Solve for new velocities using the CONSERVATION OF MOMENTUM equation
			float ma1 = m1.getMass();
			float ma2 = m2.getMass();
			float v1 = (u1 * (ma1 - ma2) + 2 * ma2 * u2) / (ma1 + ma2);
			float v2 = (u2 * (ma2 - ma1) + 2 * ma1 * u1) / (ma1 + ma2);

			// Add new velocities
			m1.setVelocity(Vector2D.add(m1.getVelocity(), Vector2D.getScaled(collision, v1 - u1)));
			m2.setVelocity(Vector2D.add(m2.getVelocity(), Vector2D.getScaled(collision, v2 - u2)));
		}
	}
}
