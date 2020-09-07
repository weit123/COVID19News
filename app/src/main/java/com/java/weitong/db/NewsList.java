package com.java.weitong.db;

import android.util.Log;

import org.json.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

class ListFetcher implements Runnable {
    private String type;
    private String index;
    public ArrayList<String> id_list;

    ListFetcher(String type, Integer index) {
        this.type = type;
        this.index = index.toString();
        id_list = new ArrayList<String>();
    }

    private void parseJson(JSONObject in) {
        try {
            JSONArray array = in.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("_id");
                id_list.add(id);
                if (News.find(News.class, "_id = ?", id).size() != 0)
                    continue;
                String content = obj.getString("content");
                String time = obj.getString("time");
                String title = obj.getString("title");
//                String seg = obj.getString("seg_text");
//                Log.e("seg_text", seg);
                String url = obj.getString("urls");

                String type = obj.getString("type");
                if (type.equals("news")) {
                    String source = obj.getString("source");
                    News news = new News(type, id, source, url, time, title, content, null,
                            null, null, null, null);
                    news.save();
                }
                else if (type.equals("paper")) {
                    String doi = obj.getString("doi");
                    String pdf = obj.getString("pdf");
                    String year = obj.getString("year");
                    JSONArray tmp = obj.getJSONArray("authors");
                    String authors = "";
                    for (int j = 0; j < tmp.length(); j ++)
                        authors = authors + " " + tmp.getJSONObject(j).getString("name");
                    News news = new News(type, id, null, url, time, title, content, null,
                            pdf, authors, doi, year);
                    news.save();
                }
//                Log.e("!!!", "Complete!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (ListFetcher.class) {
            try {
                URL url = new URL("https://covid-dashboard.aminer.cn/api/events/list?type=" + type
                        + "&page=" + index);
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
                ListFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class NewsFetcher implements Runnable {
    private String id;
    public News news;

    NewsFetcher(String id) {
        this.id = id;
        news = null;
    }

    private void parseJson(JSONObject obj) {
        try {
            String id = obj.getString("_id");
            String content = obj.getString("content");
            String time = obj.getString("time");
            String title = obj.getString("title");
            String seg = obj.getString("seg_text");
            String url = obj.getString("urls");

            String type = obj.getString("type");
            if (type.equals("news")) {
                String source = obj.getString("source");
                news = new News(type, id, source, url, time, title, content, seg,
                        null, null, null, null);
            }
            else if (type.equals("paper")) {
                String doi = obj.getString("doi");
                String pdf = obj.getString("pdf");
                String year = obj.getString("year");
                JSONArray tmp = obj.getJSONArray("authors");
                String authors = "";
                for (int j = 0; j < tmp.length(); j ++)
                    authors = authors + " " + tmp.getJSONObject(j).getString("name");
                news = new News(type, id, null, url, time, title, content, seg,
                        pdf, authors, doi, year);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (NewsFetcher.class) {
            try {
                URL url = new URL("https://covid-dashboard-api.aminer.cn/event/" + id);
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
                NewsFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class NewsList {
    public ArrayList<String> getList(String type, int index) {
        ListFetcher fetcher = new ListFetcher(type, index);
        synchronized (ListFetcher.class) {
            try {
                Thread t = new Thread(fetcher);
                t.start();
                ListFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return fetcher.id_list;
    }

    public static News getNews(String id) {
        List<News> tmp = News.find(News.class, "_id = ?", id);
        if (tmp.size() != 0) {
            Log.e("findnews", "Found!");
            return tmp.get(0);
        }
        NewsFetcher fetcher = new NewsFetcher(id);
        synchronized (NewsFetcher.class) {
            try {
                Thread t = new Thread(fetcher);
                t.start();
                NewsFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return fetcher.news;
    }
}