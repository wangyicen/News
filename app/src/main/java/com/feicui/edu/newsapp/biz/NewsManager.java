package com.feicui.edu.newsapp.biz;

import android.content.Context;

import com.feicui.edu.newsapp.base.utils.CommonUtils;
import com.feicui.edu.newsapp.base.utils.NetUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class NewsManager {

    private static NewsManager newsManager;
//    private com.android.volley.toolbox.ImageLoader imageLoader;

    private NewsManager(){}

    public static NewsManager getInstance(){
        if (newsManager == null){
            synchronized (NewsManager.class){
                if (newsManager == null){
                    newsManager = new NewsManager();
                }
            }
        }
        return newsManager;
    }

    public void newsRequest(Context context, int nid, int mode, ResponseHandlerInterface response){
//      使用volley的方法
        //        创建请求序列
       /* RequestQueue queue = Volley.newRequestQueue(context);
//        创建请求,四个参数：请求方式，路径，和服务器连接成功，和服务器连接失败
        StringRequest sr = new StringRequest(Request.Method.GET,
                NetUtils.httpGet(NetUtils.BASE_PATH + "news_list?ver="+ NetUtils.VERSION +
                        "&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20"),
                listener,
                error);
//        添加请求到序列中
        queue.add(sr);*/
//      使用AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NetUtils.BASE_PATH + "news_list?ver="+ NetUtils.VERSION +
                "&subid=1&dir="+ mode + "&nid=" + nid + "&stamp=20140321&cnt=20", response);
    }
    public void newsTypeRequest(Context context, ResponseHandlerInterface response){
//      使用volley的方法
        //        创建请求序列
       /* RequestQueue queue = Volley.newRequestQueue(context);
//        创建请求,四个参数：请求方式，路径，和服务器连接成功，和服务器连接失败
        StringRequest sr = new StringRequest(Request.Method.GET,
                NetUtils.httpGet(NetUtils.BASE_PATH + "news_list?ver="+ NetUtils.VERSION +
                        "&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20"),
                listener,
                error);
//        添加请求到序列中
        queue.add(sr);*/
//      使用AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NetUtils.BASE_PATH + "news_sort?ver=" + NetUtils.VERSION
                + "&imei=" + CommonUtils.getInstance(context).getIMEI(), response);
    }

}
