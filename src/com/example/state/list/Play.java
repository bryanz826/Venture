package com.example.state.list;

import java.awt.Color;
import java.awt.Graphics2D;

import com.example.entities.EntityManager;
import com.example.main.ReferenceConfig;
import com.example.state.State;

public class Play implements State
{
	private EntityManager em;

	@Override
	public void enter()
	{	
		em = new EntityManager();
	}
	
	@Override
	public void processInput()
	{
		// TODO Auto-generated method stub
		em.processInput();
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		em.update();
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		g.setColor(new Color(58, 46, 63));
		g.fillRect(0, 0, ReferenceConfig.getWidth(), ReferenceConfig.getHeight());
		em.render(g, interpolation);
	}

	@Override
	public String getName()
	{
		return "PLAY";
	}

}
