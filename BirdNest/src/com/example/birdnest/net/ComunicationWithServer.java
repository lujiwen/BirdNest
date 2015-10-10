package com.example.birdnest.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

import android.content.Context;
import android.util.Log;

import com.example.birdnest.database.News;
import com.example.birdnest.database.Notice;
import com.example.birdnest.database.PreNotice;
import com.example.birdnest.fileHandler.FormFile;

/**
 * 请求联系人  comm("updateContacts.action", null)); 
 **/
public class ComunicationWithServer 
{
	public static String login(HashMap<String, String> map)
	{
		return comm("userLogin.action", map);
	}
	public static String queryUserList()
	{
		return comm("updateContacts.action", null);
	}
	public static String updatePsw(HashMap<String, String> map)
	{
		return comm("userChangePassword.action", map);
	}
	/**
	 * 查询未更新的新闻，通知，预告的数量
	 **/
	public static String queryUpdateNum(int newsNum,int noticeNum,int preNum)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lastNewsNum",String.valueOf(newsNum) );
		map.put("lastNoticeNum",String.valueOf(noticeNum) );
		map.put("lastPreNum",String.valueOf(preNum) );
		return comm("queryUpdateNum.action", map); 
	}
	
	
	public static String uploadNews(News news)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(News.TITLE,news.getMessageTitile());
		map.put(News.CONTENT,news.getMessageContent());
		map.put(News.NEWS_SOURCE,news.getMessageSource());
		map.put(News.TITLE,news.getMessageTitile());
		map.put(News.TIMESTAMP, news.getMessageTime());
		return comm("uploadNews.action",map);
	}
	 public static void delete(String msgtype , int msgID)
	 {
		 HashMap<String,String> map = new HashMap<String,String>();
		 if(msgtype.equals("news"))
		 {
			 map.put("news", msgID+"");
			 Log.e("new_delete", comm("deleteNews.action",map)) ; 
		 }
		 else if(msgtype.equals("notice"))
		 {
			 map.put("notice", msgID+"");
			 comm("deleteNotice.action",map) ;
		 }
		 else if(msgtype.equals("prenotice"))
		 {
			 map.put("prenotice", msgID+"");
			 comm("deletePreNotice.action",map) ;
		 }
	 }
	 
	public static String uploadNotice(Notice notice)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(Notice.TITLE,notice.getMessageTitile());
		map.put(Notice.CONTENT,notice.getMessageContent());
		map.put(Notice.NOTICE_SOURCE,notice.getMessageSource());
		map.put(Notice.TITLE,notice.getMessageTitile());
		map.put(Notice.TIMESTAMP, notice.getMessageTime());
		map.put(Notice.NOTICE_SOURCE, notice.getMessageSource()) ;
		return comm("uploadNotice.action",map);
	}
	
	
	public static String uploadPrenotice(PreNotice prenotice)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(PreNotice.ACT_NAME,prenotice.getMessageTitile());
		map.put(PreNotice.ACT_CONTENT,prenotice.getMessageContent());
		map.put(PreNotice.ACT_OBJECT,prenotice.getObject());
		map.put(PreNotice.ACT_SITE,prenotice.getActivitySite());
		map.put(PreNotice.ACT_TIME,prenotice.getActivityTime());
		map.put(PreNotice.TIIMESTAMP,prenotice.getMessageTime());
		map.put(PreNotice.PRENOTICE_SOURCE, prenotice.getMessageSource()) ;
		//map.put(PreNotice., news.getMessageTime());
		return comm("uploadPreNotice.action",map);
	}
	
	/**
	 * 获取id比lastNewsNum大的新闻
	 **/
	public static String queryRecentNews(int lastNewsNum)
	{
		Log.i("queryRecentNews",lastNewsNum+"");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lastNewsNum", String.valueOf(lastNewsNum)) ;
		return comm("queryRecentNews.action",map);
	}
	
	/**
	 * 获取id比lastNewsNum大的通知
	 **/
	public static String queryRecentNotice(int lastNoticeNum)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lastNoticeNum", String.valueOf(lastNoticeNum)) ;
		return comm("queryRecentNotice.action",map);
	}
	
	/**
	 * 获取id比lastNewsNum大的活动预告
	 **/
	public static String queryRecentPrenotice(int lastPreNum)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lastPresNum", String.valueOf(lastPreNum)) ;
		return comm("queryRecentPrenotice.action",map);
	}
	
	/**
	 * 上传头像
	 * 
	 * @param context
	 *            activity context
	 * @param question
	 *            上传头像
	 */
	public static void uploadAvatar(final Context context, String userName, String filePath) {
		final String baseUrl = BASEURL + "user/add";
		final String action = new  String("uploadAvatar.ation") ;
		LinkedList<NameValuePair> textParams = new LinkedList<NameValuePair>();
		textParams.add(new BasicNameValuePair("user_name", userName));
		if (filePath.startsWith("systemAvatar_")) {
			textParams.add(new BasicNameValuePair("user_avatar", filePath));
		}

		final AsyncHttpParams asyncHttpParams;
		if (filePath.startsWith("systemAvatar_")) {
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, null, action);
		} else {
			LinkedList<NameValuePair> fileParams = new LinkedList<NameValuePair>();
			fileParams.add(new BasicNameValuePair(filePath.substring(filePath.lastIndexOf("/") + 1), filePath));
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, fileParams, action);
		}
		// isUploading = true;
		HttpTools.asyncUpload(context, asyncHttpParams);
	}

	//public static final String BASEURL = "http://202.115.44.116:8888/";
	public static final String BASEURL = "http://202.115.44.116:8080/";
	private static String comm(String urlString, HashMap<String,String> params) 
	{
		  String result = "";
		 //  String uploadUrl = BASEURL + "BirdNest/" + "/";
		 //  String uploadUrl = "http://192.168.1.106:8080/BirdNest/" + "/";
		String uploadUrl = "http://202.115.44.116:8080/BirdNest/" + "/";
		  String MULTIPART_FORM_DATA = "multipart/form-data";
		  String BOUNDARY = "---------7d4a6d158c9"; // 鏁版嵁鍒嗛殧绾�
		
		if (!urlString.equals("")) {
			uploadUrl = uploadUrl + urlString;
			HttpURLConnection conn = null;
			try {
				URL url = new URL(uploadUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);// 鍏佽杈撳叆
				conn.setDoOutput(true);// 鍏佽杈撳嚭
				conn.setUseCaches(false);// 涓嶄娇鐢–ache
				conn.setConnectTimeout(6000);// 6绉掗挓杩炴帴瓒呮椂
				conn.setReadTimeout(25000);// 25绉掗挓璇绘暟鎹秴鏃�
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();
				if (params != null) {
					// 鍦ㄨ繖閲屼笂浼犲弬鏁�
					for (Map.Entry<String, String> entry : params.entrySet()) {
						sb.append("--");
						sb.append(BOUNDARY);
						sb.append("\r\n");
						sb.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"\r\n\r\n");
						sb.append(entry.getValue());
						sb.append("\r\n");
					}
				}
				Log.i("Login", sb.toString());
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				dos.write(sb.toString().getBytes());
				dos.writeBytes("--" + BOUNDARY + "--\r\n");
				dos.flush();

				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);

				// 杩斿洖鐨勭粨鏋� 锛� String绫诲瀷鐨凧SON
				result = br.readLine();

			} catch (Exception e) {
				e.printStackTrace();
				result = "{network error}";
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
		return result;
	}
	
	/**
	 * 上传头像
	 * @param context
	 *            activity context
	 * @param question
	 *            上传头像
	 */
	public static void uploadAvatar(final Context context, String userName, String filePath,String url,String actionsString)
	{
		final String baseUrl = url+ "user/add";
		final String action = actionsString;
		Log.d("123", userName);
		Log.d("123", filePath);
		LinkedList<NameValuePair> textParams = new LinkedList<NameValuePair>();
		textParams.add(new BasicNameValuePair("user_name", userName));
		if (filePath.startsWith("systemAvatar_")) {
			textParams.add(new BasicNameValuePair("user_avatar", filePath));
		}

		final AsyncHttpParams asyncHttpParams;
		if (filePath.startsWith("systemAvatar_")) {
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, null, action);
		} else {
			LinkedList<NameValuePair> fileParams = new LinkedList<NameValuePair>();
			fileParams.add(new BasicNameValuePair(filePath.substring(filePath.lastIndexOf("/") + 1), filePath));
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, fileParams, action);
		}

		// isUploading = true;
		HttpTools.asyncUpload(context, asyncHttpParams);
	}
	
	public static void uploadPicture(final Context context, String userName, String filePath,String actionsString)
	{
		final String baseUrl = BASEURL+ "user/add";
		final String action = actionsString;
		Log.d("username", userName);
		Log.d("filepath", filePath);
		
		///////////////////textParams////////////////////////
		LinkedList<NameValuePair> textParams = new LinkedList<NameValuePair>();
		textParams.add(new BasicNameValuePair("user_name", userName));
		if (filePath.startsWith("systemAvatar_")) {
			textParams.add(new BasicNameValuePair("user_avatar", filePath));
		}
		////////////////////asyncHttpParams/////////////////////////
		final AsyncHttpParams asyncHttpParams;
		if (filePath.startsWith("systemAvatar_")) {
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, null, action);
		} else {
			LinkedList<NameValuePair> fileParams = new LinkedList<NameValuePair>();
			fileParams.add(new BasicNameValuePair(filePath.substring(filePath.lastIndexOf("/") + 1), filePath));
			asyncHttpParams = new AsyncHttpParams(baseUrl, textParams, fileParams, action);
		}
		HttpTools.asyncUpload(context, asyncHttpParams);
	}
	
	public static void uploadpic(String picPath) throws ClientProtocolException, IOException
	{
	    try {
	    	 HttpClient httpClient = new DefaultHttpClient();   
		     httpClient.getParams().setParameter( CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1 );  
		  
	         HttpPost httpPost = new HttpPost( BASEURL+"birdnest/" );  
	  
	         MultipartEntity postEntity = new MultipartEntity();  
	         // 字符用StringBody  
	         String fileName = "wzq.jpg";  
	  
	         ContentBody cbFileName;  
			 cbFileName = new StringBody( fileName );
		   File file = new File(picPath);  
	        ContentBody cbFileData = new FileBody( file, "image/jpg" );  
	        // 把上面创建的这些Body全部加到Entity里面去。  
	        // 注意他们的key，这些key在Struts2服务器端Action的代码里必须保持一致！！  
	        postEntity.addPart( "fileName", cbFileName );  
	        postEntity.addPart( "fileData", cbFileData );  
	  
	        httpPost.setEntity( postEntity );  
	        // 下面这句话就把数据提交到服务器去了  
	        HttpResponse response = httpClient.execute( httpPost );  
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    // 文件用FileBody，并指定文件类型  
	}
	
	
	
	 public static  String uploadFile(File imageFile)
	 {  
        try {  
            String requestUrl = "http://127.0.0.1:8080/YanWo/fileUpload.action";  
            // 请求普通信息  
            Map<String, String> params = new HashMap<String, String>();  
            // params.put("filename", "张三");  
            params.put("name", "新媒体中心");  
            params.put("fileName", imageFile.getName());  
            // 上传文件  
            FormFile formfile = new FormFile(imageFile.getName(), imageFile,  
                    "image", "application/octet-stream");  
            SocketHttpRequester.post(requestUrl, params, formfile);  
  
        } catch (Exception e) {  
            Log.i("file", "upload error");  
            e.printStackTrace();  
        }  
  
        Log.i("file", "upload end");  
        return "OK";  
    }  
	 

}
