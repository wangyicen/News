package com.feicui.edu.newsapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.feicui.edu.newsapp.R;
import com.feicui.edu.newsapp.base.activity.BaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private SlidingMenu sm;
    private ImageView left, right;
    private Fragment menuLeft, menuRight, newsList, reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSlidingMenu();
        setListener();

    }

//    关闭侧拉菜单
    public void closeMenu(){
        sm.showContent();
    }

//    显示新闻
    public void showNewsFragment(){
        closeMenu();
        if (newsList == null){
            newsList = new FragmentNewsList();
        }
        getSupportFragmentManager().beginTransaction().
                replace(R.id.layout_content, newsList).commit();
    }

    public void showFavoriteFragment(){
        if (reading == null){
            reading = new FragmentFavorite();
        }
        getSupportFragmentManager().beginTransaction().
                replace(R.id.layout_content, reading).commit();
    }

    public void initSlidingMenu() {
        sm.setMode(SlidingMenu.LEFT_RIGHT);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setAboveOffsetRes(R.dimen.main_sliding_menu_offset);
        sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        sm.setMenu(R.layout.menu_left);
        sm.setSecondaryMenu(R.layout.menu_right);

//      将左右两边的Fragment替换到页面上
        getSupportFragmentManager().beginTransaction().
                replace(R.id.menu_left, menuLeft).commit();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.menu_right, menuRight).commit();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.layout_content, newsList).commit();

    }

    @Override
    protected void initView() {
        sm = new SlidingMenu(this);
        left = (ImageView) findViewById(R.id.main_left);
        right = (ImageView) findViewById(R.id.main_right);
        if (menuLeft == null){
            menuLeft = new FragmentLeftMenu();
        }
        if (menuRight == null){
            menuRight = new FragmentRightMenu();
        }
        if (newsList == null){
            newsList = new FragmentNewsList();
        }

    }

    @Override
    protected void setListener() {
        left.setOnClickListener(this);
        right.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_left:
                if (sm != null && !sm.isMenuShowing())
                    sm.showMenu();
                break;
            case R.id.main_right:
                if (sm != null && !sm.isMenuShowing())
                    sm.showSecondaryMenu();
                break;
        }
    }
}
