package com.ss.aris.open.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.widget.IResource;

public interface IFeedView {

    void onCreate(Context context, Console console);

    void setResource(IResource res);

    void onDestroy();

    View onCreateView();
    void onBindView(FeedItem item);

}