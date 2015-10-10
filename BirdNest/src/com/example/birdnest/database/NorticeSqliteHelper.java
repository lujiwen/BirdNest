package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NorticeSqliteHelper extends SQLiteOpenHelper {
	// ������������΢���û�����Ѷ΢���û�UserID��Access Token��Access Secret�ı�
	public static final String NOTICE_TABLE_NAME = "notice";  

	public NorticeSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// ������
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
	 
	// ���±�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NOTICE_TABLE_NAME);
		onCreate(db);
		Log.e("Database", "onUpgrade");
	}

	// ������
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
