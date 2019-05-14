package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLog
{	
	public static void write(String str) { // yyyy-MM-dd 
		String currentTime =  new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
		System.out.println(currentTime + "\t| " + str);
	}
	
	public static void warn(String str) { // yyyy-MM-dd 
		String currentTime =  new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
		System.out.print(currentTime + "\t| ");
		System.err.println(str);
	}
}
