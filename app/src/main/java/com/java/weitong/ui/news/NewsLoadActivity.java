package com.java.weitong.ui.news;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.java.weitong.R;
import com.java.weitong.db.News;
import android.view.Menu;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.java.weitong.R;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

public class NewsLoadActivity extends AppCompatActivity {
    private  TextView newsTitle;
    private TextView newsSource;
    private TextView newsContent;
    private TextView newsDate;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        newsTitle = findViewById(R.id.news_title);
        newsContent = findViewById(R.id.news_content);
        newsSource = findViewById(R.id.news_source);
        newsDate = findViewById(R.id.news_time);
        Intent intent = getIntent();
        News curNews = (News)intent.getSerializableExtra("news");
        newsTitle.setText(curNews.getTitle());
        newsContent.setText(curNews.getContent());
        newsDate.setText(curNews.getTime());
        newsSource.setText(curNews.getSource());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case android.R.id.home:
////                finish();
////                break;
////        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.share) {
            //
            Toast.makeText(this, "分享至微博", Toast.LENGTH_LONG).show();
//            Context context = getConte
            Context context = this;
            Intent intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu4) {
            // refresh
        }
        return super.onOptionsItemSelected(item);
    }

}
