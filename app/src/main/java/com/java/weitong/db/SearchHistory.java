package com.java.weitong.db;

import com.orm.SugarRecord;

import java.io.Serializable;

public class SearchHistory extends SugarRecord {
    String word;

    public SearchHistory() {}
    public SearchHistory(String word) {
        this.word = word;
    }
}
