package com.feicui.edu.newsapp.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class ToastUtils {
    public static Toast toast;

    public static void show(Context context, String text, int duration){
        if (toast == null){
            toast = Toast.makeText(context, text, duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }
}
