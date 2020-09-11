package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.db.*;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import android.view.View;
import android.content.Intent;

import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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
import java.lang.reflect.Field;
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
    private SearchView searchView;
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private int index = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);



        shabi = new GridLayoutManager(getContext(), 2);
        System.out.println(getContext().getClass());

        recyclerView.setLayoutManager(shabi);

        newsList = new NewsList();

        kongyan = newsList.getList("'news'", index);

        recyclerView.setAdapter(newsAdapter = new NewsAdapter(kongyan));

        final RefreshLayout refreshLayout = (RefreshLayout) root.findViewById(R.id.refresh_layout);
        refreshLayout.setPrimaryColorsId(R.color.TsinghuaPurple, android.R.color.white);
        refreshLayout.setDragRate(0.5f);
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

        historyAdapter = new HistoryAdapter(getContext(), R.layout.history_item);

        // SearchView
        searchView = (SearchView) root.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("COVID-19");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> result = NewsList.getSearchResult(query);
                newsAdapter.refreshNews(result);
                if (SearchHistory.find(SearchHistory.class, "word = ?", query).size() == 0) {
                    SearchHistory h = new SearchHistory(query);
                    h.save();
                    historyAdapter.add(query);
                    historyAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                SearchHistory.clearAllHistory();
            }
        });
        setUnderLinetransparent(searchView);
        searchView.clearFocus();

        historyAdapter.setOnHistoryItemClickListener(new HistoryAdapter.OnHistoryItemClickListener() {
            @Override
            public void onHistoryItemClick(String word) {
                Log.e("Word", word);
                searchView.setQuery(word, true);
            }
        });

        List<SearchHistory> historyList = SearchHistory.listAll(SearchHistory.class);
        for(SearchHistory item : historyList) {
            historyAdapter.add(item.getWord());
        }

        // ListView
        listView = root.findViewById(R.id.history_list);


        ArrayList<String> temp = new ArrayList<String>();

        historyAdapter.notifyDataSetChanged();
        listView.setAdapter(historyAdapter);

        return root;
    }

    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


