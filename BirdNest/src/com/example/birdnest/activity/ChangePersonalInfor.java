package com.example.birdnest.activity;

import com.example.birdnest.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 *  该类用于用户个人信息修改
 *  @author 陶菁
 */

public class ChangePersonalInfor extends Activity {

	private Context context ;
	private TextView title_tv;;
	private String name;
	private String dep;
	private String college;
	private String hobby;
	private String beGoodAt;
	private String pNumber;
	private String hometown;
	private String birthday;
	private String remark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this ;
		setContentView(R.layout.change_person_info_layout);
				
		title_tv = (TextView) this.findViewById(R.id.title_tv);
		title_tv.setText("信息修改");

	}	
//前端信息获取，检测函数
	private boolean checked() {
		name = ((EditText) this.findViewById(R.id.p_name)).getText()
				.toString();
		dep = ((EditText) this.findViewById(R.id.p_dep)).getText()
				.toString();
		college = ((EditText) this.findViewById(R.id.p_college)).getText()
				.toString();
		hobby = ((EditText) this.findViewById(R.id.p_hobby)).getText()
				.toString();
		beGoodAt = ((EditText) this.findViewById(R.id.p_beGoodAt)).getText()
				.toString();
		pNumber = ((EditText) this.findViewById(R.id.p_pNumber)).getText()
				.toString();
		hometown = ((EditText) this.findViewById(R.id.p_hometown)).getText()
				.toString();
		birthday = ((EditText) this.findViewById(R.id.p_birthday)).getText()
				.toString();
		remark = ((EditText) this.findViewById(R.id.p_remark)).getText()
				.toString();


		if (name == null || name.equals("")) {
			Toast.makeText(this, "用户名不能为空", 1).show();
			return false;
		} else if (dep == null || dep.equals("")) {
			Toast.makeText(this, "部门不能为空", 1).show();
			return false;
		} else if (college == null || college.equals("")) {
			Toast.makeText(this, "学院不能为空", 1).show();
			return false;
		} else if (pNumber == null || pNumber.equals("")) {
			Toast.makeText(this, "电话号码不能为空", 1).show();
			return false;
		}else {

			return true;
		}
	}
	//返回按钮响应信息
	public void back_btn(View view) {
		
		Intent intent =  new Intent(ChangePersonalInfor.this,ShowPersonalInfo.class);
		ChangePersonalInfor.this.startActivity(intent);
		ChangePersonalInfor.this.finish();
	}
	//信息注销按钮响应
	public void delete_info(View view){
		Toast.makeText(this, "sorry。。。信息注销功能在调试中", 1).show();
	}
	
	//确认信息修改
	public void changeInfo_click(View view){
		if (checked()) {
	        
					
	
		}
		
		Toast.makeText(this, "sorry。。。信息修改功能在调试中，", 1).show();
	}
}
