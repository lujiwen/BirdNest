package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserSqliteHelper extends SQLiteOpenHelper {
	// 用来保存新浪微博用户和腾讯微博用户UserID、Access Token、Access Secret的表
	public static final String USER_TABLE_NAME = "user";  
	public static final String NEWS_TABLE_NAME = "news";  
	public static final String PRENOTICE_TABLE_NAME = "prenotice";  
	public static final String NOTICE_TABLE_NAME = "notice";  

	public UserSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 创建表
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createNewsTable(db);
		createNoticeTable(db);
		createPrenoticeTable(db);
		createUserTable(db);
	}

	private void createUserTable(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "("
				+ User.USERID + " integer primary key," + User.NAME
				+ " varchar," + User.DEPARTMENT + " varchar,"
				+ User.TEL + " varchar," + User.GENDER
				+ " varchar," + User.HOMETOWN + " varchar,"
				+ User.BIRTHDAY + " varchar," +User.SPECIALITY + " varchar," + User.HOBBY + " varchar,"+ User.LIMITION
				+" varchar,"+User.GRADE+" varchar,"+User.SCHOOL+" varchar,"+User.REMARKS+" varchar"+ ")");
		Log.e("Database", USER_TABLE_NAME+"_"+"onCreate");
	}
	
	private void createNewsTable(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS " + NEWS_TABLE_NAME + "("
				+ News.ID + " integer primary key," + News.TITLE
				+ " varchar," + News.CONTENT + " varchar,"
				+ News.PICTURE + " blob," + News.TIMESTAMP
				+ " varchar," + News.NEWS_SOURCE + " varchar"
				+ ")");
		Log.e("Database", NEWS_TABLE_NAME+"_"+"onCreate");
		
	}
	private void createNoticeTable(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS " + NOTICE_TABLE_NAME + "("
				+ Notice.ID  + " integer primary key," + Notice.TITLE
				+ " varchar," + Notice.CONTENT + " varchar,"
				+ Notice.PICTURE + " blob," + Notice.TIMESTAMP
				+ " varchar," +  Notice.NOTICE_SOURCE+" varchar"+
				 ")");

		Log.e("Database", NOTICE_TABLE_NAME+"_"+"onCreate");
	}
	private void createPrenoticeTable(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS " + PRENOTICE_TABLE_NAME + "("
				+ PreNotice.ID + " integer primary key," + PreNotice.ACT_NAME
				+ " varchar," +PreNotice.ACT_SITE + " varchar,"
				+ PreNotice.ACT_TIME + " varchar," + PreNotice.ACT_OBJECT
				+ " varchar," +PreNotice.ACT_SPONSOR+ " varchar,"
				+ PreNotice.ACT_CONTENT + " varchar," + PreNotice.ACT_POSTER + " blob," +
				PreNotice.TIIMESTAMP  + " varchar,"+PreNotice.PRENOTICE_SOURCE
				+" varchar"+ ")");
		Log.e("Database", PRENOTICE_TABLE_NAME+"_"+"onCreate");
	}
	 
	// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
		onCreate(db);
		Log.e("Database", "onUpgrade");
	}

	// 更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + USER_TABLE_NAME + " CHANGE " + oldColumn
					+ " " + newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
