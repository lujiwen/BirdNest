package com.example.birdnest.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.birdnest.R;
import com.example.birdnest.activity.MainActivity;
import com.example.birdnest.activity.NewsDetails;
import com.example.birdnest.activity.NoticeDetails;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.NotificationUtil;
import com.example.birdnest.control.TitleBar;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.News;
import com.example.birdnest.database.Notice;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.interfaces.Observable;
import com.example.birdnest.interfaces.Observer;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class NoticeFragment extends Fragment  implements Observer, OnItemClickListener,OnItemLongClickListener,OnRefreshListener<ListView>
{
	private final static String Tag = "NewsFragment";
	private static NoticeFragment instance ;
	private Context context;
	private TitleBar titleBar;
 	private List<Notice>  noticedatalist ;
 	private View emptyView ; 
 	final String NULL = "没有通知鸟~~";
 	private NoticeAdapter noticeAdapter ;
 	private PullToRefreshListView pullToRefreshListView ;
 	private DataHelper dataHelper;
 	private int not_updated;
 	private static final String RECNT_NOTICE_KEY = "recentNotice" ; 
 	private String label ;
 	
	public static NoticeFragment getInstance()
	{
		if(instance==null )
		{
			instance = new NoticeFragment() ;
		}
		return instance ;
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.e(Tag, "onCreateView");
		View view = inflater.inflate(R.layout.tab_layout2, null);
		context = getActivity().getApplicationContext();
		titleBar = (TitleBar)view.findViewById(R.id.tabHeader2);
		titleBar.setTitleText(getResources().getString(R.string.tabtitle2));
		emptyView = view.findViewById(R.id.empty_view);
	 	initPullRefreshList(view);
	 	queryRecentNotice();
		return view;
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
	private void queryRecentNotice() 
	{
	 	configEmptyView(null);
		//newnum = 0 ;
		noticedatalist.clear();
		dataHelper = DataBaseContext.getInstance(getActivity());
		noticedatalist = dataHelper.getNotice();
		Collections.reverse(noticedatalist);//将最新的消息放在list头部显示
		noticeAdapter.notifyDataSetChanged();
		configEmptyView(NULL);
		pullToRefreshListView.onRefreshComplete();  
	}
	
	Handler noticeHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what == 1)
			{
			//	noticeAdapter.notifyDataSetChanged();
				JSONObject jsonObject = null;
				try {
					String rencetNoticeString = msg.getData().getString(RECNT_NOTICE_KEY) ;
					jsonObject = new JSONObject(rencetNoticeString) ;
					jsonObject = new JSONObject(jsonObject.get("message").toString());
					rencetNoticeString = jsonObject.get(RECNT_NOTICE_KEY).toString() ;
					JSONArray jsonArray = new JSONArray(rencetNoticeString);
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
						Notice notice = new Notice(jsonObject.getString("id"),jsonObject.getString("title"),contentString,jsonObject.getString("pubDate"),jsonObject.getString("source")
								,jsonObject.getString("picture"));
						
						noticedatalist.add(0,notice) ;
					 	dataHelper.insertNotice(notice) ;
					}
					noticeAdapter.notifyDataSetChanged();
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
	
	private void initPullRefreshList(View view)
	{
		/*  String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),  
                  DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);*/
			label  = "您当前有"+not_updated+"条通知未更新" ;
			pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.notice_List);
			pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			pullToRefreshListView.setOnRefreshListener(this);
		 	noticedatalist = new ArrayList<Notice>();
		 	noticeAdapter = new NoticeAdapter(getActivity() );
		 	//msgView
		 	ListView msgView = pullToRefreshListView.getRefreshableView();
		 	msgView.setAdapter(noticeAdapter);
		 	msgView.setEmptyView(emptyView);
		 	msgView.setOnItemClickListener(this);
		 	msgView.setOnItemLongClickListener(this) ;
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
	public void onDestroy()
	{
		super.onDestroy(); 
		// dataHelper.Close(); 
	}
	
	/////////////////////////////////////////////////////////////////////////
	private class NoticeAdapter extends BaseAdapter 
	{
		private Context context ;
		private Activity  activity ; 
		List<Notice> list ;
		private ImageLoader loader;
		private DisplayImageOptions options ;
		
	 	public NoticeAdapter(Context context, Activity act,List<Notice> l)
		{
	 		Log.e(Tag, "NewsAdapter");
			this.context = context; 
			this.activity = act;
			this.list = l;
			//initImageLoader();
		}
	 	public NoticeAdapter(Context context)
		{
	 		Log.e(Tag, "NewsAdapter");
			this.context = context; 
			this.list = noticedatalist;
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
			return noticedatalist.size();
		}

		@Override
		public Object getItem(int position) {
			Log.e(Tag, "getItem") ;
			return noticedatalist.get(position);
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
			viewHolder.noticeContent = (TextView)convertView.findViewById(R.id.msg_content);
			viewHolder.noticeTitle = (TextView)convertView.findViewById(R.id.msg_title); 
			viewHolder.name.setText(noticedatalist.get(position).getMessageSource());
			viewHolder.noticeContent.setText(noticedatalist.get(position).getMessageContent());
			viewHolder.timeStamp.setText(SystemTime.getSystemTime("-"));
			viewHolder.noticeTitle.setText(noticedatalist.get(position).getMessageTitile());
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
			 TextView noticeContent;
			 ImageView headIcon ;
			 TextView timeStamp ;
			 TextView noticeTitle;
			// ImageView news ;
		 }
	}
	private static boolean isRefreshing = false;
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		Log.e(Tag, "onrefresh");
		isRefreshing = false ;
		label  = "您当前有"+not_updated+"条通知未更新" ;
     	pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		QueryNoticeTask queryNoticeTask = new QueryNoticeTask() ;
		queryNoticeTask.execute() ;
	}

	class QueryNoticeTask extends	AsyncTask<integer , integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(integer... params)
		{
			Log.e(Tag, "doinback");
			if(pullToRefreshListView.isHeadherShow())
			{
				if(!isRefreshing)
				{
					int lastnum =  0 ;
					lastnum = dataHelper.getLastNoticeNum();
					String newsString = ComunicationWithServer.queryRecentNotice(lastnum);
					Message  msg = new Message(); 
					Bundle data = new Bundle();
					data.putString(RECNT_NOTICE_KEY, newsString);
					msg.setData(data);
					msg.what = 1 ;
					noticeHandler.sendMessage(msg);
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
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final int  pos = position ; 
		if(!ConfigRecorder.getStringRecord(getActivity(), MyApplication.filenameString,User.LIMITION, "普通").equals("普通"))
		{
			new AlertDialog.Builder(getActivity())
			.setCancelable(true)
			.setMessage("是否删除该通知?")
			.setNegativeButton("取消",null)
			.setPositiveButton("删除", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Log.e("noticeID",  noticedatalist.get(pos-1).getMessageId()+"");
					ComunicationWithServer.delete("notice", noticedatalist.get(-1).getMessageId());
					delmesg(pos-1);
				}
			})
			.show();
		}
		return true ;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Log.e(Tag, "click"+position) ;
		Intent intent = new Intent();
		Bundle bundle = new Bundle() ;
		bundle.putSerializable("notice_detail",noticedatalist.get(position-1) );
		intent.putExtras(bundle);
		intent.setClass(getActivity(), NoticeDetails.class) ;
		startActivity(intent);
	}

	@Override
	public void update(String num) 
	{
		int number = Integer.parseInt(num) ;
		not_updated = number>=0?number:0;
		//Log.e(Tag, "receive!"+not_updated+"条通知");
		
		//Log.e(Tag, "receice"+jsonArray.length()+"条通知！") ;
		
		//NotificationUtil.showNotification(context, intent, notifyid, iconid, title, msg);
	/*	Intent i = new Intent();
		i.setClass(getActivity(),MainActivity.class);
		NotificationUtil.showNotification(getActivity(), i, 0,R.drawable.app_logo, "新闻","收到"+jsonArray.length()+"新闻了！");*/
	}
	/**
	 * 删除消息
	 **/
	private void delmesg(int index)
	{
		Log.e(Tag, "delete item "+index) ;
		int noticeID = noticedatalist.get(index).getMessageId() ;
		dataHelper.removeNews(noticeID);
		noticedatalist.remove(index);
		ComunicationWithServer.delete("notice", noticeID);
		noticeAdapter.notifyDataSetChanged();
	}
	
}
