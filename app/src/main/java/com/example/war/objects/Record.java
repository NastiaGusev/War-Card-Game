package com.example.war.objects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Record implements Comparable<Record> , Serializable {
    private String name = "";
    private String date = "";
    private int score = 0;
    private double lat = 0;
    private double lon = 0;
    private int playerId = 0;

    public Record() { }

    public Record(String name, int id, int score, String date){
        this.name = name;
        this.score = score;
        this.date = date;
        this.playerId = id;
    }

    public Record(String name, int score, double lat, double lon, int playerId) {
        this.name = name;
        this.score = score;
        setDate();
        this.lat = lat;
        this.lon = lon;
        this.playerId = playerId;
    }

    private void setDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");
        date = df.format(Calendar.getInstance().getTime());
    }

    @Override
    public int compareTo(Record record) {
        return this.score > record.score ? 1 : this.score < record.score ? -1 : 0;
    }

    public int getPlayerId() { return playerId; }

    public String getName() {
        return name;
    }

    public int getScore() { return score; }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getDate() {
        return date;
    }

}
