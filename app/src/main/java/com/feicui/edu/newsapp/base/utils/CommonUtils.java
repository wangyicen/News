package com.feicui.edu.newsapp.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class CommonUtils {

    private ConnectivityManager connMgr;
    private TelephonyManager telMgr;
    private static CommonUtils common;

    private CommonUtils(Context context){
        connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
    public static CommonUtils getInstance(Context context){
        if (common == null){
            common = new CommonUtils(context);
        }
        return common;
    }

//   判断网络是否连接
    public boolean isConnected(){
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if (info != null){
            return info.isConnected();
        }
        return false;
    }

//    获取手机IMEI
    public String getIMEI(){
        return telMgr.getDeviceId();
    }

}
