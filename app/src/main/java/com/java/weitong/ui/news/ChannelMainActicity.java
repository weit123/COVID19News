package com.java.weitong.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.java.weitong.R;

public class ChannelMainActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_display);
    }

    public void intoChannelView(View view) {
        startActivity(new Intent(this, ChannelViewActivity.class));
    }

    public void intoCustomChannelView(View view) {
        startActivity(new Intent(this, CustomChannelActivity.class));
    }
}