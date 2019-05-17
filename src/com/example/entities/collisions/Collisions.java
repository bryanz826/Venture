package com.example.entities.collisions;

import com.example.entities.Player;
import com.example.libs.ReferenceConfig;

public class Collisions
{
	private static void handleCollisionWall()
	{
		int space = 0;
		if (Player.I().getPosition().getX() < space) {
			Player.I().getPosition().setX(space);
		} else if (Player.I().getPosition().getX()
				+ Player.I().getWidth() > ReferenceConfig.getWidth() - space) {
					Player.I().getPosition()
							.setX(ReferenceConfig.getWidth() - Player.I().getWidth() - space);
				}

		if (Player.I().getPosition().getY() < space) {
			Player.I().getPosition().setY(space);
		} else if (Player.I().getPosition().getY()
				+ Player.I().getHeight() > ReferenceConfig.getHeight() - space) {
					Player.I().getPosition()
							.setY(ReferenceConfig.getHeight() - Player.I().getHeight() - space);
				}
	}

	public static void update()
	{
		handleCollisionWall();
	}
}
