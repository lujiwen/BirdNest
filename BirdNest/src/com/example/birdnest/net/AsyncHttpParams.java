package com.example.birdnest.net;

import java.util.LinkedList;

import org.apache.http.NameValuePair;

/**
 * Http参数
 * 
 * @author YouMingyang
 * 
 */
public class AsyncHttpParams {

	private String baseUrl;// 网址
	private LinkedList<NameValuePair> textParams;// 文本参数
	private LinkedList<NameValuePair> fileParams;// 文件参数


	private String action;// 接收信息action

	public AsyncHttpParams(String baseUrl, String action) {
		super();
		this.baseUrl = baseUrl;
		this.action = action;
	}

	public AsyncHttpParams(String baseUrl, LinkedList<NameValuePair> textParams, LinkedList<NameValuePair> fileParams, String action) {
		super();
		this.baseUrl = baseUrl;
		this.textParams = textParams;
		this.fileParams = fileParams;
		this.action = action;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public LinkedList<NameValuePair> getTextParams() {
		return textParams;
	}

	public void setTextParams(LinkedList<NameValuePair> textParams) {
		this.textParams = textParams;
	}

	public LinkedList<NameValuePair> getFileParams() {
		return fileParams;
	}

	public void setFileParams(LinkedList<NameValuePair> fileParams) {
		this.fileParams = fileParams;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
