package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import com.java.weitong.db.News;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.*;

import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Text;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> newsArray;
    private Context context;
    private View view;

    public NewsAdapter(ArrayList<News> list) {
        newsArray = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardview;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_news);
            cardview = (CardView) view.findViewById(R.id.news_card);
            cnt++;
            Log.d("NewsAdapter: ", "Load Class VidwHolder: " + cnt);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news, null, false);

        Log.d("NewsAdapter", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int index) {
        final News curNews = newsArray.get(index);
        viewholder.textView.setText(curNews.getTitle());
        final int info = index;
        context = view.getContext();
        viewholder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsLoadActivity.class);
                intent.putExtra("news", curNews);
                context.startActivity(intent);
            }
        } );

        Log.d("NewsAdapter", "Adhere the data" + curNews.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d("NewsAdapter:", "Get Size: " + newsArray.size());
        return newsArray.size();
    }
}
