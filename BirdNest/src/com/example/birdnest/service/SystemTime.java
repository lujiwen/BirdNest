package com.example.birdnest.service;

import android.text.format.DateFormat;


/**
 * 获取系统时间
 **/
public class SystemTime 
{
	/**
	 * 获取系统时分秒
	 **/
	public static String getSystemTime(String separator)
	{
		long systime = System.currentTimeMillis();
		CharSequence date = DateFormat.format("hh"+separator+"mm"+separator+"ss", systime);	
		return date.toString();
	}
	
	/**
	 * 获取系统年月日
	 **/
	public static String getSystemDate(String separator)
	{
		long systime = System.currentTimeMillis();
		CharSequence date = DateFormat.format("yyyy"+separator+"MM"+separator+"dd", systime);	
		return date.toString();
	}
	
	public static long getSystemTimeMillis()
	{
		return System.currentTimeMillis();
	}
	
}
