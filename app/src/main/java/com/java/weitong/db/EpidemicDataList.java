package com.java.weitong.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class DataFetcher implements Runnable {
    public EpidemicData entry;
    private String region;

    DataFetcher(String region) {
        entry = new EpidemicData(region);
        this.region = region;
    }

    private void parseJson(JSONObject obj) {
        try {
            JSONObject item = obj.getJSONObject(region);
            entry.setBegin(item.getString("begin"));
            JSONArray history = item.getJSONArray("data");
            for (int i = 0; i < history.length(); i ++) {
                JSONArray d = (JSONArray) history.get(i);
                entry.addConfirmed((Integer) d.get(0));
                entry.addCured((Integer) d.get(2));
                entry.addDead((Integer) d.get(3));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (DataFetcher.class) {
            try {
                URL url = new URL("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("请求url失败");
                }

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                JSONObject res = new JSONObject(result.toString());
                parseJson(res);
                DataFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class EpidemicDataList {
    public static EpidemicData getData(String region) {
        DataFetcher fetcher = new DataFetcher(region);
        synchronized (DataFetcher.class) {
            try {
                Thread t = new Thread(fetcher);
                t.start();
                DataFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return fetcher.entry;
    }
}