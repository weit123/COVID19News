package com.java.weitong.ui.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.weitong.R;

import java.util.ArrayList;

public class InternationalFragment extends Fragment {
    private DataAdapter dataAdapter;
    private LinearLayoutManager shabi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.data_recycler);
        shabi = new GridLayoutManager(getContext(),3);

        recyclerView.setLayoutManager(shabi);

        ArrayList<String> xubin = new ArrayList<String>();
        xubin.add("中国"); xubin.add("美国"); xubin.add("巴西"); xubin.add("印度"); xubin.add("英国");
        xubin.add("俄罗斯"); xubin.add("孟加拉国"); xubin.add("秘鲁"); xubin.add("墨西哥"); xubin.add("秘鲁");
        xubin.add("西班牙"); xubin.add("巴基斯坦"); xubin.add("阿根廷"); xubin.add("埃及"); xubin.add("沙特阿拉伯");

        dataAdapter = new DataAdapter(xubin);
//        dataAdapter.setOnitemClickLintener(new DataAdapter.OnitemClick() {
//            @Override
//            public void onItemClick(int position) {
//                Log.d("!!!", "!!!");
//            }
//        });
        recyclerView.setAdapter(dataAdapter);

        return root;
    }
}
