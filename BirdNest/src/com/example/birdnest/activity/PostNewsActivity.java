package com.example.birdnest.activity;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.MessageInterface.MESSAGE_TYPE;
import com.example.birdnest.database.News;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.example.birdnest.utils.ActivitySupport;
import com.example.birdnest.utils.Utility;

public class PostNewsActivity extends ActivitySupport implements OnClickListener 
{
	private ImageView addpic;
	private String selectPicPath ;
	private EditText newsContent ;
	private EditText titleEditText;
	private static final int  News_Length_Limitation = 100;
	private static final String Tag = "postNews" ; 
	private Context context ;
	DataHelper dataHelper ; 
	@Override 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_news_layout);
		setTitle("发新闻");
		init();
	}
	
	private void init()
	{
	    newsContent = (EditText)findViewById(R.id.msg_content) ;
	    titleEditText = (EditText)findViewById(R.id.msg_title) ;
	    addpic = (ImageView)findViewById(R.id.add_pic);
		addpic.setOnClickListener(this);
		context = this;
		dataHelper = new DataHelper(this);	 
	}
 
	
	//进入选择图片
	private void selectPic() 
	{ 
		 Log.e(Tag, "selectPic") ;
		 Intent i =  new Intent();
		 i.setClass(PostNewsActivity.this, BrowsePhotosActivity.class);
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
	
	private void sendNews()
	{
		Log.e(Tag,"sendNews" );
		String newsContenStr = newsContent.getText().toString();
		int len = newsContenStr.length();
		if(len>News_Length_Limitation)
		{
			ToastUtil.showShortToast(this, "不得多于100个字！");
		}
		else 
		{
			News news = new News();
			news.setMessageTitile(titleEditText.getText().toString());
			news.setMessageContent(newsContent.getText().toString());
			news.setMessageType(MESSAGE_TYPE.NEWS);
			news.setMessageSource(dataHelper.getUserByTel(ConfigRecorder.getStringRecord(context, MyApplication.filenameString, MyApplication.TEL_NUMBER, 0+"")).getDepartment());
			news.setMessageTime(SystemTime.getSystemTime("-"));
			news.setNewsPic("wwww.baidu.com");
		//	Log.e(Tag,DataBaseContext.getInstance(this).insertNews(news)+"");
			UploadNewsTask uploadNewsTask = new UploadNewsTask();
			uploadNewsTask.execute(news);
		}
	}
	
	class UploadNewsTask extends AsyncTask<News, Integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(News... params) 
		{
			try {
/*				if(!selectPicPath.equals(""))
				{
					ComunicationWithServer.uploadFile(new File(selectPicPath)) ;
				}*/
				JSONObject jsonObject = new JSONObject(ComunicationWithServer.uploadNews(params[0]));
				String string = jsonObject.getString("message");
				Log.i(Tag, string) ;
				//ComunicationWithServer.uploadPicture(PostNewsActivity.this, MyApplication.getAppUser().getDepartment(), selectPicPath, "uploadPicture.action");
				
				if(string.equals("success"))
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
			return false ;
		}
		
		@Override
	    protected void onPostExecute(Boolean result)
		{
			if(result)
			{
				ToastUtil.showShortToast(PostNewsActivity.this, "新闻已经发送！");
				finish();
			}
			else 
			{
				ToastUtil.showShortToast(PostNewsActivity.this, "新闻发送失败！");
			}
		}
	}
	 
	@Override
	public void onClick(View v) 
	{
		Log.e(Tag, "click");
		switch(v.getId())
		{
			case R.id.action_back:
				Log.e(Tag, "返回");
				finish();
				break;
			case R.id.action_bar_right :
				sendNews();
				break;
			case R.id.add_pic:
				selectPic();
				break;
		default:
			break;
		}
	}

	@Override
	public void OnClick(View view) {
		// TODO Auto-generated method stub
		Log.e(Tag, "click");
		switch(view.getId())
		{
			case R.id.action_back:
				Log.e(Tag, "返回");
				finish();
				break;
			case R.id.action_bar_right :
				sendNews();
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
		if(resultCode == Activity.RESULT_OK)
		{
			List<String> list = data.getStringArrayListExtra("photos") ; 
			if(list.size()==1)
			{
				///storage/sdcard0/DCIM/Camera/IMG20141217144141.jpg
				selectPicPath = list.get(0) ;
				Log.e("选择的图片路径：",selectPicPath ) ;
				selImg(selectPicPath);
			}
		}
	}
}
