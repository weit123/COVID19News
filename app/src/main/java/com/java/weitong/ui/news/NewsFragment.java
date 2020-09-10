package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.db.*;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import android.view.View;
import android.content.Intent;

import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import com.java.weitong.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewsFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private LinearLayoutManager shabi;
    private NewsList newsList;
    private ArrayList<String> kongyan;
    private ArrayList<News> xubin;
    private SwipeRefreshLayout refreshLayout;
    private int index = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
//        refreshLayout = root.findViewById(R.id.refresh_layout);

        shabi = new GridLayoutManager(getContext(), 2);
        System.out.println(getContext().getClass());

        recyclerView.setLayoutManager(shabi);

        newsList = new NewsList();

        kongyan = newsList.getList("'news'", index);


        recyclerView.setAdapter(newsAdapter = new NewsAdapter(kongyan));

        final RefreshLayout refreshLayout = (RefreshLayout) root.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                index = 1;
                kongyan = newsList.getList("'news'", index);
                newsAdapter.refreshNews(kongyan);
                refreshLayout.finishRefresh(1500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                index++;
                kongyan = newsList.getList("'news'", index);
                newsAdapter.updateNews(kongyan);
                refreshLayout.finishLoadMore(1500);
            }
        });


        return root;
    }

}


