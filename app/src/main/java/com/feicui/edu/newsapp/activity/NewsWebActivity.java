package com.feicui.edu.newsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.feicui.edu.newsapp.NewsAppApplication;
import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.activity.BaseActivity;
import com.feicui.edu.newsapp.entity.News;

public class NewsWebActivity extends BaseActivity {

    private ImageButton ib;
    private ProgressBar pb;
    private WebView wv;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        //初始化
        initView();
//        获取传递过来的News对象
//        news = (News) getIntent().getBundleExtra("bundle").getSerializable("news");

        NewsAppApplication application = (NewsAppApplication) getApplication();
        Bundle bundle = (Bundle) application.getData("bundle");
        news = (News) bundle.getSerializable("news");

//        设置网页的时候，发现没有网络，则使用无网络的方式
        WebSettings settings = wv.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置监听
        setListener();
    }
    @Override
    protected void initView() {
        pb = (ProgressBar) findViewById(R.id.news_web_pb);
        wv = (WebView) findViewById(R.id.news_web_wv);
        ib = (ImageButton) findViewById(R.id.title_bar_ib);
    }

    @Override
    protected void setListener() {
        //设置titlebar中的ImageButton的监听，点击后退出网页
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsWebActivity.this.finish();
            }
        });
        //设置WebView的监听
        wv.setWebChromeClient(webChromeClient);
        //设置点击了网页链接时的监听
        wv.setWebViewClient(webViewClient);
        //设置要显示网页
        wv.loadUrl(news.getLink());

    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wv.loadUrl(url);
            return true;
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient(){
        //在页面加载进度改变的时候调用
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);

            //判断网页是否加载完毕
            if (newProgress >= 100){
                pb.setVisibility(View.GONE);
            }
        }
    };
}
