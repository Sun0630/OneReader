package com.sx.onereader.searchbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Author sunxin
 * @Date 2017/5/8 21:59
 * @Description 数据库操作类
 */

public class BookSqlHelper extends SQLiteOpenHelper {

    //创建图书表
    static final String CREATER_BOOK = "create table book("
            + "isbn integer primary key,"
            + "image text,"
            + "name text)";
    private Context mContext;

    public BookSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /*创建表的时候调用*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATER_BOOK);
    }

    /*更新表的时候调用*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
