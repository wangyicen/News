package com.feicui.edu.newsapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.adapter.MyBaseAdapter;
import com.feicui.edu.newsapp.biz.ImageLoader;
import com.feicui.edu.newsapp.entity.News;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class NewsListAdapter extends MyBaseAdapter<News> implements ImageLoader.ImageDownloadListener {

    private ImageLoader imageLoader;
    private ListView listView;
    private boolean state;

    public void setState(boolean state) {
        this.state = state;
    }

    public NewsListAdapter(Context context){
        super(context);
        //创建ImageLoader对象
        imageLoader = imageLoader.getInstance();
        //设置监听
        imageLoader.setImageDownloadListener(this);
    }

    public void setListView(ListView listView){
        this.listView = listView;
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_news, null);
            vh.imageView = (ImageView) convertView.findViewById(R.id.item_list_news_iv);
            vh.textView1 = (TextView) convertView.findViewById(R.id.item_list_news_tv1);
            vh.textView2 = (TextView) convertView.findViewById(R.id.item_list_news_tv2);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取数据
        News news = infos.get(position);
        String imgUrl = news.getIcon();
        Log.i("News",imgUrl);
//        给ImageView设置一个图片的url地址
        vh.imageView.setTag(imgUrl);
//        vh.imageView.setImageResource(R.drawable.cccc);
//        缓存获取图片地址
        Bitmap bitmap = imageLoader.getBitmap(imgUrl, context);
        if (!state){
            if (bitmap != null){
                vh.imageView.setImageBitmap(bitmap);
            }
        }else {
            vh.imageView.setImageResource(R.drawable.cccc);
        }

        vh.textView1.setText(news.getTitle());
        vh.textView2.setText(news.getSummary());
        return convertView;
    }
//    网上下载的
    @Override
    public void imageDownloadOk(Bitmap bitmap, String imgUrl) {
//        设置在listview上
        ImageView iv = (ImageView) listView.findViewWithTag(imgUrl);
//        判断bitmap和imageview是否为空
        if (bitmap != null && iv != null){
            iv.setImageBitmap(bitmap);
        }
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView1, textView2;
    }
}
