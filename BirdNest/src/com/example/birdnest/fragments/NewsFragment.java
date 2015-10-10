package com.example.birdnest.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smackx.pubsub.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.birdnest.R;
import com.example.birdnest.activity.MainActivity;
import com.example.birdnest.activity.NewsDetails;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.NotificationUtil;
import com.example.birdnest.control.TitleBar;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.News;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.interfaces.Observable;
import com.example.birdnest.interfaces.Observer;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.drawable;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class NewsFragment extends Fragment  implements Observer, OnItemClickListener,OnItemLongClickListener,OnRefreshListener<ListView>
{
	private static NewsFragment instance ;
	private Context context;
 	private final static String Tag = "NewsFragment";
 	private TitleBar titleBar;
 	private static PullToRefreshListView pullToRefreshListView ;
 	private View emptyView ; 
 	private List<News>  newsdata ;
 	private NewsAdapter newsadpter ;
 	private static boolean isRefreshing = false;
 	final String NULL = "没有新闻鸟~~";
 	private int newnum ;
	private DataHelper dataHelper;
	private static  int not_updated;
	private static final String RECNT_NEWS_KEY = "recentNews" ; 
	private String label ;
	public static NewsFragment getInstance() 
	{
		if(instance==null )
		{
			instance = new NewsFragment() ;
		}
		return instance ;
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		dataHelper = DataBaseContext.getInstance(getActivity());
		Log.e(Tag,"onCreateView");
		View view = inflater.inflate(R.layout.tab_layout1, null);
	 	context = getActivity().getApplicationContext();
	 	titleBar = (TitleBar)view.findViewById(R.id.tabHeader1);
	 	titleBar.setTitleText(getResources().getString(R.string.tabtitle1));
	 	init( view);
		return view;
	}
	private void init(View view)
	{
		initPullRefreshList(view);
		queryRecentNews();
	}
	private void initPullRefreshList(View view)
	{
		  /*  String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),  
                  DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);*/ 
			label  = "您当前有"+not_updated+"条新闻未更新" ;
		    pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.news_List);
			pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label); 
		 
		  	pullToRefreshListView.setOnRefreshListener(this);
		 
		 	newsdata = new ArrayList<News>();
		 	newsadpter = new NewsAdapter(getActivity() );
		 	//msgView
		 	ListView msgView = pullToRefreshListView.getRefreshableView();
		 	msgView.setAdapter(newsadpter);
		 	msgView.setEmptyView(emptyView);
		 	msgView.setOnItemClickListener(this);
		 	msgView.setOnItemLongClickListener(this) ;
		 	
			newsdata.clear();
			dataHelper = DataBaseContext.getInstance(getActivity());
			newsdata = dataHelper.getNews();
			newsadpter.notifyDataSetChanged();
	}
	
	
