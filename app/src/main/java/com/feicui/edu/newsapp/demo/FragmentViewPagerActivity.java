package com.feicui.edu.newsapp.demo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui.edu.newsapp.R;

import java.util.ArrayList;

public class FragmentViewPagerActivity extends FragmentActivity {

    private ViewPager vp;
    private MyViewPagerAdapter adapter;
    private ArrayList<Fragment> fragments;
    private Fragment fragment1, fragment2, fragment3;
    private ImageView iv;
    private int huaW;
    private int curPosition;
    private TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_view_pager);

        fragment1 = FragmentFirst.getInstance(R.color.logo);
        fragment2 = FragmentFirst.getInstance(R.color.logo1);
        fragment3 = FragmentFirst.getInstance(R.color.view1);

        vp = (ViewPager) findViewById(R.id.vp);
        fragments = new ArrayList<Fragment>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        iv = (ImageView) findViewById(R.id.fragment_btn);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        huaW = (screenW - getResources().getDimensionPixelOffset(R.dimen.width) * 2) / fragments.size();

        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(huaW, 1);
        iv.setLayoutParams(params);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                做平移的动画效果
                Animation anim = new TranslateAnimation(curPosition * huaW, position * huaW, 0, 0);
                anim.setDuration(1000);
                anim.setFillAfter(true);

                iv.startAnimation(anim);
                curPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setAdapter(adapter);

    }


    public void labelClick(View view){
        switch (view.getId()){
            case R.id.tv1:
                tv1.setTextColor(Color.RED);
                break;
            case R.id.tv2:
                tv2.setTextColor(Color.RED);
                break;
            case R.id.tv3:
                tv3.setTextColor(Color.RED);
                break;

        }
    }
}
