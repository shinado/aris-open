package com.ss.aris.open.wallpaper;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.Console;

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

    @TargetVersion(1230)
    interface IConsole{
        void setConsole(Console console);
    }

}