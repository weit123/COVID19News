package com.java.weitong.db;

import com.orm.SugarRecord;

import java.util.ArrayList;

public class News extends SugarRecord {
    String type;
    String id;
    String content;
    String time;
    String title;
    String url;
    String keywords;

    String source;

    String pdf;
    String authors;
    String doi;
    String year;

    public News() {}
    public News(String type, String id, String source, String url, String time,
                String title, String content, String seg, String pdf,
                String authors, String doi, String year) {
        this.type = type;
        this.id = id;
        this.source = source;
        this.url = url;
        this.time = time;
        this.title = title;
        this.content = content;
        this.keywords = "";
        String[] words = seg.split(" ");
        for (String word : words)
            keywords = keywords + " " + word;
        this.pdf = pdf;
        this.authors = authors;
        this.doi = doi;
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTime() {
        return this.time;
    }

    public String getUrl() {
        return this.url;
    }
}
