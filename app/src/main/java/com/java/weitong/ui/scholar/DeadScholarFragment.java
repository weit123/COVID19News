package com.java.weitong.ui.scholar;

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

public class DeadScholarFragment extends Fragment {
    private ScholarAdapter scholarAdapter;
    private LinearLayoutManager shabi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scholar, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.scholar_recycler);
        shabi = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(shabi);
        String[] cities = getResources().getStringArray(R.array.domestic_region);
        ArrayList<String> xubin = new ArrayList<String>(Arrays.asList(cities));
        String[] city_codes = getResources().getStringArray(R.array.domestic_code);
        ArrayList<String> codes = new ArrayList<String>(Arrays.asList(city_codes));

        scholarAdapter = new ScholarAdapter(xubin, codes);

        recyclerView.setAdapter(scholarAdapter);

        return root;
    }
}