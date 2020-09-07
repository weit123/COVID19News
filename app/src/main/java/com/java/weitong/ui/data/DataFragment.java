package com.java.weitong.ui.data;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.java.weitong.R;

import java.util.ArrayList;

public class DataFragment extends Fragment {

    private DataAdapter dataAdapter;
    private LinearLayoutManager shabi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.data_recycler);
        shabi = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(shabi);

        ArrayList<String> xubin = new ArrayList<String>();
        xubin.add("China");
        xubin.add("India");
        xubin.add("Japan");
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