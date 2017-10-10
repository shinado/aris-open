package com.ss.aris.open.console.functionality;


public interface IText {
    void setInitText();
    void selectTextColor(OnTextColorSelectListener listener);
    void selectTextSize(OnTextSizeSetListener listener);
    void setTextColor(int color);
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
