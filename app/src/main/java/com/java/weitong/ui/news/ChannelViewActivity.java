package com.java.weitong.ui.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cheng.channel.Channel;
import com.cheng.channel.ChannelView;

import java.util.ArrayList;
import java.util.List;

import com.java.weitong.R;

public class ChannelViewActivity extends AppCompatActivity implements ChannelView.OnChannelListener {
    private String TAG = getClass().getSimpleName();
    private ChannelView channelView;
    public static NewsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);

        channelView = findViewById(R.id.channelView);
        init();
    }

    private void init() {
        String[] myChannel = {"新闻", "论文"};
        String[] otherChannel = {};

        List<Channel> myChannelList = new ArrayList<>();
        List<Channel> otherChannelList = new ArrayList<>();

        for (int i = 0; i < myChannel.length; i++) {
            String aMyChannel = myChannel[i];
            Channel channel = new Channel(aMyChannel, (Object) i);
            myChannelList.add(channel);
        }

        channelView.setChannelFixedCount(0);
        channelView.addPlate("我的频道", myChannelList);
        channelView.addPlate("其他频道", otherChannelList);
        channelView.inflateData();
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelEditBackground(R.drawable.bg_channel_edit);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);
    }


    @Override
    public void channelItemClick(int position, Channel channel) { }

    @Override
    public void channelEditFinish(List<Channel> channelList) {
        ArrayList<String> ret = new ArrayList<>();
        for (Channel channel : channelList) {
            ret.add(channel.getChannelName());
        }
        fragment.updateNews(ret);
    }

    @Override
    public void channelEditStart() {

    }
}

interface updateHelper {
    void updateNews(ArrayList<String> types);
}