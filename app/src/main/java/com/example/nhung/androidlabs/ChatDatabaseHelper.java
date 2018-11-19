package com.example.nhung.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ChatDatabaseHelper";
    public static final String TABLE_NAME="Lab5_Database";
    public static final String DATABASE_NAME = "MyDatabase";
    public static final int VERSION_NUM = 3;
    public static final String KEY_ID = "_id" ;
    public static final String KEY_MESSAGE = "_messageg" ;


    public ChatDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_MESSAGE+" Text); ");
       // Log.i(ChatDatabaseHelper, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
       // Log.i(ChatDatabaseHelper, "Calling onUpgrade, oldVersion=" + oldVer + "newVersion" + newVer);
    }
}
