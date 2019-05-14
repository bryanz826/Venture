package com.example.entities;

import com.example.entities.animations.Render;
import com.example.libs.Vector2D;

public class Inanimate extends Entity
{
	public Inanimate(Vector2D position, float width, float height, BoundsType boundsType, Render mainRender)
	{
		super(position, width, height, boundsType, mainRender);
	}
}
