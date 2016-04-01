package com.varadr.hackernews.utils;

/**
 * Created by varad on 31/3/16.
 */
public class Utils {
    public static String getDomain(String url) {
        int s = 0, e = 0;

        int i = url.indexOf('/');

        if(url.charAt(i+1) == '/')
            s = i+2;
        else
            e = i;

        if(s != 0)
            e = url.indexOf('/', s);

        if(e == -1)
            e = url.length();

        return url.substring(s, e);
    }
}
