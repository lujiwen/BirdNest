package com.example.birdnest.net;

import android.content.Context;

/**
 * Http相关工具�? 包含异步上传和下�?
 * 
 * @author YouMingyang
 */
public class HttpTools {

	/**
	 * 异步上传
	 * 
	 * @param asyncHttpParams
	 */
	public static void asyncUpload(final Context context, final AsyncHttpParams params) {
		new Thread(new AsyncUploadTask(context, params)).start();
	}

}
