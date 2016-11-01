package com.feicui.edu.newsapp.demo;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicui.edu.newsapp.R;

public class FragmentViewPagerActivity extends FragmentActivity {

    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_view_pager);

        vp = (ViewPager) findViewById(R.id.vp);

    }
}
