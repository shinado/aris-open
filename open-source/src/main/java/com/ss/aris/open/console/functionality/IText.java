package com.ss.aris.open.console.functionality;


import android.graphics.Typeface;

public interface IText {

    enum ColorType{
        BASE,
        INIT,
        FEED,
        APP,
        CONTACT,
        PIPE
    }

    void selectTextColor(ColorType type, OnTextColorSelectListener listener);
    void setTextColor(ColorType type, int color);

    void setTextSize(float textSize);
    void setTypeface(Typeface typeface);
    void setResultTypeface(Typeface typeface);
    void reloadExecutingString();

    interface OnTextColorSelectListener{
        void onTextColorSelected(int color);
    }

}
