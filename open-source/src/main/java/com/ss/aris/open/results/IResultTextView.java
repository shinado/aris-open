package com.ss.aris.open.results;

import android.graphics.Typeface;
import android.view.View;

public interface IResultTextView {

    enum Type {
        NONE,
        INPUT,
        OUTPUT,
        BOTH
    }

    void setTypeface(Typeface typeface);
    void setText(CharSequence text);
    void setTextColor(int color);
    void setup(int color, boolean solid, Type type);
    View getView();

}