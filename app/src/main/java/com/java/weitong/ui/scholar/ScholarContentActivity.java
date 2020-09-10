package com.java.weitong.ui.scholar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.weitong.MainActivity;
import com.java.weitong.R;
import com.java.weitong.db.News;
import com.java.weitong.db.ScholarData;
import com.java.weitong.db.ScholarList;
import com.java.weitong.ui.news.ShareActivity;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ScholarContentActivity extends AppCompatActivity {
    private TextView scholar_name;
    private TextView scholar_affpos;
    private ImageView scholar_photo;
    private LinearLayoutManager ac;
    private LinearLayoutManager of;
    private ArrayList<String> academic;
    private ArrayList<String> other_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scholar_content);
        scholar_name = findViewById(R.id.scholar_name);
        scholar_photo = findViewById(R.id.scholar_photo);
        scholar_affpos = findViewById(R.id.scholar_affpos);
        Intent intent = getIntent();
        String id = intent.getStringExtra("scholarId");
        ScholarData scho = ScholarList.getScholar(id);
        scholar_name.setText(scho.getName());
        scholar_photo.setImageBitmap(loadBitMap(scho.getAvatarPath()));
        String affpos = scho.getAffiliation() + "\n" + scho.getPosition();
        scholar_affpos.setText(affpos);

        final RecyclerView academic_recycler = findViewById(R.id.scholar_academic);
//        ac = new LinearLayoutManager(getApplicationContext());
        ac = new GridLayoutManager(getApplicationContext(), 2);
        academic_recycler.setLayoutManager(ac);
        final RecyclerView info_recycler = findViewById(R.id.scholar_info);
        of = new LinearLayoutManager(getApplicationContext());
        info_recycler.setLayoutManager(of);

        academic = new ArrayList<>();
        academic.add("发表数：" + scho.getPubs());
        academic.add("引用数：" + scho.getCitation());
        academic.add("g指数：" + scho.getGindex());
        academic.add("h指数：" + scho.getHindex());
        academic.add("多样性：" + scho.getDiversity());
        academic.add("社会性：" + scho.getSocial());
        academic.add("活跃度：" + scho.getActivity());
        academic.add("学术合作：" + scho.getNewStar());
        academic_recycler.setAdapter(new ScholarInfoAdapter(academic));

        other_info = new ArrayList<>();
        other_info.add("个人简历：" + scho.getBio());
        other_info.add("教育经历：" + scho.getEdu());
        other_info.add("工作经历：" + scho.getWork());
        other_info.add("主页：" + scho.getHomepage());
        other_info.add("电话：" + scho.getPhone());
        other_info.add("邮箱：" + scho.getEmail());
        other_info.add("标签：" + scho.getTags());

        info_recycler.setAdapter(new ScholarInfoAdapter(other_info));

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
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
