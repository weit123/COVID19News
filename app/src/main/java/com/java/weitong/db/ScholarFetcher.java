package com.java.weitong.db;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ScholarFetcher implements Runnable {
    private ArrayList<String> alive;
    private ArrayList<String> dead;
    private ToActivity toActivity;

    public ScholarFetcher(ArrayList<String> alive, ArrayList<String> dead, ToActivity toActivity) {
        this.alive = alive;
        this.dead = dead;
        this.toActivity = toActivity;
    }

    public interface ToActivity {
        void writeBitMap(Bitmap pic, String name);
        Bitmap loadBitMap(String name);
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
                if (ScholarData.find(ScholarData.class, "_id = ?", id).size() != 0)
                    continue;
                String name;
                if (obj.getString("name_zh").equals(""))
                    name = obj.getString("name");
                else
                    name = obj.getString("name_zh");
                String avatar_url = obj.getString("avatar");
                PicFetcher fetcher = new PicFetcher(avatar_url);
                Thread t = new Thread(fetcher);
                t.start();
                t.join();
                String fileName = id + ".png";
                toActivity.writeBitMap(fetcher.bitmap, fileName);

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
                String bio = "无";
                if (tmp.has("bio"))
                    bio = tmp.getString("bio");
                String edu = "无";
                if (tmp.has("edu"))
                    edu = tmp.getString("edu");
                String email = "无";
                if (tmp.has("email"))
                    email = tmp.getString("email");
                String homepage = "无";
                if (tmp.has("homepage"))
                    homepage = tmp.getString("homepage");
                String phone = "无";
                if (tmp.has("phone"))
                    phone = tmp.getString("phone");
                String position = "无";
                if (tmp.has("position"))
                    position = tmp.getString("position");
                String work = "";
                if (tmp.has("work"))
                    work = tmp.getString("work");
                String tag = "";
                if (obj.has("tags")) {
                    JSONArray arr = obj.getJSONArray("tags");
                    for (int j = 0; j < arr.length(); j++)
                        tag = tag + (String) arr.get(j) + ", ";
                }

                ScholarData data = new ScholarData(id, name, fileName, gindex, hindex,
                        citation, activity, newStar, pubs, social, diversity, is_passed_away,
                        affiliation, bio, edu, tag, email, homepage, position, phone, work);
                data.save();
            }
        } catch (Exception e) {
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
