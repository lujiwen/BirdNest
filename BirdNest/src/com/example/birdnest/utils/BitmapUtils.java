package com.example.birdnest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.birdnest.fileHandler.FileHandler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

public class BitmapUtils {

	// 计算抽样�?
	public static int calculateInSampleSize(BitmapFactory.Options options, int objWidth, int objHeight) {
		// 源图片的高度和宽�?
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if (height > objHeight || width > objWidth) {
			int heightRatio = Math.round((float) height / (float) objHeight);
			int widthRatio = Math.round((float) width / (float) objWidth);
			// 选择宽和高中�?小的比率作为inSampleSize的�?�，这样可以保证�?终图片的宽和�?
			// �?定都会大于等于目标的宽和高�??
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		Log.i("inSampleSize:", "" + inSampleSize);
		return inSampleSize;
	}


	// 获取缩略�?
	public static Bitmap getThumbnails(Context context, String imgPath, int objWidth, int objHeight) {
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				is = new FileInputStream(imgPath);
			else
				// 读写手机内置存储
				is = context.openFileInput(imgPath);
			// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			is = new FileInputStream(imgPath);// 不知道why
			// 调用上面定义的方法计算inSampleSize�?
			options.inSampleSize = calculateInSampleSize(options, objWidth, objHeight);
			// 使用获取到的inSampleSize值再次解析图�?
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(is, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	
	// 保存图片到SD�?
	public static String saveBitmap(Context context, Bitmap bitmap, String imageDir) {
		// if (Environment.getExternalStorageState().equals(// SD卡可�?
		// Environment.MEDIA_MOUNTED)) {
		File fileDir = new File(FileHandler.getExternalStoragePath().toString() + "/" + imageDir);
		if (!fileDir.exists())
			fileDir.mkdirs();
		try {
			imageDir = fileDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png";
			FileOutputStream fos = new FileOutputStream(imageDir);
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
			fos.close();
			Log.i("Bitmap stored in:", imageDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// } else {
		// Toast.makeText(context, "SD卡不可用", Toast.LENGTH_SHORT).show();
		// }
		return imageDir;
	}

	public static String getNewPath(Context context, String imageDir, String imgName) {
		String newPath = FileHandler.getExternalStoragePath().toString() + "/" + imageDir + "/" + imgName.substring(imgName.lastIndexOf("/") + 1);
		Log.d("newPath=", newPath);
		return newPath;
	}

	// 保存图片到SD�?
	public static String saveDownloadBitmap(Context context, Bitmap bitmap, String imageDir, String imgName) {
		File fileDir = new File(FileHandler.getExternalStoragePath().toString()+ "/" + imageDir);
		if (!fileDir.exists())
			fileDir.mkdirs();
		try {
			imgName = imgName.substring(imgName.lastIndexOf("/") + 1);
			imageDir = fileDir.getAbsolutePath() + "/" + imgName;
			FileOutputStream fos = new FileOutputStream(imageDir);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			Log.i("Bitmap stored in:", imageDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageDir;
	}

	// 读取图片
	public static Bitmap readFileFromAssets(String pathName, boolean scale, Activity activity) {
		Bitmap bitmap = null;
		try {
			InputStream is = activity.getApplicationContext().getAssets().open(pathName);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (scale) {
			DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			float sx = (float) metrics.widthPixels / width;
			return Bitmap.createScaledBitmap(bitmap, metrics.widthPixels, (int) (height * sx), false);
		} else
			return bitmap;
	}
}
