package com.example.birdnest.service;

import android.text.format.DateFormat;


/**
 * ��ȡϵͳʱ��
 **/
public class SystemTime 
{
	/**
	 * ��ȡϵͳʱ����
	 **/
	public static String getSystemTime(String separator)
	{
		long systime = System.currentTimeMillis();
		CharSequence date = DateFormat.format("hh"+separator+"mm"+separator+"ss", systime);	
		return date.toString();
	}
	
	/**
	 * ��ȡϵͳ������
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
