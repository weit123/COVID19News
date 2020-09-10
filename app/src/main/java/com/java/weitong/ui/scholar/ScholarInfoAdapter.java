package com.java.weitong.ui.scholar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.R;

import java.util.ArrayList;

public class ScholarInfoAdapter extends RecyclerView.Adapter<ScholarInfoAdapter.ViewHolder> {
    private ArrayList<String> infoList;
    private Context context;
    private View view;

    public ScholarInfoAdapter(ArrayList<String> list) {
        this.infoList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardview;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.scholar_card_text);
            cardview = (CardView) view.findViewById(R.id.scholar_card_c);
            cnt++;
            Log.d("Data: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scholar_card, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, final int index) {
        final String p = infoList.get(index);
        viewholder.textView.setText(p);
        context = view.getContext();
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

}
