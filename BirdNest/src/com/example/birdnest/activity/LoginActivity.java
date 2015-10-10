package com.example.birdnest.activity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.birdnest.R;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.DialogUtil;
import com.example.birdnest.control.ProgressDlgUtil;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.fileHandler.FileHandler;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.net.JSONAndObject;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 *  该类用于用户进行登陆操作
 *  @author 鲁继文
 */
public class LoginActivity extends Activity
{
	private static final String Tag = "LoginActivity";
	private Button  loginButton;
	private EditText usrText,pswText ;
	private String  username ;
	private String  password;
	ProgressDialog progressDialog = null ;
	private int loginFlag = 0; //0 用户名不存在   1登陆成功   2密码错误 
	DataHelper dataHelper ;
	private boolean isRemeber;
	private CheckBox remberBox ;
	public static String limit ; 
	@Override 
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		Log.e(Tag, Tag);
		init();
	}
	
	private void init()
	{
		usrText = (EditText)findViewById(R.id.username) ;
		pswText = (EditText)findViewById(R.id.password);
		loginButton = (Button)findViewById(R.id.login_btn);
		loginButton.setOnClickListener(onClickListener);
		remberBox = (CheckBox)findViewById(R.id.remeber_me) ;
		progressDialog = ProgressDlgUtil.getProgressDlg(this, "登陆", "正在登陆.请耐心等待。。。。。", true);
		dataHelper = new DataHelper(this);	
	}
	
	private OnClickListener onClickListener = new OnClickListener() 
	{
		public void onClick(View v) 
		{
			if(v == loginButton)
			{
				login();
			}
		}
	};
	
	private void login()
	{
		username = usrText.getText().toString();
		password = pswText.getText().toString();
		if((username.trim().length()==0)||(password.trim().length()==0))
		{
			DialogUtil.showErroAlterdlg(this,"登陆错误", "用户名和密码不得为空，请重新输入");
		}
		else //登陆成功
		{
			progressDialog.show();   
		 	LoginTask loginTask = new LoginTask();
		 	loginTask.execute(username,password);
		}
	}
	
	 /**
	  * 提交到服务器进行用户名密码的匹配
	  **/
	private class LoginTask extends AsyncTask<String, Integer, Boolean>
	{
		@Override
		protected Boolean doInBackground(String... params) 
		{
			try {
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("uid", params[0]);
					map.put("passwd", params[1]);
					String resJson = ComunicationWithServer.login(map); 
					Log.e(Tag, resJson);
			
					if (resJson.equals(MyApplication.NET_ERROR)) 
					{
						loginFlag = 3 ;
						return MyApplication.islogIn=false; 
					}
					JSONObject jsonObject = new JSONObject(resJson) ;
					String res  = jsonObject.get("message").toString();
					Log.e(Tag, res);
					if(res.equals("不存在该用户"))
					{
						loginFlag = 0 ;
					   
						return MyApplication.islogIn=false; 
					}
					else if(res.equals("密码不正确"))
					{
						loginFlag = 2 ;
						return MyApplication.islogIn=false; 
					}
					if(res.equals("登陆成功"))
					{
						loginFlag = 1; 
						username = params[0] ;
						return MyApplication.islogIn=true; 
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		@Override 
		protected void onPostExecute(Boolean islogsuccess)
		{
			if(MyApplication.islogIn&&(loginFlag==1))
			{
				MyApplication.user = dataHelper.getUserByTel(username) ; // 登陆成功后将用户信息存入MyAppliacation
				ToastUtil.showShortToast(LoginActivity.this, new String("登陆成功！"));
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this,MainActivity.class);
				startActivity(intent); 
				LoginActivity.this.finish();
				ConfigRecorder.recordBool(LoginActivity.this, MyApplication.filenameString, MyApplication.IS_LOGIN, true) ;
			}
			else if(loginFlag==0)
			{
				DialogUtil.showErroAlterdlg(LoginActivity.this,"登陆错误", "不存在该用户！");
			}
			else if(loginFlag==2)
			{
				DialogUtil.showErroAlterdlg(LoginActivity.this,"登陆错误", "密码错误");
			}
			else if(loginFlag==3)
			{
				DialogUtil.showErroAlterdlg(LoginActivity.this,"网络错误", "网络连接失败");
			}
			progressDialog.cancel();
			if(remberBox.isChecked())
			{
				ConfigRecorder.recordBool(LoginActivity.this, MyApplication.filenameString,MyApplication.REMEBER＿ME , true);
				ConfigRecorder.recordString(LoginActivity.this, MyApplication.filenameString,MyApplication.TEL_NUMBER , username);
				
			}
			ConfigRecorder.recordString(LoginActivity.this, MyApplication.filenameString,User.LIMITION , limit = dataHelper.getUserByTel(username).getLimit());
		}
	}
	 
}
