package com.example.birdnest.utils;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Actity 工具支持�?
 * 
 * @author shimiso
 * 
 */
public abstract class ActivitySupport extends Activity implements IActivitySupport {

	protected Context context = null;
	protected SharedPreferences preferences;
	//protected MyApplication eimApplication;
	protected ProgressDialog pg = null;
	protected NotificationManager notificationManager;
	protected ImageLoader loader;
	protected DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
	 	initImageLoader();
		// preferences = getSharedPreferences(Constant.LOGIN_SET, 0);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		pg = new ProgressDialog(context);
		//	eimApplication = (MyApplication) getApplication();
		// eimApplication.addActivity(this);
	}

	/**
	 * @author YouMingyang 点击屏幕其他地方关闭输入�?
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		closeInput();
		return true;
	}

	public abstract void OnClick(View view);

	/**
	 * 设置ActionBar 标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			((TextView) findViewById(R.id.title)).setText(title);
		}
	}

	public void setWriteText(String text) {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			((TextView) findViewById(R.id.write_text)).setText(text);
		}
	}

	/**
	 * 
	 * 隐藏actionbar
	 */
	public void hideActionbar() {
		findViewById(R.id.activity_top).setVisibility(View.GONE);
	}

	/**
	 * 设置ActionBar 显示刷新按钮
	 */

	public void disProgress() {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			findViewById(R.id.activity_top_right_action).setVisibility(View.GONE);
			findViewById(R.id.loading_more).setVisibility(View.VISIBLE);
		}
	}

	public void disWirte() {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			findViewById(R.id.actionbar_right_lay).setVisibility(View.GONE);
			findViewById(R.id.actionbar_write_lay).setVisibility(View.VISIBLE);
		}
	}

	public void hideActionRight() {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			findViewById(R.id.vertical_line).setVisibility(View.GONE);
			findViewById(R.id.action_bar_right).setVisibility(View.GONE);
		}
	}

	public void setActionRight(String text) {
		View actionBar = findViewById(R.id.activity_top);
		if (actionBar != null) {
			((TextView) findViewById(R.id.activity_top_right_action)).setText(text);
		}
	}

	public void initImageLoader() {
		loader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		loader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		/**
		 * 设置为竖�?
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * @author YouMingyang 修改为protected
	 */
	@Override
	protected void onDestroy() {
		if (loader != null) {
			 loader.stop();
			/*loader.destroy();*/
		}
		super.onDestroy();
	}

	@Override
	public ProgressDialog getProgressDialog() {
		return pg;
	}

	@Override
	public void startService() {
		// 好友联系人服�?

	}

	/**
	 * 
	 * �?毁服�?.
	 * 
	 * @author shimiso
	 * @update 2012-5-16 下午12:16:08
	 */
	@Override
	public void stopService() {

		// // 好友联系人服�?
		// Intent server = new Intent(context, IMContactService.class);
		// context.stopService(server);
		// // 聊天服务
		// Intent chatServer = new Intent(context, IMChatService.class);
		// context.stopService(chatServer);
		//
		// // 自动恢复连接服务
		// Intent reConnectService = new Intent(context,
		// ReConnectService.class);
		// context.stopService(reConnectService);
		//
		// // 系统消息连接服务
		// Intent imSystemMsgService = new Intent(context,
		// IMSystemMsgService.class);
		// context.stopService(imSystemMsgService);

		// // 好友联系人服�?
		// Intent server = new Intent(context, IMContactService.class);
		// context.stopService(server);
		// // 聊天服务
		// Intent chatServer = new Intent(context, IMChatService.class);
		// context.stopService(chatServer);
		//
		// // 自动恢复连接服务
		// Intent reConnectService = new Intent(context,
		// ReConnectService.class);
		// context.stopService(reConnectService);
		//
		// // 系统消息连接服务
		// Intent imSystemMsgService = new Intent(context,
		// IMSystemMsgService.class);
		// context.stopService(imSystemMsgService);

	}

	@Override
	public void isExit() {
		new AlertDialog.Builder(context).setTitle("确定�?出吗?").setNeutralButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				stopService();

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).show();
	}

	@Override
	public boolean hasInternetConnected() {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo network = manager.getActiveNetworkInfo();
			if (network != null && network.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateInternet() {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			openWirelessSet();
			return false;
		} else {
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		openWirelessSet();
		return false;
	}

	@Override
	public boolean hasLocationGPS() {
		LocationManager manager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		if (manager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLocationNetWork() {
		LocationManager manager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		if (manager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void checkMemoryCard() {
		if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			new AlertDialog.Builder(context).setTitle("系统提示").setMessage("请检查内存卡").setPositiveButton("设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					Intent intent = new Intent(Settings.ACTION_SETTINGS);
					context.startActivity(intent);
				}
			}).setNegativeButton("�?�?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();

				}
			}).create().show();
		}
	}

	public void openWirelessSet() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle("系统提示").setMessage("无可用网络连接，请检查网络设置！")

		.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				context.startActivity(intent);
			}
		}).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		// =======
		// .setPositiveButton("设置", new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.cancel();
		// Intent intent = new Intent(
		// Settings.ACTION_WIRELESS_SETTINGS);
		// context.startActivity(intent);
		// }
		// })
		// .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int whichButton) {
		// dialog.cancel();
		// }
		// });
		// >>>>>>> 添加附近的人和群组资源文�?
		dialogBuilder.show();
	}

	/**
	 * 
	 * 显示toast
	 * 
	 * @param text
	 * @param longint
	 * @author shimiso
	 * @update 2012-6-28 下午3:46:18
	 */
	public void showToast(String text, int longint) {
		Toast.makeText(context, text, longint).show();
	}

	@Override
	public void showToast(String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * 关闭键盘事件
	 * 
	 * @author shimiso
	 * @update 2012-7-4 下午2:34:34
	 */
	public void closeInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null && this.getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 
	 * 发出Notification的method.
	 * 
	 * @param iconId
	 *            图标
	 * @param contentTitle
	 *            标题
	 * @param contentText
	 *            你内�?
	 * @param activity
	 * @author shimiso
	 * @update 2012-5-14 下午12:01:55
	 */
	public void setNotiType(int iconId, String contentTitle, String contentText, Class activity, String from) {
		/*
		 * 创建新的Intent，作为点击Notification留言条时�? 会运行的Activity
		 */
		Intent notifyIntent = new Intent(this, activity);
		notifyIntent.putExtra("to", from);
		// notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		/* 创建PendingIntent作为设置递延运行的Activity */
		PendingIntent appIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
		/* 创建Notication，并设置相关参数 */
		Notification myNoti = new Notification();
		// 点击自动消失
		myNoti.flags = Notification.FLAG_AUTO_CANCEL;
		/* 设置statusbar显示的icon */
		myNoti.icon = iconId;
		/* 设置statusbar显示的文字信�? */
		myNoti.tickerText = contentTitle;
		/* 设置notification发生时同时发出默认声�? */
		myNoti.defaults = Notification.DEFAULT_SOUND;
		/* 设置Notification留言条的参数 */
		myNoti.setLatestEventInfo(this, contentTitle, contentText, appIntent);
		/* 送出Notification */
		notificationManager.notify(0, myNoti);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public SharedPreferences getLoginUserSharedPre() {
		return preferences;
	}

	@Override
	public boolean getUserOnlineState() {
		// preferences = getSharedPreferences(Constant.LOGIN_SET,0);

		return preferences.getBoolean(MyApplication.IS_ONLINE, true);

	}

	@Override
	public void setUserOnlineState(boolean isOnline) {
		// preferences = getSharedPreferences(Constant.LOGIN_SET,0);
		preferences.edit().putBoolean(MyApplication.IS_ONLINE, isOnline).commit();

	}

	@Override
	public MyApplication getEimApplication() {
		return null;
	}

}
