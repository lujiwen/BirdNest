package com.example.birdnest.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.birdnest.R;
import com.example.birdnest.fileHandler.FileHandler;
import com.example.birdnest.service.SystemTime;
import com.example.birdnest.utils.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.Contacts.Intents.Insert;
import android.provider.MediaStore.Video;
import android.util.Log;

public class DataHelper {
	// 数据库名称
	private static String DB_NAME = "birdnest.db";
	// 数据库版本
	private static int DB_VERSION = 1;
	private Context context ;
	private SQLiteDatabase db;
	private UserSqliteHelper dbHelper;
	
	public DataHelper(Context context) {
		dbHelper = new UserSqliteHelper(context, DB_NAME, null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
		this.context = context ;
	}
	
	/**
	 * 一定的时间内需要重复的操作数据库，那么不要调用close()方法，关闭游标就可以了。
	 **/
	public void Close() {
		db.close();
		dbHelper.close();
	}
	
	public void clearTable(String tableName)
	{
		String selectSql = "select * from " + tableName + " order by id DESC";
		Cursor cursor = db.rawQuery(selectSql, null);
		if(cursor.getCount()!=0)
		{
			String sql1="delete from "+ tableName;
			String sql2="update "+tableName+" set id ='0' where name ='tb_chatrecord'";
			db.execSQL(sql1);
			db.execSQL(sql2);
		}
	}
	
	// 获取users表中的UserID、Access Token、Access Secret的记录
	public List<UserInfo> getUserList(Boolean isSimple) {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null, UserInfo.ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			UserInfo user = new UserInfo();
			//user.setId(cursor.getString(0));
			user.setUserId(cursor.getString(1));
			user.setToken(cursor.getString(2));
			user.setTokenSecret(cursor.getString(3));
			user.setExpires_in(String.valueOf(cursor.getLong(4)));
			user.setUserName(cursor.getString(5));
			if (!isSimple) {
				ByteArrayInputStream stream = new ByteArrayInputStream(
						cursor.getBlob(6));
				Drawable icon = Drawable.createFromStream(stream, "image");
				user.setUserIcon(icon);
			}
			user.setUrl(cursor.getString(7));
			user.setType(cursor.getInt(8));
			userList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return userList;
	}

	//从本地raw数据库文件读数据
	public List<User> getUserlistFromLocal()
	{
		List<User> userTelList = new ArrayList<User>();
		/*Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null,User.USERID + " DESC");*/
	//	SQLiteDatabase.openDatabase(path, factory, flags)
		InputStream inputStream = FileHandler.getRawStream(context, R.raw.birdnest);
		
		UserSqliteHelper dbHelper = new UserSqliteHelper(context, DB_NAME, null, DB_VERSION);
		
		db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(context.getResources().openRawResource(R.raw.birdnest).toString(), null, null, null,
				null, null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			User user = new User();
			user.setName(cursor.getString(cursor.getColumnIndex("name")));
			user.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			user.setTel(cursor.getString(cursor.getColumnIndex("tel")));
			user.setLimit(cursor.getString(cursor.getColumnIndex("limit")));
			/*user.setName(cursor.getString(1));
			user.setDepartment(cursor.getString(3));
			user.setTel(cursor.getString(4));*/
			userTelList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return userTelList;
	}
	
	public List<User> getUserTels()
	{
		List<User> userTelList = new ArrayList<User>();
		/*Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null,User.USERID + " DESC");*/
		Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			User user = new User();
			user.setName(cursor.getString(cursor.getColumnIndex("name")));
			user.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
			user.setTel(cursor.getString(cursor.getColumnIndex("tel")));
			
			/*user.setName(cursor.getString(1));
			user.setDepartment(cursor.getString(3));
			user.setTel(cursor.getString(4));*/
			userTelList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return userTelList;
	}
	
	public User getUserByTel(String tel)
	{
		User user = new User();
		Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, "User.TEL="+ tel, null,
				null, null,null);
		cursor.moveToFirst();
		if(!cursor.isAfterLast())
		{
			if(cursor.getString(1)!=null)
			{
				user.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
				user.setBirthday(cursor.getString(cursor.getColumnIndex(User.BIRTHDAY)));
				user.setDepartment(cursor.getString(cursor.getColumnIndex(User.DEPARTMENT)));
				user.setGender(cursor.getString(cursor.getColumnIndex(User.GENDER)));
				user.setHobby(cursor.getString(cursor.getColumnIndex(User.HOBBY)));
				user.setTel( cursor.getString(cursor.getColumnIndex(User.TEL)));
				user.setHobby(cursor.getString(cursor.getColumnIndex(User.HOBBY)));
				user.setSpecality(cursor.getString(cursor.getColumnIndex(User.SPECIALITY)));
				user.setGender(cursor.getString(cursor.getColumnIndex(User.GENDER)));
			//	user.setUserType(cursor.getString(cursor.getColumnIndex(User.USER_TYPE)));
				user.setSchool(cursor.getString(cursor.getColumnIndex(User.SCHOOL)));
				user.setLimit(cursor.getString(cursor.getColumnIndex(User.LIMITION))); 
			}
		}
		return user ;
	}
	
	public List<Notice> getNotice()
	{
		List<Notice> notices = new ArrayList<Notice>();
		/*Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null,User.USERID + " DESC");*/
		Cursor cursor = db.query(UserSqliteHelper.NOTICE_TABLE_NAME, null, null, null,
				null, null,null);
		cursor.moveToFirst();
	 	//Log.e("getNotice", cursor.isAfterLast() +":"+cursor.getString(1)  );
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) 
		{
			Notice notice = new Notice();
			notice.setMessageId(cursor.getString(cursor.getColumnIndex("id")));
			notice.setMessageTitile(cursor.getString(cursor.getColumnIndex("title")));
			notice.setMessageSource(cursor.getString(cursor.getColumnIndex("notice_source")));
			notice.setMessageContent(cursor.getString(cursor.getColumnIndex("content")));
			notice.setMessageTime(cursor.getString(cursor.getColumnIndex("timestamp")));
			notices.add(notice);
			cursor.moveToNext();
		}
		cursor.close();
		return notices;
	}
	
