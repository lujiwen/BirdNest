package com.example.birdnest.updateManager;

import java.io.InputStream;

import com.example.birdnest.R;

import android.content.Context;

 

public class AppVersionManager {
	private static AppVersionManager instance;

	private AppVersion appVersion;

	public synchronized static AppVersionManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppVersionManager(context);
		}
		return instance;
	}

	public AppVersionManager(Context context) {
		try {
			InputStream is = context.getResources().openRawResource(
					R.raw.version);
			AppVersionParser parser = new AppVersionParser(is);
			appVersion = parser.getAppVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AppVersion getAppVersion() {
		return appVersion;
	}
}
