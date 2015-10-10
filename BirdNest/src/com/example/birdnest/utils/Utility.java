package com.example.birdnest.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.birdnest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 工具类
 * */
public class Utility 
{
	// 获取手机屏幕尺寸
	public static DisplayMetrics getWindowMetrics(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	
	public static int getDefaultPhotoWidth(Activity activity, int columnNum) {
		return (getWindowMetrics(activity).widthPixels - (columnNum - 1) * 2) / columnNum;
	}

	/**
	 * 获得微博发布的时间
	 * 
	 * @param oldTime
	 * @param currentDate
	 * @return
	 */
	public static String getTimeStr(Date oldTime, Date currentDate) {
		long time1 = currentDate.getTime();
		long time2 = oldTime.getTime();
		long time = (time1 - time2) / 1000;

		if (time >= 0 && time < 60) {
			return "刚才";
		} else if (time >= 60 && time < 3600) {
			return time / 60 + "分钟前";
		} else if (time >= 3600 && time < 3600 * 24) {
			return time / 3600 + "小时前";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			return sdf.format(oldTime);
		}
	}

	/**
	 * 解码加密的字符串
	 * 
	 * @param content
	 * @return
	 */
	public static String decodeString(String content) {
		if (content != null) {
			try {
				return new String(Base64.decode(content, android.util.Base64.DEFAULT));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
/**
 *  设置图片缩略图大小
*/	public static void setViewSize(View view, int width, int height) {
		LayoutParams params = (LayoutParams) view.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}

	/**
	 * 启动一个第三方apk的程序
	 * */
	public static void startPackage(Context context,String packageName,String className)
	{
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageName, className));
		intent.setAction(Intent.ACTION_VIEW);
		context.startActivity(intent);
	}
	
	public static void disImg(final ImageLoader loader, final DisplayImageOptions options, final ImageView imgView, final String imgUri, final int width) {
		setViewSize(imgView, width, width);
		loader.displayImage(imgUri, imgView, new SimpleImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				ImageView iView = (ImageView) view;
				final String tag = (String) iView.getTag();
				if (tag.equals(imageUri)) {
					super.onLoadingStarted(imageUri, view);
				} else {
					iView.setImageResource(R.drawable.app_logo);
				}
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				ImageView iView = (ImageView) view;
				final String tag = (String) iView.getTag();
				if (tag.equals(imageUri)) {
					super.onLoadingComplete(imageUri, imgView, loadedImage);
				}
			}
		});

	}
	/**
	 * 启动另外一个Activity
	 * */
	public static void startActivity(Context context,String packageName,String className)
	{
/*		ComponentName con = ComponentName.unflattenFromString("com.android.settings/.SoundSettings");
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.setComponent(con);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(localIntent);*/
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageName, className));
		context.startActivity(intent);
	}
	
	/**
	 * 启动activity时不自动弹出软键盘
	 * */
	private void  alwaysHideSoftInput(Activity activty)
	{
	
		activty.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	/**
	 * 安装apk文件
	 * @param context
	 * @param filename
	 */
	public static void installApk(Context context, String filename) {
		File file = new File(filename);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
		context.startActivity(intent);
	}  

	
}
