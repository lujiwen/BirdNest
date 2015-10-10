package com.example.birdnest.activity;

import com.example.birdnest.R;
import com.example.birdnest.AsyncTask.QueryContactTask;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.fileHandler.ConfigRecorder;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 启动屏   等待一段时间进入登陆页面 
 * 
 * @author ljw
 * 
 */
public class Splash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		final boolean  has_contacts =  ConfigRecorder.getBoolRecord(Splash.this,MyApplication.filenameString, MyApplication.LOADED_TEL_KEY, false);
		new Handler().postDelayed(new Runnable() {
			@Override
		
			public void run() {
				if(!has_contacts)
				{
					QueryContactTask queryContactTask = new QueryContactTask(Splash.this, null) ;
					queryContactTask.execute();
				}
				
				// 延迟3秒进入登录界面
				boolean isrember = ConfigRecorder.getBoolRecord(Splash.this, MyApplication.filenameString, MyApplication.REMEBER＿ME, false);
				boolean isLogin = ConfigRecorder.getBoolRecord(Splash.this, MyApplication.filenameString, MyApplication.IS_LOGIN, false);
				Intent intent = null ;
				if(!isrember)
				{
					 intent = new Intent(Splash.this, LoginActivity.class);
				}
				else if(isrember&&isLogin)
				{
					intent = new Intent(Splash.this, MainActivity.class);
				}
				else if(isrember&!isLogin)
				{
					intent = new Intent(Splash.this, LoginActivity.class);
				}
				startActivity(intent);
				Splash.this.finish();
			}
		}, 1000);
	}
}
