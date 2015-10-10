package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NorticeSqliteHelper extends SQLiteOpenHelper {
	// 用来保存新浪微博用户和腾讯微博用户UserID、Access Token、Access Secret的表
	public static final String NOTICE_TABLE_NAME = "notice";  

	public NorticeSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 创建表
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createNoticeTable(db);
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
	 
	// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NOTICE_TABLE_NAME);
		onCreate(db);
		Log.e("Database", "onUpgrade");
	}

	// 更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + NOTICE_TABLE_NAME + " CHANGE " + oldColumn
					+ " " + newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
