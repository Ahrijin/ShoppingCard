package jinyuanyuan.bw.com.androidprojects.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/*
 *Author:Ahri_Love
 *Date:2019/1/8
 */public class MyDao {
    private MyHelper helper;
    private SQLiteDatabase database;
    private Context mcontext;

    public MyDao(Context context) {
        mcontext = context;
        helper = new MyHelper(context);
        database = helper.getWritableDatabase();
    }

    //查询的方法
    public ArrayList<String> selectName() {
        ArrayList<String> datas = new ArrayList<>();
        Cursor cursor = database.query("shops", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            datas.add(name);
        }
        return datas;
    }

    //添加数据的方法
    public void insert(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.insert("shops", null, values);
        Toast.makeText(mcontext, "添加成功", Toast.LENGTH_SHORT).show();
    }

    //删除数据的方法
    public void delete() {
        database.execSQL("delete from shops");
    }
}
