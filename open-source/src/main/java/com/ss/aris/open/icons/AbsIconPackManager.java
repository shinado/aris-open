package com.ss.aris.open.icons;

import android.content.ComponentName;
import android.content.Context;

public abstract class AbsIconPackManager {

    protected Context context;
    protected String packageName;
    protected int theme = -1;

    public AbsIconPackManager(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;
    }

    public abstract void load();

    public abstract boolean loadIconForPackage(FolderItemLayout layout, ComponentName componentName);

    public void setThemeColor(int color){
        theme = color;
    }

}
