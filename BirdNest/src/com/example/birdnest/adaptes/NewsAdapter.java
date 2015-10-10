package com.example.birdnest.adaptes;

import java.util.List;
import java.util.Map;

import com.example.birdnest.R;
import com.example.birdnest.service.SystemTime;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter 
{

	private Context context ;
	private Activity  activity ; 
	List<Map<String,Object>> list ;
	private ImageLoader loader;
	private DisplayImageOptions options ;
	
 	public NewsAdapter(Context context, Activity act,List<Map<String, Object>> l)
	{
		this.context = context; 
		this.activity = act;
		this.list = l;
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
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
		viewHolder.newsContent = (TextView)convertView.findViewById(R.id.news_content);
		
		viewHolder.name.setText(getDepartment());
		viewHolder.newsContent.setText(getContent());
		viewHolder.timeStamp.setText(SystemTime.getSystemTime("-"));
		convertView.setTag(viewHolder);
		
		return convertView;
	}
	
	private String getDepartment()
	{
		return new String("新媒体");
	}
	
	private String getContent()
	{
		return "没啥新闻别乱发！" ;
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
		// ImageView news ;
	 }
}
