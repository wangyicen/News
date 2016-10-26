package com.feicui.edu.newsapp.base.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class NetUtils {
    //应用程序基础路径
//    public static final String BASE_PATH = "http://192.168.1.4:8080/newsClient";
    public static final String BASE_PATH = "http://118.244.212.82:9092/newsClient/";

    //应用程序版本号
    public static final int VERSION = 1;
    //最大连接数
    public static final int MAX_TOTAL_CONN = 9;
    //超时时间
    public static final int TIMEOUT = 3000;

    private NetUtils(){}

    private static HttpClient httpClient;

    /* 返回一个客户端的对象 */
    public static HttpClient getHttpClient(){
        if (httpClient == null){
            //创建参数对象
            HttpParams params = new BasicHttpParams();
            //设置属性
            ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONN);
            //从连接池中取出连接超时
            ConnManagerParams.setTimeout(params, TIMEOUT);
            //网络与服务器连接超时
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            //Socket读数据的时间超时
            HttpConnectionParams.setSoTimeout(params, 4000);

            httpClient = new DefaultHttpClient();
        }
        return httpClient;
    }

    /* 用于GET请求的网络连接 */
    public static String httpGet(String url){
        HttpGet httpGet = new HttpGet(url);
        try {
//                  发送请求
            HttpResponse response = getHttpClient().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
//                        将响应体转换为字符串
                String datas = EntityUtils.toString(entity);
                Log.i("httpGet", "datas");
                return datas;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
