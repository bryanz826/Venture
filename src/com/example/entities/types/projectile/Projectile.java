package com.example.entities.types.projectile;

import com.example.entities.Moving;
import com.example.entities.animations.Render;
import com.example.entities.collisions.Bounds;
import com.example.entities.collisions.BoundsManager.BoundsType;
import com.example.entities.collisions.ComplexBounds;
import com.example.entities.types.ID;
import com.example.refs.ReferenceResource;
import com.example.refs.Vector2D;
import com.example.utils.resource.Resource;

public class Projectile extends Moving implements ComplexBounds
{
	public static enum Config
	{
		// ONE
		BULLET(1, 0, 0, 0, 00000000000000,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "bullet.png", true))),
		MINI(1, 0, 0, 0, 000000000000000,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "mini.png", true))),
		STATUS_TIPPED(1, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "status-tipped.png", true))),

		// TWO
		MINIGUN(2, 0, 0, 0, 000000000,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "minigun.png", true))),
		HOMING_MISSILE(2, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "homing-missile.png", true))),
		ANTI_MISSILE(2, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "anti-missile.png", true))),

		// THREE
		BALLISTIC_MISSILE(3, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "ballistic-missile.png", true))),
		CRUISE_MISSILE(3, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "cruise-missile.png", true))),
		SNIPER(3, 0, 0, 0, 000000000000000,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "sniper.png", true))),

		// FOUR
		BIG_CHUNGUS(4, 0, 0, 0, 0,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "big-chungus.png", true))),
		LASER(4, 0, 0, 0, 000000000000000,
				new Render(new Resource(ReferenceResource.PROJECTILE_LOC + "laser.png", true)));

		private int		size;
		private float	iDamage;
		private float	iFireRate;
		private float	iTargetSpd, iThrust;
		private Render	mainRender;

		Config(int size, float iDamage, float iFireRate, float iTargetSpd, float iThrust, Render mainRender)
		{
			this.size = size;
			this.iDamage = iDamage;
			this.iFireRate = iFireRate;
			this.iTargetSpd = iTargetSpd;
			this.iThrust = iThrust;
			this.mainRender = mainRender;
		}

		public float getInitDamage()
		{
			return iDamage;
		}

		public float getInitTargetSpd()
		{
			return iTargetSpd;
		}

		public float getInitThrust()
		{
			return iThrust;
		}
	}

	//
	// FIELDS
	//

	private int		level;

	private float	damage;

	/**
	 * Is true if the projectile is no longer colliding with the parent ship after
	 * the initial release.
	 */
	private boolean	released;

	/**
	 * The size of the front part of the complex bounds.
	 */
	private float	complexBoundsSize;

	/**
	 * The uniqueCode from the parent ship.
	 */
	private int		parentUniqueCode;

	//
	// CONSTRUCTORS
	//

	public Projectile(Vector2D position, float size, float targetSpd, float thrust, Render mainRender,
			int parentUniqueCode)
	{
		super(position, size, size, targetSpd, thrust, BoundsType.COMPLEX, mainRender, ID.PROJECTILE);
		this.parentUniqueCode = parentUniqueCode;
	}

	public Projectile(Projectile.Config type, Vector2D position, int level)
	{
		super(position, 0, 0, 0, 0, BoundsType.COMPLEX, null, ID.PROJECTILE);

		this.level = level;
		setTargetSpd(type.getInitTargetSpd() * (float) Math.pow(1.1f, level));
		setThrust(type.getInitThrust() * (float) Math.pow(1.1f, level));

		switch (type)
		{
			case BULLET:
			{
				setSize(10);
				complexBoundsSize = 5;
				break;
			}
			case MINI:
			{

				break;
			}
			case STATUS_TIPPED:
			{

				break;
			}

			case MINIGUN:
			{

				break;
			}
			case HOMING_MISSILE:
			{

				break;
			}
			case ANTI_MISSILE:
			{

				break;
			}

			case BALLISTIC_MISSILE:
			{

				break;
			}
			case CRUISE_MISSILE:
			{

				break;
			}
			case SNIPER:
			{

				break;
			}

			case BIG_CHUNGUS:
			{

				break;
			}
			case LASER:
			{

				break;
			}

			default:
				break;
		}
	}

	//
	// GENERAL METHODS
	//

	@Override
	public Bounds[] createComplex()
	{
		Bounds[] complex = new Bounds[1];

		Vector2D center = getCenter();
		float halfHeight = getHeight() / 2;

		float topCircleDist = halfHeight - complexBoundsSize / 2;

		/*
		 * TOP
		 */
		float x = topCircleDist * (float) Math.cos(getRotation()) + center.getX() - complexBoundsSize / 2;
		float y = topCircleDist * (float) Math.sin(getRotation()) + center.getY() - complexBoundsSize / 2;
		complex[0] = new Bounds(new Vector2D(x, y), complexBoundsSize, complexBoundsSize);

		return complex;
	}

	//
	// SETTER AND GETTER METHODS
	//

	public void setReleased(boolean released)
	{
		this.released = released;
	}

	public float getDamage()
	{
		return damage;
	}

	public boolean isReleased()
	{
		return released;
	}

	public int getParentUniqueCode()
	{
		return parentUniqueCode;
	}

}
