package com.java.weitong.ui.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.R;
import com.java.weitong.db.News;
import com.java.weitong.ui.news.NewsLoadActivity;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> menuList;
    private Context context;
    private View view;

    public DataAdapter(ArrayList<String> list) {
        menuList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardview;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_data);
            cardview = (CardView) view.findViewById(R.id.data_card);
            cnt++;
            Log.d("Data: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_data, null, false);
        Log.d("Data", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, final int index) {
        final String reg = menuList.get(index);
        viewholder.textView.setText(reg);
        context = view.getContext();
        viewholder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DataDisplayActivity.class);
                intent.putExtra("region", "China");
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

}
