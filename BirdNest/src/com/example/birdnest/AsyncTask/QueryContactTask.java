package com.example.birdnest.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbill.DNS.Record;

import cn.trinea.android.common.util.ToastUtils;

import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.User;
import com.example.birdnest.database.UserSqliteHelper;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.fragments.ContactFragment;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.net.NetWorkUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class QueryContactTask  extends AsyncTask<Void, Integer, Boolean>
{
	private Context context ;
	private	DataHelper dataHelper ;
	
	List<User> userlist = null;
	private final static String Tag = "QueryContactTask";
	private ProgressDialog progressDialog ;
	
	public QueryContactTask(Context context ,ProgressDialog progreebar)
	{
		this.context = context ; 
		dataHelper = DataBaseContext.getInstance(context); 	
		this.progressDialog = progreebar ;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) 
	{
		if(NetWorkUtil.isNetWorkAvailable(context))
		{
			userlist = loadContactFromServer();
		}
		else 
		{
			ToastUtil.showShortToast(context, "无法使用网络！");
		}
		
		if(userlist!=null)
		{
			int size = userlist.size();
			return true ;
		}
		else 
		{
		    return false ;
		}
	}
	
	@Override
    protected void onPostExecute(Boolean result)
    {
		if(result)
		{
			if(userlist!=null)
			{
				int usersize = userlist.size();
		    	Log.e("onpost", usersize+"");
				for(int i=0;i<usersize;i++)
				{
					//classfyUserbyDep(temp.get(i));
				}
			}
			else	
			{
				return ;
			} 
		}
		else  
		{
			ToastUtil.showShortToast(context, "获取通讯列表失败,请稍后重试！");
		}
		ConfigRecorder.getBoolRecord(context, MyApplication.filenameString, MyApplication.LOADED_TEL_KEY, true);
	 	if(progressDialog!=null)
	 	{
	 		progressDialog.cancel(); 
	 	}
    }
	
	/////////////////从网络请求数据 存储到本地服务器/////////////////
	private List<User> loadContactFromServer()
	{
		dataHelper.clearTable(UserSqliteHelper.USER_TABLE_NAME);
		String contacString =  ComunicationWithServer.queryUserList();
		String string = null ;
		List<User> temp = new ArrayList<User>();
		try {
			if(contacString.equals(MyApplication.NET_ERROR))
			{
				return null;
			}
			else 
			{
				JSONObject jsonObject = new JSONObject(contacString);
				jsonObject =new JSONObject(jsonObject.get("message").toString());
				string = jsonObject.get("allContacts").toString();
				Log.e(Tag, string) ;
			}

		} catch (JSONException e1) 
		{
			e1.printStackTrace();
		}
		try {
			JSONArray jsonArray = new JSONArray(string);
			int len = jsonArray.length();
			JSONObject object = new JSONObject();
			User user = new User();
			for(int i=0;i<len;i++)
			{
				object = jsonArray.getJSONObject(i);
				user.setName(object.getString("name"));
				user.setTel(object.getString("phone"));
				user.setDepartment(object.getString("department"));
				user.setSpecality(object.getString("speciality"));
				user.setGender(object.getString("sex"));
			 	user.setHobby(object.getString("hobby"));
				user.setHometown(object.getString("hometown"));
				user.setID(object.getString("id")) ;
				user.setLimit(object.getString("limit"));
				user.setSchool(object.getString("college"));
				user.setBirthday(object.getString("birthday")); 
				Log.e(Tag,DataBaseContext.getInstance(context).inserUser(user)+"");
				temp.add(user) ;
			}
			if(temp!=null)
			{
				ConfigRecorder.recordBool(context, MyApplication.filenameString,MyApplication.LOADED_TEL_KEY, true);	
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return temp ;
	}
}
