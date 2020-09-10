package com.java.weitong.ui.graph;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.java.weitong.R;
import com.java.weitong.db.EpidemicData;
import com.java.weitong.ui.data.DataAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphFragment extends Fragment {
    private LinearLayout property;
    private LinearLayout relations;
    private TextView entity_name;
    private ImageView entity_image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data, container, false);
        property = root.findViewById(R.id.property_list);
        relations = root.findViewById(R.id.relation_list);
        entity_name = root.findViewById(R.id.entity_name);
        entity_image = root.findViewById(R.id.entity_image);

        return root;
    }

    private void setEntity(String name) {
        //
    }
}

class EntityData {
    String name;
    Bitmap pic;
    String info;
    ArrayList<String> properties;
    ArrayList<String> relations;
}

class EntityFetcher implements Runnable {
    public EntityData data;
    private String name;

    EntityFetcher(String region, String name) {
        data = new EntityData();
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
            data.pic = fetcher.bitmap;
        } catch (JSONException e) {
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
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("请求url失败");
                }
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                PicFetcher.class.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}