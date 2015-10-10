package com.example.birdnest.adaptes;

import com.example.birdnest.R;
import com.example.birdnest.actionbar.SlidingTabIndicator.TabItem;
import com.example.birdnest.actionbar.SlidingTabIndicator.TabResProvider;
import com.example.birdnest.fragments.ContactFragment;
import com.example.birdnest.fragments.NewsFragment;
import com.example.birdnest.fragments.NoticeFragment;
import com.example.birdnest.fragments.PersonInfoFragment;
import com.example.birdnest.fragments.PreNoticeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


/**
 * FragmentStatePagerAdapter¼Ì³Ð×ÔviewpagerAdapter
 * 
 * */
public class MainAdapter extends FragmentStatePagerAdapter implements TabResProvider
{
	private static final String Tag = "MainAdapter"; 
	public final int[][] tabs = {
			{R.drawable.main_page1_bg,R.string.tabtitle1},
			{R.drawable.main_page2_bg,R.string.tabtitle2},
			{R.drawable.main_page3_bg,R.string.tabtitle3},
			{R.drawable.main_page4_bg,R.string.tabtitle4},
			{R.drawable.main_page5_bg,R.string.tabtitle5}
	};
	public final Fragment[] fragments = {
			NewsFragment.getInstance() ,
			NoticeFragment.getInstance(), 
			PreNoticeFragment.getInstance(),
			ContactFragment.getInstance(),
			PersonInfoFragment.getInstance() 
	};
	
	public MainAdapter(FragmentManager fm) 
	{
		super(fm);
	}

	@Override
	public TabItem getTabResId(int position) 
	{
		return new TabItem(tabs[position][0], tabs[position][1]);
	}
	
 
	@Override
	public Fragment getItem(int position)
	{	
		Fragment fragment = fragments[position];
		return fragment;
	}

	@Override
	public int getCount() 
	{
		return tabs.length;
	}
	
}
