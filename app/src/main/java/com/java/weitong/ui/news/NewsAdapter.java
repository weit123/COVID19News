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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import com.java.weitong.db.News;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.*;

import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Text;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<News> newsArray;
    private Context context;
    private View view;

    private final int TYPE_ITEM=1; // normal view
    private final int TYPE_FOOT=2;  // footer
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    public NewsAdapter(ArrayList<News> list) {
        newsArray = list;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardview;
        public static int cnt = 0;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_news);
            cardview = (CardView) view.findViewById(R.id.news_card);
            cnt++;
            Log.d("NewsAdapter: ", "Load Class VidwHolder: " + cnt);
        }

    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
//            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.foot_tip);
//            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news, null, false);

            Log.d("NewsAdapter", "Fix Layour");
            return new MyViewHolder(view);
        } else if (viewType == TYPE_FOOT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(v);
        }
//        return null;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            final News curNews = newsArray.get(index);
            myViewHolder.textView.setText(curNews.getTitle());
            final int info = index;
            context = view.getContext();
            myViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsLoadActivity.class);
                    intent.putExtra("news", curNews);
                    context.startActivity(intent);
                }
            } );

            Log.d("NewsAdapter", "Adhere the data" + curNews.getTitle());
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.d("NewsAdapter:", "Get Size: " + newsArray.size());
        return newsArray.size();
    }
}
