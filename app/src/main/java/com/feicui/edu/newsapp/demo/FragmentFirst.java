package com.feicui.edu.newsapp.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feicui.edu.newsapp.R;

/**
 * Created by asus on 2016/11/1.
 */
public class FragmentFirst extends Fragment {

    public FragmentFirst(){}

    public static FragmentFirst getInstance(int color){
        FragmentFirst fragment = new FragmentFirst();
//        设置当前参数
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.setBackgroundColor(this.getArguments().getInt("color"));

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        int color = this.getArguments().getInt("color");
        layout.setBackgroundColor(color);

    }
}
