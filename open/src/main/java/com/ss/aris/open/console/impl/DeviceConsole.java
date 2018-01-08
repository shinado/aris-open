package com.ss.aris.open.console.impl;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.ss.aris.open.TargetVersion;
import com.ss.aris.open.console.InputCallback;
import com.ss.aris.open.pipes.entity.Pipe;

//for geek style console
public interface DeviceConsole extends LauncherConsole{

    Typeface getTypeface();

    TextView getDisplayTextView();

    /**
     * clear console
     */
    void clear();

    /**
     * under this mode, any input will not be received
     * whatever you type will not be displayed
     */
    @TargetVersion(4)
    void blindMode();

    @TargetVersion(4)
    void quitBlind();

    @TargetVersion(4)
    void addInputCallback(InputCallback inputCallback);

    @TargetVersion(4)
    void removeInputCallback(InputCallback inputCallback);

    @TargetVersion(11)
    void setInputType(int inputType);

    @TargetVersion(11)
    int getInputType();

    @TargetVersion(1144)
    void reshowTerminal();

    @TargetVersion(1144)
    void replaceCurrentView(View view);

    void notify(Pipe pipe);

    void notifyByName(String name);

}
