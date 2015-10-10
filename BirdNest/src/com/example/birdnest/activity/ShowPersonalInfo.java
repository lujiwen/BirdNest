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
 *  ���������û�������Ϣ�޸�
 *  @author ��ݼ
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

  //��ʼ������
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
 //��ת������Ϣ
	public void changePersonInfo(View view){
		Intent intent =  new Intent(ShowPersonalInfo.this,ChangePersonalInfor.class);
		ShowPersonalInfo.this.startActivity(intent);
		ShowPersonalInfo.this.finish();
	}
  //ע��������Ϣ
	public void btn_delete_info(View view) {
		
		Toast.makeText(this, "sorry��������Ϣע�������ڵ�����", 1).show();

	}
  //����
	public void btn_back(View view) {
		finish();
	}
  //��ע��Ϣ������Ӧ
	public void remark_solve(View view)
	{
		Toast.makeText(this, "��ı�ע�ǡ�������������", 1).show();
	}
	//ͷ����Ϣ����
	public void headImag_solve(View view){
		Toast.makeText(this, "�����ܻ�ͷ�񡣡�����������", 1).show();
	}

}
