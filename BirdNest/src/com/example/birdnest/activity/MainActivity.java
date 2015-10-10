package com.example.birdnest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.birdnest.R;
import com.example.birdnest.actionbar.SlidingTabIndicator;
import com.example.birdnest.adaptes.MainAdapter;
import com.example.birdnest.service.UpdateServeice;

/**
 * �û���������UI,ѡ����ڵ�ACtivity
 * @author ³����
 **/
public class MainActivity extends FragmentActivity 
{
	private ViewPager viewPager ;
	private SlidingTabIndicator slidingTabIndicator ;
	private static MainActivity instance ; 
	@Override 
	protected void onCreate(Bundle savedInstaceState)
	{
		super.onCreate(savedInstaceState);
		setContentView(R.layout.main_layout);
		init();
	}
	
	public static MainActivity getInstance()
	{
		return instance;
	}
	private void init()
	{
		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
		slidingTabIndicator = (SlidingTabIndicator)findViewById(R.id.tabs);
		slidingTabIndicator.setPager(viewPager);
		instance = this ;
		
		//�������͸��µķ����߳�
		Intent i = new Intent();
		i.setClass(this, UpdateServeice.class) ;
	 	this.startService(i) ;
	}
} 
