package com.example.utils.gameloop_i;

import java.awt.Graphics2D;

public interface Loopable
{
	void update();

	void render(Graphics2D g, float interpolation);
}
