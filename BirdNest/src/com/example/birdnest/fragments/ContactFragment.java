package com.example.birdnest.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smackx.pubsub.provider.RetractEventProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.trinea.android.common.util.ToastUtils;

import com.example.birdnest.R;
import com.example.birdnest.activity.MainActivity;
import com.example.birdnest.application.Const;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.DialogUtil;
import com.example.birdnest.control.ProgressDlgUtil;
import com.example.birdnest.control.TitleBar;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataBaseContext;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.net.ComunicationWithServer;
import com.example.birdnest.service.SystemService;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.R.array;
import android.R.integer;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ContactFragment extends Fragment implements Const
{
	private final static String Tag = "ContactFragment";
	private static ContactFragment instance ;
	private Context context;
	private TitleBar titlebar ; 
	private ProgressDialog progressBar ;
	private ExpandableListView contactList;
	private String[] depart; 
	private String[][] departMember ={};  
	private static Contactlist[] contactlists  = new Contactlist[21];
	private String  PhoneNumber ;
	private	DataHelper dataHelper ;
	List<User> temp = null;
	public static  boolean updated ;
	
	public static ContactFragment getInstance()
	{
		if(instance==null )
		{
			instance = new ContactFragment() ;
		}
		return instance ;
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.e(Tag,"onCreateView");
		context = getActivity().getApplicationContext();
		View view = inflater.inflate(R.layout.tab_layout4, null,false);
		depart = getResources().getStringArray(R.array.department);
		
		titlebar = (TitleBar)view.findViewById(R.id.tabHeader4);
		titlebar.setTitleText(getResources().getString(R.string.tabtitle4));
		contactList = (ExpandableListView)view.findViewById(R.id.contact_List);
		contactList.setAdapter(adapter);
		contactList.setOnChildClickListener(childClickListener);
		init();
		return view;
	}
	
	private void init()
	{
		updated = false ;
		PhoneNumber = String.valueOf(0) ;
		if(context!=null)
		{
			progressBar = ProgressDlgUtil.getProgressDlg(getActivity().getApplicationContext(), "通讯录", "正在努力加载数据...", true);
		}
	}
	
	OnChildClickListener childClickListener = new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) 
		{
	        PhoneNumber = getPhoneNum(groupPosition,childPosition);
			String[] items = {"打电话给TA","发短信给TA","把TA存到电话薄"} ;
			DialogUtil.showSingleChoiceDialog(getActivity(), getResources().getString(R.string.please_choose), items ,contactListener); 
			return false;
		}
	};
	
	private String  getPhoneNum(int groupIndex,int childIndex)
	{
		//从数据库中找到这个人的电话号码 
		String Phonenum =  contactlists[groupIndex].getContactList().get(childIndex).tel;
		return Phonenum;
	}
	
	private OnClickListener contactListener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			Log.e(Tag, which+""); 
			switch (which) {
			case 0:
				SystemService.phoneCall(getActivity(), PhoneNumber);
				break;
			case 1:
				SystemService.sendSms(getActivity(), PhoneNumber); 
				break;
			case 2:
				SystemService.addToContact(getActivity(),PhoneNumber);
			break;
				
			default:
				break;
			}
		}
	};
	
	@Override
	public void onPause()
	{
		super.onPause();
	}
	@Override 
	public void onResume()
	{
		super.onResume();
	}
	
	@Override 
	public void onDestroy()
	{
		super.onDestroy(); 
	}
	
	//滑到这一页再去加载数据   在oncreateView前被调用
	@Override 
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
		if(isVisibleToUser)
		{
			init();
			loadContactData();
		}
	}
	/**
	 * 通讯了列表需要绑定的适配器
	 **/
	 ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			TextView departTextView = getDepartmentView() ;
			departTextView.setText(depart[groupPosition]);
			return departTextView;
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}
		
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return depart.length;
		}
		
		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return depart[groupPosition] ;
		}
		
		@Override
		public int getChildrenCount(int groupPosition) 
		{
			 return contactlists[groupPosition].getContactList().size();
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
				TextView phnoeNumTextView = getDepartMemberTextView() ;
				phnoeNumTextView.setText(contactlists[groupPosition].getContactList().get(childPosition).name);
				return phnoeNumTextView;
		}
		
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	
	/**
	 * 获取通讯录的数据填充通信列表
	 * @throws JSONException 
	 * */
	public void  loadContactData() 
	{
		for(int i=0;i<21;i++)
		{
			contactlists[i] = new Contactlist();
		}
		QueryContactTask querytask= new  QueryContactTask();
		querytask.execute(); 
	}
	
	 /**
	  * 获取group布局
	  **/
		private TextView getDepartmentView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(50, 0, 0, 0);
        textView.setTextSize(17);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
		
		 /**
		  * 获取child布局
		  **/
		private TextView getDepartMemberTextView() {
	        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
	                ViewGroup.LayoutParams.FILL_PARENT, 64);
	        TextView textView = new TextView(getActivity());
	        textView.setLayoutParams(lp);
	        textView.setGravity(Gravity.CENTER_VERTICAL);
	        textView.setPadding(70, 0, 0, 0);
	        textView.setTextSize(17);
	        textView.setTextColor(Color.BLACK);
	        return textView;
	    }
		
		
		/////////////////从网络请求数据 存储到本地服务器/////////////////
		private void loadContactFromServer()
		{
			String contacString =  ComunicationWithServer.queryUserList();
			String string = null ;
			try {
				if(contacString.equals(MyApplication.NET_ERROR))
				{
					return ;
				}
				else 
				{
					JSONObject jsonObject = new JSONObject(contacString);
					jsonObject = new JSONObject(jsonObject.get("message").toString());
					string = jsonObject.get("allContacts").toString();
				}

			} catch (JSONException e1) 
			{
				e1.printStackTrace();
			}
			Log.e(Tag, contacString);
			try {
				JSONArray jsonArray = new JSONArray(string);
				int len = jsonArray.length();
				JSONObject object = new JSONObject();
				User user = new User();
				temp = new ArrayList<User>();
				for(int i=0;i<len;i++)
				{
					object = jsonArray.getJSONObject(i);
					user.setName(object.getString("name"));
					user.setTel(object.getString("phone"));
					user.setDepartment(object.getString("department"));
					
					Log.e(Tag,DataBaseContext.getInstance(getActivity()).inserUser(user)+"");
					temp.add(user) ;
				}
				if(temp!=null)
				{
					ConfigRecorder.recordBool(getActivity(), MyApplication.filenameString,MyApplication.LOADED_TEL_KEY, true);	
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
	
		private class  QueryContactTask extends AsyncTask<Void, Integer, Boolean>
		{
			@Override
		    protected void onPreExecute() 
			{
				if(progressBar!=null)
				{
				 //	progressBar.show();
				}
		    }
			
			@Override
			protected Boolean doInBackground(Void... params) 
			{
				//本地没存电话号码  就从该服务器请求
				if(!ConfigRecorder.getBoolRecord(getActivity(), MyApplication.filenameString, MyApplication.LOADED_TEL_KEY, false))
				{
					Log.i(Tag, "from server!") ;
					loadContactFromServer();
					return true ;
				}
				else 
				{
				    dataHelper = DataBaseContext.getInstance(getActivity());
					temp= dataHelper.getUserTels();
					int size = temp.size();
					Log.e(Tag, size+"from database ");
					if(size>0)
					{
						return  true;
					}
					else
					{
						return false;
					}
				}
			}
			
			@Override
		    protected void onPostExecute(Boolean result)
		    {
				if(result)
				{
					if(temp!=null)
					{
						int usersize = temp.size();
				    	Log.e("onpost", usersize+"");
						for(int i=0;i<usersize;i++)
						{
							classfyUserbyDep(temp.get(i));
						}
						Log.e(Tag, "classfy_end");
					}
					else	
					{
						return ;
					} 
				}
				else  
				{
					ToastUtil.showShortToast(getActivity(), "获取通讯列表失败,请稍后重试！");
				}
				if(progressBar!=null)
				{
					progressBar.cancel();
				}
		    }
		}

		private void classfyUserbyDep(User user)
		{
			if(user.getDepartment().equals(ADVISER_TEACHER))
			{
				contactlists[0].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(COMMITTEE_DEPT))
			{
				contactlists[1].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(PRESENDENT_DEPT))
			{
				contactlists[2].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(SECRETARY_DEPT))
			{
				contactlists[3].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(ORGNISE_DEPT))
			{
				contactlists[4].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(PUBLICITY_DEPT))
			{
				contactlists[5].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(MEDIA_DEPT))
			{
				contactlists[6].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(POSTGRD_NEWSPAPER_DEPT))
			{
				contactlists[7].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(SUNDAY_MAGZINE_DEPT))
			{
				contactlists[8].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(SURVEY_DEPT))
			{
				contactlists[9].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(LIFE_DEPT))
			{
				contactlists[10].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(EX_CONECT_DEPT))
			{
				contactlists[11].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(INTERNATION_DEPT))
			{
				contactlists[12].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(ACADEMIC_DEPT))
			{
				contactlists[13].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(DOCTOR_DEPT))
			{
				contactlists[14].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(PRACTICE_DEPT))
			{
				contactlists[15].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(POINEER_DEPT))
			{
				contactlists[16].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(ART_DEPT))
			{
				contactlists[17].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(SPORT_DEPT))
			{
				contactlists[18].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(JIANGAN_DEPT))
			{
				contactlists[19].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
			else if(user.getDepartment().equals(WEST_CHINA_DEPT))
			{
				contactlists[20].getContactList().add(new contacInfo(user.getName(),user.getTel()));
			}
		}
		
		private class contacInfo 
		{
			public String name ;
			public String tel ;
			public contacInfo(String nm,String tel)
			{
				this.name= nm;
				this.tel = tel;
			}
		}
		
		private  class Contactlist
		{
			public  List<contacInfo> list = new ArrayList<ContactFragment.contacInfo>();
			public List<contacInfo> getContactList()
			{
				return this.list;
			}
		}
}
