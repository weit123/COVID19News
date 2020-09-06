package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.db.*;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import com.java.weitong.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;
    private LinearLayoutManager shabi;
    private NewsList newsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        shabi = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        System.out.println(getContext().getClass());

        recyclerView.setLayoutManager(shabi);

        newsList = new NewsList();
        ArrayList<String> kongyan = new ArrayList<String>();
        kongyan.add("SB");
        kongyan.add("caixukun");
        kongyan.add("nimasile");
        kongyan.add("Rnimad");
        kongyan.add("?????");
        kongyan.add("sdofabo");
        kongyan.add("cbhtql");

        recyclerView.setAdapter(newsAdapter = new NewsAdapter(kongyan));

        return root;
    }
}


