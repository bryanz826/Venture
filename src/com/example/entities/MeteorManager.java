package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.example.entities.collisions.Bounds;
import com.example.libs.Reference;
import com.example.libs.ReferenceConfig;
import com.example.libs.ReferenceMath;
import com.example.libs.ReferenceRender;
import com.example.libs.Vector2D;
import com.example.utils.gameloop_instructions.Loopable;

/**
 * Manages the meteors' cooldown and launch.
 * 
 * @author poroia
 */
public class MeteorManager implements Loopable
{
	//
	// FIELDS
	//

	/**
	 * The target area size proportion compared to the screen.
	 */
	private float			targetAreaProportion;

	/**
	 * The target area of the meteors around the Player.
	 */
	private Bounds			targetArea;

	/**
	 * The target area size proportion compared to the screen.
	 */
	private float			failAreaProportion;

	/**
	 * The hitting area of the meteors if they fail to hit the target area.
	 */
	private Bounds			failArea;

	/**
	 * The chance that the the meteors will hit the target area. If it misses, it
	 * will hit within a proportion of 0.9 of the screen
	 */
	private float			successRate;

	/**
	 * The time window in ticks that a meteor can be launched. The meteor launch is
	 * random within the time window.
	 */
	private int				launchWindow;

	/**
	 * The time preparation in ticks where a meteor cannot be launched and
	 * calculations are made.
	 */
	private int				launchPreparation;

	/**
	 * The launch cooldown then the launch window combined.
	 */
	private int				launchSegment;

	/**
	 * The number of meteors spawned in the launch window. Cannot be more than the
	 * launchPreparation length in ticks.
	 */
	private int				launchCount;

	/**
	 * A list of all the launch times determined at the start of each launch time
	 * cooldown.
	 */
	private List<Integer>	launchTimes;

	/**
	 * A holder for the meteors that will update and render each one.
	 */
	private List<Meteor>	meteors;

	/**
	 * A timer
	 */
	private int				tick;

	//
	// CONSTRUCTORS
	//

	public MeteorManager(float targetAreaProportion, float failAreaProportion, float successRate,
			float launchPreparationSec, float launchWindowSec, int launchCount)
	{
		targetArea = new Bounds(Bounds.Type.RECT);
		failArea = new Bounds(Bounds.Type.RECT);

		setTargetAreaProportion(targetAreaProportion);
		setFailAreaProportion(failAreaProportion);
		setSuccessRate(successRate);
		setLaunchPreparation(ReferenceMath.toTicks(launchPreparationSec));
		setLaunchWindow(ReferenceMath.toTicks(launchWindowSec));
		launchSegment = launchPreparation + launchWindow;
		setLaunchCount(launchCount);
		launchTimes = new ArrayList<Integer>(launchCount);
		meteors = new ArrayList<Meteor>();
		tick = -1;

		updateTargetArea(targetAreaProportion);
		updateFailArea(failAreaProportion);
	}

	//
	// GAMELOOP METHODS
	//

	@Override
	public void update()
	{
		tick++;

		updateTargetArea(targetAreaProportion);

		handleLaunchTiming();

		for (Iterator<Meteor> iter = meteors.iterator(); iter.hasNext();) {
			Meteor meteor = iter.next();
			meteor.update();

			// if (a.isLanded()) {
			// a.actionOnLanding();
			// iter.remove();
			// }

			if (!meteor.getCirc().intersects(ReferenceConfig.getOuter())) {
				iter.remove(); // remove off-screen meteors
			}
		}
	}

	@Override
	public void render(Graphics2D g, float interpolation)
	{
		for (Meteor meteor : meteors) {
			meteor.render(g, interpolation);
		}

		if (Reference.DEBUG) {
			g.setColor(new Color(255, 128, 128));
			ReferenceRender.drawRect(g, failArea);
			ReferenceRender.drawString(g, "failArea", failArea);

			g.setColor(new Color(128, 255, 128));
			ReferenceRender.drawInterpolatedRect(g, targetArea, Player.I().getVelocity(), interpolation);
			ReferenceRender.drawInterpolatedString(g, "targetArea", targetArea, Player.I().getVelocity(),
					interpolation);
		}
	}

	//
	// GENERAL METHODS
	//

	/**
	 * Launches a meteor. Calculates launch and target coordinates, calculates its
	 * velocity vector, randomizes its size and rotation, and adds it to the
	 * manager.
	 */
	public void launchMeteor()
	{
		// determine launch and target coordinates
		Vector2D launch = ReferenceMath.getRandPerimeterPoint(ReferenceConfig.getOuter());
		Vector2D target = null;
		if (ThreadLocalRandom.current().nextFloat() < successRate) target = ReferenceMath.getRandInPoint(targetArea);
		else target = ReferenceMath.getRandInPoint(failArea);

		// determine velocity vector
		float rad = launch.getRadians(target);
		Vector2D velocity = new Vector2D(rad);

		// randomize size and rotation
		int size = Math.round(ReferenceMath.getRandomFloatNormal(120, 25));
		float radPerSec = ReferenceMath.getRandomFloatNormal(0, 1.5f * (float) Math.PI);

		// add it
		meteors.add(new Meteor(launch, velocity, size, radPerSec));
	}