private void queryRecentNews()
	{
	newsdata.clear();
	 dataHelper = DataBaseContext.getInstance(getActivity());
	 newsdata = dataHelper.getNews();
	Collections.reverse(newsdata);//将最新的消息放在list头部显示
	newsadpter.notifyDataSetChanged();
	pullToRefreshListView.onRefreshComplete();  
	}
	
	
	private void configEmptyView(String msg)
	{
		if (msg == null) {
			emptyView.findViewById(R.id.loading_progrssbar).setVisibility(View.VISIBLE);
			((TextView) emptyView.findViewById(R.id.progress_tips)).setText("获取最新消息中...");
		} else {
			emptyView.findViewById(R.id.loading_progrssbar).setVisibility(View.GONE);
			((TextView) emptyView.findViewById(R.id.progress_tips)).setText(msg);
		}
	} 
	
	@Override
	public void onPause()
	{
		super.onPause();
	}
	@Override 
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		label  = "您当前有"+not_updated+"条新闻未更新" ;
		pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		Log.e(Tag, "onrefresh");
		QueryNewsTask queryNewsTask = new QueryNewsTask();
		queryNewsTask.execute();
	}
	
	Handler newsHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what == 1)
			{
				pullToRefreshListView.onRefreshComplete();
		
				JSONObject jsonObject = null;
				try {
					String rencetNewString = msg.getData().getString(RECNT_NEWS_KEY) ;
					jsonObject = new JSONObject(rencetNewString) ;
					jsonObject = new JSONObject(jsonObject.get("message").toString());
					rencetNewString = jsonObject.get(RECNT_NEWS_KEY).toString() ;
					JSONArray jsonArray = new JSONArray(rencetNewString);
					int len = jsonArray.length();
					// jsonObject = new JSONObject(rencetNewString) ;
					for(int i=0;i<len;i++)
					{
						//"id":"1","content":"联谊将在本周六下午举行","picture":null,"pubDate":"2014-11-20 20:28:48.0","title":"研究生会联谊","source":"主席团"}
						jsonObject = jsonArray.getJSONObject(i);
						String contentString =  jsonObject.getString("content") ;
						if(contentString.length()>20)
						{
							contentString = contentString.substring(0, 20)+"......";
						}
						News news = new News(jsonObject.getString("id"),jsonObject.getString("title"),contentString,jsonObject.getString("pubDate"),jsonObject.getString("source")
								,jsonObject.getString("picture"));
						
						newsdata.add(0,news) ;
					    dataHelper.insertNews(news);
					}
					newsadpter.notifyDataSetChanged();
					pullToRefreshListView.onRefreshComplete();  
				} catch (Exception e) {
					e.printStackTrace();		
					}
				
				Intent i = new Intent();
				i.setClass(getActivity(),MainActivity.class);
				NotificationUtil.showNotification(getActivity(), i, 0,R.drawable.app_logo, "新闻","收到新闻了！");
			}
			super.handleMessage(msg);
		}
	};
	
	
	/**
	 * 删除消息
	 **/
	private void delmesg(int index)
	{
		Log.e(Tag, "delete item "+index) ;
		int newsID = newsdata.get(index).getMessageId() ;
		dataHelper.removeNews(newsID);
		newsdata.remove(index);
		ComunicationWithServer.delete("news", newsID);
		newsadpter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final int  pos = position ; 
		if(!ConfigRecorder.getStringRecord(getActivity(), MyApplication.filenameString,User.LIMITION, "普通").equals("普通"))
		{
			new AlertDialog.Builder(getActivity())
			.setCancelable(true)
			.setMessage("是否删除该新闻?")
			.setNegativeButton("取消",null)
			.setPositiveButton("删除", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Log.e("newsID",  newsdata.get(pos-1).getNewsID()+"");
					ComunicationWithServer.delete("news", newsdata.get(pos-1).getMessageId());
					delmesg(pos-1);
				}
			})
			.show();
		}
		return true ;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Log.e(Tag, "click"+position) ;
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("news_detail",newsdata.get(position-1) );
		intent.putExtras(bundle);
		intent.setClass(getActivity(), NewsDetails.class) ;
		startActivity(intent);
	}
	
	private class NewsAdapter extends BaseAdapter 
	{
		private Context context ;
		private Activity  activity ; 
		List<News> list ;
		private ImageLoader loader;
		private DisplayImageOptions options ;
		
	 	public NewsAdapter(Context context, Activity act,List<News> l)
		{
	 		Log.e(Tag, "NewsAdapter");
			this.context = context; 
			this.activity = act;
			this.list = l;
			//initImageLoader();
		}
	 	public NewsAdapter(Context context)
		{
	 		Log.e(Tag, "NewsAdapter");
			this.context = context; 
			this.list = newsdata;
			initImageLoader();
		}
	 	
		/**
		 *  搞不懂
		 **/
	 	private void initImageLoader()
	 	{
	 		loader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
	 	}
	 	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsdata.size();
		}

		@Override
		public Object getItem(int position) {
			Log.e(Tag, "getItem") ;
			return newsdata.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder viewHolder = new ViewHolder() ;
			
			convertView = LayoutInflater.from(context).inflate(R.layout.msg_list_item,null);
			viewHolder.name = (TextView)convertView.findViewById(R.id.name);
			viewHolder.timeStamp = (TextView)convertView.findViewById(R.id.timestamp);
			viewHolder.headIcon = (ImageView)convertView.findViewById(R.id.headicon);
			viewHolder.newsContent = (TextView)convertView.findViewById(R.id.msg_content);
			viewHolder.newsTitle = (TextView)convertView.findViewById(R.id.msg_title); 
			
			viewHolder.name.setText(newsdata.get(position).getMessageSource());
			viewHolder.newsContent.setText(newsdata.get(position).getMessageContent());
			viewHolder.timeStamp.setText(SystemTime.getSystemTime("-"));
			viewHolder.newsTitle.setText(newsdata.get(position).getMessageTitile());
			convertView.setTag(viewHolder);
			return convertView;
		}
		
		@Override
		public int getItemViewType(int position) {
			return 0;
		}
		
		 private class ViewHolder
		 {
			 TextView name;
			 TextView newsContent;
			 ImageView headIcon ;
			 TextView timeStamp ;
			 TextView newsTitle;
			// ImageView news ;
		 }
	}

	class QueryNewsTask extends	AsyncTask<integer , integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(integer... params)
		{
			Log.e(Tag, "doinback");
			if(pullToRefreshListView.isHeadherShow())
			{
				if(!isRefreshing)
				{
					int lastnum = 0;
					lastnum = dataHelper.getLastNewsNum();
					String newsString = ComunicationWithServer.queryRecentNews(lastnum);
					Message  msg = new Message(); 
					Bundle data = new Bundle();
					data.putString(RECNT_NEWS_KEY, newsString);
					msg.setData(data);
					msg.what = 1 ;
					newsHandler.sendMessage(msg);
					isRefreshing = !isRefreshing;
				}
			}
			return true;
		}
	    protected void onPreExecute() 
	    {
	    	
	    }
	    
	    @Override
	    protected void onPostExecute(Boolean result)
	    {
	    	pullToRefreshListView.onRefreshComplete();
	    	not_updated = 0 ;
	    }
	}
	@Override
	public void update(String num) 
	{
		//更新未更新的新闻数量
		int number = Integer.parseInt(num) ;
		not_updated = number>=0?number:0;
		//Log.e(Tag, "receive!"+not_updated+"条新闻") ;
	/*	int newsnum = jsonArray.length() ;
		News news = null;*/
		//if(newsnum>0)
	//	{
			/*for(int i=0;i<newsnum;i++)
			{
				JSONObject jsonObject;
				try {
					news = new News() ;
					jsonObject = jsonArray.getJSONObject(i);
					news.setMessageTitile(jsonObject.getString("title"));
					news.setMessageSource(jsonObject.getString("source"));
					news.setMessageContent(jsonObject.getString("content"));
					news.setMessageTime(jsonObject.getString("pubDate"));
					news.setNewsPic("null");
					news.setNewsId(jsonObject.getString("id"));
					dataHelper.insertNews(news);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newsdata.add(news);
			}
			*/
			//newsHandler.sendEmptyMessage(1);
			/*Intent i = new Intent();
			i.setClass(getActivity(),MainActivity.class);
			NotificationUtil.showNotification(getActivity(), i, 0,R.drawable.app_logo, "新闻","收到"+newsnum+"新闻了！");
		}*/
	}
}



