package com.ss.aris.open.console.functionality;


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

    void selectTextSize(OnTextSizeSetListener listener);
    void setTextSize(float textSize);
    void reloadExecutingString();
    void loadFont(String value);

    interface OnTextColorSelectListener{
        void onTextColorSelected(int color);
    }

    interface OnTextSizeSetListener{
        void onTextSizeSet(int size);
    }

}
