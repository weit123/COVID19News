package com.java.weitong.ui.news;

import com.java.weitong.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.db.News;

import java.util.List;
import java.util.*;

import android.support.v7.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> newsArray;
    public NewsAdapter(ArrayList<News> list) {
        newsArray = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_news);
            cnt++;
            Log.d("NewsAdapter: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news, null, false);
        Log.d("NewsAdapter", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int index) {
        News curNews = newsArray.get(index);
        viewholder.textView.setText(curNews.getTitle());
        Log.d("NewsAdapter", "Adhere the data" + curNews.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d("NewsAdapter:", "Get Size: " + newsArray.size());
        return newsArray.size();
    }

}
