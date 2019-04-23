package com.example.main;

import java.awt.GraphicsDevice;

import com.example.utils.ConsoleLog;

public class LowReference
{
	public static final int FRAME_CAP = VenturePanel.FRAME_CAP;
	public static final int TARGET_UPS = VenturePanel.TARGET_UPS;
	
	public static int getWidth() {
		return VentureRunner.WIDTH;
	}
	
	public static int getHeight() {
		return VentureRunner.HEIGHT;
	}
	
	public static int getFPS() {
		return VentureRunner.getGame().getFPS();
	}
	
	public static int getUPS() {
		ConsoleLog.write(""+ VentureRunner.getGame().getFPS());
		return VentureRunner.getGame().getUPS();
	}
	
	public static GraphicsDevice getDevice() {
		return VentureRunner.getDevice();
	}
	
	public static boolean isFull() {
		return VentureRunner.isFull();
	}
}
