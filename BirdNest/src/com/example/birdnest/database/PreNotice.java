package com.example.birdnest.database;

import java.io.Serializable;


/**
 * 活动预告
 **/
public class PreNotice implements MessageInterface ,Serializable
{

	private String  messageAccount;
	private MESSAGE_TYPE  messageType ;
	private String  messageContent;
	private String  messageTime ;
	private String site; // 活动地点
	private String activityTime; //活动时间
	private String actvityName ;
	private String spnsor  ;
	private String object ;
	private int  id ;
	public final static String ID = "id";
	public final static String ACT_NAME = "act_name";
	public final static String ACT_SITE = "act_site";
	public final static String ACT_TIME = "act_time";
	public final static String ACT_OBJECT = "act_object" ;
	public final static String ACT_POSTER = "act_poster";
	public final static String ACT_SPONSOR = "act_sponsor";
	public final static String ACT_CONTENT = "act_content";
	public final static String TIIMESTAMP = "timestamp";
	public final static String PRENOTICE_SOURCE = "prenotice_source";
	
	public  PreNotice(String ID,String actNanme,String account, String msgtime,String msgContent,String lct,String actTime)
	{
		this.messageAccount = account ;
		//this.messageType = msgtype ;
		this.messageContent = msgContent ;
		this.site = lct ;
		this.activityTime = actTime ;
		this.messageTime = msgtime; 
		this.actvityName = actNanme ;
		this.id = Integer.parseInt(ID) ;
	}
	
	public  PreNotice( )
	{
 
	}
	
	public String getActName()
	{
		return activityTime ;
	}
	public void setActName(String name )
	{
		this.actvityName = name ;
	}
	public String getActTime()
	{
		return activityTime ;
	}
	public void setActTime(String time )
	{
		this.activityTime = time ;
	}
	public String getSponsor()
	{
		return spnsor ;
	}
	public String getObject()
	{
		return object  ;
	}
	@Override
	public String getMessageSource() 
	{
		return messageAccount;
	}

	@Override
	public MESSAGE_TYPE getMessageType()
	{
		return MESSAGE_TYPE.PRE_NOTICE;
	}

	@Override
	public String getMessageContent() 
	{
		return messageContent;
	}

	@Override
	public String getMessageTime() 
	{
		return messageTime;
	}
	public String getActivitySite()
	{
		 return site; 
	}
	public String getActivityTime()
	{
		return activityTime ;
	}

	@Override
	public String getMessageTitile()
	{
		return actvityName;
	}

	public String getActObject()
	{
		return object;
	}

	@Override
	public void setMessageSource(String msgSrc) {
			this.messageAccount = msgSrc ;
	}

	@Override
	public void setMessageType(MESSAGE_TYPE msgtype) {
		this.messageType = msgtype ;
	}

	@Override
	public void setMessageContent(String msgContent) {
		this.messageContent = msgContent ; 
	}

	@Override
	public void setMessageTime(String msgtm) {
		this.messageTime = msgtm ;
	}

	@Override
	public void setMessageTitile(String msgTitle)
	{
		this.actvityName = msgTitle ;
	}
	
	public void setSponsor(String sponsor )
	{
		this.spnsor = sponsor ;
	}
	public void setObject(String obj)
	{
		this.object = obj ;
	}
	public void setActSite(String obj)
	{
		this.site = obj ;
	}

	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setMessageId(String id)
	{
		this.id =Integer.parseInt(id) ; 
	}
}
