package com.example.birdnest.adaptes;

import com.example.birdnest.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactAdapter  extends BaseAdapter
{
	//private TextView name ,phonecall,sendSms;
	private Context context;
	private LayoutInflater layoutInflater;
	public ContactAdapter(Activity context)
	{
		super();
		layoutInflater = (LayoutInflater)context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
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
		if(convertView==null)
		{
			convertView = layoutInflater.inflate(R.layout.contact_list_item, null) ;
			viewHolder.name = (TextView)convertView.findViewById(R.id.contact_name) ;
			viewHolder.phonecall = (TextView)convertView.findViewById(R.id.phnoecall) ;
			viewHolder.sendSms = (TextView)convertView.findViewById(R.id.send_sms) ;
		}
 
		return convertView;
	}

	private class ViewHolder {
		private TextView name;
		private TextView phonecall;
		private TextView sendSms;
	}

}
