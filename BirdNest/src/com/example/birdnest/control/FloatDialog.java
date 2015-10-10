package com.example.birdnest.control;


import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdnest.R;
import com.example.birdnest.display.Display;
import com.example.birdnest.display.Display;

public class FloatDialog extends Dialog {

	Window window;
	WindowManager.LayoutParams lp;
	TextView msgView;
	ImageView closeImage;
	Context mContext;
	int screen_width,screen_height;
	boolean ischock = false;

	
	public FloatDialog(final Context context,boolean ischoke) {
		super(context, R.style.dialog);
		this.mContext=context;
		
		window=getWindow();
		lp=window.getAttributes();
		window.setGravity(Gravity.CENTER);
		
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        if(!ischoke)
        	lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL   
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.format = PixelFormat.TRANSLUCENT;
        window.setAttributes(lp);
        initScreenWH(context);
	}
	
	private void initScreenWH(Context context)
	{
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		screen_width = metrics.widthPixels;
		screen_height = metrics.heightPixels - Display.getStatusbarHeight(context);
	}
	
	public void setGravity(int gravity,int xoffset,int yoffset)
	{
	    if(gravity != 0)
	        window.setGravity(gravity);
		lp.x=xoffset;
		lp.y=yoffset;
		window.setAttributes(lp);
	}
	
	public void setPos(int x,int y)
	{
		lp.x = x;
		lp.y = y;
		window.setAttributes(lp);
	}
	
	
	public void setSize(int width,int height)
	{
		lp.width=width;
		lp.height=height;
		window.setAttributes(lp);
	}
	
	public void setSizeScale(float widthscale,float heightscale)
	{
		int w,h ;
		if(widthscale <= 0)
		{
			w = LayoutParams.WRAP_CONTENT;
		}
		else
		{
			w = (int) (screen_width * Math.abs(widthscale - Math.floor(widthscale)));
		}
		
		if(heightscale <= 0)
		{
			h = LayoutParams.WRAP_CONTENT;
		}
		else
		{
			h = (int) (screen_height * Math.abs(heightscale - Math.floor(heightscale)));
		}
		this.setSize(w,h );
	}
}
