package com.example.libs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ReferenceResource
{
	public static final String	RESOURCE_LOC	= "/com/example/xres/";

	public static final String	PLAYER_LOC		= "player/";
	public static final String	METEOR_LOC		= "meteor/";

	public static BufferedImage rotate90(BufferedImage image)
	{
		int size = image.getWidth();

		BufferedImage res = new BufferedImage(size, size, image.getType());

		Graphics2D g2D = res.createGraphics();
		g2D.rotate(Math.PI / 2, size / 2f, size / 2f);
		g2D.drawRenderedImage(image, null);

		return res;
	}
}
