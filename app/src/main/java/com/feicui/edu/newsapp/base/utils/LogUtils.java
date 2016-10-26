package com.feicui.edu.newsapp.base.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class LogUtils {

    private static final boolean isLog = true;

    /**
     * 打印普通日志
     * @param title 标题
     * @param msg   日志内容
     */
    public static void i(String title, String msg){
        if (isLog){
            Log.i(title, msg);
        }
    }

    /**
     * 打印debug日志
     * @param title 标题
     * @param msg   日志内容
     */
    public static void d(String title, String msg){
        if (isLog){
            Log.d(title, msg);
        }
    }

    /**
     * 打印警告日志
     * @param title 标题
     * @param msg   日志内容
     */
    public static void w(String title, String msg){
        if (isLog){
            Log.w(title, msg);
        }
    }

}
