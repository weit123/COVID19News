package com.java.weitong.ui.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.java.weitong.R;
import com.java.weitong.db.SearchHistory;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<String> {
    private ArrayList<String> historyList;
    private int resourceId;

    public HistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resourceId = resource;

    }

    @Override
    public String getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent) {
        final String word = getItem(position);
        View view;
        HistoryViewHolder historyViewHolder;
        if (converterView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            historyViewHolder = new HistoryViewHolder();
            historyViewHolder.linearLayout = view.findViewById(R.id.history_layout);
            historyViewHolder.textView = view.findViewById(R.id.history_word);
            historyViewHolder.clear = view.findViewById(R.id.history_clear);
            view.setTag(historyViewHolder);
        } else {
            view = converterView;
            historyViewHolder = (HistoryViewHolder) view.getTag();
        }

        historyViewHolder.textView.setText(word);
        historyViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(word);
            }
        });
        historyViewHolder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchHistory.removeItem(word);
                remove(word);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public interface OnHistoryItemClickListener {
        public void onHistoryItemClick(String word);
    }

    OnHistoryItemClickListener itemClickListener;

    public void setOnHistoryItemClickListener(OnHistoryItemClickListener listener) {
        itemClickListener = listener;
    }

    private void onItemClick(String word) {
        if (itemClickListener != null) {
            itemClickListener.onHistoryItemClick(word);
        }
    }

}

class HistoryViewHolder {
    LinearLayout linearLayout;
    TextView textView;
    TextView clear;
}