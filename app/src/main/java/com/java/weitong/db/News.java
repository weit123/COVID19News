package com.java.weitong.db;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

public class News extends SugarRecord implements Serializable {
    String type;
    String _id;
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

    boolean read;

    public News() {}
    public News(String type, String id, String source, String url, String time,
                String title, String content, String seg, String pdf,
                String authors, String doi, String year) {
        this.type = type;
        this._id = id;
        this.source = source;
        this.url = url;
        this.time = time;
        this.title = title;
        this.content = content;
        this.keywords = "";
        if (seg != null) {
            String[] words = seg.split(" ");
            for (String word : words)
                keywords = keywords + " " + word;
        }
        this.pdf = pdf;
        this.authors = authors;
        this.doi = doi;
        this.year = year;
        this.read = false;
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

    public String getAuthors() {
        return this.authors;
    }

    public String getContent() {
        return this.content;
    }

    public String getSource() {
        return this.source;
    }

    public String getType() {
        return this.type;
    }

    public String getPdf() {
        return this.pdf;
    }

    public String getDoi() {
        return this.doi;
    }

    public String getYear() {
        return this.year;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public String getNewsId() {
        return this._id;
    }

    public boolean getRead() {
        return this.read;
    }

    public void readNews() {
        this.read = true;
        save();
    }
}
