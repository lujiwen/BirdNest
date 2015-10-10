package com.example.birdnest.fileHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import com.example.birdnest.R;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Environment;
import android.util.Log;

/**文件处理类
 * @author 鲁继文
 * 
 **/
public class FileHandler 
{
	/**
	 * Bitmap转化为drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}
	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	/**
	 * 递归调用删除所有文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete();
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件用这个方法进行迭代
				}
			}
			// 删除根目录
			file.delete();
		} else {
			Log.e("ljw", "所删除的文件不存在");
		}
	}

	/**
	 * Drawable 转 bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
					: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}
	
	public final byte[] input2byte(Context context ,ByteArrayInputStream inStream) throws IOException {
		if (inStream != null) {

			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = inStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] in2b = swapStream.toByteArray();
			return in2b;
		} else {
			Bitmap bm = drawable2Bitmap(context.getResources().getDrawable(com.example.birdnest.R.drawable.app_logo));
			return Bitmap2Bytes(bm);
		}
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	public static byte[] getFileBytes(File file) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int bytes = (int) file.length();
			byte[] buffer = new byte[bytes];
			int readBytes = bis.read(buffer);
			if (readBytes != buffer.length) {
				throw new IOException("Entire file not read");
			}
			return buffer;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}
	/**
	 * read file from assert folder
	 * 
	 * */
	public static InputStream getAssertStream(String Filename,Context context)
	{
		AssetManager assetManager =  context.getAssets();
		 InputStream stream = null ;
		try {
			stream  = assetManager.open(Filename);
			/*return   stream  ;*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			return stream;	
	}
	
	public static InputStream getRawStream(Context context,int resId )
	{
		InputStream inputStream = context.getResources().openRawResource(resId);
		return inputStream;
	}
	
	@SuppressWarnings("resource")
	public static long copyFile(File src,File dest) 
	{
		 long copySize = 0 ;
		 try {
			FileChannel channelIn  = new FileInputStream(src).getChannel();
			FileChannel channelout = new FileOutputStream(dest).getChannel();
			try {
				long size = channelIn.size() ;
				channelIn.transferTo(0, size, channelout);
				copySize =  channelout.size();
				channelIn.close();
				channelout.close();
				return copySize ;
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		 
		 return copySize ;
	}
	
	public static File getExternalStoragePath()
	{
			String root=Environment.getExternalStorageDirectory().getPath();
			return new File(root);
	}
	public static void  checkFreeSpace()
	{
		String root=Environment.getExternalStorageDirectory().getPath();
		File file = new File(root);
	 
		File[] childFiles =  file.listFiles();
		while(file.getFreeSpace()<1024*1024)
		{
			childFiles[0].delete();
			childFiles = file.listFiles();
		}
	}

	/**
	 * 是否有SD卡
	 * 
	 * @return
	 */
	public static boolean inquiresSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
	
}
