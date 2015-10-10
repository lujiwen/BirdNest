package com.example.birdnest.control;

import com.example.birdnest.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 操作揭秘哦按选项卡上的按钮类 ，上面为图片，下面为文字的按钮 BN(birdnest)
 * @author 鲁继文
 * */
public class BNButton extends LinearLayout
{
	private final static String Tag = "BNButton" ;
	private ImageView ButtonImage;
	private ImageView normalButtonImage;
	private ImageView slectedButtonImage;
	private ImageView disableButtonImage;
	private int normalButtonImageID;
	private int slectedButtonImageID;
	private int disableButtonImageID;
	private TextView  buttonText;
	public enum BUTTON_STATE{normal,slected,disable};//按钮的三种状态
	private Context context ;
	private LayoutInflater layoutInflater; 
	public BNButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public BNButton(Context context, AttributeSet attrs )
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public BNButton(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
 
	public BNButton(Context context,int picRes,String text)
	{
		super(context);
		init(context);
	}
	private void init(Context context)
	{
		this.context = context ;
		this.layoutInflater = LayoutInflater.from(context);
		layoutInflater.inflate(R.layout.bn_button_layout, this);
		ButtonImage = (ImageView)findViewById(R.id.ButonImage) ;
		buttonText = (TextView)findViewById(R.id.buttonText) ;
		buttonText.setTextColor(Color.GRAY);
	//	setBackgroundResource(R.drawable.ic_menu_background);
	}
	
	public void setPicText(int normalPicRes,String text )
	{
		ButtonImage.setBackgroundResource(normalPicRes);
		buttonText.setText(text);
	}
	
	public void setPics(int normalPic,int selectedPic,int disablePic )
	{
/*		normalButtonImage.setBackgroundResource(normalPic);
		slectedButtonImage.setBackgroundResource(selectedPic);
		disableButtonImage.setBackgroundResource(disablePic);*/
		normalButtonImageID = normalPic;
		slectedButtonImageID = selectedPic ;
		disableButtonImageID = disablePic ;
	}
	
	public void setPics(int normalPic,int selectedPic)
	{
	/*	normalButtonImage.setBackgroundResource(normalPic);
		slectedButtonImage.setBackgroundResource(selectedPic);*/
		normalButtonImageID = normalPic; 
		slectedButtonImageID = selectedPic ;
	}
	
	/**
	 * 设置按钮的状态 3 种状态
	 **/
	public void setState(BUTTON_STATE state)
	{
		if(state == BUTTON_STATE.normal)
		{
			ButtonImage.setBackgroundResource(normalButtonImageID);
			buttonText.setTextColor(Color.GRAY);
			//buttonText.setBackgroundColor(Color.RED);
		}
		else if(state == BUTTON_STATE.slected)
		{
			ButtonImage.setBackgroundResource(slectedButtonImageID);
			buttonText.setTextColor(Color.RED);
		}
		else if(state == BUTTON_STATE.disable)
		{
			ButtonImage.setBackgroundResource(disableButtonImageID);
			buttonText.setBackgroundColor(Color.GRAY);
		}
	}
}
