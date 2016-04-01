package com.varadr.hackernews;

/**
 * Created by varad on 28/3/16.
 *
 * Class to store important Strings/Codes for the app
 */
public class Constants {
    /**
     * Constants related to locations in HackerNews API
     */
    public static final String LOCATION_TOP_STORIES = "topstories/";
    public static final String LOCATION_NEW_STORIES = "newstories/";
    public static final String LOCATION_ITEM = "item/";

    /**
     * Constants for API URLs
     */
    public static final String API_URL = "https://hacker-news.firebaseio.com/v0/";
    public static final String URL_TOP_STORIES = API_URL + LOCATION_TOP_STORIES;
    public static final String URL_NEW_STORIES = API_URL + LOCATION_NEW_STORIES;
    public static final String URL_ITEM = API_URL + LOCATION_ITEM;
}
