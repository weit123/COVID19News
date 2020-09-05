package com.java.weitong.db;

import android.util.Log;

import org.json.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ListFetcher implements Runnable {
    private JSONObject jsonParam;
    public ArrayList<String> id_list;

    ListFetcher(String type, int index) {
        jsonParam = new JSONObject();
        try {
            jsonParam.put("type", type);
            jsonParam.put("page", index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> parseJson(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                id_list.add(id);
                if (News.find(News.class, "id = ?", id).size() != 0)
                    continue;
                String content = obj.getString("content");
                String time = obj.getString("time");
                String title = obj.getString("title");
                String seg = obj.getString("seg_test");
                String url = obj.getString("url");

                String type = obj.getString("type");
                if (type.equals("news")) {
                    String source = obj.getString("source");
                    News news = new News(type, id, source, url, time, title, content, seg,
                            null, null, null, null);
                    news.save();
                }
                else if (type.equals("paper")) {
                    String doi = obj.getString("doi");
                    String pdf = obj.getString("pdf");
                    String year = obj.getString("year");
                    ArrayList<String> authors = new ArrayList<String>();
                    JSONArray tmp = obj.getJSONArray("authors");
                    for (int j = 0; j < tmp.length(); j ++)
                        authors.add(tmp.getJSONObject(j).getString("name"));
                    News news = new News(type, id, null, url, time, title, content, seg,
                            pdf, authors, doi, year);
                    news.save();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id_list;
    }

    @Override
    public void run() {
        try {
            // sending data
            URL url = new URL("https://covid-dashboard.aminer.cn/api/events/list");
            DataOutputStream output;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();

            output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(jsonParam.toString());
            output.flush();
            output.close();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Log.d("getList", "result succeed");
                BufferedReader br=
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = br.readLine();
                JSONArray res = new JSONArray(response);
                id_list = parseJson(res);
            } else {
                Log.e("getList", String.valueOf(responseCode));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class NewsList {
    public ArrayList<String> getList(String type, int index) {
        ListFetcher fetcher = new ListFetcher(type, index);
        Thread t = new Thread(fetcher);
        t.setDaemon(true);
        t.start();
        return fetcher.id_list;
    }

    public News getNews(String id) {
        return News.find(News.class, "id = ?", id).get(0);
    }
}
