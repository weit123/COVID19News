package com.java.weitong.ui.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.R;
import com.java.weitong.db.News;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> menuList;

    public DataAdapter(ArrayList<String> list) {
        menuList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_news);
            cnt++;
            Log.d("Data: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_data, null, false);
        Log.d("Data", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int index) {
        viewholder.textView.setText(menuList.get(index));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

}
