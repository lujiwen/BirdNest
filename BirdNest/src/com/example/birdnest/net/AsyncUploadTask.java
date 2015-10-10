package com.example.birdnest.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.example.birdnest.utils.Utility;

public class AsyncUploadTask implements Runnable {

	private final String TAG = getClass().getName();
	private final String MULTIPART_FROM_DATA = "multipart/form-data";
	private final String BOUNDARY = java.util.UUID.randomUUID().toString();
	private final String PREFIX = "--";
	private final String LINEND = "\r\n";
	private final String CHARSET = "UTF-8";
	private final int BUFFERED_TIME = 2 * 60;
	private Context context;
	private AsyncHttpParams params;

	public AsyncUploadTask(Context context, AsyncHttpParams params) {
		this.context = context;
		this.params = params;
	}
	@Override
	public void run() {
		JSONObject result = null;
		try {
			result = upload(params);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//sendResult(result);
		}
	}

	/**
	 * 配置网络连接
	 * 
	 * @param url
	 *            目标网址
	 * @return
	 */
	public HttpURLConnection configConn(final String url) {
		HttpURLConnection conn = null;
		try {
			URL uri = new URL(url);
			conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(BUFFERED_TIME * 1000); // 缓存的最长时�?
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 配置文本参数
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public void addTextParams(DataOutputStream dataOutputStream, final LinkedList<NameValuePair> textParams) throws IOException {
		if (textParams != null) {
			StringBuilder param = new StringBuilder();
			for (NameValuePair entry : textParams) {
				param.append(PREFIX);
				param.append(BOUNDARY);
				param.append(LINEND);
				param.append("Content-Disposition: form-data; name=\"" + entry.getName() + "\"" + LINEND);
				param.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				param.append("Content-Transfer-Encoding: 8bit" + LINEND);
				param.append(LINEND);
				param.append(entry.getValue());
				param.append(LINEND);
			}
			dataOutputStream.write(param.toString().getBytes());
		}
	}

	/**
	 * 添加文件
	 * 
	 * @param files
	 * @throws IOException
	 */
	public void addFileParams(DataOutputStream dataOutputStream, final LinkedList<NameValuePair> fileParams) throws IOException {
		if (fileParams != null) {
			final String action = params.getAction();
			String name = null;
			for (NameValuePair file : fileParams) {
				StringBuilder param = new StringBuilder();
				param.append(PREFIX);
				param.append(BOUNDARY);
				param.append(LINEND);
				param.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + file.getName() + "\"" + LINEND);
				param.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
				param.append(LINEND);
				dataOutputStream.write(param.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					dataOutputStream.write(buffer, 0, len);
				}
				is.close();
				dataOutputStream.write(LINEND.getBytes());
			}
		}
	}

	/**
	 * 上传文本和文�?
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public JSONObject upload(final AsyncHttpParams params) throws IOException {
		JSONObject result = null;
		HttpURLConnection conn = configConn(params.getBaseUrl());
		DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
		addTextParams(dataOutputStream, params.getTextParams());
		if (params.getFileParams() != null) {
			addFileParams(dataOutputStream, params.getFileParams());
		}
		final byte[] ends = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		dataOutputStream.write(ends);
		dataOutputStream.flush();
		try {
			final int responseCode = conn.getResponseCode();
			InputStream inStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			StringBuilder response = new StringBuilder();
			String str = null;
			while ((str = reader.readLine()) != null) {
				response.append(str);
			}
			final String resStr = Utility.decodeString(response.toString());
			if (responseCode == 200) {
				result = new JSONObject(resStr);
				final int status = result.getInt("status");
				final String message = result.get("message").toString();
				Log.d(TAG, "status=" + status + "|messge=" + message + "\nresponse=" + resStr + "result:\n" + result.toString());
				return result.getJSONObject("newIDs");
			} else {
				Log.e(TAG, "responseCode=" + responseCode + "," + response.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dataOutputStream != null)
				try {
					dataOutputStream.close();
				} catch (IOException e) {
					Log.e(TAG, "[post] " + e.getMessage());
				}
			if (conn != null)
				conn.disconnect();
			Log.d(TAG, "[upload] " + "上传结束");
		}
		return result;
	}
	
	
}
