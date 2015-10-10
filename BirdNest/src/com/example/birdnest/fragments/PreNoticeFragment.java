package com.example.birdnest.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.birdnest.R;
import com.example.birdnest.activity.MainActivity;
import com.example.birdnest.activity.NoticeDetails;
import com.example.birdnest.activity.PrenoticeDetails;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.NotificationUtil;
import com.example.birdnest.control.TitleBar;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.News;
import com.example.birdnest.database.Notice;
import com.example.birdnest.database.PreNotice;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.interfaces.Observable;
import com.example.birdnest.interfaces.Observer;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PreNoticeFragment extends Fragment implements Observer,OnItemLongClickListener,OnItemClickListener,OnRefreshListener<ListView>
{
	private static PreNoticeFragment instance ;
	private Context context;
	private final static String Tag  = "PreNoticeFragment" ;
	private TitleBar titleBar;
	private List<PreNotice> prenoticeList ;
	private PrenoticeAdpter prenoticeAdpter ;
	PullToRefreshListView pullToRefreshListView ;
	private View emptyView;
	final String NULL = "没有活动预告鸟鸟~~";
	private DataHelper dataHelper ;
	private int not_updated;
	private static final String RECNT_PRENOTICE_KEY = "recentpreNotice" ; 
	private String label ;
	public static PreNoticeFragment getInstance()
	{
		if(instance==null )
		{
			instance = new PreNoticeFragment() ;
		}
		return instance ;
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.e(Tag,"onCreateView");
		View view = inflater.inflate(R.layout.tab_layout3 ,null,false);
	 	context = getActivity().getApplicationContext();
		titleBar = (TitleBar)view.findViewById(R.id.tabHeader3);
		titleBar.setTitleText(getResources().getString(R.string.tabtitle3));
		init(view);
		return view;
	}
	private void init(View view)
	{
		initPullRefreshList(view);
	 	queryRecentPrenotice();
	}
	
	private void initPullRefreshList(View view)
	{
		 /* String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),  
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);*/
			label ="您当前有"+not_updated+"条活动预告未更新" ;
			pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.prenotice_List);
			pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label); 
		  	pullToRefreshListView.setOnRefreshListener(this);
	
		 	prenoticeList = new ArrayList<PreNotice>();
		 	prenoticeAdpter = new PrenoticeAdpter(getActivity());
		 	//msgView
			emptyView = view.findViewById(R.id.empty_view);
		 	ListView msgView = pullToRefreshListView.getRefreshableView();
		 	msgView.setAdapter(prenoticeAdpter);
		 	msgView.setEmptyView(emptyView);
		 	msgView.setOnItemClickListener(this);
		    msgView.setOnItemLongClickListener(this) ;
		 	
			prenoticeList.clear();
			dataHelper = DataBaseContext.getInstance(getActivity());
			prenoticeList = dataHelper.getPrenotice();
			prenoticeAdpter.notifyDataSetChanged();
	}
	
	private void queryRecentPrenotice() 
	{
		prenoticeList.clear();
		 dataHelper = DataBaseContext.getInstance(getActivity());
		prenoticeList = dataHelper.getPrenotice();
		Collections.reverse(prenoticeList);//将最新的消息放在list头部显示
		prenoticeAdpter.notifyDataSetChanged();
	//	configEmptyView(NULL);
		pullToRefreshListView.onRefreshComplete();  
	}
	private void configEmptyView(String msg)
	{
		if (msg == null) {
			emptyView.findViewById(R.id.empty_view3).setVisibility(View.VISIBLE);
			((TextView) emptyView.findViewById(R.id.progress_tips)).setText("获取最新消息中...");
		} else {
			emptyView.findViewById(R.id.empty_view3).setVisibility(View.GONE);
			((TextView) emptyView.findViewById(R.id.progress_tips)).setText(msg);
		}
	} 
	
	private static boolean isRefreshing = false;
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) 
	{
		label ="您当前有"+not_updated+"条活动预告未更新" ;
		isRefreshing = false; 
	 	pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		QueryPreNoticeTask queryPreNoticeTask = new QueryPreNoticeTask() ;
		queryPreNoticeTask.execute();
	}
	
	class QueryPreNoticeTask extends	AsyncTask<integer , integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(integer... params)
		{
			Log.e(Tag, "doinback");
			if(pullToRefreshListView.isHeadherShow())
			{
				if(!isRefreshing)
				{
					String newsString = ComunicationWithServer.queryRecentPrenotice(dataHelper.getLastPrenoticeNum());
					Message  msg = new Message(); 
					Bundle data = new Bundle();
					data.putString(RECNT_PRENOTICE_KEY, newsString);
					msg.setData(data);
					msg.what = 1 ;
					prenoticeHandler.sendMessage(msg);
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
	Handler prenoticeHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
/*
			if(!string.equals(MyApplication.NET_ERROR))
			{
				//解析得到的最新的新闻
				
				//更新newsadpter并将最新的新闻插入到数据库
				newsadpter.notifyDataSetChanged();
				configEmptyView(NULL);
				pullToRefreshListView.onRefreshComplete();  
			}*/
			if(msg.what == 1)
			{
				prenoticeAdpter.notifyDataSetChanged();
				JSONObject jsonObject = null;
				try {
 //"recentpreNotice\":[{\"picture\":\"主席团\",\"content\":\"主席团\",\"site\":\"李华水饺\",\"id\":\"2\",\"pubDate\":\"主席团\",\"source\":\"主席团\",\"name\":\"吃饺子活动\",\"poster\":\"主席团\",\"object\":\"全体成员\",\"sponsor\":\"主席团\",\"date\":\"2014.12.23\"}]}"
					String rencetNoticeString = msg.getData().getString(RECNT_PRENOTICE_KEY) ;
					jsonObject = new JSONObject(rencetNoticeString) ;
					jsonObject = new JSONObject(jsonObject.get("message").toString());
					rencetNoticeString = jsonObject.get(RECNT_PRENOTICE_KEY).toString() ;
					JSONArray jsonArray = new JSONArray(rencetNoticeString);
					int len = jsonArray.length();
					for(int i=0;i<len;i++)
					{
						jsonObject = jsonArray.getJSONObject(i);
						String contentString =  jsonObject.getString("content") ;
						
						if(contentString.length()>20)
						{
							contentString = contentString.substring(0, 20)+"......";
						}

						PreNotice prenotice = new PreNotice(jsonObject.getString("id"),jsonObject.getString("name"),
								jsonObject.getString("source"),jsonObject.getString("pubDate"),
								contentString,jsonObject.getString("picture")
								,jsonObject.getString("picture"));
						prenoticeList.add(0,prenotice) ;
					 	dataHelper.insertPreNotice(prenotice) ;
					}
					pullToRefreshListView.onRefreshComplete();  
				} catch (Exception e) {
					e.printStackTrace();		
					}
				
				Intent i = new Intent();
				i.setClass(getActivity(),MainActivity.class);
				NotificationUtil.showNotification(getActivity(), i, 0,R.drawable.app_logo, "活动预告","活动预告收到了！");
			}
	
			super.handleMessage(msg);
		}
	};
	@Override
	//单击对象
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.e(Tag, "click"+position) ;
		Intent intent = new Intent();
		Bundle bundle = new Bundle() ;
		bundle.putSerializable("prenotice_detail",prenoticeList.get(position-1) );
		intent.putExtras(bundle);
		intent.setClass(getActivity(), PrenoticeDetails.class) ;
		startActivity(intent);
	}
	@Override 
	public void onDestroy() 
	{
		super.onDestroy(); 
		// dataHelper.Close(); 
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
	
	////////////////////////////PrenoticeAdpter/////////////////////////////////////////////////
	class PrenoticeAdpter extends BaseAdapter
	{
		private Context context ; 
		
		public PrenoticeAdpter(Context context)
		{
			this.context = context ;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return prenoticeList.size();
		}
		
		@Override
		public Object getItem(int position) 
		{
			return prenoticeList.get(position);
		}

		@Override
		public long getItemId(int position) 
		{
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
			viewHolder.prenoticeContent = (TextView)convertView.findViewById(R.id.msg_content);
			viewHolder.prenoticeTitle = (TextView)convertView.findViewById(R.id.msg_title); 
			
			viewHolder.name.setText(prenoticeList.get(position).getMessageSource());
			viewHolder.prenoticeContent.setText(prenoticeList.get(position).getMessageContent());
			viewHolder.timeStamp.setText(SystemTime.getSystemTime("-"));
			viewHolder.prenoticeTitle.setText(prenoticeList.get(position).getMessageTitile());
			convertView.setTag(viewHolder);
			return convertView;
		}
				
		 private class ViewHolder
		 {
			 TextView name;
			 TextView prenoticeContent;
			 ImageView headIcon ;
			 TextView timeStamp ;
			 TextView prenoticeTitle;
			// ImageView news ;
		 }
	}
	
	@Override
	public void update(String num)
	{
		int number = Integer.parseInt(num) ;
		not_updated = number>=0?number:0;
	//	Log.e(Tag, "receive!"+not_updated+"条预告") ;
		//Log.i(Tag, "receive"+jsonArray.length()+"条预告@") ;
		//Intent i = new Intent();
		//i.setClass(getActivity(),MainActivity.class);
		//NotificationUtil.showNotification(getActivity(), i, 0,R.drawable.app_logo, "新闻","收到"+jsonArray.length()+"新闻了！");
	}

	/**
	 * 删除消息
	 **/
	private void delmesg(int index)
	{
		Log.e(Tag, "delete item "+index) ;
		int prenoticeID = prenoticeList.get(index).getMessageId() ;
		dataHelper.removeNews(prenoticeID);
		prenoticeList.remove(index);
		ComunicationWithServer.delete("penotice", prenoticeID);
		prenoticeAdpter.notifyDataSetChanged();
		
	}

	@Override
	//长按对象后
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		final int  pos = position ; 
		if(!ConfigRecorder.getStringRecord(getActivity(), MyApplication.filenameString,User.LIMITION, "普通").equals("普通"))
		{
			new AlertDialog.Builder(getActivity())
			.setCancelable(true)
			.setMessage("是否删除该预告?")
			.setNegativeButton("取消",null)
			.setPositiveButton("删除", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Log.e("pre",  prenoticeList.get(pos-1).getMessageId()+"");
					ComunicationWithServer.delete("prenotice", prenoticeList.get(pos-1).getMessageId());
					delmesg(pos-1);
				}
			})
			.show();
		}
		return false;
	}
}
