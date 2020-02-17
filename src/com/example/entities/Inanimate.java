package com.example.entities;

import com.example.entities.animations.Render;
import com.example.entities.types.ID;
import com.example.refs.Vector2D;

public class Inanimate extends Entity
{
	public Inanimate(Vector2D position, float width, float height, Render mainRender, ID id)
	{
		super(position, width, height, mainRender, id);
	}
}
