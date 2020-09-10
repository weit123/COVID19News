package com.java.weitong.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.HttpURLConnection;
import java.net.URL;

public class PicFetcher implements Runnable {
    public Bitmap bitmap;
    private String picurl;

    public PicFetcher(String url) {
        this.picurl = url;
    }

    @Override
    public void run() {
        synchronized (PicFetcher.class) {
            try {
                URL url = new URL(picurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                PicFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}