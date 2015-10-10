package com.example.birdnest.control;

import com.example.birdnest.R;

import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil 
{
	public static void showErroAlterdlg(Context context, String title,String info)
	{
		new AlertDialog.Builder(context)
		.setIcon(context.getResources().getDrawable(R.drawable.error_icon))
		.setMessage(info)
		.setTitle(title)
		.create()
		.show();
	}
	
	public static void showSingleChoiceDialog(Context context,String title,String[] items,OnClickListener onClickListener)
	{
		new AlertDialog.Builder(context)  
		.setTitle(title)  
		.setSingleChoiceItems(items, 0, onClickListener)
		.setPositiveButton(context.getResources().getString(R.string.confirm), onClickListener)                  
		.setNegativeButton(context.getResources().getString(R.string.cancel), null)  
		.show(); 
	}
}