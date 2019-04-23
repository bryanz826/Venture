package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLog
{	
	public static void write(String str) {
		String currentTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		System.out.println(currentTime + "\t| " + str);
	}
	
	public static void warn(String str) {
		String currentTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		System.out.print(currentTime + "\t| ");
		System.err.println(str);
	}
}
