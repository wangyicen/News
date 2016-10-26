package com.feicui.edu.newsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "news.db";
    public static final String TABLE_NAME = "news";
    public static final int DB_VERSION = 1;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }
    public DBOpenHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + TABLE_NAME + "(_id integer primary key autoincrement," +
                "nid integer,title text,summary text,stamp text,icon text,link text,type integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
