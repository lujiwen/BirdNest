package com.example.birdnest.activity;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.fileHandler.ConfigRecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Ӧ�ó������ڳ���,�����жϣ����벻ͬ��Activity��֧
 * @author ³����
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
			intent.setClass(HomeActivity.this,WelcomeActivity.class); //�ոհ�װ���뻶ӭҳ
		}
		else 
		{
			Log.e(Tag, "install ago");
			intent.setClass(HomeActivity.this, Splash.class) ;  // ���Ѱ�װ�����½ 
		}
		startActivity(intent);
		this.finish();
	}

}
