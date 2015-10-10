package com.example.birdnest.database;

import java.io.Serializable;

import android.R.integer;

import com.example.birdnest.control.TitleBar;
import com.example.birdnest.database.MessageInterface.MESSAGE_TYPE;

public class Notice implements MessageInterface , Serializable
{

	public final static String ID = "id";
	public final static String TITLE = "title";
	public final static String CONTENT = "content" ;
	public final static String PICTURE = "picture";
	public final static String TIMESTAMP = "timestamp";
	public final static String NOTICE_SOURCE = "notice_source";
	
	private String  messageSource;
	private MESSAGE_TYPE  messageType ;
	private String  messageContent;
	private String  messageTime ;
	private String title ;
	private String picUrl;
	private int id ;
	
	public Notice()
	{
		
	}
	public Notice(String ID, String title, String content,
			String pic, String time, String src)
	{
		this.id = Integer.parseInt(ID) ;
		this.title = title ;
		this.messageContent = content ;
		this.messageSource = src ;
		this.picUrl = pic ;
		this.messageTime = time ;
	}

	@Override
	public String getMessageSource() {
		// TODO Auto-generated method stub
		return messageSource ;
	}

	@Override
	public MESSAGE_TYPE getMessageType() {
		// TODO Auto-generated method stub
		return messageType ;
	}

	@Override
	public String getMessageContent() {
		// TODO Auto-generated method stub
		return messageContent ;
	}

	@Override
	public String getMessageTime() {
		// TODO Auto-generated method stub
		return messageTime ;
	}

	@Override
	public String getMessageTitile() {
		// TODO Auto-generated method stub
		return title ;
	}

	@Override
	public void setMessageSource(String msgSrc) {
		this.messageSource = msgSrc ;
	}

	@Override
	public void setMessageType(MESSAGE_TYPE msgtype)
	{
		this.messageType =  msgtype ; 
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
	public void setMessageTitile(String msgTitle) {
		this.title = msgTitle ;
	}

	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setMessageId(String id)
	{
		this.id = Integer.parseInt(id) ;
	}
	
}
