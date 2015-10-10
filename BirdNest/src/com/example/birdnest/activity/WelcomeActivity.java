package com.example.birdnest.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.birdnest.R;

/**
 *  �û�����app֮�󣬵�һ�����������Ļ�ӭ���棬���������features 
 * @author ³����
 */
public class WelcomeActivity extends Activity {
	
	private static final String Tag = "WelcomeActivity";
	private ViewPager mViewPager;	
	private ImageView pages[];
	private View[] view; 
	private int currIndex = 0;
	private final static int TAB_NUM = 4 ;  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        initImageHints();
        initViewPager();
    }    
	    
       /**
        * ��ʼ��viewPager�����ͼƬ������ʾԲ��
        * */
	    private void initImageHints()
	    {
	        pages = new ImageView[4];
	        pages[0] = (ImageView)findViewById(R.id.page0);
	        pages[1] = (ImageView)findViewById(R.id.page1);
	        pages[2] = (ImageView)findViewById(R.id.page2);
	        pages[3] = (ImageView)findViewById(R.id.page3);
	    }
	    
	    /**
	     * ��ʼ��ViewPager
	     * */
	   private void initViewPager()
	   {
	        //��Ҫ��ҳ��ʾ��Viewװ��������
	        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
	        view = new View[TAB_NUM];
	        view[0] = mLayoutInflater.inflate(R.layout.welcome1, null);
	        view[1] = mLayoutInflater.inflate(R.layout.welcome2, null);
	        view[2] = mLayoutInflater.inflate(R.layout.welcome3, null);
	        view[3] = mLayoutInflater.inflate(R.layout.welcome4, null);
	        
		   //ÿ��ҳ���view����
	        final ArrayList<View> viewList = new ArrayList<View>();
	        for(int i=0;i<TAB_NUM;i++)
	        {
	        	viewList.add(view[i]);
	        }
		   /**
	    	 * ���viewpager��������,ʵ�����ݰ�
	    	 * */
	        PagerAdapter pagerAdapter = new PagerAdapter()
	        {
	        	/**
	        	 * �ж�arg0��arg1�Ƿ���ͬһ������
	        	 * */
	    		@Override
	    		public boolean isViewFromObject(View arg0, Object arg1) {
	    			// TODO Auto-generated method stub
	    			Log.e(Tag,(arg1==(View)arg1)+"");
	    			return arg0==(View)arg1;
	    		}
	    		
	    		@Override
	    		public int getCount()
	    		{
	    			return viewList.size();
	    		}
	    		 /**
	    		  * PagerAdapterֻ����4��Ҫ��ʾ��ͼƬ�����������ͼƬ�����˻���ķ�Χ���ͻ���������������ͼƬ����
	    		  * */
	    		@Override
	    		public void destroyItem(View container, int position, Object object) {
	    		 	((ViewPager)container).removeView(viewList.get(position));
	    			Log.e(Tag, "destroyItem");
	    		}
	    		
	    		@Override
	    		public Object instantiateItem(View container, int position) {
	    		  ((ViewPager)container).addView(viewList.get(position));
	    			Log.e(Tag, "instantiateItem"+position+"");
	    			return viewList.get(position);
	    		}
	    	}; 
	        mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);        
	        mViewPager.setOnPageChangeListener(onPageChangeListener);
			mViewPager.setAdapter(pagerAdapter);
	   }
	   
      private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) 
		{
			switch (arg0) {
			case 0:				
				pages[0].setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				pages[1].setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				pages[1].setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				pages[0].setImageDrawable(getResources().getDrawable(R.drawable.page));
				pages[2].setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				pages[2].setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				pages[1].setImageDrawable(getResources().getDrawable(R.drawable.page));
				pages[3].setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				pages[3].setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				pages[2].setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			}
			currIndex = arg0;
			Log.e(Tag,"currIndex"+currIndex);
			//animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			//animation.setDuration(300);
			//mPageImg.startAnimation(animation);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//Log.e(Tag, "onPageScrolled"+arg0+":"+arg1+":"+arg2);
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			//Log.e(Tag, "onPageScrollStateChanged"+":"+arg0);
		}
	};
    
    public void startbutton(View v) {  
      	Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this,Splash.class);
		startActivity(intent); 
		this.finish();
      }  
    
}
