package com.example.entities.types.player;

import java.awt.Graphics2D;

public final class Player
{
	//
	// FIELDS
	//

	/**
	 * The mechanics-controlled spaceship.
	 */
	private PlayerMechanics mechanics;

	//
	// CONSTRUCTORS
	//

	private Player()
	{
		mechanics = new PlayerMechanics();
	}

	//
	// SINGLETON IMPLEMENTATION
	//

	/**
	 * Implements a singleton pattern into this Player using a private PlayerHolder
	 * class for lazy initialization. When the Player class is loaded, this inner
	 * class will not be loaded but can be loaded whenever it is needed using I().
	 */
	private static class PlayerHolder
	{
		/**
		 * An instance of the Player class that cannot be instantiated more than once
		 * (because of the final keyword).
		 */
		private static final Player INSTANCE = new Player();
	}

	/**
	 * Returns the instance of this class to be accessed globally. Because of this,
	 * an instance of this class may theoretically be anywhere in this project.
	 * 
	 * @return INSTANCE
	 */
	public static Player I()
	{
		return PlayerHolder.INSTANCE;
	}

	//
	// GAMELOOP METHODS
	//

	public void processInput()
	{
		mechanics.processInput();
	}

	public void update()
	{
		mechanics.update();
	}

	public void render(Graphics2D g, float interpolation)
	{
		mechanics.render(g, interpolation);
	}

	//
	// SETTER AND GETTER METHODS
	//
	public PlayerMechanics getMechanics()
	{
		return mechanics;
	}
}