	/**
	 * 
	 */
	public void handleLaunchTiming()
	{
		int launchSegTime = tick % launchSegment;

		// LAUNCH PREPARATION 0: INITIALIZE ARRAY
		if (launchSegTime == 0) {
			int i = 0;
			while (launchTimes.size() < launchCount)
				launchTimes.add(i++);
		}

		// LAUNCH PREPARATION 1: PREPARE CALCULATIONS
		if (launchSegTime < launchTimes.size()) { // validate inBounds
			if (launchSegTime == launchTimes.get(launchSegTime)) {
				int randTime = ReferenceMath.getRandomInt(1, launchWindow);
				launchTimes.set(launchSegTime, randTime); // calculate rand times
			}
		}

		// LAUNCH PREPARATION 2: SORT launchTimes
		else if (launchSegTime == launchPreparation - 1) {
			Collections.sort(launchTimes);
		}

		// LAUNCH WINDOW: METEORS RELEASED
		else if (launchSegTime > launchPreparation) {
			if (launchTimes.size() > 0) { // if there are meteors left
				while (launchSegTime == launchPreparation + launchTimes.get(0)) {
					launchMeteor();
					launchTimes.remove(0);

					if (launchTimes.size() == 0) break;
				}
			}
		}
	}

	/**
	 * Sets the size of the targetArea around the ship based on a ratio to the
	 * screen.
	 * 
	 * @param proportion
	 *            The size ratio compared to the screen
	 */
	public void updateTargetArea(float proportion)
	{
		float width = ReferenceConfig.getWidth() * proportion;
		float height = ReferenceConfig.getHeight() * proportion;
		
		float x = Player.I().getCenter().getX() - width / 2;
		float y = Player.I().getCenter().getY() - height / 2;

		targetArea.setRect(new Vector2D(x, y), width, height);
	}

	/**
	 * Sets the size of the failArea around the center of the screen based on a
	 * ratio to the screen.
	 * 
	 * @param proportion
	 *            The size ratio compared to the screen
	 */
	public void updateFailArea(float proportion)
	{
		float width = ReferenceConfig.getWidth() * proportion;
		float height = ReferenceConfig.getHeight() * proportion;

		float x = ReferenceConfig.getWidth() / 2 - width / 2;
		float y = ReferenceConfig.getHeight() / 2 - height / 2;

		failArea.setRect(new Vector2D(x, y), width, height);
	}

	//
	// SETTER AND GETTER METHODS
	//

	/**
	 * Sets the target area proportion.
	 * 
	 * @param proportion
	 *            The ratio of target area rectangle to the screen.
	 */
	public void setTargetAreaProportion(float proportion)
	{
		this.targetAreaProportion = proportion;
		updateFailArea(failAreaProportion);
	}

	/**
	 * Sets the fail area proportion.
	 * 
	 * @param proportion
	 *            The ratio of fail area rectangle to the screen.
	 */
	public void setFailAreaProportion(float proportion)
	{
		this.failAreaProportion = proportion;
	}

	/**
	 * Sets the successRate.
	 * 
	 * @param successRate
	 *            The chance the meteor will hit the targetArea.
	 */
	public void setSuccessRate(float successRate)
	{
		this.successRate = successRate;
	}

	/**
	 * Sets the launch time window.
	 * 
	 * @param launchWindow
	 *            The time window in seconds that a meteor can be launched.
	 */
	public void setLaunchWindow(int launchWindow)
	{
		this.launchWindow = launchWindow;
	}

	/**
	 * Sets the launch time preparation.
	 * 
	 * @param launchPreparation
	 *            The time window in seconds that a meteor cannot be launched and
	 *            launch is prepared.
	 */
	public void setLaunchPreparation(int launchPreparation)
	{
		this.launchPreparation = launchPreparation;
	}

	/**
	 * Sets the launch count. Cannot be greater than launch preparation in ticks
	 * 
	 * @param launchCount
	 *            The number of meteors launched in the launch window.
	 */
	public void setLaunchCount(int launchCount)
	{
		this.launchCount = launchCount;
		if (launchCount > launchPreparation - 2) launchCount = (int) launchPreparation - 2;
	}

	/**
	 * Returns the target area rect of the meteors.
	 * 
	 * @return targetArea
	 */
	public Bounds getTargetArea()
	{
		return targetArea;
	}

	/**
	 * Returns the fail area rect of the meteors.
	 * 
	 * @return failArea
	 */
	public Bounds getFailArea()
	{
		return failArea;
	}
}
