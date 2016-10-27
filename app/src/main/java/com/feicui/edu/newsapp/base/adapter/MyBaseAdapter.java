package com.feicui.edu.newsapp.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public ArrayList<T> infos;
    public Context context;
    public LayoutInflater inflater;

    public MyBaseAdapter() {
    }

    public MyBaseAdapter(Context context) {
        infos = new ArrayList<T>();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public T getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getMyView(position, convertView, parent);
    }

    public abstract View getMyView(int position, View convertView, ViewGroup parent);

    //添加数据到当前适配器集合
    public void addDatas(ArrayList<T> infos, boolean isClear){
        if (isClear){
            this.infos.clear();
        }
        if (infos != null){
            this.infos.addAll(infos);
        }
    }
}
