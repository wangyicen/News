package com.feicui.edu.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.activity.BaseActivity;

public class LogoActivity extends BaseActivity {
    private ImageView imageView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initView();
        setListener();

        //设置动画属性
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        animation.setAnimationListener(listener);
        imageView.startAnimation(animation);

    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.logo_iv);
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
    }

    @Override
    protected void setListener() {

    }
}
