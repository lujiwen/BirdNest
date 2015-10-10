package com.example.birdnest.control;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationUtil {
	@SuppressLint("NewApi") 
	public static void showNotification(Context context,Intent intent,int notifyid,int iconid,String title,String msg)
	{
		PendingIntent pi = PendingIntent.getActivity(context, 1000, intent, 0);
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentIntent(pi)
		.setSmallIcon(iconid)
		.setWhen(System.currentTimeMillis())
		.setAutoCancel(true)
		.setContentTitle(title)
		.setContentText(msg);
		
		Notification n = builder.build();
		n.defaults = Notification.DEFAULT_SOUND;
		nm.notify(notifyid, n);
	}
	
	public static void cancelNofity(Context context,int notifyid)
	{
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(notifyid);
	}
}
