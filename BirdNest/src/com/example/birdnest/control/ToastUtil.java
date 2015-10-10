package com.example.birdnest.control;

import android.content.Context;
import android.widget.Toast;

/**
 * ��˾������
 * 
 * @author  ³����
 * 
 */
public class ToastUtil {
	public static void showShortToast(Context context, int str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showShortToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, int str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
}
