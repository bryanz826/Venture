package com.example.utils.resource;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.example.libs.ReferenceResource;

public class Resource
{
	//
	// FIELDS
	//

	/**
	 * A HashMp that stores all resources to be retrieved later (no repeat loading)
	 * and uses a file path to access the BufferedImage
	 */
	private final Map<String, BufferedImage>	map	= new HashMap<String, BufferedImage>();

	/**
	 * The file path of the stored BufferedImage
	 */
	private String								fileName;

	/**
	 * The BufferedImage stored in the HashMap
	 */
	private BufferedImage						image;

	//
	// CONSTRUCTORS
	//

	/**
	 * Copy Constructor for basic copying
	 * 
	 * @param resource
	 *            The other resource to be copied
	 */
	public Resource(Resource resource)
	{
		this.image = resource.image;
		this.fileName = resource.fileName;
	}

	/**
	 * Retrieve or Place an image from the HashMap storage
	 * 
	 * @param fileName
	 *            The file path to retrieve/place the image
	 */
	public Resource(String fileName)
	{
		this.fileName = fileName;
		BufferedImage resource = map.get(fileName); // check if resource is stored
		if (resource != null) { // if resource exists, get the image
			this.image = resource;
		} else { // else load the image in
			try {
				image = ImageIO.read(getClass().getResourceAsStream(ReferenceResource.RESOURCE_LOC + fileName));
				map.put(fileName, image);
			} catch (Exception e) {
				System.err.printf("Cannot find " + fileName + "!\n");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Constructs a Resource that does what Resource(String fileName) does but also
	 * has the option of rotating the image 90 degrees clockwise
	 * 
	 * @param fileName
	 * @param rotate90
	 */
	public Resource(String fileName, boolean rotate90)
	{
		this(fileName);
		if (rotate90) {
			setImage(ReferenceResource.rotate90(getImage()));
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

	//
	// GAMELOOP METHODS
	//

	public void render(Graphics2D g, float x, float y)
	{
		g.drawImage(image, Math.round(x), Math.round(y), null);
	}

	public void render(Graphics2D g, float x, float y, float width, float height) // for resizing
	{
		g.drawImage(image, Math.round(x), Math.round(y), Math.round(width), Math.round(height), null);
	}

	//
	// SETTERS AND GETTERS
	//
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
	
	//
	// INHERENT METHODS
	//

	public String toString()
	{
		return hashCode() + " | " + fileName;
	}
}
