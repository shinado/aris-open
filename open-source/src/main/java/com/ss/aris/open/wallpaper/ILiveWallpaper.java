package com.ss.aris.open.wallpaper;

public interface ILiveWallpaper {

    void start();
    void stop();

    interface IText{
        void setTextColor(int color);
        void setTextSize(float size);
    }

    interface IBackground{
        void setColor(int color);
    }

}