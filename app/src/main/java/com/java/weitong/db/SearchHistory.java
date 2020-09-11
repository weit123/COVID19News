package com.java.weitong.db;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;

public class SearchHistory extends SugarRecord {
    String word;

    public SearchHistory() {}
    public SearchHistory(String word) {
        this.word = word;
    }
    public String getWord() {
        return this.word;
    }

    public static void clearAllHistory() {
        SearchHistory.deleteAll(SearchHistory.class);
    }

    public static void removeItem(String word) {
        List<SearchHistory> tmp = SearchHistory.find(SearchHistory.class, "word = ?", word);
        if (tmp.size() != 0)
            SearchHistory.delete(tmp.get(0));
    }
}
