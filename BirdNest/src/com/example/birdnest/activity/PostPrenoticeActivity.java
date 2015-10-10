package com.example.birdnest.activity;

import java.io.File;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.birdnest.R;
import com.example.birdnest.activity.PostNoticeActivity.UploadNoticeTask;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.Notice;
import com.example.birdnest.database.MessageInterface.MESSAGE_TYPE;
import com.example.birdnest.database.PreNotice;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemTime;
import com.example.birdnest.utils.ActivitySupport;
import com.example.birdnest.utils.Utility;

public class PostPrenoticeActivity extends  ActivitySupport implements OnClickListener
{
	private final String Tag = "PostpreNoticeActivity" ;
	private EditText prenoticeTitle ,prenoticeContent ;
	private EditText nameEditText,siteEditText ,timeEditText ,spnsoreEditText,objEditText ;
	private ImageView addpic;	
	private String selectPicPath ;
	private Context context ; 
	private DataHelper dataHelper ;
	@Override 	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_prenotice_layout);
		setTitle("发活动预告");
		init(); 
		MyApplication.getInstance().addActivity(this);
	     addpic = (ImageView)findViewById(R.id.add_pic);
		 addpic.setOnClickListener(this);
		 context = this ;
		 dataHelper = new DataHelper(context) ;
		 selectPicPath = "" ;
	}
	
	private void init()
	{
		prenoticeTitle = (EditText)findViewById(R.id.msg_title) ;
		prenoticeContent = (EditText)findViewById(R.id.msg_content) ;
		
		siteEditText = (EditText)findViewById(R.id.act_site);
		timeEditText = (EditText)findViewById(R.id.act_time);
		//spnsoreEditText = (EditText)findViewById(R.id.act_sponsor);
		objEditText = (EditText)findViewById(R.id.act_object);
	}
	
	private void sendPreNotice()
	{
		Log.e(Tag,"sendPreNotice" );
		String prenoticeContenStr = prenoticeContent.getText().toString();
		int len = prenoticeContenStr.length();
		if(len>100)
		{
			ToastUtil.showShortToast(this, "不得多于100个字！");
		}
		else 
		{
			PreNotice  prenotice = new PreNotice();
			prenotice.setMessageTitile(prenoticeTitle.getText().toString());
			prenotice.setMessageContent(prenoticeContenStr);
			prenotice.setMessageType(MESSAGE_TYPE.PRE_NOTICE);
			prenotice.setMessageSource(dataHelper.getUserByTel(ConfigRecorder.getStringRecord(context, MyApplication.filenameString, MyApplication.TEL_NUMBER, 0+"")).getDepartment());
			prenotice.setMessageTime(SystemTime.getSystemTime("-"));
		//	prenotice.setSponsor(spnsoreEditText.getText().toString());
			prenotice.setObject(objEditText.getText().toString());
			prenotice.setActTime(timeEditText.getText().toString());
			prenotice.setActSite(siteEditText.getText().toString());
			//Log.e(Tag,DataBaseContext.getInstance(this).insertPreNotice(prenotice)+"");
			UploadPreNoticeTask  uploadPreNoticeTask = new UploadPreNoticeTask();
			uploadPreNoticeTask.execute(prenotice); 
		}
	}

	class UploadPreNoticeTask extends AsyncTask<PreNotice,Integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(PreNotice... params)
		{
			try {
				if(!selectPicPath.equals(""))
				{
					//ComunicationWithServer.uploadFile(new File(selectPicPath)) ;
				
				}
				JSONObject jsonObject = new JSONObject(ComunicationWithServer.uploadPrenotice(params[0]));
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
				ToastUtil.showShortToast(PostPrenoticeActivity.this, "活动预告已经发送！");
				PostPrenoticeActivity.this.finish();
			}
			else 
			{
				ToastUtil.showShortToast(PostPrenoticeActivity.this, "活动预告发送失败！");
			}
	    }

	}
	 
	@Override
	public void OnClick(View view) 
	{
		switch (view.getId()) 
		{
		case R.id.action_bar_right:
			sendPreNotice();
			break;
		case R.id.action_back:
			finish();
			break;
		case R.id.add_pic : 
			selectPic(); 
		default:
			break;
		}		
	}
	
	@Override
	protected void onDestroy() 
	{
		Log.e(Tag, "onDestroy");
		super.onDestroy();
	}
	
	//进入选择图片
	private void selectPic() 
	{ 
		 Log.e(Tag, "selectPic") ;
		 Intent i =  new Intent();
		 i.setClass(PostPrenoticeActivity.this, BrowsePhotosActivity.class);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.e(Tag, requestCode+"::"+resultCode) ;
		if(resultCode == Activity.RESULT_OK)
		{
			List<String> list = data.getStringArrayListExtra("photos") ; 
			if(list.size()==1)
			{
				selectPicPath = list.get(0) ; 
				selImg(selectPicPath);
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.action_bar_right:
			sendPreNotice();
			break;
		case R.id.action_back:
			finish();
			break;
		case R.id.add_pic : 
			selectPic(); 
		default:
			break;
		}		
	}
	
}
