package com.example.entities;

import com.example.entities.animations.Render;
import com.example.libs.ReferenceMath;
import com.example.libs.ReferenceResource;
import com.example.libs.Vector2D;
import com.example.main.ReferenceConfig;
import com.example.utils.resource.Resource;

public class Meteor extends Moving
{
	private float radPerTick;

	public Meteor(Vector2D position, Vector2D velocity, float size, float radPerSec)
	{
		super(position, size, size, ReferenceMath.getRandomFloat(5, 12), 0, BoundsType.RECT_CIRC_BOUNDS, null);
		setMainRender(new Render(0, size, size, new Resource(
				ReferenceResource.METEOR_LOC + "meteor-" + chooseRandColor() + "-" + chooseRandType() + ".png")));
		radPerTick = radPerSec / ReferenceConfig.TARGET_UPS;

		reposition(size / 2);
		velocity.scale(getTargetSpd());
		setVelocity(velocity);
	}

	@Override
	public void update()
	{
		super.update();
		getMainRender().setRadians(getMainRender().getRadians() + radPerTick);
	}

	//
	// HELPER METHODS
	//

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

	private String chooseRandType()
	{
		return "" + ReferenceMath.getRandomInt(1, 5);
	}
}
