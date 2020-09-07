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
import java.util.Arrays;

public class InternationalFragment extends Fragment {
    private DataAdapter dataAdapter;
    private LinearLayoutManager shabi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.data_recycler);
        shabi = new GridLayoutManager(getContext(),3);

        recyclerView.setLayoutManager(shabi);

        String[] countries = getResources().getStringArray(R.array.nation_list);
        ArrayList<String> region = new ArrayList<String>(Arrays.asList(countries));
        String[] country_codes = getResources().getStringArray(R.array.nation_code);
        ArrayList<String> codes = new ArrayList<String>(Arrays.asList(country_codes));

        dataAdapter = new DataAdapter(region,codes);
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
