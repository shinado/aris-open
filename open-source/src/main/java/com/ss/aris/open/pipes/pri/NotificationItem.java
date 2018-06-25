package com.ss.aris.open.pipes.pri;

import android.content.Intent;
import com.ss.aris.open.pipes.impl.ShareIntent;

public class NotificationItem {

    public int pid = 0;
    public String title = "";
    public String content = "";
    public String image = "";
    public ShareIntent intent;

    public NotificationItem(String title, String content, String url) {
        this.title = title;
        this.content = content;

        intent = new ShareIntent(Intent.ACTION_VIEW);
        intent.data = url;
        intent.type = "text/plain";
        intent.extras.put(Intent.EXTRA_TEXT, url);
    }

    public NotificationItem() {
    }

}