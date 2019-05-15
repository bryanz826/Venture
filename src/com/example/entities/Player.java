package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.libs.Reference;
import com.example.libs.ReferenceResource;
import com.example.libs.Vector2D;
import com.example.main.ReferenceConfig;
import com.example.utils.ConsoleLog;
import com.example.utils.gameloop_instructions.Interactive;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;
import com.example.utils.resource.Resource;

/**
 * The player-controlled space ship that utilizes a singleton pattern allowing
 * the class to be accessed anywhere.
 */
public final class Player extends Moving implements Interactive
{
	//
	// FIELDS
	//

	/**
	 * The current target rotation (pseudo-terminal speed) in radians based on the
	 * MouseManager in floating-point precision. Used in conjuction with a rotation
	 * variable (does not exist right now) to create a more realistic rotation but
	 * current implementations do not support this yet.
	 */
	private float		targetRotation;

	/**
	 * A vector representing the resistance (or friction) on this Player to slow it
	 * down.
	 */
	private Vector2D	resistance;
	/**
	 * A visual Render that is representative of the damage done on this ship body.
	 */
	private Render		damageRender;

	//
	// CONSTRUCTORS
	//

	/**
	 * Constructs the Player with basic features.
	 */
	private Player()
	{
		super(new Vector2D(ReferenceConfig.getWidth() / 2 - 50, ReferenceConfig.getHeight() / 2 - 50), 100, 100, 7,
				1 / (float) Math.E, BoundsCombination.ALL, new Render(0, new Resource(ReferenceResource.PLAYER_LOC + "player-orange.png", true)));
		resistance = new Vector2D();
		damageRender = new Render(0, new Resource(ReferenceResource.PLAYER_LOC + "player-damaged-2.png", true));

		ConsoleLog.write("Player constructed.");
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

	/**
	 * Processes the user input accordingly based on the WASD keys and mouse
	 * location. Acceleration is adjusted from the movement keys while the rotation
	 * is determined with the mouse location.
	 */
	public void processInput()
	{
		if (KeyManager.isDown(KeyManager.A)) {
			getAcceleration().setX(-1);
		} else if (KeyManager.isDown(KeyManager.D)) {
			getAcceleration().setX(1);
		} else {
			getAcceleration().setX(0);
		}
		if (KeyManager.isDown(KeyManager.W)) {
			getAcceleration().setY(-1);
		} else if (KeyManager.isDown(KeyManager.S)) {
			getAcceleration().setY(1);
		} else {
			getAcceleration().setY(0);
		}

		targetRotation = (float) Math.atan2(MouseManager.getY() - (getPosition().getY() + getHeight() / 2),
				MouseManager.getX() - (getPosition().getX() + getWidth() / 2));
	}

	/**
	 * Updates the various movement vectors including the resistance/friction of the
	 * Player and the renders and the rotation.
	 */
	@Override
	public void update()
	{
		calcResistance();
		getVelocity().add(resistance);
		super.update();
		damageRender.update();

		getMainRender().setRadians(targetRotation);
		damageRender.setRadians(targetRotation);
	}

	/**
	 * Renders the various Player renders.
	 */
	@Override
	public void render(Graphics2D g, float interpolation)
	{
		super.render(g, interpolation);

		float x = getPosition().getX() + interpolation * getVelocity().getX();
		float y = getPosition().getY() + interpolation * getVelocity().getY();
		if (damageRender != null) damageRender.render(g, x, y);

		if (Reference.DEBUG) {
			Vector2D front = getFront();
			g.setColor(Color.MAGENTA);
			g.fillOval(Math.round(front.getX() - 5 + interpolation * getVelocity().getX()),
					Math.round(front.getY() - 5 + interpolation * getVelocity().getY()), 10, 10);

			g.setColor(Color.ORANGE);
			g.drawString("Mouse:           (" + MouseManager.getX() + ", " + MouseManager.getY() + ")", 100, 100);
			g.drawString("Location:        (" + Math.round(getPosition().getX()) + ", "
					+ Math.round(getPosition().getY()) + ")", 100, 120);
			g.drawString("Speed:           (" + getVelocity().getX(), 100, 140);
			g.drawString(", " + getVelocity().getY() + ")", 250, 140);

			g.drawString("Acceleration: (" + getAcceleration().getX(), 100, 160);
			g.drawString(", " + getAcceleration().getY() + ")", 250, 160);

			g.drawString("Resistance:   (" + resistance.getX(), 100, 180);
			g.drawString(", " + resistance.getY() + ")", 250, 180);

			g.drawString("Target Degrees: " + Math.round(Math.toDegrees(targetRotation)), 100, 200);
		}
	}

	//
	// HELPER METHODS
	//

	/**
	 * Calculates the resistance/friction of this Player and slows it down smoothly.
	 */
	private void calcResistance()
	{
		if (!userMovementInput()) {
			if (getVelocity().getX() != 0) resistance.setX(getThrust() * -getVelocity().getX() / getTargetSpd());
			else resistance.setX(0);

			if (getVelocity().getY() != 0) resistance.setY(getThrust() * -getVelocity().getY() / getTargetSpd());
			else resistance.setY(0);
		}
	}

	@Override
	protected void calcAcceleration()
	{
		float aLength = getAcceleration().getExactLength();
		if (aLength > 1) { // limit to circular movement
			getAcceleration().quickNormalize(aLength);
		}
		getAcceleration().scale(getThrust());
	}

	@Override
	protected void calcVelocity()
	{
		float vLength = getVelocity().getExactLength();
		if (vLength > getTargetSpd()) {
			getVelocity().quickNormalize(vLength);
			getVelocity().scale(getTargetSpd());
		}
		getVelocity().validateZero();
	}

	/**
	 * Determines if there is user movement input based on the WASD keys.
	 * 
	 * @return userMovementInput
	 */
	private boolean userMovementInput()
	{
		return KeyManager.isDown(KeyManager.W) && KeyManager.isDown(KeyManager.A) && KeyManager.isDown(KeyManager.S)
				&& KeyManager.isDown(KeyManager.D);
	}
}
