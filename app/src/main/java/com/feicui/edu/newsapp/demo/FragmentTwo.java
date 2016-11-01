package com.feicui.edu.newsapp.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feicui.edu.newsapp.R;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class FragmentTwo extends Fragment {

    private FragmentThree three;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenttwo, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (three == null){
                    three = new FragmentThree();
                }
                fm.beginTransaction().replace(R.id.fragment1, three).commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
