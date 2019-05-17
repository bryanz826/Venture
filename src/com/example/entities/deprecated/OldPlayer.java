package com.example.entities.deprecated;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.libs.Reference;
import com.example.libs.ReferenceConfig;
import com.example.libs.ReferenceResource;
import com.example.utils.input.KeyManager;
import com.example.utils.input.MouseManager;
import com.example.utils.resource.Resource;

public class OldPlayer
{
	private float	width, height;
	private float	x, y;
	private float	dx, dy;
	private float	d2x, d2y;

	private float	targetSpd;
	private float	diagTargetSpd;
	private float	thrust;
	private int		tickMovement;

	private float 	rotation;
	private float	targetRotation;

	private Render	mainRender;
	private Render	damage;

	public OldPlayer()
	{
		setWidth(100);
		setHeight(100);
		setX(ReferenceConfig.getWidth() / 2 - 50);
		setY(ReferenceConfig.getHeight() / 2 - 50);
		setThrust(1 / (float) Math.E);
		targetSpd = 7;
		diagTargetSpd = targetSpd * 0.70710677f;
		mainRender = new Render(new Resource(ReferenceResource.PLAYER_LOC + "player-orange.png", true));
		damage = new Render(new Resource(ReferenceResource.PLAYER_LOC + "player-damaged-3.png", true));
	}

	/**
	 * 
	 */
	public void processInputSimple() // for demonstration purposes only (not actually used)
	{
		if (KeyManager.isDown(KeyManager.A)) {
			setDx(-5);
		} else if (KeyManager.isDown(KeyManager.D)) {
			setDx(5);
		} else {
			setDx(0);
		}

		if (KeyManager.isDown(KeyManager.W)) {
			setDy(-5);
		} else if (KeyManager.isDown(KeyManager.S)) {
			setDy(5);
		} else {
			setDy(0);
		}
	}

