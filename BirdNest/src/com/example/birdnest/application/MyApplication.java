package com.example.birdnest.application;

import java.util.LinkedList;
import java.util.List;

import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.User;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application
{
	private static MyApplication instance;
	private List<Activity> activities;
    public static String hostIp = "192.168.1.199:8080";
    public static boolean islogIn ; //判别登陆标志
	public static final String IS_ONLINE = "is_online";
	public static User user ;
	public static final String IS_LOGIN = "is_login";
	public static final String filenameString = "BirdNest";
	public static final String INSTALLED_KEY ="installed";
	public static final String LOADED_TEL_KEY ="loaded_tel";
	public static final String NET_ERROR   = "{network error}";
	public static final String REMEBER＿ME = "remeber_me" ;
	public static final String TEL_NUMBER = "tel_num" ;
	private DataHelper dataHelper ; 
	@Override
	public void onCreate() {
		super.onCreate();
		dataHelper = new DataHelper(this);	
	}

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}
	
	public void addActivity(Activity activity) {
		if (activities == null) {
			activities = new LinkedList<Activity>();
		}
		activities.add(activity);
	}

		
	public static User getAppUser()
	{
		if(user==null)
		{
		//	user = data
		}
		return user ;
	}
	/**
	 *退出 pp
	 */
	public void exit() {
		for (Activity activity : activities) {
			activity.finish();
		}
		System.exit(0);
	}
	public static void  exitApp1()
	{
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static void  exitApp2()
	{
		System.exit(0);
	}
	
}
