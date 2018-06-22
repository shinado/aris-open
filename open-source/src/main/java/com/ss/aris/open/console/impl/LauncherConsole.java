package com.ss.aris.open.console.impl;

import com.ss.aris.open.console.Console;
import com.ss.aris.open.pipes.entity.Pipe;

public interface LauncherConsole extends Console{

    void onSystemReady();

    void displayResults(Pipe...pipe);

    void onEnter(Pipe pipe);

    void onSelected(Pipe pipe);

    void onNothing();

}
