package com.example.birdnest.service;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.birdnest.activity.BrowsePhotosActivity;
import com.example.birdnest.application.Const;

public class SystemService  implements Const
{
	private static final String Tag = "SystemService" ;
	public static void  phoneCall(Context context,String phoneNum)
	{
		Intent phoneIntent = null ;
		if(phoneNum.trim().length()!=0)
		{
		    phoneIntent = new Intent("android.intent.action.CALL", 
		             Uri.parse("tel:" + phoneNum));
		  /*  Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phoneNum));*/
		    context.startActivity(phoneIntent);
		}
		else 
		{
			Toast.makeText(context, "号码不得为空！", Toast.LENGTH_SHORT).show();
		} 
	}
	public static void sendSms(Context context ,String phoneNum,String text)
	{
		/*检查好暧昧*/
		if(phoneNum.trim().length()!=0)
		{
			Uri smsToUri = Uri.parse("smsto:"+phoneNum); 
			Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri); 
			sendIntent.putExtra("address", phoneNum); 
			sendIntent.putExtra("sms_body", text); 
			sendIntent.setType("vnd.android-dir/mms-sms"); 
			context.startActivity(sendIntent); 
		}
		else 
		{
			Toast.makeText(context, "号码不得为空！", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void sendSms(Context context ,String phoneNum)
	{
		Intent intent = new Intent();

		//系统默认的action，用来打开默认的短信界面
		intent.setAction(Intent.ACTION_SENDTO);

		//需要发短息的号码
		intent.setData(Uri.parse("smsto:"+phoneNum));
		context.startActivity(intent);
	}
	
	
	public static void addToContact(Context context ,String phonenum)
	{
		 Intent intent = new Intent();
	       //为Intent设置Action属性（动作为：编辑）
	       intent.setAction(Intent.ACTION_INSERT);
         String data = "content://com.android.contacts/contacts";
	     //根据指定字符串解析出Uri对象
	     Uri uri = Uri.parse(data);                
	       //设置Data属性
	      intent.setData(uri);
	      context.startActivity(intent);  
 		/*    ContentValues values = new ContentValues();        
		    Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values); 
		    long rawContactId = ContentUris.parseId(rawContactUri); 
		 
		    values.clear(); 
		    values.put(Data.RAW_CONTACT_ID, rawContactId); 
		    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
 		    values.put(StructuredName.GIVEN_NAME, string); 
		    values.put(StructuredName.FAMILY_NAME, string2);  
		    values.put(Phone.NUMBER, phonenum); 
		    context.getContentResolver().insert(Data.CONTENT_URI, values);
		    values.clear(); 
		    values.put(Data.RAW_CONTACT_ID, rawContactId); 
		    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
		    values.put(Phone.NUMBER, string3); 
		    values.put(Phone.TYPE,Phone.TYPE_MOBILE); 
		    getContentResolver().insert(Data.CONTENT_URI, values);
		        
		    values.clear(); 
		    values.put(Data.RAW_CONTACT_ID, rawContactId); 
		    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
		    values.put(Phone.NUMBER, string4); 
		    values.put(Phone.TYPE,Phone.TYPE_HOME); 
		    getContentResolver().insert(Data.CONTENT_URI, values) */
	}
	
	// 获取手机屏幕尺寸
	public static DisplayMetrics getWindowMetrics(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	/**
	 *  调用手机图库
	 * */
	public static void phonePictures(Activity activity, int availNumber) {
		Intent selectPhoto = new Intent(activity, BrowsePhotosActivity.class);
		selectPhoto.putExtra("availNumber", availNumber);
		activity.startActivityForResult(selectPhoto, 2);
	}
/**
 * 	// 调用系统相机
*/		public static String sysCamera(Activity activity) {
			String imgPath = null;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(Environment.getExternalStorageDirectory() + "/" + CAMERA_PHOTO_DIR);
				if (!dir.exists())
					dir.mkdirs();
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				String imgName = System.currentTimeMillis() + ".png";
				File f = new File(dir, imgName);
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				activity.startActivity(intent);
				imgPath = f.getAbsolutePath();
			}
			return imgPath;
		}

	public static String getImei(Context context, String imei)
	{
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
		}
		return imei;
	}
	
    // 取得版本号
    public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
}
