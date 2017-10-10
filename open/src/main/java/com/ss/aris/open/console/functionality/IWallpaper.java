package com.ss.aris.open.console.functionality;


public interface IWallpaper {
    void selectWallpaper();
    void selectBackgroundColor(OnBackgroundColorSelectListener listener);
    void setBackgroundColor(int color);
    void loadWallpaper(String url);

    interface OnBackgroundColorSelectListener{
        void onBackgroundColorSelected(int color);
    }
}
