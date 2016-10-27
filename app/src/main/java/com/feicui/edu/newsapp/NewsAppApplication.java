package com.feicui.edu.newsapp;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class NewsAppApplication extends Application {

    private HashMap<String, Object> maps;

//    存放数据
    public void addData(String key, Object object){
        maps.put(key, object);
    }

//    获取数据
    public Object getData(String key){
        if (maps.containsKey(key)){
            return maps.get(key);
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        初始化
        maps = new HashMap<String ,Object>();

    }

    //在内存过低时调用此函数
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.print("内存过低！");
    }
//当整个应用程序执行完成后调用此函数
    @Override
    public void onTerminate() {
        super.onTerminate();
        System.out.print("应用程序执行完成...");
        maps.clear();
    }
}
