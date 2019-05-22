package com.example.entities;

import com.example.entities.animations.Render;
import com.example.entities.collisions.BoundsManager.BoundsType;
import com.example.libs.ReferenceConfig;
import com.example.libs.ReferenceMath;
import com.example.libs.ReferenceResource;
import com.example.libs.Vector2D;
import com.example.utils.resource.Resource;

public class Meteor extends Moving
{
	private float radPerTick;

	public Meteor(Vector2D position, Vector2D velocity, float size, float radPerSec)
	{
		super(position, size, size, ReferenceMath.getRandomFloat(5, 12), 0, BoundsType.CIRC, null, ID.METEOR);
		setMainRender(new Render(new Resource(
				ReferenceResource.METEOR_LOC + "meteor-" + chooseRandColor() + "-" + chooseRandType() + ".png")));
		radPerTick = radPerSec / ReferenceConfig.TARGET_UPS;

		reposition(size / 2);
		setVelocity(Vector2D.getScaled(velocity, getTargetSpd()));
	}

	//
	// GAMELOOP METHODS
	//

	@Override
	public void update()
	{
		super.update();
		setRotation(getRotation() + radPerTick);
	}

	//
	// HELPER METHODS
	//

	/**
	 * Chooses a random color meteor between brown and gray.
	 * 
	 * @return random color
	 */
	private String chooseRandColor()
	{
		String color = "";

		int randColorIndex = ReferenceMath.getRandomInt(1, 3);
		switch (randColorIndex)
		{
			case 1:
				color = "brown";
				break;
			case 2:
				color = "gray";
				break;
		}

		return color;
	}

	/**
	 * Chooses a random meteor of four types.
	 * 
	 * @return meteor type
	 */
	private String chooseRandType()
	{
		return "" + ReferenceMath.getRandomInt(1, 5);
	}
}
