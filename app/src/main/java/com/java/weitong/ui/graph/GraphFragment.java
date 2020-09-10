package com.java.weitong.ui.graph;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.java.weitong.R;
import com.java.weitong.db.News;
import com.java.weitong.db.NewsList;
import com.java.weitong.db.PicFetcher;
import com.java.weitong.ui.news.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphFragment extends Fragment implements ToGraphFragment {
    private TextView entity_name;
    private TextView entity_info;
    private ImageView entity_image;
    private ArrayList<String> prop;
    private ArrayList<String> rel_name;
    private ArrayList<String> rel_text;
    private SearchView searchView;

    private PropertyAdapter propertyAdapter;
    private LinearLayoutManager pp;

    private RelationAdapter relationAdapter;
    private LinearLayoutManager rl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_graph, container, false);

        entity_name = root.findViewById(R.id.entity_name);
        entity_image = root.findViewById(R.id.entity_image);
        entity_info = root.findViewById(R.id.entity_info);
        prop = new ArrayList<>();
        rel_name = new ArrayList<>();
        rel_text = new ArrayList<>();

        searchView = (android.support.v7.widget.SearchView) root.findViewById(R.id.graph_search);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("请输入要搜索的实体...");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setEntity(root, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.clearFocus();

        final RecyclerView property_recycler = root.findViewById(R.id.property_recycler);
        pp = new LinearLayoutManager(getContext());
        property_recycler.setLayoutManager(pp);
        final RecyclerView relation_recycler = root.findViewById(R.id.relation_recycler);
        rl = new LinearLayoutManager(getContext());
        relation_recycler.setLayoutManager(rl);

        setEntity(root, "病毒");

        property_recycler.setAdapter(propertyAdapter = new PropertyAdapter(prop));
        relation_recycler.setAdapter(relationAdapter = new RelationAdapter(rel_name, rel_text, this));

        return root;
    }

    public void setEntity(View root, String name) {
        prop.clear();
        rel_text.clear();
        rel_name.clear();
        EntityFetcher f = new EntityFetcher(name);
        synchronized (EntityFetcher.class) {
            try {
                Thread t = new Thread(f);
                t.start();
                EntityFetcher.class.wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        EntityData d = f.data;
        entity_name.setText(d.name);
        entity_image.setImageBitmap(d.pic);
        entity_info.setText(d.info);
        prop.addAll(d.properties);
        rel_text.addAll(d.relations_text);
        rel_name.addAll(d.relations_name);
        if (propertyAdapter != null)
            propertyAdapter.notifyDataSetChanged();
        if (relationAdapter != null)
            relationAdapter.notifyDataSetChanged();
    }
}

class EntityData {
    String name;
    Bitmap pic;
    String info;
    ArrayList<String> properties;
    ArrayList<String> relations_text;
    ArrayList<String> relations_name;
}

class EntityFetcher implements Runnable {
    public EntityData data;
    private String name;

    EntityFetcher(String name) {
        data = new EntityData();
        this.name = name;
    }

    private void parseJson(JSONObject obj) {
        try {
            JSONArray arr = obj.getJSONArray("data");
            if (arr.length() == 0)
                return;
            JSONObject item = arr.getJSONObject(0);
            data.name = name;
            PicFetcher fetcher = new PicFetcher(item.getString("img"));
            Thread t = new Thread(fetcher);
            t.start();
            t.join();
            data.pic = fetcher.bitmap;
            JSONObject info = item.getJSONObject("abstractInfo");
            String en_wiki = info.getString("enwiki");
            String baidu = info.getString("baidu");
            String zh_wiki = info.getString("zhwiki");
            if (!en_wiki.equals(""))
                data.info = en_wiki;
            else if (!baidu.equals(""))
                data.info = baidu;
            else
                data.info = zh_wiki;
            info = info.getJSONObject("COVID");
            item = info.getJSONObject("properties");
            data.properties = new ArrayList<>();
            data.relations_name = new ArrayList<>();
            data.relations_text = new ArrayList<>();
            Iterator<String> it = item.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = item.optString(key);
                data.properties.add(key + " : " + value);
            }
            arr = info.getJSONArray("relations");
            for (int j = 0; j < arr.length(); j ++) {
                JSONObject tmp = arr.getJSONObject(j);
                boolean forward = tmp.getBoolean("forward");
                String rel = tmp.getString("relation");
                String label = tmp.getString("label");
                if (forward)
                    data.relations_text.add(name + "   --" + rel + "->   " + label);
                else
                    data.relations_text.add(name + "   <-" + rel + "--   " + label);
                data.relations_name.add(label);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        synchronized (EntityFetcher.class) {
            try {
                URL url = new URL("https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=" + name);
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
                EntityFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*
class PicFetcher implements Runnable {
    public Bitmap bitmap;
    private String picurl;

    PicFetcher(String url) {
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

 */