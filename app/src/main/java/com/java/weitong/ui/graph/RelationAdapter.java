package com.java.weitong.ui.graph;

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
import com.java.weitong.ui.data.DataDisplayActivity;

import java.util.ArrayList;

public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.ViewHolder> {
    private ArrayList<String> nameList;
    private ArrayList<String> textList;
    private Context context;
    private View view;
    private ToGraphFragment graphFragment;

    public RelationAdapter(ArrayList<String> name, ArrayList<String> text, ToGraphFragment graphFragment) {
        this.nameList = name;
        this.textList = text;
        this.graphFragment = graphFragment;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardview;
        public static int cnt = 0;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.relation_card_text);
            cardview = (CardView) view.findViewById(R.id.relation_card);
            cnt++;
            Log.d("Data: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relation_card, null, false);
        Log.d("Data", "Fix Layour");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, final int index) {
        final String name = nameList.get(index);
        final String text = textList.get(index);
        viewholder.textView.setText(text);
        context = view.getContext();
        viewholder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphFragment.setEntity(view, name);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
}

interface ToGraphFragment {
    void setEntity(View root, String name);
}
