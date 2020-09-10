package com.java.weitong.ui.news;

import com.java.weitong.MainActivity;
import com.java.weitong.R;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import com.java.weitong.db.News;
import com.java.weitong.db.NewsList;

import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.*;

import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Text;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<String> newsArray;
    private Context context;
    private View view;

    public NewsAdapter(ArrayList<String> list) {
        newsArray = list;
    }

    public void updateNews(ArrayList<String> list) {
        int position = newsArray.size();
        newsArray.addAll(position, list);
        notifyItemInserted(position);
    }

    public void refreshNews(ArrayList<String> list) {
        newsArray = list;
        notifyDataSetChanged();
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
            Log.d("NewsAdapter: ", "Load Class ViewHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null, false);
        Log.d("NewsAdapter", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewholder, int index) {
        final int pos = index;
        final String curNewsId = newsArray.get(index);
        final News curNews = NewsList.getNews(curNewsId);
        viewholder.textView.setText(index + " " + curNewsId + " " + curNews.getTitle());
        NewsList.readNews(curNewsId);
        boolean read = NewsList.getNews(curNewsId).getRead();

        if (read)
            viewholder.textView.setTextColor(Color.parseColor("#ff0000"));

        context = view.getContext();
        viewholder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( "nima", "FUCK");
                viewholder.textView.setTextColor(Color.parseColor("#00ff00"));
                Intent intent = new Intent(context, NewsLoadActivity.class);
                intent.putExtra("news", curNews);
                NewsList.readNews(curNewsId);

                context.startActivity(intent);
            }
        } );

    }

    @Override
    public int getItemCount() {
//        Log.d("NewsAdapter:", "Get Size: " + newsArray.size());
        return newsArray.size();
    }
}
