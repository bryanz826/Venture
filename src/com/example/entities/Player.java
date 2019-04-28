package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.animations.Render;
import com.example.main.ReferenceConfig;
import com.example.utils.input.KeyManager;

public class Player extends Living
{
	private final float	diagTargetSpd;
	private int			tickCircle;

	public Player(float x, float y, Render render)
	{
		super(x, y, 70, 70, 7, 0.25f, render);
		diagTargetSpd = getTargetSpd() * 0.70710677f;
	}

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
			tickCircle = 0; // reset tick for use in diagonal below
		}

		// Models a circular curve to transform 8-directional movement to circular
		// movement
		if (diagonal) {
			if (++tickCircle > 60) tickCircle = 60; // cap tick

			// Horizontal
			if (KeyManager.isDown(KeyManager.A)) {
				if (getDx() < -diagTargetSpd) { // if slowing down
					// decelerate based on the curve
					setD2x(getThrust() * (float) Math.sin(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() + getD2x() > -diagTargetSpd) { // cap speed
						setDx(-diagTargetSpd);
						setD2x(0);
					}
				} else if (getDx() > -diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2x(-getThrust() * (float) Math.cos(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
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
					setD2x(-getThrust() * (float) Math.sin(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDx() + getD2x() < diagTargetSpd) { // cap speed
						setDx(diagTargetSpd);
						setD2x(0);
					}
				} else if (getDx() < diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2x(getThrust() * (float) Math.cos(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
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
					setD2y(getThrust() * (float) Math.sin(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() + getD2y() > -diagTargetSpd) { // cap speed
						setDy(-diagTargetSpd);
						setD2y(0);
					}
				} else if (getDy() > -diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2y(-getThrust() * (float) Math.cos(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
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
					setD2y(-getThrust() * (float) Math.sin(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
					if (getDy() + getD2y() < diagTargetSpd) { // cap speed
						setDy(diagTargetSpd);
						setD2y(0);
					}
				} else if (getDy() < diagTargetSpd) { // if speeding up
					// accelerate based on the curve
					setD2y(getThrust() * (float) Math.cos(tickCircle * Math.PI / 4 / ReferenceConfig.TARGET_UPS));
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
				// decelerate modeling "arrive at" curve where slower as approach destination
				// essentially makes slowing down smoother
				setD2x(getThrust() * -getDx() / getTargetSpd());
				if (getDx() > -0.1f) { // ensure stopping (not at 0 because "arrive at" never reaches 0)
					setDx(0);
					setD2x(0);
				}
			} else if (getDx() > 0) {
				// decelerate modeling "arrive at" curve where slower as approach destination
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
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		g.setColor(Color.RED);
		g.fillOval(Math.round(getX() + interpolation * getDx()), Math.round(getY() + interpolation * getDy()),
				Math.round(getWidth()), Math.round(getHeight()));

		g.setColor(Color.ORANGE);
		g.drawString("Location: (" + Math.round(getX()) + ", " + Math.round(getY()) + ")", 100, 100);
		g.drawString("Speed: (" + getDx() + ", " + getDy() + ")", 100, 120);
		g.drawString("Acceleration: (" + getD2x() + ", " + getD2y() + ")", 100, 140);
		g.drawString("Moving Diagonal: " + isMovingDiagonal(), 100, 160);
		g.drawString("Now Moving Diagonal: " + isNowMovingDiagonal(), 100, 180);
	}

	private static boolean isMovingDiagonal()
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
}
