package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsSqliteHelper extends SQLiteOpenHelper {
	// ������������΢���û�����Ѷ΢���û�UserID��Access Token��Access Secret�ı�
	public static final String NEWS_TABLE_NAME = "news";  
  

	public NewsSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// ������
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
 
	// ���±�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
		onCreate(db);
		Log.e("Database", NEWS_TABLE_NAME+"onUpgrade");
	}

	// ������
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
