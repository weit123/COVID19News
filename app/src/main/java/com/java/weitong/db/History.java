package com.java.weitong.db;

import com.orm.SugarRecord;

import java.io.Serializable;

public class History extends SugarRecord {
    String word;

    public History() {}
    public History(String word) {
        this.word = word;
    }
}
