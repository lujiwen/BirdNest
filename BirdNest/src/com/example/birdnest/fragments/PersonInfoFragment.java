package com.example.birdnest.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.birdnest.R;
import com.example.birdnest.AsyncTask.QueryContactTask;
import com.example.birdnest.activity.LoginActivity;
import com.example.birdnest.activity.PostNewsActivity;
import com.example.birdnest.activity.PostNoticeActivity;
import com.example.birdnest.activity.PostPrenoticeActivity;
import com.example.birdnest.activity.ShowPersonalInfo;
import com.example.birdnest.activity.UpdatePswActivity;
import com.example.birdnest.application.MyApplication;
import com.example.birdnest.control.ProgressDlgUtil;
import com.example.birdnest.control.TitleBar;
import com.example.birdnest.control.ToastUtil;
import com.example.birdnest.database.DataHelper;
import com.example.birdnest.database.User;
import com.example.birdnest.fileHandler.ConfigRecorder;
import com.example.birdnest.fileHandler.FileHandler;
import com.example.birdnest.updateManager.UpdateManager;
 
public class PersonInfoFragment  extends Fragment implements OnClickListener 
{ 
	private final static String Tag = "PersonInfoFragment";
	private static PersonInfoFragment instance ;
	private Context context;
	private TitleBar titleBar;
	private RelativeLayout personInfoLayout,passwordModifyLayout,postNewsLayout,contactUpdate,postNoticelLayout,postpreNoticeLayout;
	private Button logoutBtn ;
	private UpdateManager mLeverUp;
	public static final String CHECK_UPDATE_URL = "http://www.159758.com:7893/AppMgr/app/reqXml.do?id=2oIXMM";
	private User user ;
	private DataHelper dataHelper ;
	public static PersonInfoFragment getInstance()
	{
		if(instance==null )
		{
			instance = new PersonInfoFragment() ;
		}
		return instance ;
	}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.e(Tag, "onCreateView") ;
		View view = inflater.inflate(R.layout.tab_layout5, null,false);
	    context = getActivity().getApplicationContext();
		titleBar = (TitleBar)view.findViewById(R.id.tabHeader5);
		titleBar.setTitleText(getResources().getString(R.string.tabtitle5));
		personInfoLayout = (RelativeLayout)view.findViewById(R.id.person_info);
		passwordModifyLayout = (RelativeLayout)view.findViewById(R.id.password_modify);
		postNewsLayout = (RelativeLayout)view.findViewById(R.id.post_news);
		postNoticelLayout = (RelativeLayout)view.findViewById(R.id.post_notice);
		postpreNoticeLayout = (RelativeLayout)view.findViewById(R.id.post_pre_notice);
		logoutBtn = (Button)view.findViewById(R.id.logout_btn);
		contactUpdate = (RelativeLayout)view.findViewById(R.id.contact_update);
		contactUpdate.setOnClickListener(this);
		personInfoLayout.setOnClickListener(this);
		postNewsLayout.setOnClickListener(this);
		postNoticelLayout.setOnClickListener(this);
		postpreNoticeLayout.setOnClickListener(this);
		passwordModifyLayout.setOnClickListener(this);
		logoutBtn.setOnClickListener(this);
		
	//	user = dataHelper.getUserByTel(ConfigRecorder.getStringRecord(getActivity(), MyApplication.filenameString, MyApplication.TEL_NUMBER, 0+"")) ;
		String limitString = ConfigRecorder.getStringRecord(getActivity(), MyApplication.filenameString, User.LIMITION, "普通");
		if(limitString.equals("普通"))
		{
			ScrollView scrView = (ScrollView)view.findViewById(R.id.postlayout) ;
			scrView.setVisibility(View.INVISIBLE);
		}
		
		return view;
	}
	//滑到这一页再去加载数据   在oncreateView前被调用
	@Override 
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
		if(isVisibleToUser)
		{
		}
	}
	@Override
	public void onClick(View v) 
	{
		Intent intent = new Intent() ;
		switch (v.getId()) {
		case R.id.person_info:
			Bundle bundle = new Bundle();
			bundle.putSerializable("personal_info", MyApplication.getAppUser());
			intent.putExtras(bundle);
			intent.setClass(getActivity(), ShowPersonalInfo.class);
			startActivity(intent);
			break;
		case R.id.password_modify:
			intent.setClass(getActivity(), UpdatePswActivity.class);
			startActivity(intent);
			break;
		case R.id.post_news:
			intent.setClass(getActivity(), PostNewsActivity.class);
			startActivity(intent);
			break;
		case R.id.post_notice:
			intent.setClass(getActivity(), PostNoticeActivity.class);
			startActivity(intent);
			break;
		case R.id.post_pre_notice:
			intent.setClass(getActivity(), PostPrenoticeActivity.class);
			startActivity(intent);
			break;
		case R.id.contact_update:
			updateContactList();
			break;
		case R.id.logout_btn:
			ConfigRecorder.recordBool(getActivity(), MyApplication.filenameString, MyApplication.REMEBER＿ME, false);
			MyApplication.exitApp2();
			break;
		case R.id.updater:
			
			break ;
			
		default:
			break;
		}
	}

	private void checkToUpdate()
	{
		// 更新
		if (!com.example.birdnest.net.NetWorkUtil.checkNetWorkAvailable(context)) {
			return;
		}
		if (!FileHandler.inquiresSDCard()) {
			ToastUtil.showShortToast(context, "没有找到SD卡");
			return;
		}
		//检测更新
		mLeverUp = new UpdateManager(context, CHECK_UPDATE_URL, handler);
		mLeverUp.checkUpdate(context);
	//	DialogHelp help = DialogHelp.getInstance();
	//	help.showHttpDialog(context, R.string.tip, "正在检查更新...");
	}
	/**
	 * 更新通讯录列表
	 **/
	private void updateContactList()
	{
		ProgressDialog progressDialog = ProgressDlgUtil.getProgressDlg(getActivity(), "通讯录更新","正在努力加载....", true);
		progressDialog.show();
		QueryContactTask task = new QueryContactTask(getActivity(),progressDialog) ;
		task.execute() ;
	}
	
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
	
	private Handler handler = new Handler() {/*
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			// 取消对话框
			DialogHelp help = DialogHelp.getInstance();
			if (!help.isHttpDialogShow()) {
				return;
			} else {
				help.dismissDialog();
			}
			Log.d("update", "recieve message: " + msg.what);
			switch (msg.what) {
			case UpdateManager.HAS_NEW_VERSION: // 新版本
				AppVersionLatest app = (AppVersionLatest) msg.obj;
				if (mLeverUp != null) {
					mLeverUp.updateDialog(app);
				} else {
					new NullPointerException("mLevelUp is null");
				}
				break;
			case UpdateManager.NO_NEW_VERSION: // 没有新版本
				ToastUtil.showShortToast(mContext, (String) msg.obj);
				break;
			case UpdateManager.CONNECTION_FAIL: // 连接失败
				ToastUtil.showShortToast(mContext, (String) msg.obj);
				Log.d("update", "recieve message: " + (String) msg.obj);
				break;

			}
		};
	*/};
}
