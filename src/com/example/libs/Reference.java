package com.example.libs;

public class Reference
{
	public static final boolean DEBUG = true;
	
	public static float modulus(float a, float b)
	{
		return (a % b + b) % b;
	}

	public static double modulus(double a, double b)
	{
		return (a % b + b) % b;
	}
}
