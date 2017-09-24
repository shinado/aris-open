package com.ss.aris.open.console.functionality;


public interface IText {
    void setInitText();
    void selectTextColor();
    void setTextColor(int color);
    void setTextSize(float textSize);
    void reloadExecutingString();
    void loadFont(String value);
}
