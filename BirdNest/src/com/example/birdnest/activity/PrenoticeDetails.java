package com.example.birdnest.activity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birdnest.R;
import com.example.birdnest.database.News;
import com.example.birdnest.database.PreNotice;
import com.example.birdnest.utils.ActivitySupport;

public class PrenoticeDetails extends ActivitySupport {

	public static String tag = "prenoticeDetail";
	private PreNotice preNoticeDetail ;  //从prenotices列表传过来的具体数据
	private ImageView Detail_Back, Detail_SenComment;
	private TextView Detail_PrreNoticeName;
	private ImageView Detail_UserHead;
	private TextView Detail_UserName;
	private TextView Detail_MainText;
	private ImageView Detail_MainImg;
	private LinearLayout Detail_Up;
	private ImageView Detail_Up_Img;
	private TextView Detail_Up_text;
	private LinearLayout Detail_Down;
	private ImageView Detail_Down_Img;
	private TextView Detail_Down_text;
		private TextView Detail_ShareNum;
		private LinearLayout Detail_Share;
		private ImageView Detail_Share_Img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		preNoticeDetail = new PreNotice() ; 
		Log.e(tag, "oncreate");
		setContentView(R.layout.detail_show);
		Intent intent  = this.getIntent();
		preNoticeDetail = (PreNotice)intent.getSerializableExtra("prenotice_detail") ;  //把news的具体内容进行显示
		initView();
		addInformation();//将传来的消息传给各个控件
	}
	
	@Override
	public void OnClick(View view) {
		// TODO Auto-generated method stub

	}
	private void initView(){
		MyOnClickListner myOnclick = new MyOnClickListner();
	    Detail_Back = (ImageView) findViewById(R.id.Detail_Back);
	 	Detail_SenComment = (ImageView) findViewById(R.id.Detail_SenComment);
	 	Detail_PrreNoticeName = (TextView) findViewById(R.id.Detail_TitleName);
		Detail_UserHead = (ImageView) findViewById(R.id.Detail_UserHead);
		Detail_UserName = (TextView) findViewById(R.id.Detail_UserName);
		Detail_MainText = (TextView) findViewById(R.id.Detail_MainText);
		Detail_MainImg = (ImageView) findViewById(R.id.Detail_MainImg);
		Detail_Up = (LinearLayout) findViewById(R.id.Detail_Up);
		Detail_Up_Img = (ImageView) findViewById(R.id.Detail_Up_Img);
		Detail_Up_text = (TextView) findViewById(R.id.Detail_Up_text);
		Detail_Down = (LinearLayout) findViewById(R.id.Detail_Down);
		Detail_Down_Img = (ImageView) findViewById(R.id.Detail_Down_Img);
		Detail_Down_text = (TextView) findViewById(R.id.Detail_Down_text);
		Detail_ShareNum = (TextView) findViewById(R.id.Detail_ShareNum);
		Detail_Share = (LinearLayout) findViewById(R.id.Detail_Share);
		Detail_Share_Img = (ImageView) findViewById(R.id.Detail_Share_Img);
		
		
		Detail_Back.setOnClickListener(myOnclick);
		Detail_SenComment.setOnClickListener(myOnclick);
		Detail_Up.setOnClickListener(myOnclick);
		Detail_Down.setOnClickListener(myOnclick);
		Detail_Share.setOnClickListener(myOnclick);
	}
	
	private void addInformation() {
		Detail_PrreNoticeName.setText(preNoticeDetail.getMessageTitile());
		Detail_MainText.setText(preNoticeDetail.getMessageContent());
		Detail_UserName.setText(preNoticeDetail.getMessageSource());
	}
	
	private class MyOnClickListner implements View.OnClickListener {
		public void onClick(View arg0) {
			int mID = arg0.getId();
			switch (mID) {
			case R.id.Detail_Back:
				PrenoticeDetails.this.finish();
				break;
			case R.id.Detail_SenComment:
				Toast.makeText(PrenoticeDetails.this, "发表评论，还没有实现", 1).show();
				break;
			case R.id.Detail_Up:
				Toast.makeText(PrenoticeDetails.this, "好评功能,还没有实现", 1).show();
				break;
			case R.id.Detail_Down:
				Toast.makeText(PrenoticeDetails.this, "差评功能，还没有实现", 1).show();
				break;
			case R.id.Detail_Share:
				Toast.makeText(PrenoticeDetails.this, "分享功能，还没有实现", 1).show();
				break;
			default:
				break;
			}
		}
	};

}
