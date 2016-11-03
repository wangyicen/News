package com.feicui.edu.newsapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.feicui.edu.newsapp.NewsAppApplication;
import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.adapter.NewsListAdapter;
import com.feicui.edu.newsapp.base.utils.ActivityUtils;
import com.feicui.edu.newsapp.base.utils.CommonUtils;
import com.feicui.edu.newsapp.base.utils.LogUtils;
import com.feicui.edu.newsapp.base.utils.ToastUtils;
import com.feicui.edu.newsapp.biz.NewsManager;
import com.feicui.edu.newsapp.biz.ParserNews;
import com.feicui.edu.newsapp.db.DBHelper;
import com.feicui.edu.newsapp.entity.BaseEntity;
import com.feicui.edu.newsapp.entity.News;
import com.feicui.edu.newsapp.entity.NewsGroup;
import com.feicui.edu.newsapp.view.HorizontalListView;
import com.feicui.edu.newsapp.view.XListView;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.feicui.edu.newsapp.biz.ParserNews.parserNewsGroupWithGson;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class FragmentNewsList extends Fragment {
    private ArrayList<News> datas;
    private XListView listView;
    private HorizontalListView hListview;
    private NewsListAdapter adapter;
    private DBHelper helper;
    private ProgressDialog dialog;
    private ActivityUtils activityUtils;
    private static final int REFRESH = 1;
    private static final int LOADMODE = 2;
    private int mode = REFRESH;//上拉和下拉的属性


//    private ImageLoader imageLoader;
    //分页加载时需要记录的条目数
    private int startId;
    private int count = 9;
    /*private Handler handler = new Handler(){
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
    };*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newslist, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hListview = (HorizontalListView) view.findViewById(R.id.fragment_horizontal_list_view);
        listView = (XListView) view.findViewById(R.id.news_list_lv);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        adapter = new NewsListAdapter(getActivity());
        helper = new DBHelper(getActivity());
        activityUtils = new ActivityUtils(this);
        adapter.setListView(listView);
        listView.setAdapter(adapter);

        //判断数据库中是否存在本地缓存文件，判断手机的网络连接是否正常
        if (helper.quaryNewsCount() || !CommonUtils.getInstance(getActivity()).isConnected()) {
            quaryFromDB(startId);
        } else {
            downloadNewsWithRefresh();

        }

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

    private void quaryFromDB(int startId) {
        datas = helper.quaryAllNewsWithLimit(startId, count);
        Log.i("quaryFromDB", datas.toString());
        adapter.addDatas(datas, false);
        adapter.notifyDataSetChanged();
    }

    /*private Response.Listener<String> listener =  new Response.Listener<String>(){
        @Override
        public void onResponse(String response) {
            LogUtils.i("volley", response);
            BaseEntity baseEntity = ParserNews.parserNewsWithGson(response);
            datas = (ArrayList<News>) baseEntity.getData();
            //将获取到的数据传递到适配器中
            adapter.addDatas(datas, true);
            //更新适配器，更新UI
            adapter.notifyDataSetChanged();
            helper.addNews(datas);
            //销毁进度条
            dialog.dismiss();
        }
    };
    private Response.ErrorListener error =  new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            LogUtils.i("volley", error.getMessage());
        }
    };*/

    class MyResponse extends TextHttpResponseHandler{

        @Override
        public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
            LogUtils.i("MyResponse", response);
            ToastUtils.show(getActivity(), "网络下载失败...", 0);


        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            BaseEntity<News> baseEntity = ParserNews.parserNewsWithGson(response);
            datas = (ArrayList<News>) baseEntity.getData();
            if (datas != null){
                if (mode == REFRESH){
                    //将获取到的数据传递到适配器中
                    adapter.addDatas(datas, true);
                }else{
                    //将获取到的数据传递到适配器中
                    adapter.addDatas(datas, false);
                }
                //更新适配器，更新UI
                adapter.notifyDataSetChanged();
                helper.addNews(datas);
            }

            //销毁进度条
            dialog.dismiss();
        }
    }

    private void downloadNewsType(){
        NewsManager.getInstance().newsTypeRequest(getActivity(),
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        ToastUtils.show(getActivity(), "下载失败...", 0);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        BaseEntity<NewsGroup> baseEntity = parserNewsGroupWithGson(response);
                        List<NewsGroup> newsGroups = baseEntity.getData();
                        for (NewsGroup newsGroup : newsGroups) {
                            LogUtils.i("news", newsGroup.getGid() + "," + newsGroup.getSubgrp() + "," + newsGroup.getGroup());
                        }
                    }
                });


    }

    private void downloadNewsWithRefresh() {
        mode = REFRESH;
        //先显示进度条
        dialog = ProgressDialog.show(getActivity(), null, "不要着急，请稍后...");
        /*new Thread(){
            @Override
            public void run() {
                super.run();
//                发送请求的客户端对象
                String json = NetUtils.httpGet(NetUtils.BASE_PATH +
                        "news_list?ver="+ NetUtils.VERSION +
                        "&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20");
                //解析json数据
//                datas = ParserNews.parserNews(json);
                BaseEntity baseEntity = ParserNews.parserNewsWithGson(json);
                datas = (ArrayList<News>) baseEntity.getData();

                //发送消息给Handler
                handler.sendEmptyMessage(0);
            }
        }.start();*/

//        NewsManager.getInstance().newsRequest(getActivity(), listener, error);
        NewsManager.getInstance().newsRequest(getActivity(), 1, mode, new MyResponse());




        /*获取新闻数据DefaultHttpClient和HttpGet*/
    }
    public void setListener(){

        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
//                获取最新的数据
                downloadNewsWithRefresh();
//                下拉刷新后，关闭视图
                listView.stopRefresh();
                listView.stopLoadMore();
            }

            @Override
            public void onLoadMore() {
//                获取最后一条数据的索引
                int lastPosition = datas.size() - 1;
//                int lastPosition = adapter.getDatas().size() - 1;
                int nid = datas.get(lastPosition).getNid();
                downloadNewsWithLoadMode(nid);
                listView.stopRefresh();
                listView.stopLoadMore();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*News news = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
                activityUtils.startActivityWithBundle(NewsList.this, NewsWebActivity.class, bundle);
           */
                NewsAppApplication application = (NewsAppApplication) getActivity().getApplication();
                News news = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
                application.addData("bundle", bundle);
                activityUtils.startActivity(getActivity(), NewsWebActivity.class);

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //设置滚动的监听
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //不滑动时，下载图片，并显示
                    case SCROLL_STATE_IDLE:
                        adapter.setState(false);
                        break;
                    //滑动时，显示本地图片，等停止滑动，显示下载图片
                    case SCROLL_STATE_FLING:
                    case SCROLL_STATE_TOUCH_SCROLL:
                        adapter.setState(true);
                        break;
                }
                //更新适配器，如果不更新状态，则不显示新闻下载后的图片
                adapter.notifyDataSetChanged();
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

    private void downloadNewsWithLoadMode(int nid) {
        mode = REFRESH;
        //先显示进度条
        dialog = ProgressDialog.show(getActivity(), null, "不要着急，请稍后...");
        NewsManager.getInstance().newsRequest(getActivity(), nid, mode, new MyResponse());
    }
}