	/**
	 * 获取最新的新闻的ID号】
	 **/
	public int getLastNewsNum()
	{
		int lastnum = 0 ;
		Cursor cursor = db.query(UserSqliteHelper.NEWS_TABLE_NAME, null, null, null,
				null, null,"id DESC");
		cursor.moveToFirst();
		if(cursor.getCount()!=0)
		{
			lastnum = cursor.getInt(cursor.getColumnIndex("id")) ;
		}
		cursor.close();
		return lastnum;
	}
	/**
	 * 获取最新的通知的ID号
	 **/
	public int getLastNoticeNum()
	{
		int lastnum = 0 ;
		Cursor cursor = db.query(UserSqliteHelper.NOTICE_TABLE_NAME, null, null, null,
				null, null,"id DESC");
		cursor.moveToFirst();
		if(cursor.getCount()!=0)
		{
			lastnum = cursor.getInt(cursor.getColumnIndex("id")) ;
		}
		cursor.close();
		return lastnum;
	}
	/**
	 * 获取最新的活动预告的ID号
	 **/
	public int getLastPrenoticeNum()
	{
		int lastnum = 0 ;
		Cursor cursor = db.query(UserSqliteHelper.PRENOTICE_TABLE_NAME, null, null, null,
				null, null,"id DESC");
		cursor.moveToFirst();
		if(cursor.getCount()!=0)
		{
			lastnum = cursor.getInt(cursor.getColumnIndex("id")) ;	
		}
		cursor.close();
		return lastnum;
	}
	
	public List<News> getNews()
	{
		List<News> newsList = new ArrayList<News>();
		Cursor cursor = db.query(UserSqliteHelper.NEWS_TABLE_NAME, null, null, null,
				null, null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) 
		{
			News news = new News();
			news.setNewsId(cursor.getString(cursor.getColumnIndex("id")));
			news.setMessageTitile(cursor.getString(cursor.getColumnIndex("title")));
			news.setMessageSource(cursor.getString(cursor.getColumnIndex("news_source")));
			news.setMessageContent(cursor.getString(cursor.getColumnIndex("content")));
			news.setMessageTime(cursor.getString(cursor.getColumnIndex("timestamp")));
			newsList.add(news);
			cursor.moveToNext();
		}
		cursor.close();
		return newsList;
	}
	
