package com.java.weitong.db;

import java.util.ArrayList;

public class EpidemicData {
    String region;
    String begin;
    ArrayList<Integer> confirmed;
    ArrayList<Integer> cured;
    ArrayList<Integer> dead;

    public EpidemicData(String region) {
        this.region = region;
        this.confirmed = new ArrayList<Integer>();
        this.cured = new ArrayList<Integer>();
        this.dead = new ArrayList<Integer>();
    }

    public String getRegion() {
        return this.region;
    }
    public String getBegin() {
        return this.begin;
    }
    public ArrayList<Integer> getConfirmed() {
        return this.confirmed;
    }
    public ArrayList<Integer> getCured() {
        return this.cured;
    }
    public ArrayList<Integer> getDead() { return this.dead; }

    public void setBegin(String begin) { this.begin = begin; }
    public void addConfirmed(int num) { this.confirmed.add(num); }
    public void addCured(int num) { this.cured.add(num); }
    public void addDead(int num) { this.dead.add(num); }
}