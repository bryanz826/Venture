package com.example.utils.timing;

public class TimingCurve extends TimeDuration
{
	public static enum Preset
	{
		LINEAR,
		EASE_IN,
		EASE_OUT,
		EASE_IN_OUT,
		RUSH_IN_OUT
	}

	private TimingCurve.Preset	preset;
	private float		progression;

	public TimingCurve(TimingCurve.Preset preset, float seconds)
	{
		super(seconds);
		this.preset = preset;
		progression = 0;
	}

	public void update()
	{
		super.update();
		calculateProgression();
	}

	private void calculateProgression()
	{
		switch (preset)
		{
			case LINEAR:
			{
				if (getTime() < 1) {
					progression = getTime();
				}
				break;
			}

			case EASE_IN:
			{
				if (getTime() < 1f / 6) {
					progression = 8.5f * (float) Math.pow(getTime(), 2);
				} else if (getTime() < 1) {
					progression = 1.32f * (float) Math.pow(getTime() - 1, 3) + 1;
				}
				break;
			}

			case EASE_OUT:
			{
				if (getTime() < 5f / 6) {
					progression = (float) Math.cbrt(getTime() / 1.32f);
				} else if (getTime() < 1) {
					progression = -1 * (float) Math.sqrt(-1 * (getTime() - 1) / 8.5f) + 1;
				}
				break;
			}

			case EASE_IN_OUT:
			{
				if (getTime() < 0.5) {
					progression = 2 * (float) Math.pow(getTime(), 2);
				} else if (getTime() < 1) {
					progression = -2 * (float) Math.pow(getTime() - 1, 2) + 1;
				}
				break;
			}

			case RUSH_IN_OUT:
			{
				if (getTime() < 0.5) {
					progression = (float) Math.sqrt(getTime() / 2);
				} else if (getTime() < 1) {
					progression = 1 - (float) Math.sqrt(-1 * (getTime() - 1) / 2f);
				}
				break;
			}

			default:
				break;
		}
	}

	public float getProgression()
	{
		return progression;
	}
}
