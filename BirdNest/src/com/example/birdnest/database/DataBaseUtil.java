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
    public static String dbName = "Kao.db";// 数据库的名字  
    private static String DATABASE_PATH;// 数据库在手机里的路径  
  
    public DataBaseUtil(Context context) {  
        this.context = context;  
        String packageName = context.getPackageName();  
        DATABASE_PATH="/data/data/"+packageName+"/databases/";  
    }  
    
    /** 
     * 判断数据库是否存在 
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
     * 复制数据库到手机指定文件夹下 
     *  
     * @throws IOException 
     */  
    public void copyDataBase() throws IOException
    {  
        String databaseFilenames = DATABASE_PATH + dbName;  
        File dir = new File(DATABASE_PATH);  
        if (!dir.exists())// 判断文件夹是否存在，不存在就新建一个  
            dir.mkdir();  
        FileOutputStream os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流  
        InputStream is = context.getResources().openRawResource(R.raw.birdnest);// 得到数据库文件的数据流  
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
        // 判断数据库是否存在  
        boolean dbExist = util.checkDataBase();  
  
        if (dbExist) {  
            Log.i("tag", "The database is exist.");  
        } else {// 不存在就把raw里的数据库写入手机  
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
        showToast("未检测到SDCard");  
    }  */
}
