package com.example.entities.collisions;

import java.util.List;

import com.example.entities.ID;
import com.example.entities.Moving;
import com.example.entities.Player;
import com.example.libs.ReferenceConfig;
import com.example.libs.Vector2D;

// ur mum gei
public class Collisions
{
	private static void handleCollisionWall()
	{
		int space = 0;
		if (Player.I().getPosition().getX() < space) {
			Player.I().getPosition().setX(space);
		} else if (Player.I().getPosition().getX() + Player.I().getWidth() > ReferenceConfig.getWidth() - space) {
			Player.I().getPosition().setX(ReferenceConfig.getWidth() - Player.I().getWidth() - space);
		}

		if (Player.I().getPosition().getY() < space) {
			Player.I().getPosition().setY(space);
		} else if (Player.I().getPosition().getY() + Player.I().getHeight() > ReferenceConfig.getHeight() - space) {
			Player.I().getPosition().setY(ReferenceConfig.getHeight() - Player.I().getHeight() - space);
		}
	}

	private static void handleCollisionMovables(List<Moving> movables)
	{
		for (int i = 0; i < movables.size(); i++)
			for (int j = i + 1; j < movables.size(); j++) {
				Moving m1 = movables.get(i);
				Moving m2 = movables.get(j);
				
				/*
				 * Player Collision
				 */
				if (m1.getId() == ID.PLAYER) {
					complexCirc(m1, m2, 0.7f);
				}

				/*
				 * Others
				 */
				else {
					circCirc(m1, m2, 0.7f);
				}
			}
	}

	public static void update(List<Moving> movables)
	{
		handleCollisionWall();
		handleCollisionMovables(movables);
	}

	public static void complexCirc(Moving m1, Moving m2, float restitution)
	{
		boolean intersects = false;
		int index = -1;
		for (int i = 0; i < m1.getComplex().length; i++)
			if (m1.getComplex()[i].intersects(m2.getCirc())) {
				intersects = true;
				index = i;
			}

		if (intersects) {
			Vector2D collision = Vector2D.add(m1.getComplex()[index].getCenter(), m2.getCenter().getNegate());
			float dist = collision.getExactLength();

			float r1 = m1.getComplex()[index].getRadius();
			float r2 = m2.getCirc().getRadius();
			if (dist > r1 + r2) return;

			float mtdCalc = ((r1 + r2) - dist) / dist;

			Vector2D mtd = Vector2D.getScaled(collision, mtdCalc); // min translation dist
			m1.setPosition(Vector2D.add(m1.getPosition(), Vector2D.getScaled(mtd, r1 / (r1 + r2))));
			m2.setPosition(Vector2D.add(m2.getPosition(), Vector2D.getScaled(mtd, r2 / (r1 + r2)).getNegate()));

			mtd.normalize();

			// impulse
			Vector2D v = Vector2D.add(m1.getVelocity(), m2.getVelocity().getNegate());
			float vn = Vector2D.getDot(v, mtd);

			if (vn > 0f) return;

			float impulseCalc = -((1 + restitution) * vn) / 2f;
			Vector2D impulse = Vector2D.getScaled(mtd, impulseCalc);

			m1.setVelocity(Vector2D.add(m1.getVelocity(), impulse));
			m2.setVelocity(Vector2D.add(m2.getVelocity(), impulse.getNegate()));
		}
	}

	public static void circCirc(Moving m1, Moving m2, float restitution)
	{
		if (m1.getCirc().intersects(m2.getCirc())) {
			Vector2D collision = Vector2D.add(m1.getCenter(), m2.getCenter().getNegate());
			float dist = collision.getExactLength();

			float r1 = m1.getCirc().getRadius();
			float r2 = m2.getCirc().getRadius();
			if (dist > r1 + r2) return;

			float mtdCalc = ((r1 + r2) - dist) / dist;

			Vector2D mtd = Vector2D.getScaled(collision, mtdCalc); // min translation dist
			m1.setPosition(Vector2D.add(m1.getPosition(), Vector2D.getScaled(mtd, r1 / (r1 + r2))));
			m2.setPosition(Vector2D.add(m2.getPosition(), Vector2D.getScaled(mtd, r2 / (r1 + r2)).getNegate()));

			mtd.normalize();

			// impulse
			Vector2D v = Vector2D.add(m1.getVelocity(), m2.getVelocity().getNegate());
			float vn = Vector2D.getDot(v, mtd);

			if (vn > 0f) return;

			float impulseCalc = -((1 + restitution) * vn) / 2f;
			Vector2D impulse = Vector2D.getScaled(mtd, impulseCalc);

			m1.setVelocity(Vector2D.add(m1.getVelocity(), impulse));
			m2.setVelocity(Vector2D.add(m2.getVelocity(), impulse.getNegate()));
		}
	}
}
