package com.java.weitong.db;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

import java.util.ArrayList;

public class ScholarData extends SugarRecord {
    String _id;
    String name;
    String avatar_path;
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

    public ScholarData(String id, String name, String avatar_path, int gindex, int hindex, int citation, double activity,
                       double newStar, int pubs, double social, double diversity, boolean is_passedaway,
                        String affiliation, String bio, String edu, String tags, String email, String homepage,
                        String position, String phone, String work) {
        this._id = id;
        this.name = name;
        this.avatar_path = avatar_path;
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

    public String getName() {
        return this.name;
    }
    public String getAffiliation() {
        return this.affiliation;
    }
    public String getPosition() {
        return this.position;
    }
    public String getAvatarPath() { return this.avatar_path; }
    public double getActivity() { return this.activity; }
    public double getDiversity() { return diversity; }
    public double getNewStar() { return newStar; }
    public double getSocial() { return social; }
    public int getCitation() { return citation; }
    public int getGindex() { return gindex; }
    public int getHindex() { return hindex; }
    public int getPubs() { return pubs; }
    public String getBio() { return bio; }
    public String getEdu() { return edu; }
    public String getEmail() { return email; }
    public String getHomepage() { return homepage; }
    public String getPhone() { return phone; }
    public String getTags() { return tags; }
    public String getWork() { return work; }
}