package com.example.utils.resource;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resource
{
	// Stores all resources to be retrieved later (no repeat loading)
	private final static Map<String, BufferedImage>	map	= new HashMap<String, BufferedImage>();
	private BufferedImage							image;
	private String									fileName;

	/*
	 * Retrieve or Place an image from the HashMap storage
	 */
	public Resource(String fileName)
	{
		this.fileName = fileName;
		BufferedImage resource = map.get(fileName); // check if resource is stored
		if (resource != null) { // if resource exists, get the image
			this.image = resource;
		} else { // else load the image in
			try {
				image = ImageIO.read(getClass().getResourceAsStream(ReferenceRes.RESOURCE_LOC + fileName));
				map.put(fileName, image);
			} catch (Exception e) {
				System.err.printf("Cannot find " + fileName + "!\n");
				e.printStackTrace();
			}
		}
	}

	/*
	 * Get a subimage from a spritesheet.
	 * 
	 * 1st constructor: square size; use x, y with 0, 0 being the top left ......
	 * 2nd constructor: rectangle size; same directions as above ................
	 * 3rd constructor: variable size; use exact coords and size to get image ...
	 */
	public Resource(Resource res, int x, int y, int size)
	{
		this(res, x, y, size, size);
	}

	public Resource(Resource res, int x, int y, int width, int height)
	{
		this(res, x * width, y * height, width, height, false); // make a block coordinate system
	}

	public Resource(Resource res, int xCoord, int yCoord, int width, int height, boolean EXACT_COORDINATES)
	{
		BufferedImage resource = map.get(res.fileName);
		if (resource != null) this.image = resource;
		this.image = res.image.getSubimage(xCoord, yCoord, width, height); // get subimage
	}

	/*
	 * Render the image
	 */
	public void render(Graphics2D g, float x, float y)
	{
		g.drawImage(image, Math.round(x), Math.round(y), null);
	}

	public void render(Graphics2D g, float x, float y, float width, float height) // for resizing
	{
		g.drawImage(image, Math.round(x), Math.round(y), Math.round(width), Math.round(height), null);
	}

	/*
	 * Setters and getters
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public float getWidth()
	{
		return image.getWidth();
	}

	public float getHeight()
	{
		return image.getHeight();
	}

	public String toString()
	{
		return fileName;
	}
}
