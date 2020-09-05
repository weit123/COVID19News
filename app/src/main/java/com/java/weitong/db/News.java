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
    ArrayList<String> keywords;


    String source;

    String pdf;
    ArrayList<String> authors;
    String doi;
    String year;

    public News() {}
    public News(String type, String id, String source, String url, String time,
                String title, String content, String seg, String pdf,
                ArrayList<String> authors, String doi, String year) {
        this.type = type;
        this.id = id;
        this.source = source;
        this.url = url;
        this.time = time;
        this.title = title;
        this.content = content;
        keywords = new ArrayList<String>();
        String[] words = seg.split(" ");
        for (String word : words)
            keywords.add(word);
        this.pdf = pdf;
        this.authors = authors;
        this.doi = doi;
        this.year = year;
    }
}
