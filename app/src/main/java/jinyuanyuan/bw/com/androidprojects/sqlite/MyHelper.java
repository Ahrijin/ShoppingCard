package jinyuanyuan.bw.com.androidprojects.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *Author:Ahri_Love
 *Date:2019/1/8
 */public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "jyys", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists shops(id Integer primary key autoincrement,name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
