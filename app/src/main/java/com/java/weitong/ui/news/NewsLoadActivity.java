package com.java.weitong.ui.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.java.weitong.R;
import com.java.weitong.db.News;

import android.content.Intent;


public class NewsLoadActivity extends AppCompatActivity {
    private  TextView newsTitle;
    private TextView newsSource;
    private TextView newsContent;
    private TextView newsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        newsTitle = findViewById(R.id.news_title);
        newsContent = findViewById(R.id.news_content);
        newsSource = findViewById(R.id.news_source);
        newsDate = findViewById(R.id.news_date);
        Intent intent = getIntent();
        News curNews = (News)intent.getSerializableExtra("news");
        newsTitle.setText(curNews.getTitle());
        newsContent.setText(curNews.getContent());
        newsDate.setText(curNews.getTime());
        newsSource.setText(curNews.getSource());
        System.out.println(curNews.getSource());
    }

}
