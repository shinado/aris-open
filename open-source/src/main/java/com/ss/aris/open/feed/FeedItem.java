package com.ss.aris.open.feed;

import com.ss.aris.open.pipes.entity.Pipe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedItem {

    private static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
    public long timestamp;
    public String value = "";
    public Pipe pipe = null;
    public CharSequence display = "";

    public CharSequence getDisplayValue(){
        if (display.toString().isEmpty()) return value;
        else return display;
    }

    public FeedItem(String value) {
        this.value = value;
        timestamp = System.currentTimeMillis();
    }

    public String getDefaultFormatedTime(){
        return sdf.format(new Date(timestamp));
    }

}