package com.example.birdnest.display ;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.birdnest.BuildConfig; 

public class Display {
    Activity activity;
    DisplayMetrics metrics;
    public Display(Activity activity)
    {
        this.activity=activity;
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        this.metrics=mDisplayMetrics;
        if(BuildConfig.DEBUG)
         {
            Log.i("density",metrics.density+"");
            Log.i("density-dpi",metrics.densityDpi+"");
            Log.i("xdpi",metrics.xdpi+"");
            Log.i("ydpi",metrics.ydpi+"");
            Log.i("scaled-density",metrics.scaledDensity+"");
         } 
    }
    
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    
    public static DisplayMetrics getDisplayMetrics(Activity activity)
    {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics;
    }
    
    public DisplayMetrics getMetrics()
    {
        return metrics;
    }
    
    public int getScreenHeight()
    {
        return metrics.heightPixels;
    }
    
    public int getScreenWidth()
    {
        return metrics.widthPixels;
    }
    
    public static int getStatusbarHeight(Context context)
    {
        int statusHeight = 0;
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int id = Integer.parseInt(localClass.getField("navigation_bar_height").get(localObject).toString());
            statusHeight = context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
         e.printStackTrace();
        }
      return statusHeight;
    }

}
