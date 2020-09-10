package com.java.weitong.ui.scholar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.weitong.R;
import com.java.weitong.db.ScholarData;
import com.java.weitong.db.ScholarFetcher;
import com.java.weitong.db.ScholarList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScholarAdapter extends RecyclerView.Adapter<ScholarAdapter.ViewHolder3> {
    private ArrayList<String> scholarIdList;
    private Context context;
    private View view;
    private ScholarFetcher.ToActivity toActivity;

    public ScholarAdapter(ArrayList<String> idList, ScholarFetcher.ToActivity toActivity) {
        this.scholarIdList = idList;
        this.toActivity = toActivity;
    }

    static class ViewHolder3 extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardview;
        public static int cnt = 0;

        public ViewHolder3(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.scholar_info);
            imageView = (ImageView) view.findViewById(R.id.scholar_image);
            cardview = (CardView) view.findViewById(R.id.scholar_card);
            cnt++;
            Log.d("Data: ", "Load Class VidwHolder: " + cnt);
        }
    }

    @Override
    public ViewHolder3 onCreateViewHolder(ViewGroup parent,  int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scholar_item, null, false);
        Log.d("Data", "Fix Layour");
        return new ViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder3 viewholder, final int index) {
        final String scholarId = scholarIdList.get(index);
        ScholarData scholarData = ScholarList.getScholar(scholarId);
        if (viewholder.imageView == null) {
            System.out.println("2333333");
        }
        Bitmap avatar = toActivity.loadBitMap(scholarData.getAvatarPath());
        viewholder.imageView.setImageBitmap(avatar);
        viewholder.textView.setText(scholarData.getName());
        context = view.getContext();
        viewholder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScholarContentActivity.class);
                intent.putExtra("scholarId", scholarId);
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        if (scholarIdList == null) {
            return 0;
        }
        return scholarIdList.size();
    }

}
