package com.java.weitong.db;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class ScholarFetcher implements Runnable {
    private ArrayList<String> alive;
    private ArrayList<String> dead;

    ScholarFetcher(ArrayList<String> alive, ArrayList<String> dead) {
        this.alive = alive;
        this.dead = dead;
    }

    private void parseJson(JSONObject in) {
        try {
            JSONArray array = in.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                boolean is_passed_away = obj.getBoolean("is_passedaway");
                if (is_passed_away)
                    dead.add(id);
                else
                    alive.add(id);
                if (ScholarData.find(ScholarData.class, "id = ?", id).size() != 0)
                    continue;
                String name = obj.getString("name_zh") + obj.getString("name");
                String avatar_url = obj.getString("avatar");
                JSONObject tmp = obj.getJSONObject("indices");
                int gindex = tmp.getInt("gindex");
                int hindex = tmp.getInt("hindex");
                int citation = tmp.getInt("citations");
                double activity = tmp.getDouble("activity");
                double newStar = tmp.getDouble("newStar");
                int pubs = tmp.getInt("pubs");
                double social = tmp.getDouble("sociability");
                double diversity = tmp.getDouble("diversity");
                tmp = obj.getJSONObject("profile");
                String affiliation = tmp.getString("affiliation");
                String bio = tmp.getString("bio");
                String edu = tmp.getString("edu");
                String email = tmp.getString("email");
                String homepage = tmp.getString("homepage");
                String phone = tmp.getString("phone");
                String position = tmp.getString("position");
                String work = tmp.getString("work");
                String tag = "";
                JSONArray arr = obj.getJSONArray("tags");
                for (int j = 0; j < arr.length(); j ++)
                    tag += (String) arr.get(j);

                ScholarData data = new ScholarData(id, name, avatar_url, gindex, hindex,
                        citation, activity, newStar, pubs, social, diversity, is_passed_away,
                        affiliation, bio, edu, tag, email, homepage, position, phone, work);
                data.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (ScholarFetcher.class) {
            try {
                URL url = new URL("https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2");
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
                ScholarFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class ScholarList {
    public static ArrayList<String> alive;
    public static ArrayList<String> dead;
    public ScholarList() {
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        ScholarFetcher fetcher = new ScholarFetcher(alive, dead);
        synchronized (ScholarFetcher.class) {
            try {
                Thread t = new Thread(fetcher);
                t.start();
                ScholarFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static ScholarData getScholar(String id) {
        List<ScholarData> scho = ScholarData.find(ScholarData.class, "id = ?", id);
        if (scho.size() == 0)
            return null;
        return scho.get(0);
    }
}