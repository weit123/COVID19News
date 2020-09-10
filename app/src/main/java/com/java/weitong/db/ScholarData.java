package com.java.weitong.db;

import com.orm.SugarRecord;

import java.util.ArrayList;

public class ScholarData extends SugarRecord {
    String id;
    String name;
    String avatar_url;
    int gindex;
    int hindex;
    int citation;
    double activity;
    double newStar;
    int pubs;
    double social;
    double diversity;
    boolean is_passedaway;
    String affiliation;
    String bio;
    String edu;
    String tags;
    String email;
    String homepage;
    String position;
    String phone;
    String work;

    public ScholarData() {}

    public ScholarData(String id, String name, String avatar_url, int gindex, int hindex, int citation, double activity,
                       double newStar, int pubs, double social, double diversity, boolean is_passedaway,
                        String affiliation, String bio, String edu, String tags, String email, String homepage,
                        String position, String phone, String work) {
        this.id = id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.gindex = gindex;
        this.hindex = hindex;
        this.citation = citation;
        this.activity = activity;
        this.newStar = newStar;
        this.pubs = pubs;
        this.social = social;
        this.diversity = diversity;
        this.is_passedaway = is_passedaway;
        this.affiliation = affiliation;
        this.bio = bio;
        this.edu = edu;
        this.tags = tags;
        this.homepage = homepage;
        this.position = position;
        this.phone = phone;
        this.work = work;
        this.email = email;
    }
}