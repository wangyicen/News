package com.feicui.edu.newsapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feicui.edu.newsapp.entity.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class DBHelper {

    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public DBHelper(Context context){
        if (helper == null){
            helper = new DBOpenHelper(context);
        }
        db = helper.getWritableDatabase();
    }

//    添加数据到数据库
    public void addNews(ArrayList<News> datas){
        //打开数据库
        db = helper.getWritableDatabase();
        for (int x = 0; x < datas.size(); x++) {
            News news = datas.get(x);
            ContentValues cv = new ContentValues();
//nid integer,title text,summary text,stamp text,icon text,link text,type text
            cv.put("nid", news.getNid());
            cv.put("title", news.getTitle());
            cv.put("summary", news.getSummary());
            cv.put("stamp", news.getStamp());
            cv.put("icon", news.getIcon());
            cv.put("link", news.getLink());
            cv.put("type", news.getType());

            db.insert(DBOpenHelper.TABLE_NAME, null, cv);
        }
        //关闭数据库
        db.close();
    }

//    查询新闻表中有多少数据
    public boolean quaryNewsCount(){
        //打开数据库
        db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select count(*) from news", null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        Log.i("dbhelper", count+"");
        cursor.close();
        db.close();
        return count == 0 ? false : true;
    }

//    查询所有的新闻的数据
    public ArrayList<News> quaryAllNewsWithLimit(int startId, int count){
        ArrayList<News> datas = new ArrayList<News>();
        //打开数据库
        db = helper.getWritableDatabase();
//      select * from news;
//        Cursor cursor = db.query(DBOpenHelper.TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = db.rawQuery("select * from news limit " + count + " offset " + startId, null);
        if (cursor.moveToFirst()){
            do {
//                db.execSQL("create table" + TABLE_NAME + "(_id integer primary key autoincrement," +
//                        "nid integer,title text,summary text,stamp text,icon text,link text,type integer)");
                int nid = cursor.getInt(cursor.getColumnIndex("nid"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
//                int type, int nid, String stamp, String icon, String title, String summary, String link
                News news = new News(type, nid, stamp, icon, title, summary, link);
                datas.add(news);

                cursor.close();
                db.close();

            }while (cursor.moveToNext());
        }
        return datas;
    }

}
