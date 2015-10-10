package com.example.birdnest.service;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.Notice;
import com.example.birdnest.database.User;
import com.example.birdnest.fragments.NewsFragment;
import com.example.birdnest.fragments.NoticeFragment;
import com.example.birdnest.fragments.PreNoticeFragment;
import com.example.birdnest.interfaces.Observable;
import com.example.birdnest.interfaces.Observer;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.net.NetWorkUtil;

public class UpdateServeice extends Service implements Observable
{
	public static final String Tag = "UpdateServeice";
	public final int UPDATE_CYCLE = 30*1000;  //10秒更新一次
	public int count  ;
	public Context context ;
	public static String newsResult,noticeResult ,preNoticeResut ;
//	public int newsResultNum,noticeResultNum ,preNoticeResutNum ;
	public  JSONArray newsArray,noticeArray ,prenoticeArray  ;
	private NewsFragment newsObserver ;
	private NoticeFragment NoticeObserver ;
	private PreNoticeFragment prenoticeObserver ;
	
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}
	@Override
    public void onCreate() 
    {
    	context = this ;
    	count = 0;
    	final DataHelper  dataHelper = new DataHelper(context) ;
    	newsObserver = NewsFragment.getInstance();
    	NoticeObserver = NoticeFragment.getInstance() ;
    	prenoticeObserver = PreNoticeFragment.getInstance() ;
    	observers = new Vector<Observer>() ;
    	this.register(NewsFragment.getInstance());
    	Log.i(Tag, "service start!~") ;
	    new Thread()
	    {
	    	@Override
	    	public void run()
	    	{
			 	while(true)
			 	{
					if(NetWorkUtil.checkNetWorkAvailable(context))
					{
						
						//查询还有多少条最新的消息没有更新
					 	String updateres = ComunicationWithServer.queryUpdateNum(dataHelper.getLastNewsNum(), dataHelper.getLastNoticeNum(), dataHelper.getLastPrenoticeNum());
					 	Log.e("updating", updateres) ;
					 	try {
							JSONObject jsonObject = new JSONObject(updateres);
							jsonObject = new JSONObject(jsonObject.get("message").toString());
							newsResult = jsonObject.getString("news");
							noticeResult = jsonObject.getString("notice");
							preNoticeResut = jsonObject.getString("prenotice");
		 					if(!newsResult.equals("0"))
							{
								 notifyObservers(newsObserver);
							}
							if(!noticeResult.equals("0"))
							{
								 notifyObservers(NoticeObserver);
							}
							if(!preNoticeResut.equals("0"))
							{
								notifyObservers(prenoticeObserver);
							} 
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
					/*	newsResult = ComunicationWithServer.queryRecentNews(dataHelper.getLastNewsNum());
						noticeResult =  ComunicationWithServer.queryRecentNotice(dataHelper.getLastNoticeNum());
					//	Log.i(Tag, noticeResult);
					 	preNoticeResut = ComunicationWithServer.queryRecentPrenotice(dataHelper.getLastPrenoticeNum()) ;
					// 	Log.i(Tag, preNoticeResut) ;
					 	JSONObject jsonObject = null ;
					 	String  ArrString = null ;
						try {
							jsonObject = new JSONObject(newsResult);
							jsonObject = new JSONObject(jsonObject.get("message").toString()) ;
							ArrString = jsonObject.getString("recentNews");
							newsArray = new JSONArray(ArrString);
							
							jsonObject = new JSONObject(noticeResult);
							jsonObject = new JSONObject(jsonObject.get("message").toString()) ;
							ArrString = jsonObject.getString("recentNotice");
							noticeArray = new JSONArray(ArrString);
							
							jsonObject = new JSONObject(preNoticeResut);
							jsonObject = new JSONObject(jsonObject.get("message").toString()) ;
							ArrString = jsonObject.getString("recentpreNotice");
							prenoticeArray = new JSONArray(ArrString);
							if(newsArray.length()!=0)
						 	{
							 	notifyObservers(newsObserver);
						 	}
							if(noticeArray.length()!=0)
							{
								notifyObservers(NoticeObserver);
							}
							if(prenoticeArray.length()!=0)
							{
								notifyObservers(prenoticeObserver);
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
					try {
						sleep(UPDATE_CYCLE);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
			 	}
	    	}
	    }.start();
    }
     
	@Override
    public void onDestroy()
	{
		
    }
	
	private Vector<Observer>  observers;
	
	@Override
	public void notifyObservers(Observer o) 
	{
		if(o.equals(NewsFragment.getInstance()))
		{
			newsObserver.update(newsResult);
		}
		else if(o.equals(NoticeFragment.getInstance())) 
		{
			NoticeObserver.update(noticeResult);
		}
		else if(o.equals(PreNoticeFragment.getInstance()))
		{
			prenoticeObserver.update(preNoticeResut);
		}
	}
	
	@Override
	public void register(Observer obs) 
	{
		observers.add(obs) ;
	}
	@Override
	public void unRegister(Observer obs)
	{
		observers.remove(obs);
	}
 }
