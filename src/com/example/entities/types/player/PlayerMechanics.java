package com.example.entities.types.player;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.Moving;
import com.example.entities.animations.Render;
import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.BoundsManager.BoundsType;
import com.example.entities.collisions.ComplexBounds;
import com.example.entities.types.ID;
import com.example.libs.Reference;
import com.example.libs.ReferenceConfig;
import com.example.libs.ReferenceResource;
import com.example.libs.Vector2D;
import com.example.utils.ConsoleLog;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;
import com.example.utils.resource.Resource;
import com.sun.glass.events.KeyEvent;

/**
 * The player-controlled space ship that utilizes a singleton pattern allowing
 * the class to be accessed anywhere.
 */
public class PlayerMechanics extends Moving implements ComplexBounds
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
	public PlayerMechanics()
	{
		super(new Vector2D(ReferenceConfig.getWidth() / 2 - 50, ReferenceConfig.getHeight() / 2 - 50), 100, 100, 7,
				1 / (float) Math.E, BoundsType.COMPLEX,
				new Render(new Resource(ReferenceResource.PLAYER_LOC + "player-orange.png", true)), ID.PLAYER);
		resistance = new Vector2D();
		damageRender = new Render(new Resource(ReferenceResource.PLAYER_LOC + "player-damaged-2.png", true));

		getBm().updateComplex(createComplex());
		setMass(getBm().getBounds());

		ConsoleLog.write("Player constructed.");
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
		if (KeyManager.isDown(KeyEvent.VK_A)) {
			getAcceleration().setX(-1);
		} else if (KeyManager.isDown(KeyEvent.VK_D)) {
			getAcceleration().setX(1);
		} else {
			getAcceleration().setX(0);
		}
		if (KeyManager.isDown(KeyEvent.VK_W)) {
			getAcceleration().setY(-1);
		} else if (KeyManager.isDown(KeyEvent.VK_S)) {
			getAcceleration().setY(1);
		} else {
			getAcceleration().setY(0);
		}

		setTargetRotation((float) Math.atan2(MouseManager.getY() - (getPosition().getY() + getHeight() / 2),
				MouseManager.getX() - (getPosition().getX() + getWidth() / 2)));
	}

	/**
	 * Updates the various movement vectors including the resistance/friction of the
	 * Player and the renders and the rotation.
	 */
	@Override
	public void update()
	{
		calcResistance();
		setVelocity(Vector2D.add(getVelocity(), resistance));
		super.update();
		damageRender.update(getWidth(), getHeight(), getRotation());

		setRotation(targetRotation);
		getBm().updateComplex(createComplex());
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
			g.setColor(Color.ORANGE);
			g.drawString("Mouse:           (" + MouseManager.getX() + ", " + MouseManager.getY() + ")", 100, 100);
			g.drawString("Location:        (" + Math.round(getPosition().getX()) + ", "
					+ Math.round(getPosition().getY()) + ")", 100, 120);
			g.drawString("Speed:            (" + getVelocity().getX(), 100, 140);
			g.drawString(", " + getVelocity().getY() + ")", 250, 140);

			g.drawString("Acceleration:  (" + getAcceleration().getX(), 100, 160);
			g.drawString(", " + getAcceleration().getY() + ")", 250, 160);

			g.drawString("Resistance:   (" + resistance.getX(), 100, 180);
			g.drawString(", " + resistance.getY() + ")", 250, 180);

			g.drawString("Rotation:         " + Math.round(Math.toDegrees(getRotation())), 100, 200);
		}
	}

	//
	// GENERAL METHODS
	//

	public Bounds[] createComplex()
	{
		Bounds[] complex = new Bounds[4];

		Vector2D center = getCenter();

		/*
		 * TOP
		 */
		float size = 22;
		float x = 38 * (float) Math.cos(getRotation()) + center.getX() - size / 2;
		float y = 38 * (float) Math.sin(getRotation()) + center.getY() - size / 2;
		complex[0] = new Bounds(new Vector2D(x, y), size, size);

		/*
		 * CENTER
		 */
		size = 60;
		x = center.getX() - size / 2;
		y = center.getY() - size / 2;
		complex[1] = new Bounds(new Vector2D(x, y), size, size);

		/*
		 * BOTTOM LEFT
		 */
		size = 24;
		x = 36 * (float) Math.cos(getRotation() + Math.toRadians(102)) + center.getX() - size / 2;
		y = 36 * (float) Math.sin(getRotation() + Math.toRadians(102)) + center.getY() - size / 2;
		complex[2] = new Bounds(new Vector2D(x, y), size, size);

		/*
		 * BOTTOM RIGHT
		 */
		size = 24;
		x = 36 * (float) Math.cos(getRotation() + Math.toRadians(-102)) + center.getX() - size / 2;
		y = 36 * (float) Math.sin(getRotation() + Math.toRadians(-102)) + center.getY() - size / 2;
		complex[3] = new Bounds(new Vector2D(x, y), size, size);

		return complex;
	}

	//
	// HELPER METHODS
	//

	/**
	 * Calculates the resistance/friction of this Player and slows it down smoothly.
	 */
	private void calcResistance()
	{
		// if there is no user input...
		if (!(KeyManager.isDown(KeyEvent.VK_W) && KeyManager.isDown(KeyEvent.VK_A) && KeyManager.isDown(KeyEvent.VK_S)
				&& KeyManager.isDown(KeyEvent.VK_D))) {
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
		setAcceleration(Vector2D.getScaled(getAcceleration(), getThrust()));
	}

	@Override
	protected void calcVelocity()
	{
		float vLength = getVelocity().getExactLength();
		if (vLength > getTargetSpd()) {
			getVelocity().quickNormalize(vLength);
			setVelocity(Vector2D.getScaled(getVelocity(), getTargetSpd()));
		}
		getVelocity().validateZero();
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Returns the targetRotation
	 * 
	 * @param targetRotation
	 *            The target rotation in radians
	 */
	public void setTargetRotation(float targetRotation)
	{
		this.targetRotation = targetRotation;
	}

	/**
	 * Returns the damageRender
	 * 
	 * @param damageRender
	 */
	public void setDamageRender(Resource damage)
	{
		damageRender.setCurrentFrame(damage);
	}
}
