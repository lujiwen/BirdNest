package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsSqliteHelper extends SQLiteOpenHelper {
	// 用来保存新浪微博用户和腾讯微博用户UserID、Access Token、Access Secret的表
	public static final String NEWS_TABLE_NAME = "news";  
  

	public NewsSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 创建表
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createNewsTable(db);
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
 
	// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
		onCreate(db);
		Log.e("Database", NEWS_TABLE_NAME+"onUpgrade");
	}

	// 更新列
	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + NEWS_TABLE_NAME + " CHANGE " + oldColumn
					+ " " + newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
