package com.example.birdnest.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.birdnest.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

public class DataBaseUtil 
{
	private Context context;  
    public static String dbName = "Kao.db";// ���ݿ������  
    private static String DATABASE_PATH;// ���ݿ����ֻ����·��  
  
    public DataBaseUtil(Context context) {  
        this.context = context;  
        String packageName = context.getPackageName();  
        DATABASE_PATH="/data/data/"+packageName+"/databases/";  
    }  
    
    /** 
     * �ж����ݿ��Ƿ���� 
     *  
     * @return false or true 
     */  
    public boolean checkDataBase() {  
        SQLiteDatabase db = null;  
        try {  
            String databaseFilename = DATABASE_PATH + dbName;  
            db = SQLiteDatabase.openDatabase(databaseFilename, null,SQLiteDatabase.OPEN_READONLY);  
        } catch (SQLiteException e) {  
  
        }  
        if (db != null) {  
            db.close();  
        }  
        return db != null ? true : false;  
    }  
    
    /** 
     * �������ݿ⵽�ֻ�ָ���ļ����� 
     *  
     * @throws IOException 
     */  
    public void copyDataBase() throws IOException
    {  
        String databaseFilenames = DATABASE_PATH + dbName;  
        File dir = new File(DATABASE_PATH);  
        if (!dir.exists())// �ж��ļ����Ƿ���ڣ������ھ��½�һ��  
            dir.mkdir();  
        FileOutputStream os = new FileOutputStream(databaseFilenames);// �õ����ݿ��ļ���д����  
        InputStream is = context.getResources().openRawResource(R.raw.birdnest);// �õ����ݿ��ļ���������  
        byte[] buffer = new byte[8192];  
        int count = 0;  
        while ((count = is.read(buffer)) > 0) {  
            os.write(buffer, 0, count);  
            os.flush();  
        }  
        is.close();  
        os.close();  
    }  
    
    /*private void copyDataBaseToPhone() {  
        DataBaseUtil util = new DataBaseUtil(this);  
        // �ж����ݿ��Ƿ����  
        boolean dbExist = util.checkDataBase();  
  
        if (dbExist) {  
            Log.i("tag", "The database is exist.");  
        } else {// �����ھͰ�raw������ݿ�д���ֻ�  
            try {  
                util.copyDataBase();  
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }  */
    
/*    boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
    if(hasSDCard){  
        copyDataBaseToPhone();  
    }else{  
        showToast("δ��⵽SDCard");  
    }  */
}
