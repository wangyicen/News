package com.feicui.edu.newsapp.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.adapter.NewsListAdapter;
import com.feicui.edu.newsapp.base.activity.BaseActivity;
import com.feicui.edu.newsapp.base.utils.NetUtils;
import com.feicui.edu.newsapp.biz.ParserNews;
import com.feicui.edu.newsapp.db.DBHelper;
import com.feicui.edu.newsapp.entity.News;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ArrayList<News> datas;
    private ListView listView;
    private NewsListAdapter adapter;
    private DBHelper helper;
    private ProgressDialog dialog;
    //分页加载时需要记录的条目数
    private int startId;
    private int count = 9;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //将获取到的数据传递到适配器中
            adapter.addDatas(datas, true);
            //更新适配器，更新UI
            adapter.notifyDataSetChanged();
            helper.addNews(datas);
            //销毁进度条
            dialog.dismiss();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
        initView();
        setListener();
        //判断数据库中是否存在本地缓存文件
        if (helper.quaryNewsCount()){
            quaryFromDB(startId);
        }else {
            downloadNews();

        }

//        new Thread(){
//            @Override
//            public void run() {
//                InputStream is = null;
//                HttpURLConnection conn = null;
//                super.run();
//                try {
//                    conn = (HttpURLConnection) new URL(NetUtils.BASE_PATH +
//                            "news_list?ver=1&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20").openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(3000);
//
//                    if (conn.getResponseCode() == 200){
//                        is = conn.getInputStream();
//                        int x;
//                        byte[] datas = new byte[1024];
//                        while ((x = is.read(datas)) != -1){
//                            LogUtils.i("news", new String(datas, 0, x));
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if (is != null){
//                        try {
//                            is.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (conn != null){
//                        conn.disconnect();
//                    }
//                }
//            }
//        }.start();

        /*获取新闻数据DefaultHttpClient和HttpGet*/

    }

    private void quaryFromDB(int startId) {
        datas = helper.quaryAllNewsWithLimit(startId, count);
        Log.i("quaryFromDB", datas.toString());
        adapter.addDatas(datas, false);
        adapter.notifyDataSetChanged();
    }
    private void downloadNews() {
        //先显示进度条
        dialog = ProgressDialog.show(this, null, "不要着急，请稍后...");
        new Thread(){
            @Override
            public void run() {
                super.run();
//                发送请求的客户端对象
                String json = NetUtils.httpGet(NetUtils.BASE_PATH +
                        "news_list?ver="+ NetUtils.VERSION +
                        "&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20");
                //解析json数据
                datas = ParserNews.parserNews(json);
                //发送消息给Handler
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.news_list_lv);
        adapter = new NewsListAdapter(this);
        helper = new DBHelper(this);
        adapter.setListView(listView);
        listView.setAdapter(adapter);

    }


    @Override
    protected void setListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //当滚动时调用
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("News", firstVisibleItem + "," + visibleItemCount + "," + totalItemCount);
                //判断是否拉到了最底部
                if (totalItemCount >= 7 && listView.getLastVisiblePosition() + 1 == totalItemCount){
                    Log.i("News", "上拉加载");
                    quaryFromDB(totalItemCount);
                    adapter.notifyDataSetChanged();
                }


            }
        });


    }
}
