package com.ss.aris.open.w.impl;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import java.util.Collection;
import com.ss.aris.open.w.Console;
import com.ss.aris.open.pipes.entity.Pipe;

public interface DeviceConsole extends Console {

    Typeface getTypeface();

    void onSystemReady();

    void displayResult(Collection<Pipe> pipe, int selection);

    void onEnter(Pipe pipe);

    void onSelected(Pipe pipe);

    void onNothing();

    @TargetApi(5)
    void requestPermission(String[] permissions, PermissionCallback callback);

}
