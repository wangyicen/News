package com.feicui.edu.newsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.activity.BaseActivity;
import com.feicui.edu.newsapp.base.adapter.BasePagerAdapter;
import com.feicui.edu.newsapp.base.utils.ActivityUtils;
import com.feicui.edu.newsapp.base.utils.LogUtils;

public class LeadActivity extends BaseActivity {

    private ViewPager viewPager;
    private ImageView[] icons = new ImageView[4];
    private BasePagerAdapter adapter;
    private String className;
    private ActivityUtils activityUtils;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initView();
        //设置页面数量
        adapter.setCount(4);
        //给ViewPager设置适配器
        viewPager.setAdapter(adapter);

        setListener();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null){
            //如果没有获取到数据则返回null
            className = bundle.getString("className");
        }
        SharedPreferences shared = getSharedPreferences("first", Context.MODE_PRIVATE);
        boolean isFirst = shared.getBoolean("first", true);

            if(!isFirst){
                intent = new Intent(LeadActivity.this, LogoActivity.class);
                startActivity(intent);
                finish();
            }else{//如果是第一次登录
                LogUtils.d("isin", "ok");
                SharedPreferences.Editor edit = shared.edit();
                edit.putBoolean("first", false);
                edit.commit();
            }
        }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        icons[0] = (ImageView) findViewById(R.id.lead_icon1);
        icons[1] = (ImageView) findViewById(R.id.lead_icon2);
        icons[2] = (ImageView) findViewById(R.id.lead_icon3);
        icons[3] = (ImageView) findViewById(R.id.lead_icon4);
        //设置fragment适配器
        adapter = new BasePagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                for (int i = 0; i < icons.length; i++) {
                    icons[i].setImageResource(R.drawable.adware_style_default);
                }
                icons[position].setImageResource(R.drawable.adware_style_selected);

                if (position == icons.length - 1){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LeadActivity.this,LogoActivity.class);
                    startActivity(intent);
                }
            }


        });

    }
}
