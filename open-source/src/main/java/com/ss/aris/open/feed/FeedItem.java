package com.ss.aris.open.feed;

import com.ss.aris.open.pipes.entity.Pipe;

public class FeedItem {

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

}