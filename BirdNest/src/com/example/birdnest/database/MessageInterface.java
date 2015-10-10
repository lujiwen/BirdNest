package com.example.birdnest.database;


public interface  MessageInterface 
{
	public enum MESSAGE_TYPE{NEWS,NOTICE,PRE_NOTICE};
	public String getMessageSource();
	public MESSAGE_TYPE getMessageType();
	public String getMessageContent();
	public String getMessageTime();
	public String getMessageTitile();
	public int getMessageId();
/////////////////////////////////////////////
	public void setMessageSource(String msgSrc);
	public void setMessageType(MESSAGE_TYPE msgtype);
	public void setMessageContent(String msgContent);
	public void setMessageTime(String msgtm);
	public void setMessageTitile(String msgTitle);
	public void setMessageId(String id) ;
}