	public List<PreNotice> getPrenotice() 
	{
		List<PreNotice> prenoticeList = new ArrayList<PreNotice>();
		/*Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, null, null,
				null, null,User.USERID + " DESC");*/
		Cursor cursor = db.query(UserSqliteHelper.PRENOTICE_TABLE_NAME, null, null, null,
				null, null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()/* && (cursor.getString(1) != null)*/) 
		{
			PreNotice prenotice = new PreNotice();
			prenotice.setMessageId(cursor.getString(cursor.getColumnIndex(PreNotice.ID)));
			prenotice.setMessageTitile(cursor.getString(cursor.getColumnIndex(PreNotice.ACT_NAME)));
			prenotice.setMessageSource(cursor.getString(cursor.getColumnIndex(PreNotice.PRENOTICE_SOURCE)));
			prenotice.setMessageContent(cursor.getString(cursor.getColumnIndex(PreNotice.ACT_CONTENT)));
			prenotice.setMessageTime(cursor.getString(cursor.getColumnIndex(PreNotice.TIIMESTAMP)));
			prenoticeList.add(prenotice);
			cursor.moveToNext();
		}
		cursor.close();
		return prenoticeList;
	}

	// 判断users表中的是否包含某个UserID的记录
	public Boolean haveUserInfo(String UserId) {
		Boolean b = false;
		Cursor cursor = db.query(UserSqliteHelper.USER_TABLE_NAME, null, UserInfo.USERID
				+ "=?", new String[] { UserId }, null, null, null);
		b = cursor.moveToFirst();
		Log.e("HaveUserInfo", b.toString());
		cursor.close();
		return b;
	}

