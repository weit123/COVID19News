package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.db.*;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import android.view.View;
import android.content.Intent;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private LinearLayoutManager shabi;
    private NewsList newsList;
    private ArrayList<String> kongyan;
    private ArrayList<News> xubin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        shabi = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        System.out.println(getContext().getClass());

        recyclerView.setLayoutManager(shabi);

        newsList = new NewsList();
        kongyan = newsList.getList("'news'", 1);
        xubin = new ArrayList<News>();
        for (String item:kongyan) {
            xubin.add(newsList.getNews(item));
        }
        System.out.println(NewsList.getNews(kongyan.get(0)).getTitle());

        recyclerView.setAdapter(newsAdapter = new NewsAdapter(xubin));

        return root;
    }

}


