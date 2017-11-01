package indi.ss.pipes.zhihu;

import android.content.Intent;
import com.ss.aris.open.pipes.impl.ShareIntent;
import com.ss.aris.open.pipes.pri.FeedItem;

/**
 * {"images":["http:\/\/pic4.zhimg.com\/b5bb0754c6bab0adec4edd1256efbef7_t.jpg"],
 * "type":2,"id":7315220,"title":"第一天上班 超激动，可惜后来被打了脸…"
 */
public class Zhihu {

    public String[] images;
    public int type;
    public long id;
    public String title;

    public FeedItem toFeed(int pid){
        FeedItem item = new FeedItem();
        item.pid = pid;
        item.title = "知乎日报";
        item.content = title;
        item.image = images != null && images.length > 0 ? images[0] : "";
        item.intent = new ShareIntent(Intent.ACTION_VIEW);
        String url = "https://daily.zhihu.com/story/" + id;
        item.intent.data = url;
        item.intent.type = "text/plain";
        item.intent.extras.put(Intent.EXTRA_TEXT, url);
        return item;
    }

}
