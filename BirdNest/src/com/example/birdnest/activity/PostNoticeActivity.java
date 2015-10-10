package com.example.birdnest.activity;

import java.io.File;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.birdnest.R;
import com.example.birdnest.activity.PostNewsActivity.UploadNewsTask;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.News;
import com.example.birdnest.database.MessageInterface.MESSAGE_TYPE;
import com.example.birdnest.database.Notice;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.example.birdnest.utils.ActivitySupport;
import com.example.birdnest.utils.Utility;

public class PostNoticeActivity extends  ActivitySupport implements android.view.View.OnClickListener
{
	private final String Tag = "PostNoticeActivity" ;
	private EditText noticeTitle ,noticeContent ;
	private final int Notice_Length_Limitation = 100;
	private String selectPicPath ;
	private ImageView addpic;	
	private Context context ;
	private DataHelper dataHelper ;
	@Override 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_notice_layout);
		noticeContent = (EditText)findViewById(R.id.msg_content);
		noticeTitle = (EditText)findViewById(R.id.msg_title);
		setTitle("发通知");
		MyApplication.getInstance().addActivity(this);
	    addpic = (ImageView)findViewById(R.id.add_pic);
		addpic.setOnClickListener(this);
		context = this ;
		dataHelper = new DataHelper(context) ;
		selectPicPath = "" ;
	}
	
	@Override
	public void OnClick(View view)
	{
		switch (view.getId()) {
		case R.id.action_bar_right:
			sendNotice();
			break;
		case R.id.action_back:
			finish();
			break;
		case R.id.add_pic:
			selectPic();
			break;
		default:
			break;
		}
	}
	//进入选择图片
	private void selectPic() 
	{ 
		 Log.e(Tag, "selectPic") ;
		 Intent i =  new Intent();
		 i.setClass(PostNoticeActivity.this, BrowsePhotosActivity.class);
		 startActivity(i);
		 startActivityForResult(i, 0);
	}
	
	//将从图库中的图片放到 添加图片的按钮上
	public void selImg(final String imgPath) 
	{
		addpic.setVisibility(View.VISIBLE);
		int width = Utility.getDefaultPhotoWidth(this, 4);
		addpic.setImageBitmap(com.example.birdnest.utils.BitmapUtils.getThumbnails(this, imgPath, width, width));
	}
	
	
	private void sendNotice()
	{
		Log.e(Tag,"sendNotice" );
		String newsContenStr = noticeContent.getText().toString();
		int len = newsContenStr.length();
		if(len>Notice_Length_Limitation)
		{
			ToastUtil.showShortToast(this, "不得多于100个字！");
		}
		else 
		{
			Notice notice = new Notice();
			notice.setMessageTitile(noticeTitle.getText().toString());
			notice.setMessageContent(noticeContent.getText().toString());
			notice.setMessageType(MESSAGE_TYPE.NOTICE);
			notice.setMessageSource(dataHelper.getUserByTel(ConfigRecorder.getStringRecord(context, MyApplication.filenameString, MyApplication.TEL_NUMBER, 0+"")).getDepartment());
			notice.setMessageTime(SystemTime.getSystemTime("-"));
		//	Log.e(Tag,DataBaseContext.getInstance(this).insertNotice(notice)+"");
			UploadNoticeTask uploadnoticeTask = new UploadNoticeTask();
			uploadnoticeTask.execute(notice);
		}
	}
	
	
	class UploadNoticeTask extends AsyncTask<Notice, Integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(Notice... params) 
		{
			if(!selectPicPath.equals(""))
			{
				ComunicationWithServer.uploadFile(new File(selectPicPath)) ;
			}
			try {
				JSONObject jsonObject = new JSONObject(ComunicationWithServer.uploadNotice(params[0]));
				if(jsonObject.getString("messaeg").equals("success"))
				{
					return true ;
				}
				else 
				{
					return false;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true ;
		}
		
		@Override
	    protected void onPostExecute(Boolean result)
		{
			if(result)
			{
				ToastUtil.showShortToast(PostNoticeActivity.this, "通知已经发送！");
				finish();
			}
			else 
			{
				ToastUtil.showShortToast(PostNoticeActivity.this, "通知发送失败！");
			}
		}
	}

	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_bar_right:
			sendNotice();
			break;
		case R.id.action_back:
			finish();
			break;
		case R.id.add_pic:
			selectPic();
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.e(Tag, requestCode+"::"+resultCode) ;
		if(resultCode == Activity.RESULT_OK)
		{
			List<String> list = data.getStringArrayListExtra("photos") ; 
			if(list.size()==1)
			{
				selectPicPath = list.get(0);
				Log.e(Tag, selectPicPath) ;
				selImg(selectPicPath);
			}
		}
	}
	
}
