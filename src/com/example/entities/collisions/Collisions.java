package com.example.entities.collisions;

import com.example.entities.Player;
import com.example.main.ReferenceConfig;

public class Collisions
{
	private static void handleCollisionWall(Player player)
	{
		int space = 0;
		if (player.getX() < space) {
			player.setX(space);
		} else if (player.getX() + player.getWidth() > ReferenceConfig.getWidth() - space) {
			player.setX(ReferenceConfig.getWidth() - player.getWidth() - space);
		}
		
		if (player.getY() < space) {
			player.setY(space);
		} else if (player.getY() + player.getHeight() > ReferenceConfig.getHeight() - space) {
			player.setY(ReferenceConfig.getHeight() - player.getHeight() - space);
		}
	}

	public static void update(Player player)
	{
		handleCollisionWall(player);
	}
}