	// 更新users表的记录，根据UserId更新用户昵称和用户图标
	public int updateUserInfo(String userName, Bitmap userIcon, String UserId) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERNAME, userName);
		// BLOB类型
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 将Bitmap压缩成PNG编码，质量为100%存储
		userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
		// 构造SQLite的Content对象，这里也可以使用raw
		values.put(UserInfo.USERICON, os.toByteArray());
		int id = db.update(UserSqliteHelper.USER_TABLE_NAME, values, UserInfo.USERID
				+ "=?", new String[] { UserId });
		Log.e("UpdateUserInfo2", id + "");
		return id;
	}

	// 更新users表的记录
	public int updateUserInfo(UserInfo user) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, user.getUserId());
		values.put(UserInfo.TOKEN, user.getToken());
		values.put(UserInfo.TOKENSECRET, user.getTokenSecret());
		values.put(UserInfo.EXPIRES_IN, user.getExpires_in());
		int id = db.update(UserSqliteHelper.USER_TABLE_NAME, values, UserInfo.USERID
				+ "=" + user.getUserId(), null);
		Log.e("UpdateUserInfo", id + "");
		return id;
	}

	// 添加users表的记录
	public Long saveUserInfo(UserInfo user) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, user.getUserId());
		values.put(UserInfo.TOKEN, user.getToken());
		values.put(UserInfo.TOKENSECRET, user.getTokenSecret());
		values.put(UserInfo.EXPIRES_IN, user.getExpires_in());
		Long uid = db.insert(UserSqliteHelper.USER_TABLE_NAME, UserInfo.ID, values);
		Log.e("SaveUserInfo", uid + "");
		return uid;
	}

	public long inserUser(User user)
	{
		ContentValues values = new ContentValues();
		values.put(User.NAME, user.getName());
		values.put(User.DEPARTMENT, user.getDepartment());
		values.put(User.TEL, user.getTel());
		values.put(User.LIMITION, user.getLimit());
		values.put(User.GENDER, user.getGender());
		values.put(User.SCHOOL, user.getSchool());
		values.put(User.HOBBY, user.getHobby());
		values.put(User.BIRTHDAY, user.getBirthday());
		values.put(User.HOMETOWN, user.getHometown());
		values.put(User.SPECIALITY, user.getSpecality());
		Long uid = db.insert(UserSqliteHelper.USER_TABLE_NAME,null, values);
		return uid ;
	}

	public long insertPreNotice(PreNotice prenotice)
	{
		ContentValues values = new ContentValues();
		values.put(PreNotice.ACT_NAME ,prenotice.getMessageTitile());
		values.put(PreNotice.ACT_SITE,prenotice.getActivitySite());
		values.put(PreNotice.ACT_TIME ,prenotice.getActivityTime());
		values.put(PreNotice.ACT_SPONSOR , prenotice.getSponsor());
		values.put(PreNotice.ACT_OBJECT,prenotice.getObject());
		values.put(PreNotice.ACT_CONTENT,prenotice.getMessageContent());
		values.put(PreNotice.TIIMESTAMP, SystemTime.getSystemTime("/"));
		values.put(PreNotice.PRENOTICE_SOURCE, prenotice.getMessageSource());
		values.put(PreNotice.ACT_POSTER, "www.baidu.com");
		Long uid = db.insert(UserSqliteHelper.PRENOTICE_TABLE_NAME,null, values);
		return uid;
	}
	
	public long  insertNews(News news)
	{
		ContentValues values = new ContentValues();
		values.put(News.ID, news.getNewsID());
		values.put(News.TITLE, news.getMessageTitile());
		values.put(News.CONTENT, news.getMessageContent());
		values.put(News.NEWS_SOURCE,"新媒体中心");
		values.put(News.TIMESTAMP, SystemTime.getSystemTime("-"));
		Long uid = db.insert(UserSqliteHelper.NEWS_TABLE_NAME,null, values);
		return uid ;
	}
	
	public long  insertNotice(Notice notice)
	{
		ContentValues values = new ContentValues();
		values.put(Notice.ID, notice.getMessageId());
		values.put(Notice.TITLE, notice.getMessageTitile());
		values.put(Notice.CONTENT, notice.getMessageContent());
		values.put(Notice.NOTICE_SOURCE,"新媒体中心");
		values.put(Notice.TIMESTAMP, SystemTime.getSystemTime("-"));
		Long uid = db.insert(UserSqliteHelper.NOTICE_TABLE_NAME,null, values);
		return uid ;
	}
	
	// 添加users表的记录
	public Long saveUserInfo(UserInfo user, byte[] icon) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.USERID, user.getUserId());
		values.put(UserInfo.USERNAME, user.getUserName());
		values.put(UserInfo.TOKEN, user.getToken());
		values.put(UserInfo.TOKENSECRET, user.getTokenSecret());
		values.put(UserInfo.EXPIRES_IN, user.getExpires_in());
		if (icon != null) {
			values.put(UserInfo.USERICON, icon);
		}
		// 保存文件url
		values.put(UserInfo.URL, user.getUrl());
		values.put(UserInfo.TYPE, user.getType());
		Long uid = db.insert(UserSqliteHelper.USER_TABLE_NAME, UserInfo.ID, values);
		Log.e("SaveUserInfo", uid + "");
		return uid;
	}

	// 删除users表的记录
	public int delUserInfo(String UserId) {
		int id = db.delete(UserSqliteHelper.USER_TABLE_NAME, UserInfo.USERID + "=?",
				new String[] { UserId });
		Log.e("DelUserInfo", id + "");
		return id;
	}
	
	/**
	 * 删除一条新闻
	 **/
	public void removeNews(int newsID)
	{
		int id  = db.delete(UserSqliteHelper.NEWS_TABLE_NAME,News.ID+"=?", new String[]{newsID+""}) ;
		Log.i("delete news ",newsID+""); 
	}
	/**
	 * 删除一条新闻
	 **/
	public void removeNotice(int noticeID)
	{
		int id  = db.delete(UserSqliteHelper.NOTICE_TABLE_NAME,Notice.ID+"=?", new String[]{noticeID+""}) ;
		Log.i("delete news ",noticeID+""); 
	}
	/**
	 * 删除一条新闻
	 **/
	public void removePrenotice(int prenoticeID)
	{
		int id  = db.delete(UserSqliteHelper.NEWS_TABLE_NAME,News.ID+"=?", new String[]{prenoticeID+""}) ;
		Log.i("delete news ",prenoticeID+""); 
	}
	public static UserInfo getUserByName(String userName,
			List<UserInfo> userList) {
		UserInfo userInfo = null;
		int size = userList.size();
		for (int i = 0; i < size; i++) {
			if (userName.equals(userList.get(i).getUserName())) {
				userInfo = userList.get(i);
				break;
			}
		}
		return userInfo;
	}
}
