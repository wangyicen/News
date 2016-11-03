package com.feicui.edu.newsapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.feicui.edu.newsapp.R;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class FragmentLeftMenu extends Fragment implements View.OnClickListener {

    RelativeLayout[] layouts = new RelativeLayout[5];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_left, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layouts[0] = (RelativeLayout) view.findViewById(R.id.layout_news);
        layouts[1] = (RelativeLayout) view.findViewById(R.id.layout_favorite);
        layouts[2] = (RelativeLayout) view.findViewById(R.id.layout_local);
        layouts[3] = (RelativeLayout) view.findViewById(R.id.layout_comment);
        layouts[4] = (RelativeLayout) view.findViewById(R.id.layout_photo);
        for (int x = 0; x < layouts.length; x++) {
            layouts[x].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        for (int x = 0; x < layouts.length; x++) {
            layouts[x].setBackgroundColor(0);
        }

        switch (view.getId()){
            case R.id.left_news:
                layouts[0].setBackgroundColor(0x33c85555);
                ((MainActivity)getActivity()).showNewsFragment();
                break;
            case R.id.left_favorite:
                layouts[1].setBackgroundColor(0x33c85555);
                ((MainActivity)getActivity()).showFavoriteFragment();
                break;
            case R.id.left_local:
                layouts[2].setBackgroundColor(0x33c85555);
                break;
            case R.id.left_comment:
                layouts[3].setBackgroundColor(0x33c85555);
                break;
            case R.id.left_photo:
                layouts[4].setBackgroundColor(0x33c85555);
                break;
        }
    }
}
