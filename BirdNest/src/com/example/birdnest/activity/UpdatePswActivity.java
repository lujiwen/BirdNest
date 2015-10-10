package com.example.birdnest.activity;

import java.util.HashMap;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.birdnest.R;
import com.example.birdnest.control.DialogUtil;
import  com.example.birdnest.control.FloatDialog;
import com.example.birdnest.control.ProgressDlgUtil;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.utils.ActivitySupport;

public class UpdatePswActivity extends ActivitySupport 
{
	private static final String TAG = "UpdatePswActivity";
	private Button updateBtn,updateBackBtn;
	private EditText originPsw,newPsw,confirmPsw;
	private String originpswsString,newpswsString,confirmPswString;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_psw_layout);
		Log.e(TAG, TAG);
		init();
		setTitle("修改密码");
		hideActionRight();
	}
	
	private void init()
	{
		updateBtn = (Button)findViewById(R.id.update_btn);
		//updateBackBtn = (Button)findViewById(R.id.updatepsw_btn_back);
		originPsw = (EditText)findViewById(R.id.origin_psw);
		newPsw = (EditText)findViewById(R.id.new_password);
		confirmPsw = (EditText)findViewById(R.id.confirm_password);
		//updateBackBtn.setOnClickListener(onClickListener);
		//updateBtn.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() 
	{
		public void onClick(View v)
		{
			/*switch (v.getId()) {
			case R.id.update_btn:
				updatePsw();
				break;
			//case R.id.updatepsw_btn_back:
				finish();
				break;
			default:
				break;
			}*/
		}

		
	};
	ProgressDialog  progressDialog;
	private void updatePsw() 
	{
		originpswsString =  originPsw.getText().toString();
		newpswsString =  newPsw.getText().toString();
		confirmPswString =  confirmPsw.getText().toString();
		if(originpswsString.equals("")||newpswsString.equals("")||confirmPsw.equals(""))
		{
			  DialogUtil.showErroAlterdlg(this,getResources().getString(R.string.update_psw), "任意一项不得为空！");
		}
		else if(!newpswsString.equals(confirmPswString))
		{
			  DialogUtil.showErroAlterdlg(this,getResources().getString(R.string.update_psw), "新密码两次输入不一致！");
		}
		else 
		{
			  //提交服务器
			   progressDialog = ProgressDlgUtil.getProgressDlg(this,getResources().getString(R.string.update_psw),getResources().getString(R.string.loadinfo) , false) ;
			   UpdatePswTask  updatePswTask = new UpdatePswTask() ;
			   updatePswTask.execute(originpswsString,newpswsString,confirmPswString);
		}
	}
	
	class UpdatePswTask extends AsyncTask<String, Integer,Boolean>  
	{

		boolean isUpdateSuccess = false ;
		@Override
		protected Boolean doInBackground(String... params) {
			 HashMap<String,String> map = new HashMap<String, String>();
			 map.put("uid", "13548188553");
			 map.put("old_passwd", originpswsString);
			 map.put("new_passwd", newpswsString);
			 String jsonString =  ComunicationWithServer.updatePsw(map);
			 Log.e(TAG, jsonString);
			 JSONObject jsonObject;
				try {
					 jsonObject = new JSONObject(jsonString);
					 if(jsonObject.getString("message").equals("success"))
					 {
						 isUpdateSuccess = true ;
					 }
					 else 
					 {
						 isUpdateSuccess = false ;
					 }
					} catch (JSONException e)
						{
							e.printStackTrace();
						}
			
			 return isUpdateSuccess;
		}
		
		@Override 
		protected void onPostExecute(Boolean isUpdateSuccess )
		{
			if(isUpdateSuccess)
			{
				ToastUtil.showShortToast(UpdatePswActivity.this, "密码修改成功");
				 finish();
			}
			else 
			{
				ToastUtil.showShortToast(UpdatePswActivity.this, new String("密码修改失败"));
			}
			progressDialog.cancel();
		}
		
	}

	@Override
	public void OnClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.action_back:
			finish();
			break;
		 
		default:
			break;
		}
	}
}