	public void processInput()
	{
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		// * * * * * * * * * * * * KEY INPUT * * * * * * * * * * * *
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

		/*
		 * Singular Movement (Only WASD pressed individually)
		 */
		int hCount = 0; // keep track of # of horizontal keys pressed (look location used for more info)
		int vCount = 0; // keep track of # of vertical keys pressed (look location used for more info)

		// Horizontal
		if (KeyManager.isDown(KeyManager.A)) {
			hCount++;
			setD2x(-getThrust()); // accelerate
			if (getDx() < -getTargetSpd()) { // cap speed
				setDx(-getTargetSpd());
				setD2x(0);
			} else if (getDx() == -getTargetSpd()) setD2x(0); // validate capped speed
		}

		if (KeyManager.isDown(KeyManager.D)) {
			hCount++;
			setD2x(getThrust()); // accelerate
			if (getDx() > getTargetSpd()) { // cap speed
				setDx(getTargetSpd());
				setD2x(0);
			} else if (getDx() == getTargetSpd()) setD2x(0); // validate capped speed
		}

		// Vertical
		if (KeyManager.isDown(KeyManager.W)) {
			vCount++;
			setD2y(-getThrust()); // accelerate
			if (getDy() < -getTargetSpd()) { // cap speed
				setDy(-getTargetSpd());
				setD2y(0);
			} else if (getDy() == -getTargetSpd()) setD2y(0); // validate capped speed
		}

		if (KeyManager.isDown(KeyManager.S)) {
			vCount++;
			setD2y(getThrust()); // accelerate
			if (getDy() > getTargetSpd()) { // cap speed
				setDy(getTargetSpd());
				setD2y(0);
			} else if (getDy() == getTargetSpd()) setD2y(0); // validate capped speed
		}

		/*
		 * Multi-Directional Movement (Two adjacent keys are pressed)
		 */
		boolean diagonal = isMovingDiagonal(); // save "if moving diagonal"
		if (isNowMovingDiagonal()) {
			tickMovement = 0; // reset tick for use in diagonal below
		}

		// Models a circular curve to transform 8-directional movement to circular
		// movement
		if (diagonal) {
			if (++tickMovement > 60) tickMovement = 60; // cap tick

			// Horizontal
			if (KeyManager.isDown(KeyManager.A)) {
				if (getDx() < -diagTargetSpd) { // if slowing down
					// decelerate based on the curve
					setD2x(getThrust() * (float) Math.sin(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() + getD2x() > -diagTargetSpd) { // cap speed
						setDx(-diagTargetSpd);
						setD2x(0);
					}
				} else if (getDx() > -diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2x(-getThrust() * (float) Math.cos(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() < -diagTargetSpd) { // cap speed
						setDx(-diagTargetSpd);
						setD2x(0);
					}
				} else { // validate capped speed
					setD2x(0);
				}
			} else if (KeyManager.isDown(KeyManager.D)) {
				if (getDx() > diagTargetSpd) { // if slowing down
					// decelerate based on the curve
					setD2x(-getThrust() * (float) Math.sin(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() + getD2x() < diagTargetSpd) { // cap speed
						setDx(diagTargetSpd);
						setD2x(0);
					}
				} else if (getDx() < diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2x(getThrust() * (float) Math.cos(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() > diagTargetSpd) {
						setDx(diagTargetSpd); // cap speed
						setD2x(0);
					}
				} else { // validate capped speed
					setD2x(0);
				}
			}

			// Vertical
			if (KeyManager.isDown(KeyManager.W)) {
				if (getDy() < -diagTargetSpd) { // if slowing down
					// decelerate based on the curve
					setD2y(getThrust() * (float) Math.sin(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() + getD2y() > -diagTargetSpd) { // cap speed
						setDy(-diagTargetSpd);
						setD2y(0);
					}
				} else if (getDy() > -diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2y(-getThrust() * (float) Math.cos(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() < -diagTargetSpd) { // cap speed
						setDy(-diagTargetSpd);
						setD2y(0);
					}
				} else { // validate capped speed
					setD2y(0);
				}
			} else if (KeyManager.isDown(KeyManager.S)) {
				if (getDy() > diagTargetSpd) { // if slowing down
					// decelerate based on the curve
					setD2y(-getThrust() * (float) Math.sin(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() + getD2y() < diagTargetSpd) { // cap speed
						setDy(diagTargetSpd);
						setD2y(0);
					}
				} else if (getDy() < diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2y(getThrust() * (float) Math.cos(tickMovement * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() > diagTargetSpd) { // cap speed
						setDy(diagTargetSpd);
						setD2y(0);
					}
				} else { // validate capped speed
					setD2y(0);
				}
			}
		}

		/*
		 * Slowing Down To Stop (occurs when letting go of keys)
		 */
		// Horizontal
		if (hCount != 1) { // if no horizontal buttons are pressed (A or D) OR if both are pressed
			if (hCount == 2) { // validate that speed is capped
				setD2x(0);
			}

			if (getDx() < 0) {
				// decelerate modeling "arrive at" curve where slowing down
				// essentially makes slowing down smoother
				setD2x(getThrust() * -getDx() / getTargetSpd());
				if (getDx() > -0.1f) { // ensure stopping (not at 0 because "arrive at" never reaches 0)
					setDx(0);
					setD2x(0);
				}
			} else if (getDx() > 0) {
				// decelerate modeling "arrive at" curve where slowing down
				// essentially makes slowing down smoother
				setD2x(-getThrust() * getDx() / getTargetSpd());
				if (getDx() < 0.1f) { // ensure stopping (not at 0 because "arrive at" never reaches 0)
					setDx(0);
					setD2x(0);
				}

			}
		}

		// Vertical
		if (vCount != 1) { // if no vertical buttons are pressed (W or S) OR if both are pressed
			if (vCount == 2) { // validate that speed is capped
				setD2y(0);
			}

			if (getDy() < 0) {
				// decelerate modeling "arrive at" curve where slower as approach destination
				// essentially makes slowing down smoother
				setD2y(getThrust() * -getDy() / getTargetSpd());
				if (getDy() > -0.1f) { // ensure stopping (not at 0 because "arrive at" never reaches 0)
					setDy(0);
					setD2y(0);
				}
			} else if (getDy() > 0) {
				// decelerate modeling "arrive at" curve where slower as approach destination
				// essentially makes slowing down smoother
				setD2y(-getThrust() * getDy() / getTargetSpd());
				if (getDy() < 0.1f) { // ensure stopping (not at 0 because "arrive at" never reaches 0)
					setDy(0);
					setD2y(0);
				}
			}
		}

		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		// * * * * * * * * * * * * MOUSE INPUT * * * * * * * * * * *
		// * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

		targetRotation = (float) Math.atan2(MouseManager.getY() - getCenterY(), MouseManager.getX() - getCenterX());

		// private float rotation; (at top)
		//
		// float d = (getFrontX() - getCenterX()) * (MouseManager.getY() - getCenterY())
		// - (getFrontY() - getCenterY()) * (MouseManager.getX() - getCenterX());
		//
		// if (d > 0) {
		// rotation += Math.PI / 30;
		// float magnitudeRotation = rotation - Reference.modulus(rotation, 2 * (float)
		// Math.PI);
		// targetRotation += magnitudeRotation;
		// if (rotation > targetRotation) rotation = targetRotation;
		// } else if (d < 0) {
		// rotation -= Math.PI / 30;
		// float magnitudeRotation = rotation - Reference.modulus(rotation, 2 * (float)
		// Math.PI);
		// targetRotation += magnitudeRotation;
		// if (rotation < targetRotation) rotation = targetRotation;
		// }

	}

	public void update()
	{
		rotation = targetRotation;

		setDx(getDx() + getD2x());
		setDy(getDy() + getD2y());
		setX(getX() + getDx());
		setY(getY() + getDy());

		mainRender.update(width, height, rotation);
		damage.update(width, height, rotation);
	}

	public void render(Graphics2D g, float interpolation)
	{
		mainRender.render(g, Math.round(getX() + interpolation * getDx()),
				Math.round(getY() + interpolation * getDy()));
		damage.render(g, Math.round(getX() + interpolation * getDx()), Math.round(getY() + interpolation * getDy()));

		g.setColor(Color.MAGENTA);
		g.fillOval(Math.round(getFrontX() - 5 + interpolation * getDx()),
				Math.round(getFrontY() - 5 + interpolation * getDy()), 10, 10);

		if (Reference.DEBUG) {
			g.setColor(new Color(128, 128, 128, 64));
			g.fillRect(Math.round(getX() + interpolation * getDx()), Math.round(getY() + interpolation * getDy()),
					Math.round(getWidth()), Math.round(getHeight()));

			g.setColor(Color.MAGENTA);
			g.fillOval(Math.round(getFrontX() - 5 + interpolation * getDx()),
					Math.round(getFrontY() - 5 + interpolation * getDy()), 10, 10);

			g.setColor(Color.ORANGE);
			g.drawString("Mouse:           (" + MouseManager.getX() + ", " + MouseManager.getY() + ")", 100, 100);

			g.drawString("Location:        (" + Math.round(getX()) + ", " + Math.round(getY()) + ")", 100, 120);

			g.drawString("Speed:           (" + getDx(), 100, 140);
			g.drawString(", " + getDy() + ")", 250, 120);

			g.drawString("Acceleration: (" + getD2x(), 100, 160);
			g.drawString(", " + getD2y() + ")", 250, 140);

			g.drawString("Target Degrees: " + Math.round(Math.toDegrees(targetRotation)), 100, 180);

			g.drawString("Front: " + getFrontX() + ", " + getFrontY(), 100, 200);
		}
	}

	private static boolean isMovingDiagonal() // check if two keys not along the same axis is pressed
	{
		int hCount = 0;
		int vCount = 0;

		if (KeyManager.isDown(KeyManager.A)) hCount++;
		if (KeyManager.isDown(KeyManager.D)) hCount++;
		if (KeyManager.isDown(KeyManager.W)) vCount++;
		if (KeyManager.isDown(KeyManager.S)) vCount++;

		return hCount == 1 && vCount == 1;
	}

	public static boolean isNowMovingDiagonal()
	{
		int hCount = 0;
		int vCount = 0;
		if (KeyManager.isDown(KeyManager.A)) hCount++;
		if (KeyManager.isDown(KeyManager.D)) hCount++;
		if (KeyManager.wasPressed(KeyManager.W)) vCount++;
		if (KeyManager.wasPressed(KeyManager.S)) vCount++;

		if (hCount == 1 && vCount == 1) return true;

		hCount = 0;
		vCount = 0;
		if (KeyManager.wasPressed(KeyManager.A)) hCount++;
		if (KeyManager.wasPressed(KeyManager.D)) hCount++;
		if (KeyManager.isDown(KeyManager.W)) vCount++;
		if (KeyManager.isDown(KeyManager.S)) vCount++;

		if (hCount == 1 && vCount == 1) return true;

		return false;
	}

	/**
	 * Calculates and returns the X coordinate of the top-center of Entity.
	 * 
	 * @return frontX
	 */
	public float getFrontX()
	{
		float xLoc = getHeight() / 2 * (float) Math.cos(rotation);
		return xLoc + getCenterX();
	}

	/**
	 * Calculates and returns the Y coordinate of the top-center of Entity.
	 * 
	 * @return frontY
	 */
	public float getFrontY()
	{
		float yLoc = getHeight() / 2 * (float) Math.sin(rotation);
		return yLoc + getCenterY();
	}

	/**
	 * Calculates and returns the X coordinate of the absolute center of Entity.
	 * 
	 * @return centerX
	 */
	public float getCenterX()
	{
		return x + width / 2;
	}

	/**
	 * Calculates and returns the Y coordinate of the absolute center of Entity.
	 * 
	 * @return centerY
	 */
	public float getCenterY()
	{
		return y + height / 2;
	}

	//
	// SETTER AND GETTER METHODS
	//

	public void setWidth(float width)
	{
		this.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public void setDx(float dx)
	{
		this.dx = dx;
	}

	public void setDy(float dy)
	{
		this.dy = dy;
	}

	public void setD2x(float d2x)
	{
		this.d2x = d2x;
	}

	public void setD2y(float d2y)
	{
		this.d2y = d2y;
	}

	public void setThrust(float thrust)
	{
		this.thrust = thrust;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getX()
	{
		return x;
	}

	public float getHeight()
	{
		return height;
	}

	public float getDx()
	{
		return dx;
	}

	public float getDy()
	{
		return dy;
	}

	public float getD2x()
	{
		return d2x;
	}

	public float getD2y()
	{
		return d2y;
	}

	public float getTargetSpd()
	{
		return targetSpd;
	}

	public float getThrust()
	{
		return thrust;
	}

	public float getTargetRotation()
	{
		return targetRotation;
	}
}
