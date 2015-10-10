package com.example.birdnest.activity;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.fileHandler.ConfigRecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 应用程序的入口程序,进行判断，进入不同的Activity分支
 * @author 鲁继文
 */
public class HomeActivity extends Activity
{
	private static final String Tag = "HomeActiivty" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.home_layout);
		
		Log.e(Tag, Tag);
		Intent intent = new Intent();
		if(ConfigRecorder.getBoolRecord(this, MyApplication.filenameString, MyApplication.INSTALLED_KEY, false)==false)
		{
			Log.e(Tag, "install newly");
			ConfigRecorder.recordBool(this, MyApplication.filenameString, MyApplication.INSTALLED_KEY, true);
			intent.setClass(HomeActivity.this,WelcomeActivity.class); //刚刚安装进入欢迎页
		}
		else 
		{
			Log.e(Tag, "install ago");
			intent.setClass(HomeActivity.this, Splash.class) ;  // 早已安装进入登陆 
		}
		startActivity(intent);
		this.finish();
	}

}
