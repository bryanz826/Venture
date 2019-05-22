package com.example.entities.collisions;

import java.util.List;

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
				elasticCollision(m1, m2, 0.7f);
			}
	}

	public static void update(List<Moving> movables)
	{
		handleCollisionWall();
		handleCollisionMovables(movables);
	}

	public static void elasticCollision(Moving m1, Moving m2, float restitution)
	{
		int intersect = m1.getBm().intersects(m2.getBm());
		if (intersect != -1) {

			int m1i = 0, m2i = 0;
			if (m1.getBm().getBounds().length > 0) m1i = m1.getBm().intersects(m2.getBm());
			if (m2.getBm().getBounds().length > 0) m2i = m2.getBm().intersects(m1.getBm());

			Vector2D collision = Vector2D.add(m1.getBm().getBounds()[m1i].getCenter(),
					m2.getBm().getBounds()[m2i].getCenter().getNegate());
			float dist = collision.getExactLength();

			float r1 = m1.getBm().getBounds()[m1i].getRadius();
			float r2 = m2.getBm().getBounds()[m2i].getRadius();
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
