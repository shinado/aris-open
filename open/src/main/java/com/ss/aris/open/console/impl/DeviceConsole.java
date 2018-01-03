package com.ss.aris.open.console.impl;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.widget.TextView;
import java.util.Collection;
import com.ss.aris.open.console.Console;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;

public interface DeviceConsole extends Console {

    Typeface getTypeface();

    void onSystemReady();

    void displayResult(Collection<Pipe> pipe, Instruction instruction, int selection);

    void onEnter(Pipe pipe);

    void onSelected(Pipe pipe);

    void onNothing();

    TextView getDisplayTextView();

    @TargetApi(5)
    void requestPermission(String[] permissions, PermissionCallback callback);

    @TargetApi(1188)
    void requestResult(Intent intent, ResultCallback callback);

    @TargetApi(1188)
    interface ResultCallback{
        void onActivityResult(int resultCode, Intent intent);
    }

}
