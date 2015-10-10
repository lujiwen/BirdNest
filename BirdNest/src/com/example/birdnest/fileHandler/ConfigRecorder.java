package com.example.birdnest.fileHandler;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * 软件和用户参数存储类 操作sharepreference 
 * @author 鲁继文
 **/
public class ConfigRecorder 
{
	public static final String filenameString = "BirdNest";
	public static final String INSTALLED_KEY ="installed";
	
	/** 
	 * store int data into xml file 
	 * @param context 
	 * @param filename the name of the XML file 
	 * @param keyStr the key of the data
	 * @param value the int value 
	 */
	public static void  recordInt(Context context,String filename,String key,int value)
	{
		 SharedPreferences sharedPreferences = context.getSharedPreferences(filename,context.MODE_PRIVATE);
	     Editor editor = sharedPreferences.edit();   
	     editor.putInt(key, value);
	     editor.commit();	 
	}
	
	/** 
	 * store string data into xml file 
	 * @param context 
	 * @param filename the name of the XML file 
	 * @param keyStr the key of the data
	 * @param value the string value 
	 */
	public static void recordString(Context context,String filename,String key,String value)
	{
		 SharedPreferences sharedPreferences = context.getSharedPreferences(filename,context.MODE_PRIVATE);
	     Editor editor = sharedPreferences.edit();   
	     editor.putString(key, value);
	     editor.commit();
	}
	
	/** 
	 * store boolean data into xml file 
	 * @param context 
	 * @param filename the name of the XML file 
	 * @param keyStr the key of the data
	 * @param value the string value 
	 */
	public static void recordBool(Context context,String filename,String key,boolean value)
	{
		 SharedPreferences sharedPreferences = context.getSharedPreferences(filename,context.MODE_PRIVATE);
	     Editor editor = sharedPreferences.edit();   
	     editor.putBoolean(key, value);
	     editor.commit();
	}
	
	 /**
	  * get int data from XML 
	  * @param context
	  * @param keyStr
	  * @param defValue if  the data is never stored before, then return it   
	  */
	public static int getIntRecord(Context context,String filename,String key,int defaultValue)
	{
		 SharedPreferences sharedPreferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
		 return  sharedPreferences.getInt(key , defaultValue);	  
	}
	
	 /**
	  * get boolean data from XML 
	  * @param context
	  * @param keyStr
	  * @param defValue if  the data is never stored before, then return it   
	  */
	public static boolean getBoolRecord(Context context,String filename,String key,Boolean defaultValue)
	{
		if(context!=null)
		{
			 SharedPreferences sharedPreferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
			 return  sharedPreferences.getBoolean(key, defaultValue);	  
		}
		return false ;
	}
	
	 /**
	  * get string data from XML 
	  * @param context
	  * @param key 
	  * @param defValue if  the data is never stored before, then return it   
	  */
	public static String getStringRecord(Context context,String filename,String key,String defaultValye)
	{
		 SharedPreferences sharedPreferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
		 String s =  sharedPreferences.getString(key, defaultValye);
		 Log.e("getRecord",  s);
		 return  sharedPreferences.getString(key ,defaultValye);
	}
	
	
}
