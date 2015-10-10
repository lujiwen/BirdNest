package com.example.birdnest.database;

import java.io.Serializable;

import android.R.integer;
import android.os.Parcel;
import android.os.Parcelable;


public class News implements MessageInterface ,Serializable 
{
	private String  messageSource;
	private MESSAGE_TYPE  messageType ;
	private String  messageContent;
	private String  messageTime ;
	private String title ;
	private String picUrl;
	private int id ;
	public final static String ID = "id";
	public final static String TITLE = "title";
	public final static String CONTENT = "content" ;
	public final static String PICTURE = "picture_url";
	public final static String TIMESTAMP = "timestamp";
	public final static String NEWS_SOURCE = "news_source";
	public News()
	{
		
	}
	public News(String ID ,String title,String content,String time,String src,String picUrl)
	{
		this.messageContent = content ;
		this.messageSource = src ;
		this.title = title ;
		this.picUrl = picUrl ;
		this.id = Integer.parseInt(ID);
	}
	public int getNewsID()
	{
		return this.id ;
	}
	
	@Override
	public String getMessageSource() {
		// TODO Auto-generated method stub
		return messageSource;
	}

	@Override
	public MESSAGE_TYPE getMessageType() {
		// TODO Auto-generated method stub
		return MESSAGE_TYPE.NEWS ;
	}
	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return messageContent;
	}

	@Override
	public String getMessageTime() {
		// TODO Auto-generated method stub
		return messageTime;
	}

	@Override
	public String getMessageTitile() {
		// TODO Auto-generated method stub
		return title;
	}
 
	@Override
	public void setMessageSource(String msgSrc)
	{
		this.messageSource = msgSrc ;
	}

	@Override
	public void setMessageType(MESSAGE_TYPE msgtype) 
	{
		this.messageType = msgtype ;		
	}

	@Override
	public void setMessageContent(String msgContent)
	{
		this.messageContent = msgContent ;
		
	}

	@Override
	public void setMessageTime(String msgtm)
	{
		this.messageTime = msgtm ;
	}

	@Override
	public void setMessageTitile(String msgTitle)
	{
		this.title = msgTitle ;
	}
	
	public void setNewsPic(String picUrl)
	{
		this.picUrl = picUrl ;
	}
	public void setNewsId(String id)
	{
		this.id = Integer.parseInt(id) ;
	}
	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public void setMessageId(String id)
	{
		this.id = Integer.parseInt(id);
	}
	
/*	@Override
	public int describeContents() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		// TODO Auto-generated method stub
		
	}*/
}
