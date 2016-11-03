package com.feicui.edu.newsapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.adapter.MyBaseAdapter;
import com.feicui.edu.newsapp.entity.NewsType;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class NewsTypeAdapter extends MyBaseAdapter<NewsType> {

    public NewsTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_list_news_type, null);
            vh.tv = (TextView) convertView.findViewById(R.id.tv_news_type);
            convertView.setTag(position);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
//        获取数据
        NewsType newsType = infos.get(position);
        vh.tv.setText(newsType.getSubgroup());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
