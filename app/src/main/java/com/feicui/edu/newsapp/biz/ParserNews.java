package com.feicui.edu.newsapp.biz;

import android.util.Log;

import com.feicui.edu.newsapp.entity.BaseEntity;
import com.feicui.edu.newsapp.entity.News;
import com.feicui.edu.newsapp.entity.NewsGroup;
import com.feicui.edu.newsapp.entity.NewsType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24 0024.

 */
public class ParserNews {
//    使用原始的json解析方法
    public static ArrayList<News> parserNews(String json){
        ArrayList<News> datas = new ArrayList<News>();
        try {
            Log.i("parserNews", json);
            //转换成JSONObject
            JSONObject object = new JSONObject(json);
            String msg = object.getString("message");
            if (msg.equals("OK")){
                //继续解析数据
                JSONArray array = object.getJSONArray("data");
                for (int x = 0; x < array.length(); x++) {
                    JSONObject obj = array.getJSONObject(x);
                    int type = obj.getInt("type");
                    int nid = obj.getInt("nid");
                    String icon = obj.getString("icon");
                    String summary = obj.getString("summary");
                    String link = obj.getString("link");
                    String title = obj.getString("title");
                    String stamp = obj.getString("stamp");
//                    int type, int nid, String stamp, String icon, String title, String summary, String link
                    News news = new News(type, nid, stamp, icon, title, summary, link);
                    datas.add(news);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;
    }

//    使用高端的gson框架解析
    public static BaseEntity<News> parserNewsWithGson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity>(){}.getType();
        BaseEntity<News> baseEntity = gson.fromJson(json,type);
        return baseEntity;
    }

    public static BaseEntity<NewsGroup> parserNewsGroupWithGson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<NewsGroup>>(){}.getType();
        BaseEntity<NewsGroup> baseEntity = gson.fromJson(json, type);
        return baseEntity;
    }



}
