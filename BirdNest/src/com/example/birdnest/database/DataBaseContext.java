package com.example.birdnest.database;

import android.content.Context;


public class DataBaseContext {
	
	private static DataHelper dataHelper;
	
	private static Object INSTANCE_LOCK = new Object();  //Å£±Æ
	
	public static DataHelper getInstance(Context context) {
	       synchronized (INSTANCE_LOCK) {
	           if (dataHelper == null) {
	        	   dataHelper = new DataHelper(context);
	           }
	           return dataHelper;
	       }
	   }
}
