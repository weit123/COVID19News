package com.java.weitong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.java.weitong.db.PicFetcher;
import com.java.weitong.db.ScholarData;
import com.java.weitong.db.ScholarFetcher;
import com.java.weitong.db.ScholarList;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class StartupActivity extends AppCompatActivity implements ScholarFetcher.ToActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        ScholarList list = new ScholarList();

        SugarContext.init(this);
        initData();
    }

    private void initData() {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                startActivity(new Intent(StartupActivity.this, MainActivity.class));
                finish();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    JSONArray array = res.getJSONArray("data");
                    for (int i = 0; i < array.length(); i ++) {
                        JSONObject obj = array.getJSONObject(i);
                        String id = obj.getString("id");
                        boolean is_passed_away = obj.getBoolean("is_passedaway");
                        if (is_passed_away)
                            ScholarList.dead.add(id);
                        else
                            ScholarList.alive.add(id);
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
                        writeBitMap(fetcher.bitmap, fileName);

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
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void writeBitMap(Bitmap pic, String name) {
        try {
            FileOutputStream fos = this.openFileOutput(name, MODE_PRIVATE);
            pic.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("saveBitmap", "Store to " + name);
    }

    public Bitmap loadBitMap(String name) {
        try {
            FileInputStream fis = this.openFileInput(name);
            //获取文件长度
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            return BitmapFactory.decodeByteArray(buffer, 0, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}