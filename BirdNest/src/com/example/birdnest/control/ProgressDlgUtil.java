package com.example.birdnest.control;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDlgUtil
{
	public static ProgressDialog  getProgressDlg(Context context ,String titile,String msg,Boolean cancelable)
	{
		ProgressDialog progressDialog = new ProgressDialog(context) ;
		progressDialog.setTitle(titile);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(cancelable);
		return progressDialog ;
	}
}
