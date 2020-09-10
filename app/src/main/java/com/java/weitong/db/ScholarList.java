package com.java.weitong.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScholarList {
    public static ArrayList<String> alive;
    public static ArrayList<String> dead;
    public ScholarList(ScholarFetcher.ToActivity activity) {
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        ScholarFetcher fetcher = new ScholarFetcher(alive, dead, activity);
        synchronized (ScholarFetcher.class) {
            try {
                Thread t = new Thread(fetcher);
                t.start();
                ScholarFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Log.e("alive", alive.toString());
        Log.e("dead", dead.toString());
    }

    public static ScholarData getScholar(String id) {
        List<ScholarData> scho = ScholarData.find(ScholarData.class, "_id = ?", id);
        if (scho.size() == 0) {
            Log.e("getScholar", "NOT FOUND");
            return null;
        }
        return scho.get(0);
    }
}