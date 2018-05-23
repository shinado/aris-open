package com.ss.aris.open.results;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ss.aris.open.view.BaseArisView;

public abstract class IResultTextView extends BaseArisView{

    public enum Type {
        NONE,
        INPUT,
        OUTPUT,
        BOTH
    }

    protected Context context;

    public IResultTextView(Context context, ViewGroup parent) {
        this.context = context;
    }

    public abstract void setTypeface(Typeface typeface);
    public abstract void setText(CharSequence text);
    public abstract void setTextColor(int color);
    public abstract void setup(int color, boolean solid, Type type);

    public View getView(){
        return getView(null, "");
    }

}