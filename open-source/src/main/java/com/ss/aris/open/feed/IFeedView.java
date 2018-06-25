package com.ss.aris.open.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.widget.IResource;

public abstract class IFeedView {

    protected boolean hasDestroyed = false;
    protected Context context;
    protected Console console;
    protected IResource resource = null;

    public void onCreate(Context context, Console console){
        this.context = context;
        this.console = console;
        hasDestroyed = false;
    }

    public void setResource(IResource res){
        this.resource = res;
    }

    public void onDestroy(){
        hasDestroyed = true;
    }

    public abstract View onCreateView();
    public abstract void onBindView(FeedItem item);

}