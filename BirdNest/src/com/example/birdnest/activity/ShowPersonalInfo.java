package com.example.birdnest.activity;

import com.example.birdnest.R;
import com.example.birdnest.control.TitleBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.birdnest.database.User;
/**
 *  该类用于用户个人信息修改
 *  @author 陶菁
 */
public class ShowPersonalInfo extends Activity {

	private TextView tv_name;
	private TextView tv_dep;
	private TextView tv_college;
	private TextView tv_hobby;
	private TextView tv_beGoodAt;
	private TextView tv_pNumber;
	private TextView tv_homeTown;
	private TextView tv_birthday;

        private User appUser;

	

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_person_info_layout);
		Intent intent  = this.getIntent();
		appUser  = (User)intent.getSerializableExtra("personal_info");
		initData();
		addInformation();
	}

  //初始化界面
	public void initData() {

		tv_name = (TextView) findViewById(R.id.name_id);
		tv_dep = (TextView) findViewById(R.id.dep_id);
		tv_college = (TextView) findViewById(R.id.college_id);
		tv_hobby = (TextView) findViewById(R.id.hobby_id);	
		tv_beGoodAt = (TextView) findViewById(R.id.beGoodAt_id);
        tv_pNumber = (TextView) findViewById(R.id.pNumber_id);
    	  tv_homeTown= (TextView) findViewById(R.id.hometown_id);
    	  tv_birthday= (TextView) findViewById(R.id.birthday_id);
	 }

	private void addInformation() {
		tv_name.setText(appUser.getName());
		tv_dep.setText(appUser.getDepartment());
		tv_college.setText(appUser.getSchool());
		tv_hobby.setText(appUser.getHobby());
		tv_beGoodAt.setText(appUser.getSpecality());
        tv_pNumber.setText(appUser.getTel());
        tv_homeTown.setText(appUser.getHometown());
  	    tv_birthday.setText(appUser. getBirthday());
	}
 //跳转个人信息
	public void changePersonInfo(View view){
		Intent intent =  new Intent(ShowPersonalInfo.this,ChangePersonalInfor.class);
		ShowPersonalInfo.this.startActivity(intent);
		ShowPersonalInfo.this.finish();
	}
  //注销个人信息
	public void btn_delete_info(View view) {
		
		Toast.makeText(this, "sorry。。。信息注销功能在调试中", 1).show();

	}
  //返回
	public void btn_back(View view) {
		finish();
	}
  //备注信息处理响应
	public void remark_solve(View view)
	{
		Toast.makeText(this, "你的备注是。。。。。。。", 1).show();
	}
	//头像信息处理
	public void headImag_solve(View view){
		Toast.makeText(this, "还不能换头像。。。。。。。", 1).show();
	}

}
