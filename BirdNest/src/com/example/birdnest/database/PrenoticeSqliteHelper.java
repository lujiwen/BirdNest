package com.example.birdnest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PrenoticeSqliteHelper extends SQLiteOpenHelper {
	// ������������΢���û�����Ѷ΢���û�UserID��Access Token��Access Secret�ı�
	public static final String PRENOTICE_TABLE_NAME = "prenotice";  

	public PrenoticeSqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// ������
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createPrenoticeTable(db);
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
	 
	// ���±�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + PRENOTICE_TABLE_NAME);
		onCreate(db);
		Log.e("Database", "onUpgrade");
	}

	// ������
	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + PRENOTICE_TABLE_NAME + " CHANGE " + oldColumn
					+ " " + newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
