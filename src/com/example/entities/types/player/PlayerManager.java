package com.example.entities.types.player;

import java.awt.Graphics2D;

import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;

public final class PlayerManager
{
	//
	// FIELDS
	//
	
	/**
	 * The player-controlled spaceship.
	 */
	private Player player;
	
	//
	// CONSTRUCTORS
	//
	
	private PlayerManager()
	{
		player = new Player();
	}

	//
	// SINGLETON IMPLEMENTATION
	//

	/**
	 * Implements a singleton pattern into this Player using a private PlayerHolder
	 * class for lazy initialization. When the Player class is loaded, this inner
	 * class will not be loaded but can be loaded whenever it is needed using I().
	 */
	private static class PlayerManagerHolder
	{
		/**
		 * An instance of the Player class that cannot be instantiated more than once
		 * (because of the final keyword).
		 */
		private static final PlayerManager INSTANCE = new PlayerManager();
	}

	/**
	 * Returns the instance of this class to be accessed globally. Because of this,
	 * an instance of this class may theoretically be anywhere in this project.
	 * 
	 * @return INSTANCE
	 */
	public static PlayerManager I()
	{
		return PlayerManagerHolder.INSTANCE;
	}
	
	//
	// GAMELOOP METHODS
	//

	/**
	 * Processes the user input accordingly based on the WASD keys and mouse
	 * location. Acceleration is adjusted from the movement keys while the rotation
	 * is determined with the mouse location.
	 */
	public void processInput()
	{
		/*
		 * PLAYER
		 */
		if (KeyManager.isDown(KeyManager.A)) {
			player.getAcceleration().setX(-1);
		} else if (KeyManager.isDown(KeyManager.D)) {
			player.getAcceleration().setX(1);
		} else {
			player.getAcceleration().setX(0);
		}
		if (KeyManager.isDown(KeyManager.W)) {
			player.getAcceleration().setY(-1);
		} else if (KeyManager.isDown(KeyManager.S)) {
			player.getAcceleration().setY(1);
		} else {
			player.getAcceleration().setY(0);
		}

		player.setTargetRotation((float) Math.atan2(MouseManager.getY() - (player.getPosition().getY() + player.getHeight() / 2),
				MouseManager.getX() - (player.getPosition().getX() + player.getWidth() / 2)));
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics2D g, float interpolation)
	{
		
	}
	
	//
	// SETTER AND GETTER METHODS
	//
	public Player getPlayer()
	{
		return player;
	}
}
