package com.example.birdnest.control;

import com.example.birdnest.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import  android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends LinearLayout
{
	private final static String Tag = "TitleBar";
	private Context context ;
	private TextView titleView ;
	private LayoutInflater layoutInflater ;
	public TitleBar(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		init(context);
	}
	public TitleBar(Context context, AttributeSet attrs) 
	{
		super(context, attrs);  
		init(context);
	}
	public TitleBar(Context context)
	{
		super(context);
		init(context);
	}
	
	private void init(Context contex)
	{
		this.context = contex ;
		this.layoutInflater = LayoutInflater.from(contex);
		layoutInflater.inflate(R.layout.titlebar_layout, this);
		titleView = (TextView)findViewById(R.id.title);
	//	titleView.setText("บวบว");
	}
 
	public void setTitleText(String string) 
	{
	//	Log.e(Tag, string);
		titleView.setText(string);
	}
	
}
