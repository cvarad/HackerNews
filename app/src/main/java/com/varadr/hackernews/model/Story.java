package com.varadr.hackernews.model;

import java.util.ArrayList;

/**
 * Created by varad on 28/3/16.
 */
public class Story {
    private String by;
    private String title;
    private String url;
    private String type;

    private int id;
    private int score;
    private int descendants;

    private long time;

    private ArrayList<Integer> kids;

    public Story() {
    }

    public String getBy() {
        return by;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public int getDescendants() {
        return descendants;
    }

    public ArrayList<Integer> getKids() {
        return kids;
    }
}
