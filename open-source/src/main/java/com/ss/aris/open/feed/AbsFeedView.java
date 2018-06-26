package com.ss.aris.open.feed;

import android.content.Context;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.widget.IResource;

public abstract class AbsFeedView implements IFeedView{

    protected boolean hasDestroyed = false;
    protected Context context;
    protected Console console;
    protected IResource resource = null;

    @Override
    public void onCreate(Context context, Console console){
        this.context = context;
        this.console = console;
        hasDestroyed = false;
    }

    @Override
    public void setResource(IResource res){
        this.resource = res;
    }

    @Override
    public void onDestroy(){
        hasDestroyed = true;
    }


}